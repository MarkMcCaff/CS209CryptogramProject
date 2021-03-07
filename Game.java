import java.io.FileNotFoundException;
import java.util.*;

public class Game {

	public Player currentPlayer;
	// A boolean is used to loop through the while loop until the player wishes to exit
	static boolean complete = false;
	// This character array stores the current player's answer and updates when they guess or remove a letter
	static char[] playerGuess;
	
	public Game(Player p, int cryptoType) {
	}
	
	public Game(Player p) {
		
	}
	
	public void getHint() {
		
	}
	
	public void loadPlayer() {
		
	}
	
	public void playGame() {

	}

	// Depending on the player's input, the desired Cryptogram will be generated
	public static void generateCryptogram(Scanner sc, int cryptoType) {
		if (cryptoType == 1) {
			System.out.println("Alphabetical Cryptogram: ");
			alphabeticalCrypto cryptogram = new alphabeticalCrypto();
			commandList(sc, cryptogram);
		}
		else if (cryptoType == 2) {
			System.out.println("Numerical Cryptogram: NOTE a 0 represents empty space!");
			numericalCrypto cryptogram = new numericalCrypto();
			commandList(sc, cryptogram);
		}
	}
	
	// Method for guessing a letter within an alphabetical Cryptogram
	public static void enterLetterAlpha(Scanner sc, Cryptogram currCrypto) { 
	    System.out.println("Choose a letter to replace: ");
	    // Scanner reads the next two separate characters from the input stream
	    char replacer = sc.next().charAt(0);
		System.out.println("What letter do you think it is? ");
		char guess = sc.next().charAt(0);
		// The encrypted puzzle is called from the Cryptogram class
	    char[] encryption = currCrypto.getEncryption();
	    // Loops through the puzzle and if the character which the user wants to take a guess at exists, 
	    // then the playerGuess variable is updated
	    for (int i = 0; i < encryption.length; i++ ) {
	    		if (encryption[i] == replacer) {
	    			playerGuess[i] = guess;
	    		}
	    }
	    // The player's current solution is then printed to the screen
	    System.out.print("Your current solution is: ");
	    System.out.println(playerGuess);
	}
	
	// Method for guessing a number within a numerical Cryptogram
	public static void enterLetterNumber(Scanner sc, Cryptogram currCrypto) { 
	    System.out.println("Choose a number to replace: ");
	    // Scanner reads the next two inputs from the input stream
	    int replacer = sc.nextInt();
		System.out.println("What letter do you think it is? ");
		char guess = sc.next().charAt(0);
		// The encrypted puzzle is called from the Cryptogram class
	    int[] encryption = currCrypto.getIntEncryption();
	    // Loops through the puzzle and if the number which the user wants to take a guess at exists, 
	    // then the playerGuess variable is updated
	    for (int i = 0; i < encryption.length; i++ ) {
	    		if (encryption[i] == replacer) {
	    			playerGuess[i] = guess;
	    		}
	    }
	    // The player's current solution is then printed to the screen
	    System.out.print("Your current solution is: ");
	    System.out.println(playerGuess);
	}
	
	// Method for removing a player's answer from their current solution 
	public static void undoLetter(Scanner sc) { 
	    System.out.println("Choose a letter in your solution you would like to undo: ");
	    // Scanner reads the next character from the input stream
	    char remover = sc.next().charAt(0);
	    // Loops through their solution and if the character which the user wants to take a guess at 
	    // exists, then the character is removed from the playerGuess variable
	    for (int i = 0; i < playerGuess.length; i++ ) {
	    	if (playerGuess[i] == remover) {
	    		playerGuess[i] = ' ';
	    	}
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
		System.out.println("--------------------------------------------------------");
		System.out.println("the following digits correspond to the specified action:");
		System.out.println("1 - alphabetical cryptogram");
		System.out.println("2 - numerical cryptogram");
		System.out.println("--------------------------------------------------------");
		// Scanner reads the next integer and creates the specified Cryptogram 
		System.out.print("which action would you like to carry out? ");  
		int input = sc.nextInt();  
		sc.nextLine();
		
		if (input == 1 || input == 2) { 
			generateCryptogram(sc, input);
		}
		// If the input it read was not of the two digits, it gives an error
		else {
			System.out.println("Invalid Input!");
		}
	}
	
	// Method used to show the player which actions they can carry out 
	public static void commandList(Scanner sc, Cryptogram currCrypto) {
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
		commandInput(sc, currCrypto);
	}
	
	// Method used to deal with the player's input from the commandList method 
	public static void commandInput(Scanner sc, Cryptogram currCrypto) {
		// determines the length of the playerGuess array from the specific getEncryption method, depending
		// on which Cryptogram object was created 
		if (currCrypto instanceof alphabeticalCrypto) {
			playerGuess = new char[currCrypto.getEncryption().length];
		}
		else {
		    playerGuess = new char[currCrypto.getIntEncryption().length];		
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
					enterLetterAlpha(sc, currCrypto);
				}
				else {
					enterLetterNumber(sc, currCrypto);
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
