public class Player extends Players {
	
	private String username;
	private int accuracy;
	private int totalGuesses;
	private int correctGuesses;
	private int cryptogramsPlayed;
	private int cryptogramsCompleted;
	
	public Player(String name) {
		username = name;
		accuracy = 0;
		totalGuesses = 0;
		correctGuesses = 0;
		cryptogramsPlayed = 0;
		cryptogramsCompleted = 0;
	}
	
	public void incrementCryptogramsCompleted() {
		cryptogramsCompleted++;
	}
	
	public void incrementCryptogramsPlayed() {
		cryptogramsPlayed++;
	}
	
	public void incrementGuesses() {
		totalGuesses++;
	}
	
	public void incrementCorrGuesses() {
		correctGuesses++;
	}
	
	public double getAccuracy() {
		if (totalGuesses!=0) {
			accuracy = (correctGuesses / totalGuesses) * 100;
		}
		return accuracy;
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
