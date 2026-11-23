package org.uob.a2;
import org.uob.a2.engine.*;
import org.uob.a2.parser.*;

import java.util.Scanner;

public class Main {


    
    public static void main(String [] args){

        Scanner input = new Scanner(System.in); //scanner for user inputs

        SimulationState state = new SimulationState(); //create the simualtion state
        Engine engine = new Engine(state); //create the engine

        Parser parser = new Parser(); //create parser

        Context ctx = new Context(engine, state); //create context refrencing sim. state and engine for command acess




        
        while (!engine.shouldQuit()) { //in engine should quit is already pre set as false --> !false = true

            System.out.print("[" + engine.getCurrentTick() + "] Enter command> "); //so based on the example it goes [1] Enter Command> w the brackets signifying current tick
            

            String line = input.nextLine(); //we assign user input to a string variable

            
            Command command = parser.parse(line); //parser uses the .parse method to read line then assigns it to command which can then call the diffrent commands
            
            String result = command.execute(ctx); //so this assigns the return values that we have that are often text smt

            if (result != null && !result.isEmpty()) { //since some of the command return are empty we obviusly dont want to print that so we skip it
                System.out.println(result);
            }//if

            
        }//while loop



        
    }//main
    
}//main