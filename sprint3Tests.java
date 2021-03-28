import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class sprint3Tests {
    private Game game;
    private Player Player;

    @BeforeEach
    void setUp(){
        this.game = new Game();
        this.Player = new Player("test");
    }

    @Test
    void showSolution(){
        numericalCrypto crypto = new numericalCrypto();
        String solution = crypto.phrase;
        String solutionDisplayed = game.showSolution(crypto);
        assertEquals(solution,solutionDisplayed);
    }

    @Test
    void getHint(){
        alphabeticalCrypto crypto1 = new alphabeticalCrypto();
        game.playerGuess = new char[crypto1.encryptedPhrase.length];
        game.intTypeCrypto = 1;

        int count = 0;
        //checks the amount of empty spaces in the user's guess and increments the count where empty
        for(int i = 0;i<game.playerGuess.length;i++) {
            if (game.playerGuess[i] == 0){
                count++;
            }
        }
        game.giveHint(crypto1,Player);
        int secondCount = 0;
        ArrayList<Integer> elements = new ArrayList<>();
        // //checks the amount of empty spaces in the user's guess after a hint is given and increments the count where empty
        for(int i = 0;i<game.playerGuess.length;i++) {
            if (game.playerGuess[i] == 0){
                secondCount++;
            }else{
                //adds the elements of the non-empty parts of the user's guess so it can be checked whether the hint given is correct
                elements.add(i);
            }
        }
        boolean required = false;
        //checks whether the amount of non-empty elements in the guess is lower than previously
        if(secondCount<count){
            required = true;
        }
        assertEquals(required,true);
        //checks whether the hints given match up with the solution
        char[] charSolution = crypto1.phrase.toCharArray();
        for(int i = 0;i<elements.size();i++){
            assertEquals(game.playerGuess[elements.get(i)],charSolution[elements.get(i)]);
        }
    }

     @Test
    void emptyLeaderboard() {
    	File myObj = new File("NoPlayers.txt");
    	boolean required = false;
    	Double topScores[] = new Double [10];
		String topPlayers[] = new String [10];
	
		Double max;
		Double cryptogramsCompleted;
		Double cryptogramsPlayed;

		List<String> players = new ArrayList<String>();
		List<Double> scores = new ArrayList<Double>();
		List<String> words = new ArrayList<String>();
    	try (Scanner sc = new Scanner((myObj), StandardCharsets.UTF_8.name())) {
			while (sc.hasNextLine()) {
				words.add(sc.nextLine());
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
    	for (String line : words) {
			String[] split = line.split(",");
			players.add(split[0]);
			cryptogramsPlayed = (Double.parseDouble(split[4]));
			cryptogramsCompleted = (Double.parseDouble(split[5]));
			if(cryptogramsPlayed!=0) {
				scores.add(cryptogramsCompleted / cryptogramsPlayed);
			}else{
				scores.add(0.0);
			}
		}
    	int size = 10;
    	if(scores.size()<10){
			size = scores.size();
		}
    	if (size==0){
			System.out.println("Error - there are not any scores yet");
			required = true;
		}else {
			for (int i = 0; i < size; i++) {
				System.out.println((i + 1) + ": " + topPlayers[i] + " - " + topScores[i]);
				required = false;
			}
		}

    }
}
