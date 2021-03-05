import java.util.*;

public class Game {

	public String playerGameMapping;		// not sure on type
	public Player currentPlayer;
	
	
	private Game() {
		
	}
	
	public void Game(Player p, String cryptoType) {
		
	}
	
	public void Game(Player p) {
		
	}
	
	public void getHint() {
		
	}
	
	public void loadPlayer() {
		
	}
	
	public void playGame() {
		Scanner chooseCryptogram = new Scanner(System.in);  
	    System.out.println("ENTER WHAT TYPE OF CRYPTOGRAM YOU WOULD LIKE TO PLAY ");
	    System.out.println("NUMBER OR LETTER ");

	}
	
	public void generateCryptogram() {
		
	}
	
	public void enterLetter() {
		Scanner letterGuess = new Scanner(System.in);  
	    System.out.println("CHOOSE A LETTER ");
	    Scanner posGuess = new Scanner(System.in);
	    System.out.println("CHOOSE A POSITION FOR YOUR GUESS ");
	    
	    System.out.println();
	}
	
	public void undoLetter() {
		Scanner letterGuess = new Scanner(System.in);  
	    System.out.println("CHOOSE A LETTER TO UNDO");
	    Scanner posGuess = new Scanner(System.in);
	    System.out.println("ENTER THE POSITION OF THE LETTER YOU WOULD LIKE TO UNDO");
	    
	    
	}
	
	
	public void viewFrequencies() {
		
	}
	
	public void saveGame() {
		
	}
	
	public void loadGame() {
		
	}
	
	public void showSolution() {
		
	}
}
