package org.uob.a2.parser;

import java.util.List;

public class TickCommand extends Command {

    public TickCommand(List<String> words) {
        super(words);
    }

    @Override
    public String execute(org.uob.a2.engine.Context ctx) {

        return ctx.engine().nextTick(); //method to call next tick
        
    }//execute

    
}//class