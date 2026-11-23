package org.uob.a2.parser;

import java.util.List;

public class CheatCommand extends Command {

    public CheatCommand(List<String> words) {
        super(words);
    }

    @Override
    public String execute(org.uob.a2.engine.Context ctx) {
        //cheat will give you a bunch of every resource to speed up the game process

        ctx.state().addResource(org.uob.a2.model.ResourceType.EGGS, 1000); //resources in capital because enum
        ctx.state().addResource(org.uob.a2.model.ResourceType.MILK, 1000);
        ctx.state().addResource(org.uob.a2.model.ResourceType.WHEAT, 1000);
        ctx.state().addResource(org.uob.a2.model.ResourceType.SUGARCANE, 1000);

        //processed resources
        ctx.state().addResource(org.uob.a2.model.ResourceType.FLOUR, 1000);
        ctx.state().addResource(org.uob.a2.model.ResourceType.SUGAR, 1000);

        //currency for spending
        ctx.state().addResource(org.uob.a2.model.ResourceType.DOLLARS, 5000);

        return "CHEAT CODE ACTIVATED.";
    }//execute
}//class