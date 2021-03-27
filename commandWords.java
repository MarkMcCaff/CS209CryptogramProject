import java.util.HashMap;

public class commandWords {
	// A mapping between a command word and the commandWord associated with it 
	private HashMap<String, commandWord> validCommands;
	
    // The constructor creates a new hashmap and then fills it with the strings which correspond to the commandWord
	public commandWords() {
        validCommands = new HashMap<>();
        validCommands.put("guess", commandWord.GUESS);
        validCommands.put("save", commandWord.SAVE);
        validCommands.put("undo", commandWord.UNDO);
        validCommands.put("load", commandWord.LOAD);
        validCommands.put("exit", commandWord.EXIT);
        validCommands.put("help", commandWord.HELP);
        validCommands.put("solution", commandWord.SOLUTION);
        validCommands.put("stats", commandWord.STATS);
        validCommands.put("hint", commandWord.HINT);
        validCommands.put("frequencies", commandWord.FREQ);
        validCommands.put("leaderboard", commandWord.LEADERBOARD);
    }

    // Returns either the command if it's valid and not-null or the UNKNOWN command
	public commandWord getCommandWord(String commandWord) {
        commandWord command = validCommands.get(commandWord);
        if (command != null) {
            return command;
        }
        else {
            return command.UNKNOWN;
        }
    }
    
    // Checks a string is a valid command
    public boolean isCommand(String aString) {
        return validCommands.containsKey(aString);
    }

    // Prints all of the valid commands
    public void showAll() {
        for(String command : validCommands.keySet()) {
            System.out.print(command + " | ");
        }
        System.out.println();
    }
}
