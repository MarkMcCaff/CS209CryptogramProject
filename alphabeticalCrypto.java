import java.util.ArrayList;
import java.util.Random;

public class alphabeticalCrypto extends Cryptogram {

	public alphabeticalCrypto() {
		this.getPhrase();
		this.encrypt(phrase);
	}
	
	public void encrypt(String phrase) {
		// usedChars is used as referral to ensure duplicate characters have the same mapping
		char[] usedChars = phrase.toUpperCase().toCharArray();
		// encChars stores the encrypted phrase 
		char[] encChars = new char[usedChars.length];
		// numsUsed stores the random numbers used so mapping cannot be repeated 
		ArrayList<Integer> numsUsed = new ArrayList<Integer>();
			
		for (int i = 0; i < phrase.length(); i++) {
			// If the phrase has an empty space, the puzzle must also show this 
			if (usedChars[i] == ' ') {
				encChars[i] = ' ';
			}
			else {
				// Generates random numbers
				Random r = new Random();
				int low = 65;
				int high = 91;
				int result = r.nextInt(high - low) + low;
				
				// If already used in a mapping, another random number is chosen 
				if (numsUsed.contains(result)) {
					while (numsUsed.contains(result)) {
						result = r.nextInt(high-low) + low;
					}
				}
				// Converts the random number generated into a character 
				char newChar = (char) result;
				// Base case - first entry has nothing to compare to so it can always be set
				if (i == 0) {
					encChars[i] = newChar;
					numsUsed.add(((int) newChar));
				}
				// Compares an element with all of the previous elements 
				for (int j = 0; j < i; j++) { 
					// If it appeared previously, the mapping is copied 
					if (usedChars[i] == usedChars [j]) {
						encChars[i] = encChars[j];
						break;
					}
					// Otherwise a new mapping is made
					else {
						encChars[i] = newChar;
						numsUsed.add(((int) newChar));
					}
				}	
			}
		}
		// Prints the results of the mapping 
		System.out.println("Encoded phrase: ");
		for (int i = 0; i < encChars.length; i++) {
			System.out.print(encChars[i]);
		}
		// Clones the result of the mapping to a variable from the overall Cryptogram class
		encryptedPhrase = encChars.clone();
		System.out.println(" ");
	}
}
