package org.uob.a2.parser;

import java.util.List;

public class LaunchCommand extends Command { //launch is for consumer to take needed resources to level up

    public LaunchCommand(List<String> words) {
        super(words);
    }

    @Override
    public String execute(org.uob.a2.engine.Context ctx) {

        if (words.size() < 2) { //must have 2 words to know what consumer to activate
            return "You haven't specified what consumer to launch.";
        }

        String consumerName = words.get(1); //isolate the consumers name

        
        return ctx.engine().launch(consumerName); //call the method to execute launch of consumer

        
    }//execute
}//class