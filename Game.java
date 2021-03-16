import java.io.FileNotFoundException;
import java.util.*;

public class Game {

	//public Player currentPlayer;
	// A boolean is used to loop through the while loop until the player wishes to exit
	static boolean complete = false;
	// This character array stores the current player's answer and updates when they guess or remove a letter
	static char[] playerGuess;
	
	//public Game(Player p, int cryptoType) {
	//}
	
	//public Game(Player p) {
		
	//}
	
	public void getHint() {
		
	}
	
	public void loadPlayer() {
		
	}
	
	public void playGame() {

	}

	// Depending on the player's input, the desired Cryptogram will be generated
	public static void generateCryptogram(Scanner sc, int cryptoType, Player play) {
		if (cryptoType == 1) {
			System.out.println("Alphabetical Cryptogram: ");
			alphabeticalCrypto cryptogram = new alphabeticalCrypto();
			commandList(sc, cryptogram, play);
		}
		else if (cryptoType == 2) {
			System.out.println("Numerical Cryptogram: NOTE a 0 represents empty space!");
			numericalCrypto cryptogram = new numericalCrypto();
			commandList(sc, cryptogram, play);
		}
		// Increments the number of cryptograms a player has played when one is generated 
		play.incremementCryptogramsPlayed();
	}
	
	// Method for guessing a letter within an alphabetical Cryptogram
	public static void enterLetterAlpha(Scanner sc, Cryptogram currCrypto, Player play) { 
	    System.out.println("Choose a letter to replace: ");
	    // Scanner reads the next two separate characters from the input stream
	    char replacer = Character.toUpperCase(sc.next().charAt(0));
		System.out.println("What letter do you think it is? ");
		char guess = Character.toUpperCase(sc.next().charAt(0));
		// The encrypted puzzle is called from the Cryptogram class
	    char[] encryption = currCrypto.getEncryption();
	    // A temporary array is used to compare elements and inputs to ensure there's no automatic overwriting 
	    char[] temp = currCrypto.getPhrase().toUpperCase().toCharArray();
	    // Finds the first instance of the letter being guessed and makes sure it hasn't been guessed already
	    int overwriteLocation = 0;
	    for (int i = 0; i < encryption.length; i++ ) {
	    	if (encryption[i] == replacer) {
	    		overwriteLocation = i;
	    		break;
	    	}
	    }
	    // If it has already been guessed, it asks the player if they want to overwrite it
	    if (playerGuess[overwriteLocation] != 0) {
	    	System.out.println("You have already made a guess for this letter. Overwrite? Y/N: ");
	    	char ans = sc.next().charAt(0);
	    	// If they answer yes, it's overwritten
	    	if (ans == 'Y') {
	    		enterLetterAlphHelper(temp, guess, replacer, encryption, play);
	    	}
	    	// Any other answers won't update it
	    	else {
	    		System.out.println("Your solution has not been overwritten.");
	    	}
	    }
	    else {
	    	enterLetterAlphHelper(temp, guess, replacer, encryption, play);
	    }
	    // If the player's answer matches, their stats are updated and the game ends 
	    int wrong = 0;
	    for (int i = 0; i < playerGuess.length; i++) {
		    if (playerGuess[i] != temp[i]) {
		    	wrong++;
		    }
	    }
	    // If there are no incorrect entries, the player is done
	    if (wrong == 0) {
	    	System.out.println("Congratulations! You got the answer!");	
	    	
	    	play.incremementCryptogramsCompleted();
	    	System.exit(0);
	    }
	    int entries = 0;
	    for (int i = 0; i < playerGuess.length; i++) {
	    	if (playerGuess[i] != 0) {
	    		entries++;
	    	}
	    }
	    if (entries == playerGuess.length) {
	    	System.out.println("This is incorrect. Try overwriting some of your solution");
	    }
	    // The player's current solution is then printed to the screen
	    System.out.print("Encoded Phrase: ");
	    for (int i = 0; i < encryption.length; i++) {
	    	System.out.print(encryption[i]);
	    }
	    System.out.println(" ");
	    System.out.print("Current solution: ");
	    System.out.println(playerGuess);
	}
	
	public static void enterLetterAlphHelper(char[] temp, char guess, char replacer, char[] encryption, Player play) {
	    // Loops through the puzzle and if the character which the user wants to take a guess at exists, 
	    // then the playerGuess variable is updated
		 int location = 0;
		 int replacementNo = 0;
		    for (int i = 0; i < encryption.length; i++ ) {
		    		if (encryption[i] == replacer) {
		    			playerGuess[i] = guess;
		    			location = i;
		    			replacementNo++;
		    		}
		    }
		    // Prints an error if the player tried to replace an absent letter
		    if (replacementNo == 0) {
		    	System.out.println("The letter you tried to replace was not in the puzzle");
		    }
		    // Updates the players stats based on whether the guess was correct or not
		    if (temp[location] == guess) {
		    	play.incrementCorrGuesses();
		    	play.incrementGuesses();
		    }
		    else {
		    	play.incrementGuesses();
		    }
	}
	
	// Method for guessing a number within a numerical Cryptogram
	public static void enterLetterNumber(Scanner sc, Cryptogram currCrypto, Player play) { 
	    System.out.println("Choose a number to replace: ");
	    // Scanner reads the next two inputs from the input stream
	    int replacer = sc.nextInt();
		System.out.println("What letter do you think it is? ");
		char guess = Character.toUpperCase(sc.next().charAt(0));
		// The encrypted puzzle is called from the Cryptogram class
	    int[] encryption = currCrypto.getIntEncryption();
	    // A temporary array is used to compare elements and inputs to ensure there's no automatic overwriting 
	    char[] temp = currCrypto.getPhrase().toUpperCase().toCharArray();
	    // Finds the first instance of the letter being guessed and makes sure it hasn't been guessed already
	    int overwriteLocation = 0;
	    for (int i = 0; i < encryption.length; i++ ) {
	    	if (encryption[i] == replacer) {
	    		overwriteLocation = i;
	    		break;
	    	}
	    }
	    // If it has already been guessed, it asks the player if they want to overwrite it
	    if (playerGuess[overwriteLocation] != 0) {
	    	System.out.println("You have already made a guess for this letter. Overwrite? Y/N: ");
	    	char ans = sc.next().charAt(0);
	    	// If they answer yes, it's overwritten
	    	if (ans == 'Y') {
	    		enterLetterNumHelper(temp, guess, replacer, encryption, play);
	    	}	
	    	// Any other answers won't update it
	    	else {
	    		System.out.println("Your solution has not been overwritten.");
	    	}
	    }
	    else {
	    	enterLetterNumHelper(temp, guess, replacer, encryption, play);
	    }
	    // If the player's answer matches, their stats are updated and the game ends 
	    int wrong = 0;
	    for (int i = 0; i < playerGuess.length; i++) {
		    if (playerGuess[i] != temp[i]) {
		    	wrong++;
		    }
	    }
	    if (wrong == 0) {
	    	System.out.println("Congratulations! You got the answer!");
	    	play.incremementCryptogramsCompleted();
	    	System.exit(0);
	    }
	    int entries = 0;
	    for (int i = 0; i < playerGuess.length; i++) {
	    	if (playerGuess[i] != 0) {
	    		entries++;
	    	}
	    }
	    if (entries == playerGuess.length) {
	    	System.out.println("This is incorrect. Try overwriting some of your solution");
	    }
	    // The player's current solution is then printed to the screen
	    System.out.print("Encoded Phrase: ");
	    for (int i = 0; i < encryption.length; i++) {
	    	System.out.print(encryption[i] + " ");
	    }
	    System.out.println(" ");
	    System.out.print("Current solution: ");
	    System.out.println(playerGuess);
	}
	
	public static void enterLetterNumHelper(char[] temp, char guess, int replacer, int[] encryption, Player play) {
		// Loops through the puzzle and if the number which the user wants to take a guess at exists, 
		// then the playerGuess variable is updated
		int location = 0;
		int replacementNo = 0;
		for (int i = 0; i < encryption.length; i++ ) {
			if (encryption[i] == replacer) {
				playerGuess[i] = guess;
				location = i;
				replacementNo++;
			}
		}
		// Prints an error if the player tried to replace an absent letter
	    if (replacementNo == 0) {
	    	System.out.println("The letter you tried to replace was not in the puzzle");
	    }
		// Updates the players stats based on whether the guess was correct or not
		if (temp[location] == guess) {
			play.incrementCorrGuesses();
		}
		else {
			play.incrementGuesses();
		}
	}
	
	// Method for removing a player's answer from their current solution 
	public static void undoLetter(Scanner sc) { 
	    System.out.println("Choose a letter in your solution you would like to undo: ");
	    // Scanner reads the next character from the input stream
	    char remover = sc.next().charAt(0);
	    // Loops through their solution and if the character which the user wants to take a guess at 
	    // exists, then the character is removed from the playerGuess variable
	    int removed = 0;
	    for (int i = 0; i < playerGuess.length; i++ ) {
	    	if (playerGuess[i] == remover) {
	    		playerGuess[i] = 0;
	    		removed++;
	    	}
	    }
	    if (removed == 0) {
	    	System.out.println("This letter was not included in your solution");
	    }
	    // The player's current solution is then printed to the screen
	    System.out.print("Your current solution is: ");
	    System.out.println(playerGuess);
	}
	
	public void viewFrequencies() {
		
	}
	
	public void saveGame() {
		
	}
	
	public void loadGame() {
		
	}
	
	public void showSolution() {
		
	}
	
	// Method used to start the program
	public static void startup(Scanner sc) {
		System.out.println("Please enter your username: ");
		String name = sc.nextLine();
		Player play = new Player(name);
		System.out.println("--------------------------------------------------------");
		System.out.println("Welcome " + name + ", the following digits correspond to the specified action:");
		System.out.println("1 - alphabetical cryptogram");
		System.out.println("2 - numerical cryptogram");
		System.out.println("--------------------------------------------------------");
		// Scanner reads the next integer and creates the specified Cryptogram 
		System.out.print("which action would you like to carry out? ");  
		int input = sc.nextInt();  
		sc.nextLine();
		if (input == 1 || input == 2) { 
			generateCryptogram(sc, input, play);
		}
		// If the input it read was not of the two digits, it gives an error
		else {
			System.out.println("Invalid Input!");
		}
	}
	
	// Method used to show the player which actions they can carry out 
	public static void commandList(Scanner sc, Cryptogram currCrypto, Player play) {
		System.out.println("--------------------------------------------------------");
		System.out.println("the following digits correspond to the specified action:");
		System.out.println("1 - Guess a letter/number");
		System.out.println("2 - Undo a letter");
		System.out.println("3 - Get a hint");
		System.out.println("4 - Show the solution");
		System.out.println("5 - Save cryptogram");
		System.out.println("6 - Load cryptogram");
		System.out.println("7 - Exit");
		System.out.println("--------------------------------------------------------");
		commandInput(sc, currCrypto, play);
	}
	
	// Method used to deal with the player's input from the commandList method 
	public static void commandInput(Scanner sc, Cryptogram currCrypto, Player play) {
		// determines the length of the playerGuess array from the specific getEncryption method, depending
		// on which Cryptogram object was created and then inputs the spaces into the correct places for
		// the user's guesses
		if (currCrypto instanceof alphabeticalCrypto) {
			playerGuess = new char[currCrypto.getEncryption().length];
			for (int i = 0; i < currCrypto.encryptedPhrase.length; i++ ) {
				if (currCrypto.encryptedPhrase[i] == ' ') {
					playerGuess[i] = ' ';
				}
			}
		}
		else {
		    playerGuess = new char[currCrypto.getIntEncryption().length];
			for (int i = 0; i < currCrypto.intEncryptedPhrase.length; i++ ) {
				if (currCrypto.intEncryptedPhrase[i] == 0) {
					playerGuess[i] = ' ';
				}
			}
		}
		// Loops until the player opts to exit 
		while (!complete) {
			System.out.println("Which action would you like to carry out?");
			int input = sc.nextInt();  
			sc.nextLine();
			// a switch is used to ensure the user's input is valid and also carries out the spcific methods 
			switch (input) {
			case 1: 
				// Carries out the enterLetter method, depending on the type of Cryptogram created 
				if (currCrypto instanceof alphabeticalCrypto) {
					enterLetterAlpha(sc, currCrypto, play);
				}
				else {
					enterLetterNumber(sc, currCrypto, play);
				}
				break;
			case 2:
				// Carries out the undoLetter method - allowing players to remove letters from their solution
				undoLetter(sc);
				break;
			case 7:
				// Exits the program 
				System.out.println("Now exiting...");
				complete = true;
				break;
			default:
				// In future, this will tell the user their input is invalid, however many of the method are 
				// not implemented so this also has to be taken into account 
				System.out.println("This has not been implemented yet or is invalid.");
			}
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(System.in); 
		startup(sc);
		sc.close();
	}	
}
