package org.uob.a2.model;

import org.uob.a2.engine.Producer;
import org.uob.a2.engine.Context;

public class CowBarn extends Producer {

    private static int nextId = 1;
    private int id;

    public CowBarn() {
        
        super("CowBarn", ResourceType.MILK, 10); //String name, ResourceType product, int amount (from producer)

        this.id = nextId;
        nextId++;

        addCost(ResourceType.DOLLARS, 20);

    }//constructor

    public int getId() {
        return id;
        
    }//getId

    @Override
    public void produce(Context ctx) {
        ctx.state().addResource(product, amount); 
    }//produce

    @Override
    public void tick(Context ctx) {
        produce(ctx);
        
    }//tick

    @Override
    public String toCSV() {
        return getName() + "-" + id + "," + product + "," + amount; //get bc field is protected
        
    }//toCSV

    @Override
    public String toString() {
        return getName() + "-" + id + " [" + product + " x" + amount + "]";
    }//toString (producers)


    
}//CowBarn