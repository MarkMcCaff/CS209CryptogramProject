import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Cryptogram {

	protected String phrase;
	public int frequency;
	
	public Cryptogram() {
		this.getPhrase();
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
	
	public int getFrequency() {
		return frequency;
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Cryptogram cryp = new Cryptogram();
	}
}
