package org.uob.a2.parser;

import java.util.List;

public class GraphCommand extends Command {

    public GraphCommand(List<String> words) {
        super(words);
    }

    @Override
    public String execute(org.uob.a2.engine.Context ctx) {

        if (words.size() < 2) { //need to know which resource to graph so must be 2 words min
            return "You haven't specified what resource to graph.";
        }

        String resourceName = words.get(1); //whats the resource

        return ctx.engine().graph(resourceName); //graph made based on the resource

        
    }//extends
    
}//class