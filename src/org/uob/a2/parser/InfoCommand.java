package org.uob.a2.parser;
import java.util.List;

public class InfoCommand extends Command {

    public InfoCommand(List<String> words) {
        super(words);
    }

    @Override
    public String execute(org.uob.a2.engine.Context ctx) {

        
        if (words.size() < 2) { //because parser only expects min of 1 word and info needs min 2 to identify the entity we must check for a min len of 2 words
            return "Insufficient detail, please specify resources or entities.";
        }

        
        String specifier = words.get(1).toLowerCase();

        if (specifier.equals("resources")) {
            return ctx.state().displayResources();
        }
       
        else if (specifier.equals("entities")) {
             return ctx.state().displayEntities();
        }
    return "Unknown info type: " + specifier; //if the specifier is something other then an entity or resource

    }//execute
}//class