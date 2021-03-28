import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.HashMap;

public class Cryptogram {

	protected String phrase;
	HashMap<Character, Integer> frequencies = new HashMap<>();
	public char[] encryptedPhrase;
	public int[] intEncryptedPhrase;
	public ArrayList<String> phraseList;
	
	public Cryptogram() {
		this.readFile();
		this.createPhrase();
	}
	
	// exits if there's no phrases found or chooses a random phrase
	public void createPhrase() {
			if (phraseList.isEmpty()) {
				System.out.println("No phrases detected - Now exiting...");
				System.exit(0);
			}
			// Creates a random number to choose the phrase 
			Random r = new Random();
			int low = 0;
			int high = phraseList.size();
			int result = r.nextInt(high - low) + low;
			phrase = phraseList.get(result);
		}
	
	// reads phrases from from a text file and stores them into an ArrayList
	public void readFile() {
		try {
			File myObj = new File("phrases.txt");
			phraseList = new ArrayList<String>();
			Scanner sc = new Scanner(myObj);
			while (sc.hasNextLine()) {
				String data = sc.nextLine().toUpperCase();
				phraseList.add(data);
			}
			sc.close();
		}	
		catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
		}
	}
	
	public String getPhrase() {
		return phrase;
	}
	
	// If the puzzle is alphabetical, it will call this method 
	public char[] getEncryption() {
		return encryptedPhrase;
	}

	// If the puzzle is numerical, it will call this method 
	public int[] getIntEncryption() {
		return intEncryptedPhrase;
	}
	
	// Gets the frequency of each letter and returns their frequencies as a hashmap
	public HashMap<Character, Integer> getFrequencies() {
		// Gets the original string for comparing
		String phrase = this.getPhrase();	
		for (int i = 0; i < phrase.length(); i++) {
			// Gets the character and adds it to the hashmap only if it's not a space or hasn't been seen already
			if (phrase.charAt(i) != ' ') {
				char indivChar = phrase.charAt(i);
				if (!frequencies.containsKey(indivChar)) {
					// As there's already 1 character there, the frequency can be one
					int thisFrequency = 1;
					for (int j = 0; j < phrase.length(); j++) {
						// Gets each phrase and checks it against the rest of the string
						char currentChar = phrase.charAt(j);
						if (currentChar == indivChar && j != i) {
							// Increments the frequency variable only if they're equal and not at the same positions
							thisFrequency++;
						}
					}
					// Puts the character and its frequency into the hashmap
					frequencies.put(indivChar, thisFrequency);
				}
			}
		}
		return frequencies;
	}
	
	public int getCharTotal() {
		// Gets the phrase and sets the number of characters to 0
		int totalChars = 0;
		String phrase = this.getPhrase();
		// Loops for the string length
		for (int i = 0; i < phrase.length(); i++) {
			// If the character isn't a space, the number of total characters is incremented 
			if (phrase.charAt(i) != ' ') {    
	                totalChars++;    
	        }
		}
		return totalChars;
	}
}
