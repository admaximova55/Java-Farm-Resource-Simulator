package org.uob.a2.parser;

import org.uob.a2.*;

import java.util.List;
import java.util.Arrays;


public class Parser { //parser process the user input and calls appropriate command
    
   public Command parse(String input) {

        if (input == null) {  //checking that input isnt empty
            return new InvalidCommand(Arrays.asList("invalid")); //must return "invalid" since words isnt yet defines, cant leave empty to satisfy inheritance
        }//if

        input = input.trim(); //to remove leading and ending spaces

        if (input.equals("")) { //based on the ./test.sh it want empty string to default to a tick so im seperating it from null and making it go to tick command instead
            return new TickCommand(Arrays.asList("tick"));
        }//if

        String[] seperated = input.split(" "); //seperates command into individual words so you can detect what needed command is seperate from the extra info
        List<String> words = Arrays.asList(seperated); //convert array to list because thats whats expected
        String commandWord = seperated[0].toLowerCase(); //we want to check the first word since the structure of the game has you inputting command words first eg. build farm

        if (commandWord.equals("build") || commandWord.equals("b")) { //checks the word and for applicable words also checks for its first letter 
            return new BuildCommand(words); }
            
        else if (commandWord.equals("cheat")) {
            return new CheatCommand(words);  }
            
        else if (commandWord.equals("help")) {
            return new HelpCommand(words); }
            
        else if (commandWord.equals("info") || commandWord.equals("i")) {
            return new InfoCommand(words); }
            
        else if (commandWord.equals("launch")) {
            return new LaunchCommand(words); }
            
        else if (commandWord.equals("quit")) {
            return new QuitCommand(words); }
            
        else if (commandWord.equals("tick") || commandWord.equals("t")) {
            return new TickCommand(words); }
            
        else if (commandWord.equals("graph") || commandWord.equals("g")) {
            return new GraphCommand(words); }
            
        else if (commandWord.equals("save") || commandWord.equals("s")) {
            return new SaveCommand(words); }
            
        else if (commandWord.equals("load") || commandWord.equals("l")) {
            return new LoadCommand(words); }
            
        else {
            return new InvalidCommand(words); } //must return InvalidCommand again to catch any illegal inputs eg. hello, that dont apply to the game 
       
    }//parse
 
}//class