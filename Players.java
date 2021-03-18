import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class Players extends Player{

	public Players(Player p) {
		String username = this.getUsername();
		int accuracy = this.getAccuracy();
		int totalGuesses = this.getGuesses();
		int correctGuesses = this.getCorrectGuesses();
		int cryptogramsPlayed = this.getNumCryptogramsPlayed();
		int cryptogramsCompleted = this.getNumCryptogramsCompleted();

	}

	public static ArrayList <String> playerList;


    public void addPlayer(Player p, Scanner sc){
        System.out.println("Enter your desired username: ");
        String newUserName = sc.next();
        try {
            FileWriter myWriter = new FileWriter("C:\\Users\\alex\\Desktop\\players.txt");
            myWriter.write(newUserName);
            myWriter.close();
            System.out.println("Successfully created new player: " + newUserName); 
/*    	catch(IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace(); 
            } */
        }
    }
/*

    public void savePlayers(Player play, Scanner sc) {
    	//boolean previousSave = previousSave(play);
        if (Game.savegame()) {
            File myObj = new File("C:\\Users\\alex\\Desktop\\players.txt");
            for(int i = 0; i < playerList.length; i++) {
                if(playerList[i] = username) {
                    // overwrite the stored data for the user with new data
                }
                else {
                    myWriter.write(username + cryptogramsCompleted + cryptogramsPlayed + correctGuesses );
                    myWriter.close();
                }

            }
        }

    }



    public void findPlayer(Player p) {
        try {
            File myObj = new File("C:\\Users\\alex\\Desktop\\players.txt");
            playerList = new ArrayList<String>();
            Scanner sc = new Scanner(myObj);
            while (sc.hasNextLine()) {
                String username = sc.nextLine();
                playerList.add(username);
            }
            sc.close();
            if (playerList.isEmpty()) {
                System.out.println("No users exist - try creating a new one");
                System.exit(0);
            }
        }
    } */
	
	public static void savePlayers(Player play, Scanner sc) {
		boolean previousSave = findPlayer(play);
		
			if(previousSave) {
				System.out.println("Update previous data? Y/N: ");
				char ans = Character.toUpperCase(sc.next().charAt(0));
				if (ans == 'Y') {
					overwrite(play);
					writeToFile(play);
					System.out.println("Your player info has been saved.");
				}
				else {
					System.out.println("Your player info has not been saved.");
				}

			}else{
				writeToFile(play);
				System.out.println("Your player info has been saved.");
			}
		
			
	}
		
	
   
    public static void overwrite(Player play) {
		File myObj1 = new File("C:\\Users\\euanb\\Documents\\2ndYear\\CS207\\2ndSemesterAssignment\\savedPlayers.txt");
		List<String> words1 = new ArrayList<String>();
		List<String> loadUsername = new ArrayList<String>();
		List<String> accuracy = new ArrayList<String>();
		List<String> totalGuesses = new ArrayList<String>();
		List<String> correctGuesses = new ArrayList<String>();
		List<String> cryptogramsPlayed = new ArrayList<String>();
		List<String> cryptogramsCompleted = new ArrayList<String>();
		
		accuracy.add(play.getAccuracy());
		
		String remove = play.getUsername();
		int removeInt = -1;
		try (Scanner sc = new Scanner((myObj1), StandardCharsets.UTF_8.name())) {
			while (sc.hasNextLine()) {
				words1.add(sc.nextLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		int i = 0;
		for (String line:words1) {
			String[] split = line.split(",");
			loadUsername.add(split[0]);
			accuracy.add(split[1]);
			totalGuesses.add(split[2]);
			correctGuesses.add(split[3]);
			cryptogramsPlayed.add(split[4]);
			cryptogramsCompleted.add(split[5]);
			if (split[0].equals(remove)) {
				removeInt = i;
			}
			i++;
		}
		if (removeInt != -1) {
			loadUsername.remove(removeInt);
			accuracy.remove(removeInt);
			totalGuesses.remove(removeInt);
			correctGuesses.remove(removeInt);
			cryptogramsPlayed.remove(removeInt);
			cryptogramsCompleted.remove(removeInt);
			try {
				FileWriter fw = new FileWriter("C:\\Users\\euanb\\Documents\\2ndYear\\CS207\\2ndSemesterAssignment\\savedPlayers.txt");
				BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\euanb\\Documents\\2ndYear\\CS207\\2ndSemesterAssignment\\savedPlayers.txt", true));
				fw.write("");
				for (int j = 0; j < loadUsername.size(); j++) {
					bw.append(loadUsername.get(j));
					bw.append(",");
					bw.append(accuracy.get(j));
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
			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}
    }
    
  //This method checks whether the user has any previous saves
  	public static boolean findPlayer(Player play) {
  		boolean previous = false;

  		File myObj1 = new File("C:\\Users\\euanb\\Documents\\2ndYear\\CS207\\2ndSemesterAssignment\\savedPlayers.txt");
  		List<String> words1 = new ArrayList<String>();
  		try (Scanner sc = new Scanner((myObj1), StandardCharsets.UTF_8.name())) {
  			while (sc.hasNextLine()) {
  				words1.add(sc.nextLine());
  			}
  		} catch (IOException e) {
  			e.printStackTrace();
  		}
  		for (String line : words1) {
  			String[] split = line.split(",");
  			if (split[0].equals(play.getUsername())) {
  				previous = true;
  			}
  		}
  
  		return previous;
  	}
  	
  	public static void writeToFile(Player play){
  		try {
  			BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\euanb\\Documents\\2ndYear\\CS207\\2ndSemesterAssignment\\savedLetterCryptos.txt", true));
  			bw.append(play.getUsername());
  			bw.append(",");
  			for (int i = 0; i < playerList.size(); i++) {
  				bw.append();
  			}
  			bw.append(",");
  			for (int i = 0; i < playerList.size(); i++) {
  				bw.append(String.valueOf(playerGuess[i]));
  			}
  			bw.append(",");
  			bw.append(currCrypto.phrase);
  			bw.newLine();
  			bw.close();
  		} catch (IOException e) {
  			System.out.println("An error occurred.");
  			e.printStackTrace();
  		}
  	}
		


	public static void readSavedPlayers( Player play){
		File myObj = new File("C:\\Users\\euanb\\Documents\\2ndYear\\CS207\\2ndSemesterAssignment\\savedPlayers.txt");
		List<String> words = new ArrayList<String>();
		try(Scanner sc = new Scanner((myObj), StandardCharsets.UTF_8.name())) {
			while(sc.hasNextLine()) {
				words.add(sc.nextLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(String line : words){
			String [] split = line.split(",");
			if (split[0].equals(play.getUsername())) {
				String accuracy = split[1];
				String totalGuesses = split[2];
				String correctGuesses = split[3];
				String cryptogramsPlayed = split[4];
				String cryptogramsCompleted = split[5];
				
				
			}
		}
	}
}
