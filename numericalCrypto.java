import java.util.Random;

public class numericalCrypto extends Cryptogram {
	
	public numericalCrypto() {
		this.encrypt(phrase);
	}
	
	public numericalCrypto(String phrase, int[] intEncryptedPhrase) {
		this.phrase = phrase;
		this.intEncryptedPhrase = intEncryptedPhrase;
	}
	
	// usedChars is used as referral to ensure duplicate characters have the same mapping
	char[] usedChars = getPhrase().toCharArray();
	// encChars stores the encrypted phrase 
	int[] encChars = new int[usedChars.length];
	
	public void encrypt(String phrase) {
		for (int i = 0; i < phrase.length(); i++) {
			// If the phrase has an empty space, the puzzle shows this through a 0 (for now) as it's impossible for 
			// this to be mapped to.
			if (usedChars[i] == ' ') {
				encChars[i] = 0;
			}
			else {
				compareElements(i);
			}
		}
		printMapping();
	}
	
	public void compareElements(int position) {
		int mappingNumber = verifyNumber();
		// Base case - first entry has nothing to compare to so it can always be set
		if (position == 0) {
			encChars[position] = mappingNumber;
		}
		// Compares an element with all of the previous elements 
		for (int j = 0; j < position; j++) { 
			// If it appeared previously, the mapping is copied 
			if (usedChars[position] == usedChars[j]) {
				encChars[position] = encChars[j];
				break;
			}
			// Otherwise a new mapping is made
			else {
				encChars[position] = mappingNumber;
			}
		}
	}
	
	// creates and verifies a number to make sure it hasn't already been mapped
	public int verifyNumber() {
		int result = randomNum();
		// If already used in a mapping, another random number is chosen 
		for (int j = 0; j < encChars.length; j++) {
			if (encChars[j] == result) {
				while (encChars[j] == result) {
					// generates random number until it hasn't been used already
					result = randomNum();
				}
			}
		}
		return result;
	}
	
	// method for generating a random number
	public int randomNum() {
		Random r = new Random();
		// low and high numbers are set as according to the alphabet
		int low = 1;
		int high = 27;
		return r.nextInt(high - low) + low;
	}
	
	// method for printing the mapping
	private void printMapping() {
		// Prints the results of the mapping
		System.out.print("Encoded phrase: ");
		for (int i = 0; i < encChars.length; i++) {
			System.out.print(encChars[i] + " ");
		}
		// Clones the result of the mapping to an integer specific variable from the overall Cryptogram class
		intEncryptedPhrase = encChars.clone();
		System.out.println(" ");
	}
}
