public class Player extends Players {
	
	private String username;
	private double accuracy;
	private int totalGuesses;
	private int correctGuesses;
	private int cryptogramsPlayed;
	private int cryptogramsCompleted;
	
	public Player(String name) {
		username = name;
		accuracy = 0;
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
	
	public double getAccuracy() {
		return (((double) correctGuesses / (double) totalGuesses) * 100);
	}
	
	public void setAccuracy(double set) {
		accuracy = set;
	}
	
	public int getNumCryptogramsCompleted() {
		return cryptogramsCompleted;
	}
	
	public void setNumCryptogramsCompleted(int set) {
		cryptogramsCompleted = set;
	}
	
	public int getNumCryptogramsPlayed() {
		return cryptogramsPlayed;
	}
	
	public void setNumCryptogramsPlayed(int set) {
		cryptogramsPlayed = set;
	}
	
	public int getGuesses() {
		return totalGuesses;
	}
	
	public void setGuesses(int set) {
		totalGuesses = set;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String name) {
		username = name;
	}
	
	public int getCorrectGuesses() {
		return correctGuesses;
	}
	
	public void setCorrectGuesses(int set) {
		correctGuesses = set;
	}
}
