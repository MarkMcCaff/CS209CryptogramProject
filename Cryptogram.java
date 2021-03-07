import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Cryptogram {

	protected String phrase;
	public int frequency;
	public char[] encryptedPhrase;
	public int[] intEncryptedPhrase;
	
	public Cryptogram() {
		this.createPhrase();
	}
	
	// reads a phrase (currently only one) from a text file
	public void createPhrase() {
		try {
			File myObj = new File("filename.txt");
			Scanner sc = new Scanner(myObj);
				if (sc.hasNextLine()) {
					String data = sc.nextLine();
					phrase = data;
				}
			sc.close();
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
