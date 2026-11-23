package org.uob.a2.parser;

import java.util.List;

public class InvalidCommand extends Command {

    public InvalidCommand(List<String> words) {
        super(words); //inheritance from command file: this.words = words;
    }

    @Override //overrides the method in Command.java with same name 
    public String execute(org.uob.a2.engine.Context ctx) {
        return "Invalid command inputted."; //this will be output to users
    }//execute
    
}//class