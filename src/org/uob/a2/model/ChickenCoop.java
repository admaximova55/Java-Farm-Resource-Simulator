package org.uob.a2.model;

import org.uob.a2.engine.Producer;
import org.uob.a2.engine.Context;

public class ChickenCoop extends Producer {

    private static int nextId = 1; //static so all methods share same next id
    private int id;

    public ChickenCoop() {
        
        super("ChickenCoop", ResourceType.EGGS, 10); //String name, ResourceType product, int amount (from producer)

        this.id = nextId; //so id of each class would get the next available id number
        nextId++;//increments id so you can track number of entities that you have
        addCost(ResourceType.DOLLARS, 20);
        
    }//constructor

    
    public int getId() { 
        return id;
    }//getId

    @Override
    public void produce(Context ctx) {
        ctx.state().addResource(product, amount); //increases the number of eggs every tick
    }//produce

    @Override
    public void tick(Context ctx) {
        //every tick calls produce that increases the num resources
        produce(ctx);
    }

    @Override
    public String toCSV() { 
        return getName() + "-" + id + "," + product + "," + amount; //get bc field is protected
    }

    @Override
    public String toString() {
        return getName() + "-" + id + " [" + product + " x" + amount + "]";
    }//toString (producers)

    
}//chickencoop