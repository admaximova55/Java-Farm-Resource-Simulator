package org.uob.a2.model;

import org.uob.a2.engine.Producer;
import org.uob.a2.engine.Context;

public class WheatField extends Producer {

    private static int nextId = 1;
    private int id;

    public WheatField() {
        super("WheatField", ResourceType.WHEAT, 10); //String name, ResourceType product, int amount (from producer)

        this.id = nextId;
        nextId++;

        // build costs
        addCost(ResourceType.DOLLARS, 20);
    }//constructor

    public int getId() {
        return id;
    }//getId

    @Override
    public void produce(Context ctx) {
        ctx.state().addResource(product, amount);
    }

    @Override
    public void tick(Context ctx) {
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
    
}//WheatField