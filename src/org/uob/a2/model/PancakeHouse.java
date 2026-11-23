package org.uob.a2.model;

import org.uob.a2.engine.Consumer;
import org.uob.a2.engine.Context;

public class PancakeHouse extends Consumer {

    private static int nextId = 1;
    private int id;

    private int level = 0;

    public PancakeHouse() {
        super("PancakeHouse", ResourceType.PANCAKES, 10); // 10 pancakes per level
        
        this.id = nextId;
        nextId++;

        addCost(ResourceType.DOLLARS, 100); //100 DOLLARS per pancake house
        
    }//PancakeHouse

    public int getId() {
        return id;
    }//getId

    public int getLevel() {
        return level;
    }//getLevel

    public void resetLevel() {
        level = 0;
    }//resetLevel

    @Override
    public void consume(Context ctx) {
        int available = ctx.state().getResourceAmount(consumedResource); //we pull up how many pancakes we have right now

        
        if (available < amount) { //checks if we have enough pancakes to level up
            return; //insufficient pancakes means we move on without doing anything
        }//if

        
        ctx.state().removeResource(consumedResource, amount); //we take the 10 pancakes
  
        level++;//level up the consumer

        System.out.println("PancakeHouse level is now: " + level);
        
    }//consume

    
    @Override
    public void tick(Context ctx) {
        consume(ctx);
    }//tick

    
    @Override
    public String toCSV() {
        return getName() + "-" + id + "," + consumedResource + "," + amount + ",LEVEL," + level;

    }//toCSV

    
    @Override
    public String toString() {
        return getName() + "-" + id + " [" + consumedResource + " x" + amount + " to level up] -- Current Level: " + level + " --";
    }//toString

    
}//PancakeHouse