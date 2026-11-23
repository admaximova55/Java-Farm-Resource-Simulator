package org.uob.a2.model;

import org.uob.a2.engine.Converter;
import org.uob.a2.engine.Context;

public class Mill extends Converter {

    private static int nextId = 1;
    private int id;


    public Mill() {
        super("Mill", ResourceType.WHEAT, 10, ResourceType.FLOUR, 5);  //String name, ResourceType input, int inputAmount, ResourceType output, int outputAmount 

        this.id = nextId;
        nextId++;

        addCost(ResourceType.DOLLARS, 25);
    }//constructor

    public int getId() {
        return id;
    }//getId

    @Override
    public void convert(Context ctx) { //method from Converter.java

        int currentAmount = ctx.state().getResourceAmount(input); //how much of the needed input resource we currently have

        if (currentAmount < inputAmount) { //checks that you have enough wheat to grind to flour
            return;  //if insufficient eg. you have 0 just returns nothing since you dont do anything if you dont convert
        }

        ctx.state().removeResource(input, inputAmount); //takes in 10 wheat
        ctx.state().addResource(output, outputAmount); //adds 5 flour to the inventory 
        
    }//convert

    @Override
    public void tick(Context ctx) { //method from Tickable.java
        convert(ctx);
    }//tick

    @Override
    public String toCSV() { //method from Entity.java
        return getName() + "-" + id + "," + input + "," + inputAmount + "," + output + "," + outputAmount; //get bc field is protected
    }//toCSV

    @Override
    public String toString() {
        return getName() + "-" + id + " [" + input + " x" + inputAmount + " --> " + output + " x" + outputAmount + "]";
    }//toString (converters)
    
}//Mill