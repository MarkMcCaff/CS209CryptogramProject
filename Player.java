public class Player {
	
	private String username;
	private int totalGuesses;
	private int correctGuesses;
	private int cryptogramsPlayed;
	private int cryptogramsCompleted;
	
	public Player(String name) {
		username = name;
		totalGuesses = 0;
		cryptogramsPlayed = 0;
		cryptogramsCompleted = 0;
	}
	
	public void incremementCryptogramsCompleted() {
		cryptogramsCompleted++;
	}
	
	public void incremementCryptogramsPlayed() {
		cryptogramsPlayed++;
	}
	
	public void incrementGuesses() {
		totalGuesses++;
	}
	
	public void incrementCorrGuesses() {
		totalGuesses++;
		correctGuesses++;
	}
	
	public int getAccuracy() {
		return ((correctGuesses / totalGuesses) * 100);
	}
	
	public int getNumCryptogramsCompleted() {
		return cryptogramsCompleted;
	}
	
	public int getNumCryptogramsPlayed() {
		return cryptogramsPlayed;
	}
	
	public int getGuesses() {
		return totalGuesses;
	}

	public String getUsername() {
		return username;
	}
	
	public int getCorrectGuesses() {
		return correctGuesses;
	}
}
