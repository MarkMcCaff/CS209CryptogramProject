import java.util.ArrayList;
import java.util.Random;

public class alphabeticalCrypto extends Cryptogram {

	public alphabeticalCrypto() {
		this.encrypt(phrase);
	}

	public alphabeticalCrypto(String phrase, char[] encryptedPhrase) {
		this.phrase = phrase;
		this.encryptedPhrase = encryptedPhrase;
	}

	// usedChars is used as referral to ensure duplicate characters have the same mapping
	char[] usedChars = getPhrase().toCharArray();
	// encChars stores the encrypted phrase 
	char[] encChars = new char[usedChars.length];
	// numsUsed stores the random numbers used so mapping cannot be repeated 
	ArrayList<Integer> numsUsed = new ArrayList<Integer>();

	public void encrypt(String phrase) {
		for (int i = 0; i < phrase.length(); i++) {
			// If the phrase has an empty space, the puzzle must also show this 
			if (usedChars[i] == ' ') {
				encChars[i] = ' ';
			}
			else {
				compareElements(i);
			}
		}
		printMapping();
	}

	public void compareElements(int position) {
		char newChar = generateLetter();
		// Base case - first entry has nothing to compare to so it can always be set
		if (position == 0) {
			encChars[position] = newChar;
			numsUsed.add(((int) newChar));
		}
		else {
			// Compares an element with all of the previous elements
			for (int j = 0; j < position; j++) {
				// If it appeared previously, the mapping is copied
				if (usedChars[position] == usedChars[j]) {
					encChars[position] = encChars[j];
					return;
				}
			}
			// Otherwise a new mapping is made
			encChars[position] = newChar;
			numsUsed.add(((int) newChar));
		}
	}

	// method for generating a letter which hasn't been used yet
	public char generateLetter() {
		int result = randomNum();
		// If already used in a mapping, another random number is chosen
		while (numsUsed.contains(result)) {
			result = randomNum();
		}
		// Converts the random number generated into a character
		return (char) result;
	}

	// method for generating a random number
	public int randomNum() {
		Random r = new Random();
		// low and high numbers are set as according to the alphabet (to ensure the mapping is uppercase)
		int low = 65;
		int high = 91;
		return r.nextInt(high - low) + low;
	}

	// Method for printing the results of the mapping
	private void printMapping() {
		System.out.print("Encoded phrase: ");
		for (int i = 0; i < encChars.length; i++) {
			System.out.print(encChars[i]);
		}
		// Clones the result of the mapping to a variable from the overall Cryptogram class
		encryptedPhrase = encChars.clone();
		System.out.println(" ");
	}
}

