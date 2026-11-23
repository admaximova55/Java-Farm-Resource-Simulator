package org.uob.a2.model;

import org.uob.a2.engine.Converter;
import org.uob.a2.engine.Context;

public class PancakeMaker extends Converter { //well use pancake maker to make pancakes which consumer pancakehouse will then consume

    private static int nextId = 1;
    private int id;

    private int eggsNeeded  = 10;//what it will need to convert to pancakes
    private int milkNeeded  = 10;
    private int flourNeeded = 10;
    private int sugarNeeded = 10;
    
    private int pancakesOut = 10;

    public PancakeMaker() {
        super("PancakeMaker", ResourceType.FLOUR, 0, ResourceType.PANCAKES, 0); //through inherittance the program expects 1 resource type in but since we need 4
                                                                                 //ill do it through diffrent system where this child class will be updated to match my games requirements
        this.id = nextId;
        nextId++;

        addCost(ResourceType.DOLLARS, 50);  
    }//PancakeMaker

    public int getId() {
        return id;
    }//getId

    @Override
    public void convert(Context ctx) {
        var state = ctx.state();

        int eggs  = state.getResourceAmount(ResourceType.EGGS);
        int milk  = state.getResourceAmount(ResourceType.MILK);
        int flour = state.getResourceAmount(ResourceType.FLOUR);
        int sugar = state.getResourceAmount(ResourceType.SUGAR);

        
        if (eggs  < eggsNeeded || milk  < milkNeeded || flour < flourNeeded || sugar < sugarNeeded) { //must check that there is enough of teh resources required
            return;  //insufficient amount means we move on without doing anything
        }//if

        //since my game is pancake simulator itll consume these ingredients to 'make' the pancakes w
        state.removeResource(ResourceType.EGGS,  eggsNeeded); //10 eggs
        state.removeResource(ResourceType.MILK,  milkNeeded); //10 milk
        state.removeResource(ResourceType.FLOUR, flourNeeded); //10 flour
        state.removeResource(ResourceType.SUGAR, sugarNeeded); //10 sugar

        state.addResource(ResourceType.PANCAKES, pancakesOut);//adds 10 pancakes to our inventory
    }//convert

    
    @Override
    public void tick(Context ctx) {
        convert(ctx);
    }//tick

    
    
    @Override
    public String toCSV() {
        // use whatever format you want for saving; this is one option:
        return getName() + "-" + id + ",EGGS,"  + eggsNeeded + ",MILK,"  + milkNeeded + ",FLOUR," + flourNeeded + ",SUGAR," + sugarNeeded + ",PANCAKES," + pancakesOut;
    }//toCSV


    
    @Override
    public String toString() {
        return getName() + "-" + id + " [EGGS x"  + eggsNeeded + ", MILK x"  + milkNeeded + ", FLOUR x" + flourNeeded + ", SUGAR x" + sugarNeeded +" --> PANCAKES x" + pancakesOut + "]";
        
    }//toString
}