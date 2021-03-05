import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Cryptogram {

	private String phrase;
	private String cryptogramAlphabet;
	public int frequency;
	
	public Cryptogram() {
		this.getPhrase();
		this.encrypt(phrase);
	}
	
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
		char[] encryptPhrase = phrase.toUpperCase().toCharArray();
		
		for (int i = 0; i < phrase.length(); i++) {
			int a = (int) encryptPhrase[i];

			Random r = new Random();
			int low = 65;
			int high = 91;
			int result = r.nextInt(high-low) + low;
			
			char b = (char) result;
			
			System.out.println(encryptPhrase[i] + " : " + a + " : " + result + " : " + b);
		}
	}
	
	public int getFrequency() {
		return frequency;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Cryptogram cryp = new Cryptogram();
	}
}
