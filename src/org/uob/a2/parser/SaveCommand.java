package org.uob.a2.parser;

import java.util.List;

public class SaveCommand extends Command {

    public SaveCommand(List<String> words) {
        super(words);
    }

    @Override
    public String execute(org.uob.a2.engine.Context ctx) {

        if (words.size() < 2) { //we need a filename to do a save so len less then 2 would be insufficient
            return "You haven't specified file name.";
        }

        String filename = words.get(1); //here we isolate the file name

        
        String result = ctx.engine().save(filename); //NOTE write .save
        return result; 
        
    }//execute
}//class