import java.io.File;
import java.io.FileWriter
import java.util.*;


public class Players implements Player{

	public ArrayList <String> playerList;	
	
	
	
	public void addPlayer(Player p, Scanner sc){
		System.out.prinln("Enter your desired username: ");
		String newUserName = sc.next();
		try {
     	FileWriter myWriter = new FileWriter("C:\\Users\\alex\\Desktop\\players.txt");
     	 	myWriter.write(newUserName);
     		myWriter.close();
     		System.out.println("Successfully created new player: " + newUserName);
     		username = newUserName;
    	catch (IOException e) {
     		System.out.println("An error occurred.");
		    e.printStackTrace();
		    }
	}
		
		
	public void savePlayers() {
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
			myWriter.write(newUserName + cryptogramsPlayed + cryptogramsCompleted + correctGuesses);
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
