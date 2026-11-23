package org.uob.a2.engine;

import org.uob.a2.model.*;

import java.util.List;
import java.util.ArrayList;

import java.util.EnumMap;
import java.util.Map;

public class SimulationState {

    private List<Producer> producers;
    private List<Converter> converters;
    private List<Consumer> consumers;
    private Map<ResourceType, Integer> inventory = new EnumMap<>(ResourceType.class);
    private List<Map<ResourceType, Integer>> resourceHistory = new ArrayList<>();

    public SimulationState() {
        producers = new ArrayList<>();
        converters = new ArrayList<>();
        consumers = new ArrayList<>();
    }//constructor

    public List<Producer> getProducers() {
        return producers;
    }

    public List<Converter> getConverters() {
        return converters;
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }

    public List<Map<ResourceType, Integer>> getHistory() {
        return resourceHistory;
    }



    public void addResource(ResourceType resource, int amount){
        int current = inventory.getOrDefault(resource, 0); // .getOrDefault is a method for maps it checks the key for its value, but if key doesnt exist eg. we havent made a chicken coop so we have no eggs
                                                                                                                                          //it would make the value 0 to prevent any program breaking
        inventory.put(resource, current + amount); // .put used for maps --> resource is the key and current+amount is the value 
    }//addResource

    
    public void updateResource(ResourceType resource, int amount){
        inventory.put(resource, amount);
    }//updateResource

    
    public int getResourceAmount(ResourceType resource){  //for when you load resources you want to overwrite the old values
        return inventory.getOrDefault(resource, 0);
    }//getResourceAmount

    
    public boolean removeResource(ResourceType resource, int neededAmount){ //for when you use converters or consumers
        int currentAmount = inventory.getOrDefault(resource, 0); //fetch the current amount that we have

        if (currentAmount < neededAmount) { //make sure that we have sufficient resources
            return false;
        }

        inventory.put(resource, currentAmount - neededAmount); //we use .put to update the amount that we have left after use
        return true;
    }//removeResource

    
    public void updateHistory(){ //we use this to keep track of resources after every tick so we can then graph them
        Map<ResourceType, Integer> historyTracker = new EnumMap<>(ResourceType.class); //we make same map as inventory, this one will just keep track of changes in resources over time

        for (ResourceType type : ResourceType.values()) { //loop through all the resources
            int amount = getResourceAmount(type); //for each resource we get the amount 
            historyTracker.put(type, amount); //we put each resource into the tracker
        }

        resourceHistory.add(historyTracker); //we put each tick into the resourceHistory array - itll be a 2d array each tick is array with values inside it for the resources
    }//updateHistory

    
    public void clearInventory() { //need this for load since we want to make sure we dont have resources from prev simulation 
        inventory.clear();
    }//clearInventory




    //METHODS FOR ADDING FROM FILE IN ENGINE.JAVA
    
    public void addProducer(Producer producer) {
        producers.add(producer);
    }//addProducer

    public void addConverter(Converter converter) {
        converters.add(converter);
    }//addConverter

    public void addConsumer(Consumer consumer) {
        consumers.add(consumer);
    }//addConsumer


    //METHODS FOR DISPLAYING FOR INFOCOMMAND.JAVA
    public String displayResources() {

    String output = "";

    for (ResourceType type : ResourceType.values()) { //loop through all resources
        int amount = getResourceAmount(type); //fetch the amount of each 
        output = output + type + ": " + amount + "\n"; 
    }//for

    return output;
        
    }//displayResources


    
    public String displayEntities() {

        String output = "";

        for (Producer producers1 : producers) { //loops producers then converters then consumers so format is corrects
            output = output + producers1.toString() + "\n";
        }

        for (Converter converter1 : converters) {
            output = output + converter1.toString() + "\n";
        }

        for (Consumer consumer1 : consumers) {
            output = output + consumer1.toString() + "\n";
        }

        return output;
    }//displayEntities
    

    
    

    
}//SimulationState