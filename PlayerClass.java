public class Player {
	
	private String username;
	private int accuracy;
	private int totalGuesses;
	private int cryptogramsPlayed;
	private int cryptogramsCompleted;
	
	
	
	public Player() {
		
	}
	
	public void updateAccuracy() {
		// accuracy = ?correctGuesses?/totalGuesses
	}
	
	public void incremementCryptogramsCompleted() {
		
	}
	
	public void incremementCryptogramsPlayed() {
		
	}
	
	//getters
	
	public int getAccuracy() {
		return accuracy;
	}
	
	public int getNumCryptogramsCompleted() {
		return cryptogramsCompleted;
	}
	
	public int getNumCryptogramsPlayed() {
		return cryptogramsPlayed;
	}
}
