package org.uob.a2.parser;

import java.util.List;

public class LoadCommand extends Command {

    public LoadCommand(List<String> words) {
        super(words);
    }

    @Override
    public String execute(org.uob.a2.engine.Context ctx) {

        if (words.size() < 2) { //must specify a filename
            return "You haven't specified file name.";
        }

        String filename = words.get(1); //extract the name of the file
        return ctx.engine().load(filename); 
        
    }//execute
    
}//class