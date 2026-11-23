package org.uob.a2.parser;

import java.util.List;

public class HelpCommand extends Command {
    public HelpCommand(List<String> words) {
        super(words); 
    }

    @Override
    public String execute(org.uob.a2.engine.Context ctx) {

        return "You called the help command.\n" + "Available commands:\n" + "build <type>\n" + "cheat\n" + "help\n" + "info <resources/entities>\n" + "launch <consumer>\n" + "quit\n" + "tick\n" + "graph <resource>\n" + "save <filename>\n" + "load <filename>\n>";
        //so help command would give you all possible valid commands, I decided to add \n to space it out better and make the UI more digestable
        

    }//execute
}//class