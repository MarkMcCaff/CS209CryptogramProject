public class Command {
	
	private commandWord commandWord;

    public Command(commandWord commandWord) {
        this.commandWord = commandWord;
    }

    public commandWord getCommandWord() {
        return commandWord;
    }

    public boolean isUnknown() {
        return (commandWord == commandWord.UNKNOWN);
    }
}
