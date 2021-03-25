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
			File myObj = new File("C:\\Users\\scott\\Desktop\\savedPhrases.txt");
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
	
	public HashMap<Character, Integer> getFrequencies() {
		String phrase = this.getPhrase();	
		for (int i = 0; i < phrase.length(); i++) {
			if (phrase.charAt(i) != ' ') {
				char indivChar = phrase.charAt(i);
				if (!frequencies.containsKey(indivChar)) {
					int thisFrequency = 1;
					for (int j = 0; j < phrase.length(); j++) {
						char currentChar = phrase.charAt(j);
						if (currentChar == indivChar && j != i) {
							thisFrequency++;
						}
					}
					frequencies.put(indivChar, thisFrequency);
				}
			}
		}
		return frequencies;
	}
	
	public int getCharTotal() {
		int totalChars = 0;
		String phrase = this.getPhrase().strip();
		for (int i = 0; i < phrase.length(); i++) {
			if(phrase.charAt(i) != ' ')    
	                totalChars++;    
	        }    
		return totalChars;
	}
}
