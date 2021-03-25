import java.util.HashMap;

public class commandWords {
	// A mapping between a command word and the commandWord associated with it 
	private HashMap<String, commandWord> validCommands;
	
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
    }

	public commandWord getCommandWord(String commandWord) {
        commandWord command = validCommands.get(commandWord);
        if(command != null) {
            return command;
        }
        else {
            return command.UNKNOWN;
        }
    }
    
    public boolean isCommand(String aString) {
        return validCommands.containsKey(aString);
    }

    public void showAll() {
        for(String command : validCommands.keySet()) {
            System.out.print(command + " | ");
        }
        System.out.println();
    }
}
