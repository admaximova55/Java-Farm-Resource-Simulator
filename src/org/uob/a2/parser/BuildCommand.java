package org.uob.a2.parser;

import java.util.List;

public class BuildCommand extends Command {

    public BuildCommand(List<String> words) {
        super(words); 
    }//constructor

    @Override
    public String execute(org.uob.a2.engine.Context ctx) {
        
        if (words.size() < 2) { //checks if words list has less then 2 words since you need a command word 'build' and then 2nd word for the actual building eg. ChickenCoop
            return "Building type not specified.";
        }

        // here you extract the second word which is in position 1 of list since it specifies the building
        String buildingType = words.get(1);
    
        return  ctx.engine().build(buildingType);
    }//execute
    
}//class