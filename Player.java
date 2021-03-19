public class Player extends Players {
	
	private String username;
	private double accuracy;
	private int totalGuesses;
	private int correctGuesses;
	private int cryptogramsPlayed;
	private int cryptogramsCompleted;
	
	public Player(String name) {
		username = name;
		accuracy = getAccuracy();
		totalGuesses = getGuesses();
		correctGuesses = getCorrectGuesses();
		cryptogramsPlayed = getNumCryptogramsPlayed();
		cryptogramsCompleted = getNumCryptogramsCompleted();
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
			accuracy = ((Double.valueOf(correctGuesses)) / (Double.valueOf(totalGuesses))) * 100;
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

	public void setAccuracy(double set) {
		accuracy = set;
	}

	public void setNumCryptogramsCompleted(int set) {
		cryptogramsCompleted = set;
	}

	public void setNumCryptogramsPlayed(int set) {
		cryptogramsPlayed = set;
	}

	public void setGuesses(int set) {
		totalGuesses = set;
	}

	public void setUsername(String set) {
		username = set;
	}

	public void setCorrectGuesses(int set) {
		correctGuesses = set;
	}
}
