package org.uob.a2.parser;

import java.util.List;

public class QuitCommand extends Command {

    public QuitCommand(List<String> words) {
        super(words);
    }

    @Override
    public String execute(org.uob.a2.engine.Context ctx) {
        
        ctx.engine().setQuitFlag(true);
        return "You called the quit command";
        
    }//execute
}//class