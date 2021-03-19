import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Game {
	// A boolean is used to loop through the while loop until the player wishes to exit
	static boolean complete = false;
	// This character array stores the current player's answer and updates when they guess or remove a letter
	static char[] playerGuess;
	// Integer for the player to decide which Cryptogram to play
	static int intTypeCrypto;
	static commandWords commands = new commandWords();

	// Depending on the player's input, the desired Cryptogram will be generated
	public static void generateCryptogram(Scanner sc, int cryptoType, Player play) {
		// Increments the number of cryptograms a player has played when one is generated 
		play.incremementCryptogramsPlayed();
		if (cryptoType == 1) {
			intTypeCrypto = 0;
			System.out.println("Alphabetical Cryptogram: ");
			alphabeticalCrypto cryptogram = new alphabeticalCrypto();
			System.out.println("Welcome! Type 'help' if you need help ");
			commandInput(sc, cryptogram, play);
		}
		else if (cryptoType == 2) {
			intTypeCrypto = 1;
			System.out.println("Numerical Cryptogram: NOTE a 0 represents empty space!");
			numericalCrypto cryptogram = new numericalCrypto();
			System.out.println("Welcome! Type 'help' if you need help");
			commandInput(sc, cryptogram, play);
		}
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
	    checkForAlphOverwriting(encryption, replacer, sc, temp, guess, play);
	    checkCorrectness(temp, play);
	    for (int i = 0; i < encryption.length; i++) {
	    	System.out.print(encryption[i]);
	    }
	    System.out.println(" ");
	    currSolution();
	    sc.nextLine();
	}
	
	public static void checkForAlphOverwriting(char[] encryption, char replacer, Scanner sc, char[] temp, char guess, Player play) {
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
	    	char ans = Character.toUpperCase(sc.next().charAt(0));
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
		}
		play.incrementGuesses();
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
	    checkForNumOverwriting(encryption, replacer, sc, temp, guess, play);
	    checkCorrectness(temp, play);
	    for (int i = 0; i < encryption.length; i++) {
	    	System.out.print(encryption[i] + " ");
	    }
	    System.out.println(" ");
	    currSolution();
	    sc.nextLine();
	}
	
	public static void checkForNumOverwriting(int[] encryption, int replacer, Scanner sc, char[] temp, char guess, Player play) {
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
	    	char ans = Character.toUpperCase(sc.next().charAt(0));
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
	}
	
	public static void checkCorrectness(char[] temp, Player play) {
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
			play.incrementGuesses();
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
		System.out.println("--------------------------------------------------------");
		System.out.println("Welcome " + name + ", the following digits correspond to the specified action:");
		System.out.println("1 - alphabetical cryptogram");
		System.out.println("2 - numerical cryptogram");
		System.out.println("--------------------------------------------------------");
		// Scanner reads the next integer and creates the specified Cryptogram 
		System.out.print("which cryptogram would you like to create? ");
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
	
	// Method used to deal with the player's input from the commandList method 
	public static void commandInput(Scanner sc, Cryptogram currCrypto, Player play) {
		setupPlayerGuess(currCrypto);
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
					if (currCrypto instanceof alphabeticalCrypto) {
						enterLetterAlpha(sc, currCrypto, play);
					}
					else {
						enterLetterNumber(sc, currCrypto, play);
					}
					break;
				case UNDO:
					// Carries out the undoLetter method - allowing players to remove letters from their solution
					undoLetter(sc);
					break;
				case SAVE:
					saveGame(currCrypto, play, sc);
					break;
				case LOAD:
					loadGame(currCrypto, play);
					break;
				case EXIT:
					// Exits the program
					play.savePlayers(play);
					System.out.println("Now exiting...");
					complete = true;
					break;
				case HELP:
					printHelp();
					break;
			}
		}
	}
	
	private static void printHelp() {
		System.out.println("Your command words are:");
		commands.showAll();
	}

	// determines the length of the playerGuess array from the specific getEncryption method, depending
	// on which Cryptogram object was created and then inputs the spaces into the correct places for
	// the user's guesses
	public static void setupPlayerGuess(Cryptogram currCrypto) {
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
	}

// ------------------ SPRINT 2 ------------------ //	
	
	//Method for saving the user's cryptogram progress
	public static void saveGame(Cryptogram currCrypto, Player play, Scanner sc) {
		//Checks whether the user has any previous saves
		boolean previousSave = previousSaveGame(play);
		//Checks what type the current cryptogram is
		if (currCrypto instanceof alphabeticalCrypto) {
			//If there is a previous save for the user then it asks the user whether they want to overwrite
			if(previousSave) {
				System.out.println("There is already a cryptogram saved under this username.");
				System.out.println("Would you like to overwrite it? Y/N: ");
				char ans = Character.toUpperCase(sc.next().charAt(0));
				// If they answer yes, it's overwritten
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
			if(previousSave) {
				System.out.println("There is already a cryptogram saved under this username.");
				System.out.println("Would you like to overwrite it? Y/N: ");
				char ans = Character.toUpperCase(sc.next().charAt(0));
				// If they answer yes, it's overwritten
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

	//Method for getting rid of user's previous save when they want to save a new crypto
	public static void overwrite(Cryptogram currCrypto, Player play) {
		File myObj1 = new File("savedLetterCryptos.txt");
		List<String> words1 = new ArrayList<String>();
		List<String> loadUsername = new ArrayList<String>();
		List<String> loadPuzzle = new ArrayList<String>();
		List<String> loadGuess = new ArrayList<String>();
		List<String> loadSolution = new ArrayList<String>();
		String remove = play.getUsername();
		int removeInt = -1;
		try (Scanner sc = new Scanner((myObj1), StandardCharsets.UTF_8.name())) {
			while (sc.hasNextLine()) {
				words1.add(sc.nextLine());
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		int i = 0;
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

	//This method checks whether the user has any previous saves
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

	//This method writes a letter cryptogram to a text file
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

	//This method writes a number cryptogram to a text file
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

	//This method loads the user's previous save if it exists
	public static void loadGame(Cryptogram currCrypto, Player play) {
		readLetterCryptogramsFile(currCrypto, play);
		readNumberCryptogramsFile(currCrypto, play);
	}

	//This method read the letter cryptograms file
	public static void readLetterCryptogramsFile(Cryptogram currCrypto, Player play){
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
				intTypeCrypto=0;
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
			}
		}
	}

	//This method read the number cryptograms file
	public static void readNumberCryptogramsFile(Cryptogram currCrypto, Player play) {
		File myObj = new File("saveNumberCryptos.txt");
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
				intTypeCrypto=1;
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
			}
		}
	}
		
	public static void showSolution(Cryptogram currCrypto) {
		System.out.println("This is the solution: " + currCrypto.phrase);
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(System.in); 
		startup(sc);
		sc.close();
	}	
}
