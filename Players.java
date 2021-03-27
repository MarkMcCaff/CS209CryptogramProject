import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Players {
	//static ArrayList <String> playerList;
	static String username;
	static Double accuracy;
	static int totalGuesses;
	static int correctGuesses;
	static int cryptogramsPlayed;
	static int cryptogramsCompleted;
	static int count;

	public Players() {
		//String username = this.getUsername();
		//int accuracy = this.getAccuracy();
		//int totalGuesses = this.getGuesses();
		//int correctGuesses = this.getCorrectGuesses();
		//int cryptogramsPlayed = this.getNumCryptogramsPlayed();
		//int cryptogramsCompleted = this.getNumCryptogramsCompleted();
	}

	// Checks to make sure the player doesn't already exist and creates one if necessary
	public void addPlayer(Player play) {
		// Reads the file to load all of the players
		readSavedPlayers(play);
		String newUsername = play.getUsername();
		boolean found = false;
		// If the player exists, found becomes true
		for (int i = 0; i < count; i++) {
			if (newUsername.equals(username)) {
				found = true;

			}
		}
		// Prints a different message depending on whether they already exist or not
		if (found) {
			System.out.println("Signed in as: " + newUsername);
		} else {
			// Writes the new user to the file
			writeToFile(play);
			System.out.println("New user: " + newUsername);
		}
	}

	// Looks for a specific user in the file
	public static Player findUser(String username) {
		// Creates a new player object
		Player foundPlayer = new Player();
		List<String> data = createPlayerList();
		// Loops through the entirety of the file
		for (String line : data) {
			// Splits the string on a comma
			String [] split = line.split(",");
			// If the first token is equal to the username it loads the details into the new player object
			if (split[0].equals(username)) {
				foundPlayer = new Player(split[0], Integer.parseInt(split[2]), Integer.parseInt(split[3]), Integer.parseInt(split[4]), Integer.parseInt(split[5]));
			}
		}
		return foundPlayer;
	}
    
	// Reads the unprocessed data from the file
	public static List<String> createPlayerList() {
		List<String> data = new ArrayList<String>();
		try {
			// Reads each line of a file and adds it to a list
			File myObj = new File("C:\\Users\\scott\\Desktop\\savedPlayers.txt");
			Scanner sc = new Scanner(myObj);
			while (sc.hasNextLine()) {
				data.add(sc.nextLine());
			}
			sc.close();
		}
		// If the file can't be found it prints an error
		catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}		return data;
	}
	
	// Reads and updates the data of any identical players
	public static void readSavedPlayers(Player play) {
		// Reads the player file
		List<String> data = createPlayerList();
		count = 0;
			// If the file contains the current player it updates the their stats
			for(String line : data) {
				String [] split = line.split(",");
				if (split[0].equals(play.getUsername())) {
					username = split[0];
					play.setUsername(split[0]);
					accuracy = Double.parseDouble(split[1]);
					play.setAccuracy(Double.parseDouble(split[1]));
					totalGuesses = Integer.parseInt(split[2]);
					play.setGuesses(Integer.parseInt(split[2]));
					correctGuesses = Integer.parseInt(split[3]);
					play.setCorrectGuesses(Integer.parseInt(split[3]));
					cryptogramsPlayed = Integer.parseInt(split[4]);
					play.setNumCryptogramsPlayed(Integer.parseInt(split[4]));
					cryptogramsCompleted = Integer.parseInt(split[5]);
					play.setNumCryptogramsCompleted(Integer.parseInt(split[5]));
				}
				count++;
			}
		}
	
	// Returns a boolean based on whether the user has any saved data
	public static boolean findPlayer(Player play) {
		// Initialises the boolean to be false
		boolean existingPlayer = false;
		File myObj = new File("savedPlayers.txt");
		// Reads the files and gets each of the lines
		List<String> words = new ArrayList<String>();
		try (Scanner sc = new Scanner((myObj), StandardCharsets.UTF_8.name())) {
			while (sc.hasNextLine()) {
				words.add(sc.nextLine());
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		// Loops through each line are parses it
		for (String line : words) {
			String[] split = line.split(",");
			// Changes the boolean to true if the usernames are equal
			if (split[0].equals(play.getUsername())) {
				existingPlayer = true;
			}
		}
		return existingPlayer;
	}
	
	// Decides what should be done with the current players stats
	public void savePlayers(Player play) {
		// Looks for a previous save
		boolean previousSave = findPlayer(play);
		// If found, overwrites it
		if (previousSave) {
			overwrite(play);
		}
		// Writes to the file
		writeToFile(play);
	}

    public static void overwrite(Player play) {
    	// Opens the file and creates new lists for the different variables
		File myObj1 = new File("C:\\Users\\scott\\Desktop\\savedPlayers.txt");
		List<String> words1 = new ArrayList<String>();
		List<String> loadUsername = new ArrayList<String>();
		List<Double> accuracy = new ArrayList<Double>();
		List<String> totalGuesses = new ArrayList<String>();
		List<String> correctGuesses = new ArrayList<String>();
		List<String> cryptogramsPlayed = new ArrayList<String>();
		List<String> cryptogramsCompleted = new ArrayList<String>();
		accuracy.add(play.getAccuracy());
		// Sets remove variables for looping
		String remove = play.getUsername();
		int removeInt = -1;
		// Reads all of the lines in the file
		try (Scanner sc = new Scanner((myObj1), StandardCharsets.UTF_8.name())) {
			while (sc.hasNextLine()) {
				words1.add(sc.nextLine());
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		int i = 0;
		// Loop parses each line
		for (String line : words1) {
			String[] split = line.split(",");
			loadUsername.add(split[0]);
			accuracy.add(Double.parseDouble(split[1]));
			totalGuesses.add(split[2]);
			correctGuesses.add(split[3]);
			cryptogramsPlayed.add(split[4]);
			cryptogramsCompleted.add(split[5]);
			// If the current player matches a save, the remove variable is set to its location
			if (split[0].equals(remove)) {
				removeInt = i;
			}
			i++;
		}
		// Removed the player if it was found
		if (removeInt != -1) {
			loadUsername.remove(removeInt);
			accuracy.remove(removeInt);
			totalGuesses.remove(removeInt);
			correctGuesses.remove(removeInt);
			cryptogramsPlayed.remove(removeInt);
			cryptogramsCompleted.remove(removeInt);
			// After removing, it tries to open the file and write the new stats to it
			try {
				FileWriter fw = new FileWriter("C:\\Users\\scott\\Desktop\\savedPlayers.txt");
				BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\scott\\Desktop\\savedPlayers.txt", true));
				fw.write("");
				// Sends a line seperated by commas to the file
				for (int j = 0; j < loadUsername.size(); j++) {
					bw.append(loadUsername.get(j));
					bw.append(",");
					bw.append(Double.toString(accuracy.get(j)));
					bw.append(",");
					bw.append(totalGuesses.get(j));
					bw.append(",");
					bw.append(correctGuesses.get(j));
					bw.append(",");
					bw.append(cryptogramsPlayed.get(j));
					bw.append(",");
					bw.append(cryptogramsCompleted.get(j));
					bw.append(",");
					bw.newLine();
				}
				bw.close();
				fw.close();
			}
			catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}
    }
    
    // Writes the current player's stats to the file
    public static void writeToFile(Player play) {
    	// Tries to write all of the stats to a file seperated by commas
    	try {
    		BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\scott\\Desktop\\savedPlayers.txt", true));
    		bw.append(play.getUsername());
    		bw.append(",");
    		bw.append(Double.toString(play.getAccuracy()));
    		bw.append(",");
    		bw.append(Integer.toString(play.getGuesses()));
    		bw.append(",");
    		bw.append(Integer.toString(play.getCorrectGuesses()));
    		bw.append(",");
    		bw.append(Integer.toString(play.getNumCryptogramsPlayed()));
    		bw.append(",");
    		bw.append(Integer.toString(play.getNumCryptogramsCompleted()));
    		bw.append(",");
    		bw.newLine();
    		bw.close();
    	}
    	// Prints a message if it catches an exception
    	catch (IOException e) {
    		System.out.println("An error occurred.");
  			e.printStackTrace();
  		}
  	} 	
}
