import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Cryptogram {

	protected String phrase;
	public int frequency;
	public char[] encryptedPhrase;
	public int[] intEncryptedPhrase;
	public ArrayList<String> phrases;
	
	public Cryptogram() {
		this.createPhrase();
	}
	
	// reads a phrase (currently only one) from a text file
	public void createPhrase() {
		try {
			File myObj = new File("filename.txt"); // path for the file required
			phrases = new ArrayList<String>();
			Scanner sc = new Scanner(myObj);
				while (sc.hasNextLine()) {
					String data = sc.nextLine();
					phrases.add(data);
				}
			sc.close();
			if (phrases.isEmpty()) {
				System.out.println("No phrases detected - Now exiting...");
				System.exit(0);
			}
			// Creates a random number to choose the phrase 
			Random r = new Random();
			int low = 0;
			int high = phrases.size();
			int result = r.nextInt(high - low) + low;
			phrase = phrases.get(result);
		}
		catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
		}
	}
	
	public int getFrequency() {
		return frequency;
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
}
