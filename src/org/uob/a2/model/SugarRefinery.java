package org.uob.a2.model;

import org.uob.a2.engine.Converter;
import org.uob.a2.engine.Context;

public class SugarRefinery extends Converter {

    private static int nextId = 1;
    private int id;

    public SugarRefinery() {
        super("SugarRefinery", ResourceType.SUGARCANE, 10, ResourceType.SUGAR, 5); //String name, ResourceType input, int inputAmount, ResourceType output, int outputAmount 

        this.id = nextId;
        nextId++;

        addCost(ResourceType.DOLLARS, 25);
    }//constructor

    public int getId() {
        return id;
    }//getId

    @Override
    public void convert(Context ctx) { //method from Converter.java

        int currentAmount = ctx.state().getResourceAmount(input); //how much sugarcane is currently available

        if (currentAmount < inputAmount) { //check if enough to refine this tick
            return; //insufficient = return nothing since nothing happens
        }

        ctx.state().removeResource(input, inputAmount);   //takes 10 sugarcane
        ctx.state().addResource(output, outputAmount);    //adds 5 sugar to inventory
    }//convert

    @Override
    public void tick(Context ctx) {
        convert(ctx);
    }//tick

    @Override
    public String toCSV() {
        return getName() + "-" + id + "," + input + "," + inputAmount + "," + output + "," + outputAmount; //get bc field is protected
    }//toCSV

    @Override
    public String toString() {
        return getName() + "-" + id + " [" + input + " x" + inputAmount + " --> " + output + " x" + outputAmount + "]";
    }//toString (converters)

}//SugarRefinery