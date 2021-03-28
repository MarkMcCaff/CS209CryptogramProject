import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Game {
	// A boolean is used to loop through the while loop until the player wishes to exit
	static boolean complete = false;
	// This character array stores the current player's answer and updates when they guess or remove a letter
	static char[] playerGuess;
	// Integer for the player to decide which Cryptogram to play
	static int intTypeCrypto = 0;
	// creates the commandWords used for input
	static commandWords commands = new commandWords();

	// Depending on the player's input, the desired Cryptogram will be generated
	public static void generateCryptogram(Scanner sc, int cryptoType, Player play, int use) {
		// Increments the number of cryptograms a player has played when one is generated 
		play.incremementCryptogramsPlayed();
		if (use == 0) {
			// Creates an alphabetical cryptogram
			if (cryptoType == 1) {
				intTypeCrypto = 1;
				System.out.println("Alphabetical Cryptogram: ");
				alphabeticalCrypto cryptogram = new alphabeticalCrypto();
				System.out.println("Welcome! Type 'help' if you need help ");
				// Initiates the playerGuess variable 
				setupPlayerGuess(cryptogram);
				// Starts the input cycle
				commandInput(sc, cryptogram, play, playerGuess);
			}
			// Creates a numerical cryptogram
			else if (cryptoType == 2) {
				intTypeCrypto = 2;
				System.out.println("Numerical Cryptogram: NOTE a 0 represents empty space!");
				numericalCrypto cryptogram = new numericalCrypto();
				System.out.println("Welcome! Type 'help' if you need help");
				// Initiates the playerGuess variable
				setupPlayerGuess(cryptogram);
				// Starts the input cycle
				commandInput(sc, cryptogram, play, playerGuess);
			}
		}
	}
	
    // Checks for letters the player is trying to guess which they've already used
	public static boolean checkForDuplicates(Cryptogram currCrypto, char guess) {
	    // Temporary variable initialised to false 
		boolean dupes = false;
		for (int i = 0; i < playerGuess.length; i++) {
	    	// If it has been found, 'dupes' is set to true
			if (playerGuess[i] == guess) {
	    		dupes = true;
	    		break;
	    	}
	    }
		return dupes;
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
	    // Checks to see if the player has already guessed this letter
	    boolean duplicates = checkForDuplicates(currCrypto, guess);
	    // If not found in playerGuess, it can be added
	    if (!duplicates) {
		    checkForAlphOverwriting(encryption, replacer, sc, temp, guess, play);
		    checkCorrectness(temp, play, 0);
		    for (int i = 0; i < encryption.length; i++) {
		    	System.out.print(encryption[i]);
		    }
		    System.out.println(" ");
		    currSolution();
		    sc.nextLine();
	    }
	    // Otherwise it tells the player they've already used it
	    else {
	    	System.out.println("You have already used this letter in your solution");
	    	sc.nextLine();
	    }
	}
	
	// Checks to see if a letter has to be overwritten
	public static void checkForAlphOverwriting(char[] encryption, char replacer, Scanner sc, char[] temp, char guess, Player play) {
		int overwriteLocation = 0;
	    // Loops through the puzzle and find the first instance of the replacer
	    for (int i = 0; i < encryption.length; i++ ) {
	    	if (encryption[i] == replacer) {
	    		overwriteLocation = i;
	    		break;
	    	}
	    }
	    // If it has already been guessed, it asks the player if they want to overwrite it
	    if (playerGuess[overwriteLocation] != 0) {
	    	System.out.println("You have already made a guess for this letter. Overwrite? Y/N: ");
	    	char ans = Character.toUpperCase(sc.next().charAt(0));
	    	// If they answer yes, it's overwritten
	    	if (ans == 'Y') {
	    		enterLetterAlphHelper(temp, guess, replacer, encryption, play,0);
	    	}
	    	// Any other answers won't update it
	    	else {
	    		System.out.println("Your solution has not been overwritten.");
	    	}
	    }
	    // If there's no need to overwrite it, it can just be added
	    else {
	    	enterLetterAlphHelper(temp, guess, replacer, encryption, play,0);
	    }
	}
	
	// Loops through the puzzle and if the character which the user wants to take a guess at exists, 
    // then the playerGuess variable is updated
	public static void enterLetterAlphHelper(char[] temp, char guess, char replacer, char[] encryption, Player play, int use) {
		// Temporary variables for checking locations and number of character replacements
		int location = 0;
		int replacementNo = 0;
		for (int i = 0; i < encryption.length; i++ ) {
			if (encryption[i] == replacer) {
				playerGuess[i] = guess;
		 		location = i;
		 		replacementNo++;
		 	}
		 }
		 if (use == 0) {
			 // Prints an error if the player tried to replace an absent letter
			 if (replacementNo == 0) {
				 System.out.println("The letter you tried to replace was not in the puzzle");
			 }
			 // Updates the players stats based on whether the guess was correct or not
			 if (temp[location] == guess) {
				 play.incrementCorrGuesses();
			 } else {
				 play.incrementGuesses();
			 }
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
	    boolean duplicates = checkForDuplicates(currCrypto, guess);
	    // If the user hasn't already guessed this letter they can proceed
	    if (!duplicates) {
		    checkForNumOverwriting(encryption, replacer, sc, temp, guess, play);
		    checkCorrectness(temp, play, 0);
		    for (int i = 0; i < encryption.length; i++) {
		    	System.out.print(encryption[i] + " ");
		    }
		    System.out.println(" ");
		    currSolution();
		    sc.nextLine();
	    }
	    // Prints an error if they guessed it already
	    else {
	    	System.out.println("You have already used this letter in your solution");
	    	sc.nextLine();
	    }
	}
	
	// Checks if there's any cases for overwriting in a number cryptogram
	public static void checkForNumOverwriting(int[] encryption, int replacer, Scanner sc, char[] temp, char guess, Player play) {
		int overwriteLocation = 0;
	    for (int i = 0; i < encryption.length; i++ ) {
	    	// Updates the overwriteLocation if the condition is met
	    	if (encryption[i] == replacer) {
	    		overwriteLocation = i;
	    		break;
	    	}
	    }
	    // If it has already been guessed, it asks the player if they want to overwrite it
	    if (playerGuess[overwriteLocation] != 0) {
	    	System.out.println("You have already made a guess for this letter. Overwrite? Y/N: ");
	    	char ans = Character.toUpperCase(sc.next().charAt(0));
	    	// If they answer yes, it's overwritten
	    	if (ans == 'Y') {
	    		enterLetterNumHelper(temp, guess, replacer, encryption, play,0);
	    	}	
	    	// Any other answers won't update it
	    	else {
	    		System.out.println("Your solution has not been overwritten.");
	    	}
	    }
	    // If it hasn't been guessed, it can just skip getting input
	    else {
	    	enterLetterNumHelper(temp, guess, replacer, encryption, play,0);
	    }
	}
	
	// Checks the user's guess against the solution to see if they match
	public static void checkCorrectness(char[] temp, Player play, int use) {
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
	    	play.savePlayers(play);
	    	if (use == 0 | use == 2) {
				System.exit(0);
			}
	    }
	    // If it isn't correct it checks if playerGuess is full
	    int entries = 0;
	    for (int i = 0; i < playerGuess.length; i++) {
	    	if (playerGuess[i] != 0) {
	    		entries++;
	    	}
	    }
	    // If full, it tells the user they're wrong
	    if (entries == playerGuess.length) {
	    	System.out.println("This is incorrect. Try overwriting some of your solution");
	    }
	    // The player's current solution is then printed to the screen
		if(use != 2) {
			System.out.print("Encoded Phrase: ");
		}
	}
	
	// Loops through the puzzle and if the number which the user wants to take a guess at exists, 
	// then the playerGuess variable is updated
	public static void enterLetterNumHelper(char[] temp, char guess, int replacer, int[] encryption, Player play, int use) {
		// Temporary variables for checking locations and number of character replacements
		int location = 0;
		int replacementNo = 0;
		// If the letter is in the puzzle, replacementNo is updates as well as the location
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
	    char remover = Character.toUpperCase(sc.next().charAt(0));
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
	    sc.nextLine();
	    currSolution();
	}
	
	public static void currSolution() {
		// The player's current solution is then printed to the screen
	    System.out.print("Your current solution is: ");
	    System.out.println(playerGuess);
	    System.out.println(" ");
	}
	
	// Method used to start the program
	public static void startup(Scanner sc) {
		System.out.print("Please enter your username: ");
		String name = sc.next();
		Player play = new Player(name);
		play.addPlayer(play);
		System.out.println("--------------------------------------------------------");
		System.out.println("Welcome " + name + ", the following digits correspond to the specified action:");
		System.out.println("1 - alphabetical cryptogram");
		System.out.println("2 - numerical cryptogram");
		System.out.println("--------------------------------------------------------");
		// Scanner reads the next integer and creates the specified Cryptogram 
		System.out.print("Which cryptogram would you like to create? ");
		int input = sc.nextInt();  
		sc.nextLine();
		if (input == 1 || input == 2) { 
			generateCryptogram(sc, input, play,0);
		}
		// If the input it read was not of the two digits, it gives an error
		else {
			System.out.println("Invalid Input!");
		}
	}
	
	// Method used to deal with the player's input from the commandList method 
	public static void commandInput(Scanner sc, Cryptogram currCrypto, Player play, char[] playerGuess) {
		// Loops until the player opts to exit 
		while (!complete) {
			System.out.print("> ");
			String input = sc.nextLine();
			commandWord command = commands.getCommandWord(input);
			// a switch is used to ensure the user's input is valid and also carries out the specific methods 
			switch (command) {
				case UNKNOWN:
					System.out.println("I don't know what you mean...");
	                break;
				case GUESS: 
					// Carries out the enterLetter method, depending on the type of Cryptogram created 
					if (intTypeCrypto == 1) {
						enterLetterAlpha(sc, currCrypto, play);
					}
					else {
						enterLetterNumber(sc, currCrypto, play);
					}
					break;
				case UNDO:
					undoLetter(sc);
					break;
				case HINT:
					giveHint(currCrypto, play);
					break;
				case SAVE:
					saveGame(currCrypto, play, sc);
					break;
				case LOAD:
					loadGame(currCrypto, play);
					break;
				case EXIT:
					// updates the player file before exiting
					play.savePlayers(play);
					System.out.println("Now exiting...");
					complete = true;
					break;
				case HELP:
					printHelp();
					break;
				case SOLUTION:
					showSolution(currCrypto);
					break;
				case STATS:
					showStatOptions(play, sc);
					break;
				case FREQ:
					printFrequencies(currCrypto);
					break;
				case LEADERBOARD:
					showLeaderboard();
					break;
			}
		}
	}
	
	// Prints out all of the command words
	private static void printHelp() {
		System.out.println("Your command words are:");
		commands.showAll();
	}

	// determines the length of the playerGuess array from the specific getEncryption method, depending
	// on which Cryptogram object was created and then inputs the spaces into the correct places for
	// the user's guesses
	public static void setupPlayerGuess(Cryptogram currCrypto) {
		// If an alphabetical cryptogram, then it checks for an empty space and puts this into the player Guess
		if (currCrypto instanceof alphabeticalCrypto) {
			playerGuess = new char[currCrypto.getEncryption().length];
			for (int i = 0; i < currCrypto.encryptedPhrase.length; i++ ) {
				if (currCrypto.encryptedPhrase[i] == ' ') {
					playerGuess[i] = ' ';
				}
			}
		}
		// Otherwise if a numerical cryptogram, it fills the encrypted puzzle with 0's
		else {
		    playerGuess = new char[currCrypto.getIntEncryption().length];
			for (int i = 0; i < currCrypto.intEncryptedPhrase.length; i++) {
				if (currCrypto.intEncryptedPhrase[i] == 0) {
					playerGuess[i] = ' ';
				}
			}
		}
	}

	// Gives the player a hint
	public static void giveHint(Cryptogram currCrypto,Player play) {
		// Checks which kind of cryptogram is currently being used
		if (intTypeCrypto == 1) {
			giveLetterHint(currCrypto,play);
		} 
		else {
			giveNumberHint(currCrypto,play);
		}
	}

	public static void giveLetterHint(Cryptogram currCrypto, Player play) {
		boolean hintApplied = false;
		char[] charSolution = currCrypto.phrase.toCharArray();
		char[] encryption = currCrypto.getEncryption();
		char[] temp = currCrypto.getPhrase().toUpperCase().toCharArray();
		while(!hintApplied) {
			//chooses a random number within the length of the phrase in play to attempt to provide a hint for
			int rndm = new Random().nextInt(currCrypto.encryptedPhrase.length);
			//checks whether the random element of the guess has already been entered into by the user
			if (playerGuess[rndm] != 0) {
				//checks whether the guess entered by the user is correct or not
				if (playerGuess[rndm] != charSolution[rndm]) {
					//enters the letter into the user's guess
					char replacer = Character.toUpperCase(currCrypto.encryptedPhrase[rndm]);
					char guess = Character.toUpperCase(charSolution[rndm]);
					enterLetterAlphHelper(temp,guess,replacer,encryption,play,1);
					System.out.println("An incorrect guess has been corrected to give you a hint.");
					currSolution();
					checkCorrectness(temp, play, 2);
					hintApplied = true;
				}
			} else {
				//enters the letter into the user's guess
				char replacer = Character.toUpperCase(currCrypto.encryptedPhrase[rndm]);
				char guess = Character.toUpperCase(charSolution[rndm]);
				enterLetterAlphHelper(temp,guess,replacer,encryption,play,1);
				System.out.println("A letter has been revealed to give you a hint.");
				currSolution();
				checkCorrectness(temp, play, 2);
				hintApplied = true;
			}
		}
	}

	public static void giveNumberHint(Cryptogram currCrypto, Player play) {
		boolean hintApplied = false;
		char[] charSolution = currCrypto.phrase.toCharArray();
		int[] encryption = currCrypto.getIntEncryption();
		char[] temp = currCrypto.getPhrase().toUpperCase().toCharArray();
		while(!hintApplied) {
			//chooses a random number within the length of the phrase in play to attempt to provide a hint for
			int rndm = new Random().nextInt(currCrypto.intEncryptedPhrase.length);
			//checks whether the random element of the guess has already been entered into by the user
			if (playerGuess[rndm] != 0) {
				//checks whether the guess entered by the user is correct or not
				if (playerGuess[rndm] != charSolution[rndm]) {
					//enters the letter into the user's guess
					int replacer = Character.toUpperCase(currCrypto.intEncryptedPhrase[rndm]);
					char guess = Character.toUpperCase(charSolution[rndm]);
					enterLetterNumHelper(temp,guess,replacer,encryption,play,1);
					System.out.println("An incorrect guess has been corrected to give you a hint.");
					currSolution();
					checkCorrectness(temp, play, 2);
					hintApplied = true;
				}
			} else {
				//enters the letter into the user's guess
				int replacer = Character.toUpperCase(currCrypto.intEncryptedPhrase[rndm]);
				char guess = Character.toUpperCase(charSolution[rndm]);
				enterLetterNumHelper(temp,guess,replacer,encryption,play,1);
				System.out.println("A letter has been revealed to give you a hint.");
				currSolution();
				checkCorrectness(temp, play, 2);
				hintApplied = true;
			}
		}
	}

	//Method for saving the user's cryptogram progress
	public static void saveGame(Cryptogram currCrypto, Player play, Scanner sc) {
		//Checks whether the user has any previous saves
		boolean previousSave = previousSaveGame(play);
		//Checks what type the current cryptogram is
		if (currCrypto instanceof alphabeticalCrypto) {
			//If there is a previous save for the user then it asks the user whether they want to overwrite
			if (previousSave) {
				System.out.println("There is already a cryptogram saved under this username.");
				System.out.println("Would you like to overwrite it? Y/N: ");
				char ans = Character.toUpperCase(sc.next().charAt(0));
				// If they answer yes, it's overwritten
				sc.nextLine();
				if (ans == 'Y') {
					//Gets rid of the user's previous saved cryptogram
					overwrite(currCrypto, play);
					//Saves the current cryptogram
					writeToLetterCryptogramFile(currCrypto, play);
					System.out.println("Your cryptogram has been saved.");
				}
				// Any other answers won't update it
				else {
					System.out.println("Your cryptogram has not been saved.");
				}
			}
			else {
				//Saves the current cryptogram
				writeToLetterCryptogramFile(currCrypto, play);
				System.out.println("Your cryptogram has been saved.");
			}
		}
		else {
			if (previousSave) {
				System.out.println("There is already a cryptogram saved under this username.");
				System.out.println("Would you like to overwrite it? Y/N: ");
				char ans = Character.toUpperCase(sc.next().charAt(0));
				// If they answer yes, it's overwritten
				sc.nextLine();
				if (ans == 'Y') {
					overwrite(currCrypto, play);
					writeToNumberCryptogramFile(currCrypto, play);
					System.out.println("Your cryptogram has been saved.");
				}
				// Any other answers won't update it
				else {
					System.out.println("Your cryptogram has not been saved.");
				}
			}
			else {
				writeToNumberCryptogramFile(currCrypto, play);
				System.out.println("Your cryptogram has been saved.");
			}
		}
	}

	// Method for getting rid of user's previous save when they want to save a new cryptogram
	public static void overwrite(Cryptogram currCrypto, Player play) {
		File myObj1 = new File("savedLetterCryptos.txt");
		List<String> words1 = new ArrayList<String>();
		List<String> loadUsername = new ArrayList<String>();
		List<String> loadPuzzle = new ArrayList<String>();
		List<String> loadGuess = new ArrayList<String>();
		List<String> loadSolution = new ArrayList<String>();
		String remove = play.getUsername();
		int removeInt = -1;
		// Inputs data from file
		try (Scanner sc = new Scanner((myObj1), StandardCharsets.UTF_8.name())) {
			while (sc.hasNextLine()) {
				words1.add(sc.nextLine());
			}
		}
		// Catches any exceptions
		catch (IOException e) {
			e.printStackTrace();
		}
		int i = 0;
		//Assigns data from each text file to each player
		for (String line:words1) {
			String[] split = line.split(",");
			loadUsername.add(split[0]);
			loadPuzzle.add(split[1]);
			loadGuess.add(split[2]);
			loadSolution.add(split[3]);
			if (split[0].equals(remove)) {
				removeInt = i;
			}
			i++;
		}
		if (removeInt != -1) {
			loadUsername.remove(removeInt);
			loadPuzzle.remove(removeInt);
			loadGuess.remove(removeInt);
			loadSolution.remove(removeInt);
			try {
				FileWriter fw = new FileWriter("savedLetterCryptos.txt");
				BufferedWriter bw = new BufferedWriter(new FileWriter("savedLetterCryptos.txt", true));
				fw.write("");
				for (int j = 0; j < loadUsername.size(); j++) {
					bw.append(loadUsername.get(j));
					bw.append(",");
					bw.append(loadPuzzle.get(j));
					bw.append(",");
					bw.append(loadGuess.get(j));
					bw.append(",");
					bw.append(loadSolution.get(j));
					bw.newLine();
				}
				bw.close();
				fw.close();
			} 
			catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}
		else {
			File myObj2 = new File("savedNumberCryptos.txt");
			List<String> words2 = new ArrayList<String>();
			List<String> loadIntUsername = new ArrayList<String>();
			ArrayList<String> tempPuzzle = new ArrayList<String>();
			ArrayList<ArrayList<String> > loadIntPuzzle = new ArrayList<ArrayList<String> >();
			List<String> loadIntGuess = new ArrayList<String>();
			List<String> loadIntSolution = new ArrayList<String>();
			try (Scanner sc = new Scanner((myObj2), StandardCharsets.UTF_8.name())) {
				while (sc.hasNextLine()) {
					words2.add(sc.nextLine());
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			i = 0;
			for (String line : words2) {
				String[] split = line.split(",");
				loadIntUsername.add(split[0]);
				loadIntGuess.add(split[1]);
				loadIntSolution.add(split[2]);
				for (int m = 3; m < split.length; m++) {
					tempPuzzle.add(split[m]);
				}
				loadIntPuzzle.add(tempPuzzle);
				if (split[0].equals(remove)) {
					removeInt = i;
				}
				i++;
				if (removeInt != -1) {
					loadIntUsername.remove(removeInt);
					loadIntPuzzle.remove(removeInt);
					loadIntGuess.remove(removeInt);
					loadIntSolution.remove(removeInt);
					try {
						FileWriter fw = new FileWriter("savedNumberCryptos.txt");
						BufferedWriter bw = new BufferedWriter(new FileWriter("savedNumberCryptos.txt", true));
						fw.write("");
						for (int j = 0; j < loadIntUsername.size(); j++) {
							bw.append(loadIntUsername.get(j));
							bw.append(",");
							bw.append(loadIntGuess.get(j));
							bw.append(",");
							bw.append(loadIntSolution.get(j));
							bw.append(",");
							for (int m = 0; i < split.length-3; m++) {
								bw.append(loadIntPuzzle.get(j).get(m));
							}
							bw.newLine();
						}
						bw.close();
						fw.close();
					}
					catch (IOException e) {
						System.out.println("An error occurred.");
						e.printStackTrace();
					}
				}
			}
		}
	}

	// This method checks whether the user has any previous saves
	public static boolean previousSaveGame(Player play) {
		boolean previous = false;
		File myObj1 = new File("savedLetterCryptos.txt");
		List<String> words1 = new ArrayList<String>();
		try (Scanner sc = new Scanner((myObj1), StandardCharsets.UTF_8.name())) {
			while (sc.hasNextLine()) {
				words1.add(sc.nextLine());
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		for (String line : words1) {
			String[] split = line.split(",");
			if (split[0].equals(play.getUsername())) {
				previous = true;
			}
		}
		File myObj2 = new File("savedNumberCryptos.txt");
		List<String> words2 = new ArrayList<String>();
		try(Scanner sc = new Scanner((myObj2), StandardCharsets.UTF_8.name())) {
			while(sc.hasNextLine()) {
				words2.add(sc.nextLine());
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		for(String line : words2) {
			String[] split = line.split(",");
			if (split[0].equals(play.getUsername())) {
				previous = true;
			}
		}
		return previous;
	}

	// This method writes a letter cryptogram to a text file
	public static void writeToLetterCryptogramFile(Cryptogram currCrypto, Player play){
		char[] encryption = currCrypto.getEncryption();
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("savedLetterCryptos.txt", true));
			bw.append(play.getUsername());
			bw.append(",");
			for (int i = 0; i < encryption.length; i++) {
				bw.append(encryption[i]);
			}
			bw.append(",");
			for (int i = 0; i < playerGuess.length; i++) {
				bw.append(String.valueOf(playerGuess[i]));
			}
			bw.append(",");
			bw.append(currCrypto.phrase);
			bw.newLine();
			bw.close();
		}
		catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	// This method writes a number cryptogram to a text file
	public static void writeToNumberCryptogramFile(Cryptogram currCrypto, Player play){
		int[] encryption = currCrypto.getIntEncryption();
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("savedNumberCryptos.txt", true));
			bw.append(play.getUsername());
			bw.append(",");
			for (int i = 0; i < playerGuess.length; i++) {
				bw.append(String.valueOf(playerGuess[i]));
			}
			bw.append(",");
			bw.append(currCrypto.phrase);
			bw.append(",");
			for (int i = 0; i < encryption.length; i++) {
				bw.append(String.valueOf(encryption[i]));
				bw.append(",");
			}
			bw.newLine();
			bw.close();
		}
		catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	// This method loads the user's previous save if it exists
	public static void loadGame(Cryptogram currCrypto, Player play) {
		int letters = readLetterCryptogramsFile(currCrypto, play);
		int numbers = readNumberCryptogramsFile(currCrypto, play);
		if (letters + numbers == 0){
			System.out.println("You have no saved cryptograms to load.");
		}
	}


	// This method read the letter cryptograms file
	public static int readLetterCryptogramsFile(Cryptogram currCrypto, Player play){
		int present = 0;
		File myObj = new File("savedLetterCryptos.txt");
		List<String> words = new ArrayList<String>();
		try(Scanner sc = new Scanner((myObj), StandardCharsets.UTF_8.name())) {
			while(sc.hasNextLine()) {
				words.add(sc.nextLine());
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		for(String line : words){
			String [] split = line.split(",");
			if (split[0].equals(play.getUsername())) {
				intTypeCrypto=1;
				String loadPuzzle = split[1];
				String loadGuess = split[2];
				String loadSolution = split[3];
				char[] ch1 = new char[loadPuzzle.length()];
				for (int i = 0; i < loadPuzzle.length(); i++) {
					ch1[i] = loadPuzzle.charAt(i);
				}
				char[] ch2 = new char[loadGuess.length()];
				for (int i = 0; i < loadGuess.length(); i++) {
					ch2[i] = loadGuess.charAt(i);
				}
				currCrypto.phrase = loadSolution;
				currCrypto.encryptedPhrase = ch1;
				playerGuess = ch2;
				System.out.println("Encoded phrase: ");
				System.out.println(loadPuzzle);
				System.out.println("Current guess: ");
				System.out.println(loadGuess);
				present = 1;
			}
		}
		return present;
	}


	// This method read the number cryptograms file
	public static int readNumberCryptogramsFile(Cryptogram currCrypto, Player play) {
		int present = 0;
		File myObj = new File("savedNumberCryptos.txt");
		List<String> words = new ArrayList<String>();
		try (Scanner sc = new Scanner((myObj), StandardCharsets.UTF_8.name())) {
			while (sc.hasNextLine()) {
				words.add(sc.nextLine());
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		for (String line : words) {
			String[] split = line.split(",");
			if (split[0].equals(play.getUsername())) {
				intTypeCrypto=2;
				String loadGuess = split[1];
				String loadSolution = split[2];
				int[] ch1 = new int[loadSolution.length()];
				for (int i = 0; i < loadSolution.length(); i++) {
					ch1[i] = Integer.parseInt(split[i + 3]);
				}
				char[] ch2 = new char[loadGuess.length()];
				for (int i = 0; i < loadGuess.length(); i++) {
					ch2[i] = loadGuess.charAt(i);
				}
				currCrypto.phrase = loadSolution;
				currCrypto.intEncryptedPhrase = ch1;
				playerGuess = ch2;
				System.out.println("Encoded phrase: ");
				for (int i = 0; i < loadSolution.length(); i++) {
					System.out.print(ch1[i]);
					System.out.print(" ");
				}
				System.out.println("");
				System.out.println("Current guess: ");
				System.out.println(loadGuess);
				present = 1;
			}
		}
		return present;
	}

	// Prints the solution to the current cryptogram
	public static String showSolution(Cryptogram currCrypto) {
		String solution = currCrypto.phrase;
		System.out.println("The solution is: " + solution);
		return solution;
	}
	
	// Prints the options the player can take when viewing stats
	public static void showStatOptions(Player player, Scanner sc) {
		System.out.println("--------------------------------------------------------");
		System.out.println("The following digits correspond to your specified statistic:");
		System.out.println("1 - information on guesses");
		System.out.println("2 - number of cryptograms played");
		System.out.println("3 - number of cryptograms completed");
		System.out.println("--------------------------------------------------------");
		System.out.print("Which statistic would you like to see? ");
		// Reads the next int and passes it in for the specific stat to be shown
		int action = sc.nextInt();
		selectStat(action, player);
		sc.nextLine();
	}
	
	// Prints the player's specified stat
	public static void selectStat(int input, Player player) {
		Player previousStats = Players.findUser(player.getUsername());
		switch (input) {
			case 1:
				// Guess information
				int totalGuesses = player.getGuesses();
				int correctGuesses = player.getCorrectGuesses();
				double accuracy = player.getAccuracy();
				System.out.println("You have made " + totalGuesses + " guesses. " + correctGuesses + " of which were right meaning 									you have an accuracy of " + accuracy + "%");
				break;
			case 2:
				// Number of cryptograms played
				int cryptogramsPlayed = player.getNumCryptogramsPlayed();
				System.out.println("You have played " + cryptogramsPlayed + " cryptograms");
				break;
			case 3:
				// Number of cyptograms completed
				int cryptogramsCompleted = player.getNumCryptogramsCompleted();
				System.out.println("You have completed " + cryptogramsCompleted + " cryptograms");
				break;
			default:
				System.out.println("This was not a valid action");
				break;
		}
	}
	
	// Prints the frequencies of each character used
	public static void printFrequencies(Cryptogram currCrypto) {
		// Gets the hashmap of current frequencies 
		HashMap<Character, Integer> frequencies = currCrypto.getFrequencies();
		// Gets the total number of characters
		int totalChars = currCrypto.getCharTotal();
		System.out.println("The following is the frequencies for every letter in the english alphabet:");
		System.out.println("A : 8.12%");
		System.out.println("B : 1.49%");
		System.out.println("C : 2.71%");
		System.out.println("D : 4.32%");
		System.out.println("E : 12.02%");
		System.out.println("F : 2.30%");
		System.out.println("G : 2.03%");
		System.out.println("H : 5.92%");
		System.out.println("I : 7.31%");
		System.out.println("J : 0.10%");
		System.out.println("K : 0.69%");
		System.out.println("L : 3.98%");
		System.out.println("M : 2.61%");
		System.out.println("N : 6.95%");
		System.out.println("O : 7.68%");
		System.out.println("P : 1.82%");
		System.out.println("Q : 0.11%");
		System.out.println("R : 6.02%");
		System.out.println("S : 6.28%");
		System.out.println("T : 9.10%");
		System.out.println("U : 2.88%");
		System.out.println("V : 1.11%");
		System.out.println("W : 2.09%");
		System.out.println("X : 0.17%");
		System.out.println("Y : 2.11%");
		System.out.println("Z : 0.07%");
		// Prints each character, the number of times they appear, and that as an overall %
		System.out.println("The following is the frequencies for the cryptogram:");
		frequencies.forEach((key, value) -> {
			double percent = ((double) value / (double) totalChars * 100);
			System.out.println(key + " : " + value + " : " + Math.round(percent) + "%");
		});
	}

	// Method for creating and displaying the leader board of the top 10 players 
	public static void showLeaderboard() {
		// Set up File, Arrays, Variables and ArrayLists
		File myObj = new File("savedPlayers.txt");
		Double topScores[] = new Double [10];
		String topPlayers[] = new String [10];
		Double max;
		Double cryptogramsCompleted;
		Double cryptogramsPlayed;
		List<String> players = new ArrayList<String>();
		List<Double> scores = new ArrayList<Double>();
		List<String> words = new ArrayList<String>();
		// Read file 
		try (Scanner sc = new Scanner((myObj), StandardCharsets.UTF_8.name())) {
			while (sc.hasNextLine()) {
				words.add(sc.nextLine());
			}
		}
		// Catch any exceptions
		catch (IOException e) {
			e.printStackTrace();
		}
		// Search through the data and add players along with their no. of cryptograms played and completed to the
		// new ArrayLists, allowing us to search through them
		for (String line : words) {
			String[] split = line.split(",");
			// Store player name, cryptograms played and cryptograms completed to new ArrayLists
			players.add(split[0]);
			cryptogramsPlayed = (Double.parseDouble(split[4]));
			cryptogramsCompleted = (Double.parseDouble(split[5]));
			// Calculate proportion of successfully completed cryptograms
			if (cryptogramsPlayed != 0) {
				scores.add(cryptogramsCompleted / cryptogramsPlayed);
			} else {
				scores.add(0.0);
			}
		}
		// set default size to 10
		int size = 10;
		// if there are less than 10 high scores, alter the amount
		if(scores.size() < 10) {
			size = scores.size();
		}
		max = 0.0;
		// Find the highest score, add the player and score to our arrays and then remove them from the ArrayList
		for(int i = 0; i < size; i++) {
			max = Collections.max(scores);
			topScores[i] = max;
			int index = scores.indexOf(Collections.max(scores));
			topPlayers[i] = players.get(index);
			scores.remove(max);
			players.remove(index);
		}
		// Checking if there are no scores, this will never activate as there will always be player info stored
		if (size == 0) {
			System.out.println("Error - there are not any scores yet");
		} else {
			for (int i = 0; i < size; i++) {
				System.out.println((i + 1) + ": " + topPlayers[i] + " - " + topScores[i]);
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(System.in); 
		startup(sc);
		sc.close();
	}	
}
