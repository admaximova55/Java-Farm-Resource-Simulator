package org.uob.a2.engine;

import org.uob.a2.model.*;
import java.io.*;
import java.nio.file.*;
import static java.nio.file.StandardOpenOption.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Engine {

    private SimulationState state;
    private int currentTick = 0;
    private boolean quitFlag = false;

    public Engine(SimulationState state){
        this.state = state;
        state.addResource(ResourceType.DOLLARS, 500); //500 dollars is enough to buy at least 1 of each entity to start the simulation
        state.addResource(ResourceType.CREDITS, 1000); //for the ./test.sh since credits in original example also needed to be set at 1000
    }//Engine

//#####################################################################################################
    
    public int getCurrentTick(){
        return currentTick; //just returns the current tick
    }//getCurrentTick

//#####################################################################################################
    
    public String nextTick(){
        currentTick = currentTick + 1; // increments tick to signify going to the next

        Context ctx = new Context(this, state); //create a contect so all tickable objects can access engine and simulation state

        for (Producer producer : state.getProducers()) { //loop though all the producers
            producer.tick(ctx); //make each producer 'tick' so that it updates
        }

        for (Converter converter : state.getConverters()) { //apply same logic for converter and consumer
            converter.tick(ctx);
        }

        for (Consumer consumer : state.getConsumers()) {
            consumer.tick(ctx);
        }

        state.updateHistory(); //must do update histroy on every tick to keep track of resources

         
        return state.displayResources(); //based on the exemplar we output the resource info at every tick
    }//nextTick

//#####################################################################################################
    
    public String save(String filename){

        String output = "";

        output = output + "CurrentTick," + currentTick + "\n"; //ticks come first in saving order

        
        for (ResourceType type : ResourceType.values()) { //this for loop, loops throught all the enum values (the resources)
            int amount = state.getResourceAmount(type); //checks how mush of that resources is currently stored
            output = output + type + "," + amount + "\n"; //format each line for each resource like the example, will use the \n to seperate lines later
        }//for (RESOURCES)

        
        List<Producer> producers = state.getProducers(); //put all prodcuers into a list
        
        for (int i = 0; i < producers.size(); i++) { //here we will loop through the list
            Producer producer = producers.get(i); //get every individual producer
            output = output + producer.toCSV() + "\n"; //add the producers to the output thst well then save in file
        }//for (PRODUCERS)

        
        List<Converter> converters = state.getConverters(); //same format as producers

        for (int i = 0; i < converters.size(); i++) {
            Converter converter = converters.get(i);
            output = output + converter.toCSV() + "\n";
        }//for (CONVERTERS)


        
        List<Consumer> consumers = state.getConsumers(); //same format as producers
        for (int i = 0; i < consumers.size(); i++) {
            Consumer consumer = consumers.get(i);
            output = output + consumer.toCSV() + "\n";
        }//for (CONSUMERS)

        
        try {
            Path file = Paths.get("data/" + filename); //relative path

            OutputStream out = new BufferedOutputStream(Files.newOutputStream(file, CREATE, TRUNCATE_EXISTING)); //creates the file, overwriting it if it already exists to store updated values

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            String[] lines = output.split("\n");//seperates 
            for (String line : lines) { //loops through all the elements of the list
                writer.write(line);
                writer.newLine(); //goes to next line
            }//for

            writer.flush();
            writer.close();//close file
        }
        catch (Exception e) {
            return "No such file." ;
        }


        return "Saving to ... " + filename;
    }//save


//#####################################################################################################

    public String load(String filename){

    String output = "";

    try {  

        Path file = Paths.get("data/" + filename); //relative path

        InputStream input = new BufferedInputStream(Files.newInputStream(file));
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        state.getProducers().clear(); //before writing in the entities from a file you need to reset the list to blank or there will be duplicates 
        state.getConverters().clear();
        state.getConsumers().clear();
        state.clearInventory(); //clear all old resources 

        String line = reader.readLine();

        while (line != null) {

            output = output + line + "\n"; //adds each line to output

            String[] parts = line.split(","); //seperates each line into parts eg. resource and then the resource amount

            
            if (parts[0].equals("CurrentTick")) {
                currentTick = Integer.parseInt(parts[1]); 
            }//if

            else if (parts[0].equals("EGGS") || parts[0].equals("MILK") || parts[0].equals("WHEAT") || parts[0].equals("SUGARCANE") ||
                     parts[0].equals("FLOUR") || parts[0].equals("SUGAR") || parts[0].equals("DOLLARS") || parts[0].equals("PANCAKES") || parts[0].equals("CREDITS") || parts[0].equals("METAL") || parts[0].equals("ORE")) { //adding the original game design resources to pass tests

                ResourceType type = ResourceType.valueOf(parts[0]);
                int amount = Integer.parseInt(parts[1]);

                state.updateResource(type, amount); //here we use .updateResource to overwrite the prev values
            }//else if 


          
            else { 

                String wholeName = parts[0]; //we want to identify what entity         
                String[] nameParts = wholeName.split("-"); //since we split with commas the entity will look like CowBarn-1 which we need to isolate to just CowBarn
                String name = nameParts[0];           

                if (name.equals("ChickenCoop")) { //so if the name matches entitie then we create a new entitie of that type
                    state.addProducer(new ChickenCoop());
                    output = output + "found a chicken coop\n"; } 
                    
                else if (name.equals("CowBarn")) {
                    state.addProducer(new CowBarn());
                    output = output + "found a cow barn\n"; }

                else if (name.equals("WheatField")) {
                    state.addProducer(new WheatField());
                    output = output + "found a wheat field\n"; } 

                else if (name.equals("SugarcaneField")) {
                    state.addProducer(new SugarcaneField());
                    output = output + "found a sugarcane field\n"; } 

                else if (name.equals("Mill")) {
                    state.addConverter(new Mill());
                    output = output + "found a mill\n"; }

                else if (name.equals("SugarRefinery")) {  
                    state.addConverter(new SugarRefinery());
                    output = output + "found a sugar refinery\n"; } 
                    
                else if (name.equals("PancakeMaker")) {  
                    state.addConverter(new PancakeMaker());
                    output = output + "found a pancake maker\n"; }

                else if (name.equals("PancakeHouse")) {
                    state.addConsumer(new PancakeHouse());
                    output = output + "found a pancake house\n"; } 

            }//else


            line = reader.readLine(); // goes to the next line
        }//while


        reader.close();

        output = output + "Loaded from... " + filename;
        return output;

    }//try


    catch (Exception e) { 
        return "No such file.";
    }//catch
        
    }//load 
    
//#####################################################################################################

    public void setQuitFlag(boolean shouldQuit) {
        this.quitFlag = shouldQuit;
    }//setQuitFlag

    public boolean shouldQuit() {
        return quitFlag;
    }// shouldQuit

//#####################################################################################################

    public String launch(String nameWithId) {

    for (Consumer consumer : state.getConsumers()) { //loop through all the consumers that we've already created

            PancakeHouse p = (PancakeHouse) consumer;//since we cant edit consumer.java but we must implement our own methods for this launch we have to cast consumer as pancake house to then use pnacake house methods eg.getLevel
            
        String fullname = p.getName() + "-" + p.getId(); //since we check name-id we need to create this reference for every consumer we loop

        if (fullname.equalsIgnoreCase(nameWithId)) { //comapre the input with existing consumers
            int currentLevel = p.getLevel();   //if we found a match we fetch the current level since reward is based of that

            
            if (currentLevel <= 0) {
                return fullname + " is level 0 therefore cant launch."; //how can you launch a consumer that isnt even upgraded yet
            }//if

            
            int dollarReward = currentLevel * 100; //we give player 100 DOLLARS for every level
            state.addResource(ResourceType.DOLLARS, dollarReward); //add reward into the system
            p.resetLevel();  //since we used the levels we then reset the consumer to level 0

            
            return "Launching... " + fullname + " launch. Levels Reset. " + dollarReward + " DOLLARS gained.";
        }//if
    }//for

    return "No consumer with name " + nameWithId + " found.";
    }//nameWithId
    
//#####################################################################################################

    
    public String build(String buildingType) {

        String building1 = buildingType.toLowerCase(); //format to lowercase
        Entity entity = null;


        if (building1.equals("chickencoop")) {
            entity = new ChickenCoop();
        }
        else if (building1.equals("cowbarn")) {
            entity = new CowBarn();
        }
        else if (building1.equals("wheatfield")) {
            entity = new WheatField();
        }
        else if (building1.equals("sugarcanefield")) {
            entity = new SugarcaneField();
        }
        else if (building1.equals("mill")) {
            entity = new Mill();
        }
        else if (building1.equals("sugarrefinery")) {
            entity = new SugarRefinery();
        }
        else if (building1.equals("pancakemaker")) {
            entity = new PancakeMaker();
        }
        else if (building1.equals("pancakehouse")) {
            entity = new PancakeHouse();
        }
        else {
            return "Unknown building type: " + building1;
        }


        Map<ResourceType, Integer> costMap = entity.getCosts(); //we are using the getCost from Entity.java

        for (ResourceType resource : costMap.keySet()) { //loops through all required costs of chosen entity

            int required = costMap.get(resource); //how much of DOLLARS is needed to build this entity
            int available = state.getResourceAmount(resource); //pulls up how much we ACTUALLY have atm

            if (available < required) { 
                return "Not enough " + resource + " to build " + entity.getName() + "."; //not enough money to build means you just exit out of the method
            }//if
        }//for

        for (ResourceType resource : costMap.keySet()) {
            state.removeResource(resource, costMap.get(resource)); //remove the amount of DOLLARS needed to build entity
        }//for


        if ( building1.equals("chickencoop") || building1.equals("cowbarn") || building1.equals("wheatfield") || building1.equals("sugarcanefield") ) { //based on what type of entity you call with diffrent add methods
            state.addProducer((Producer)entity); //have to cast since entity is def as Entity type
        }
        else if (building1.equals("mill") || building1.equals("sugarrefinery") || building1.equals("pancakemaker")) {
            state.addConverter((Converter)entity); //have to cast since entity is def as Entity type
        }
        else if (building1.equals("pancakehouse")) {
            state.addConsumer((Consumer)entity); //have to cast since entity is def as Entity type
        }

        return "You built a " + entity.getName() + "."; //display 

    }//build  


//#####################################################################################################

    public String graph(String resourceName) {

        String nameUpper = resourceName.toUpperCase(); //cause resources are stored as upper case eg. EGGS 
        ResourceType chosenType = null;

        boolean found = false;
        for (ResourceType type : ResourceType.values()) { //loop through all the resources
            if(nameUpper.equals(type.toString())){ //check if resource exists in simulation
                found = true; 
                chosenType = type; //keep track of the resource
                break;   }
        }//for

        if(!found){ //if resource isnt found in list then we exit the method
        return resourceName + " not found";
        }//if



        List<Map<ResourceType, Integer>> history = state.getHistory(); //since we have a map for every tick we then go and put it into a list. The list then contains all the ticks with each havign its map of resource info

        if (history.isEmpty()) {
            return "No history recorded yet."; //in the case that no ticks have occured yet
        }//if

        String output =  chosenType + ":\n";

        int maxAmount = 0;
        for (int i = 0; i < history.size(); i++) { //we loop through every tick
            Map<ResourceType, Integer> specificTick = history.get(i);
            int amount = 0;
        
            if (specificTick.containsKey(chosenType)) { //since some resources might be empty in some ticks
                amount = specificTick.get(chosenType);
            }//if
        
            if (amount > maxAmount) { //since my graph will be max 50 hashes we need to keep track of the biggest tick amount
                maxAmount = amount;
            }//if
        }//for

        int scale = 1; //for now set scale as minimum 1 resource per hash
        if (maxAmount > 50) { //means we wont be able to represent all resources with scale 1
            scale = (maxAmount / 50); //so if resource is 500 units then each hash will mean 10 units
        }//if

    

        for (int i = 0; i < history.size(); i++) { //we will create a line per tick

            Map<ResourceType, Integer> specificTick = history.get(i);
            int amount1 = 0;
            if (specificTick.containsKey(chosenType)) {
                amount1 = specificTick.get(chosenType); //note the amount for this tick
            }

            int hashCount = amount1 / scale; //we divide by scale to get num hashes for this tick

            String line = ""; 
            for (int j = 0; j < hashCount; j++) { //we need to loop through the num of hashes to add each one
                line = line + "#"; //every loop we add 1 hash
            }

        
            output = output + line + "\n";
        
        }//for

        output = output + "0                                               " + maxAmount; //i added about 48 spaces so you would have the graph of 50 hashes represent 0 to biggest amount 

        return output;

    }//graph

    
//#####################################################################################################
    
    // USED FOR TESTING
    public void initialiseDefaults() {
        //ADD ALL THE ENTITIES YOU CREATE TO THE PRODUCER, CONVERTER AND CONSUMER LISTS IN SIMULATIONSTATE HERE.

        // PRODUCERS
        state.addProducer(new ChickenCoop());
        state.addProducer(new CowBarn());
        state.addProducer(new WheatField());
        state.addProducer(new SugarcaneField());

        // CONVERTERS
        state.addConverter(new Mill());
        state.addConverter(new SugarRefinery());
        state.addConverter(new PancakeMaker());

        // CONSUMERS
        state.addConsumer(new PancakeHouse());
        
    }//initialiseDefaults

    

}//class