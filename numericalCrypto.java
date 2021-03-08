import java.util.Random;

public class numericalCrypto extends Cryptogram {
	
	public numericalCrypto() {
		this.getPhrase();
		this.encrypt(phrase);
	}
	
	public void encrypt(String phrase) {
		// usedChars is used as referral to ensure duplicate characters have the same mapping
		char[] usedChars = phrase.toUpperCase().toCharArray();
		// encChars stores the encrypted phrase 
		int[] encChars = new int[usedChars.length];
		for (int i = 0; i < phrase.length(); i++) {
			// If the phrase has an empty space, the puzzle shows this through a 0 (for now) as it's impossible for 
			// this to be mapped to.
			if (usedChars[i] == ' ') {
				encChars[i] = 0;
			}
			else {
				// Generates random numbers
				Random r = new Random();
				int low = 1;
				int high = 27;
				int result = r.nextInt(high - low) + low;
				// If already used in a mapping, another random number is chosen 
				for (int j = 0; j < encChars.length; j++) {
					if (encChars[j] == result) {
						while (encChars[j] == result) {
							result = r.nextInt(high-low) + low;
						}
					}
				}
				// Base case - first entry has nothing to compare to so it can always be set
				if (i == 0) {
					encChars[i] = result;
				}
				// Compares an element with all of the previous elements 
				for (int j = 0; j < i; j++) { 
					// If it appeared previously, the mapping is copied 
					if (usedChars[i] == usedChars[j]) {
						encChars[i] = encChars[j];
						break;
					}
					// Otherwise a new mapping is made
					else {
						encChars[i] = result;
					}
				}	
			}
		}
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
