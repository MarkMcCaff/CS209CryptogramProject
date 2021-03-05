import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Cryptogram {

	private String phrase;
	public int frequency;
	
	public Cryptogram() {
		this.getPhrase();
		this.encrypt(phrase);
	}
	
	// reads a phrase (currently only one) from a text file
	public void getPhrase() {
		try {
			File myObj = new File("C:\\Users\\scott\\Desktop\\hello.txt");
			Scanner myReader = new Scanner(myObj);
				if (myReader.hasNextLine()) {
					String data = myReader.nextLine();
					phrase = data;
				}
			myReader.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
		}
	}
	
	public void encrypt(String phrase) {
		
		// usedChars is used as referral to ensure duplicate characters have the same mapping
		char[] usedChars = phrase.toUpperCase().toCharArray();
		// encChars stores the encrypted phrase 
		char[] encChars = new char[usedChars.length];
		// numsUsed stores the random numbers used so mapping cannot be repeated 
		ArrayList<Integer> numsUsed = new ArrayList<Integer>();
		
		for (int i = 0; i < phrase.length(); i++) {
			
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
		
		// TESTING ONLY
		System.out.println(usedChars);
		System.out.println(encChars);
	}
	
	public int getFrequency() {
		return frequency;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Cryptogram cryp = new Cryptogram();
	}
}
