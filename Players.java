import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class Players{
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

/*
    public void addPlayer(Player p, Scanner sc){
        System.out.println("Enter your desired username: ");
        String newUserName = sc.next();
        try {
            FileWriter myWriter = new FileWriter("C:\\Users\\alex\\Desktop\\players.txt");
            myWriter.write(newUserName);
            myWriter.close();
            System.out.println("Successfully created new player: " + newUserName); 
    	catch(IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace(); 
            } 
        }
    } */


	public void addPlayer(Player play) {
		readSavedPlayers(play);
		String newUsername = play.getUsername();
		boolean found = false;
		for (int i = 0; i < count; i++) {
			if (newUsername.equals(username)) {
				found = true;

			}
		}
		if (found) {
			System.out.println("Signed in as: " + newUsername);
		} else {
			writeToFile(play);
			System.out.println("New user: " + newUsername);
		}
	}


    
     public static void readSavedPlayers(Player play) {
    	List<String> data = new ArrayList<String>();

    	try {
    	File myObj = new File("filenname.txt");
    	Scanner sc = new Scanner(myObj);
    		while (sc.hasNextLine()) {
    		data.add(sc.nextLine());
    		}
    	} catch (FileNotFoundException e) {
    	      	System.out.println("An error occurred.");
    	      	e.printStackTrace();
    	    }
    	count = 0;
    	    for(String line : data){
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
     
     //This method checks whether the user has any saved data
   	public static boolean findPlayer(Player play) {
   		boolean existingPlayer = false;

   		File myObj = new File("filename.txt");
   		List<String> words = new ArrayList<String>();
   		try (Scanner sc = new Scanner((myObj), StandardCharsets.UTF_8.name())) {
   			while (sc.hasNextLine()) {
   				words.add(sc.nextLine());
   			}
   		} catch (IOException e) {
   			e.printStackTrace();
   		}
   		for (String line : words) {
   			String[] split = line.split(",");
   			if (split[0].equals(play.getUsername())) {
   				existingPlayer = true;
   			}
   		}
   
   		return existingPlayer;
   	}
   	
	
	public void savePlayers(Player play) {
		boolean previousSave = findPlayer(play);
		
		if(previousSave) {
			overwrite(play);

		}
		writeToFile(play);
	}

    public static void overwrite(Player play) {
		File myObj1 = new File("filename.txt");
		List<String> words1 = new ArrayList<String>();
		List<String> loadUsername = new ArrayList<String>();
		List<Double> accuracy = new ArrayList<Double>();
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
			accuracy.add(Double.parseDouble(split[1]));
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
				FileWriter fw = new FileWriter("filename.txt");
				BufferedWriter bw = new BufferedWriter(new FileWriter("filename.txt", true));
				fw.write("");
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
			} catch (IOException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}
    }
    
  	public static void writeToFile(Player play){
  		try {
  			BufferedWriter bw = new BufferedWriter(new FileWriter("filename.txt", true));
  			bw.append(play.getUsername());
			bw.append(",");
			bw.append(Double.toString(play.getAccuracy()));
			bw.append(",");
			bw.append(Integer.toString(play.getGuesses()));;
			bw.append(",");
			bw.append(Integer.toString(play.getCorrectGuesses()));
			bw.append(",");
			bw.append(Integer.toString(play.getNumCryptogramsPlayed()));
			bw.append(",");
			bw.append(Integer.toString(play.getNumCryptogramsCompleted()));
			bw.append(",");
  			bw.newLine();
  			bw.close();
  		} catch (IOException e) {
  			System.out.println("An error occurred.");
  			e.printStackTrace();
  		}
  	} 	
}
