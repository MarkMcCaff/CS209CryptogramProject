import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class sprint2Tests {
    private Game game;
    private alphabeticalCrypto alphabeticalCrypto;
    private numericalCrypto numericalCrypto;
    private Player Player;

    @BeforeEach
    void setUp(){
        this.game = new Game();
        this.Player = new Player("test");
        this.alphabeticalCrypto = new alphabeticalCrypto();
        this.numericalCrypto = new numericalCrypto();

        game.playerGuess = new char[5];
        game.playerGuess[0] = 'G';
        game.playerGuess[1] = 'T';
        game.playerGuess[2] = 'E';
        game.playerGuess[3] = 'S';
        game.playerGuess[4] = 'T';
    }

    @Test
    void saveCryptogram(){
        Scanner sc1 = new Scanner(System.in);
        String loadUsername = "";
        String loadSolution = "";
        char[] ch1 = new char[alphabeticalCrypto.encryptedPhrase.length];
        char[] ch2 = new char[game.playerGuess.length];
        int[] ch3 = new int[numericalCrypto.intEncryptedPhrase.length];

        //tests saving a letter crypto
        String generatedString = randomString();
        Player.setUsername(generatedString);

        game.saveGame(alphabeticalCrypto,Player,sc1);

        File myObj = new File("savedLetterCryptos.txt");
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
            if (split[0].equals(Player.getUsername())) {
                loadUsername = split[0];
                String loadPuzzle = split[1];
                String loadGuess = split[2];
                loadSolution = split[3];
                for (int i = 0; i < loadPuzzle.length(); i++) {
                    ch1[i] = loadPuzzle.charAt(i);
                }
                for (int i = 0; i < loadGuess.length(); i++) {
                    ch2[i] = loadGuess.charAt(i);
                }
            }
        }
        assertEquals(loadUsername, Player.getUsername());
        assertEquals(loadSolution, alphabeticalCrypto.phrase);
        for(int i=0;i<ch1.length;i++) {
            assertEquals(ch1[i], alphabeticalCrypto.encryptedPhrase[i]);
        }
        for(int i=0;i<ch2.length;i++) {
            assertEquals(ch2[i], game.playerGuess[i]);;
        }

        //tests saving a number crypto
        generatedString = randomString();
        Player.setUsername(generatedString);

        game.saveGame(numericalCrypto,Player,sc1);

        File myObj2 = new File("savedNumberCryptos.txt");
        List<String> words2 = new ArrayList<String>();
        try(Scanner sc = new Scanner((myObj2), StandardCharsets.UTF_8.name())) {
            while(sc.hasNextLine()) {
                words2.add(sc.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String line : words2){
            String [] split = line.split(",");
            if (split[0].equals(Player.getUsername())) {
                loadUsername = split[0];
                String loadGuess = split[1];
                loadSolution = split[2];
                for (int i = 0; i < loadSolution.length(); i++) {
                    ch3[i] = Integer.parseInt(split[i + 3]);
                }
                for (int i = 0; i < loadGuess.length(); i++) {
                    ch2[i] = loadGuess.charAt(i);
                }
            }
        }
        assertEquals(loadUsername, Player.getUsername());
        assertEquals(loadSolution, numericalCrypto.phrase);
        for(int i=0;i<ch3.length;i++) {
            assertEquals(ch3[i], numericalCrypto.intEncryptedPhrase[i]);
        }
        for(int i=0;i<ch2.length;i++) {
            assertEquals(ch2[i], game.playerGuess[i]);
        }
    }

    @Test
    void loadCryptogram(){
        //tests loading a letter crypto
        Scanner sc1 = new Scanner(System.in);
        alphabeticalCrypto crypto1 = new alphabeticalCrypto();
        String generatedString = randomString();
        Player.setUsername(generatedString);
        char[] loadPuzzle = crypto1.encryptedPhrase;
        String loadSolution = crypto1.phrase;
        char[] guess = game.playerGuess;
        game.saveGame(crypto1,Player,sc1);
        crypto1.phrase = "";
        crypto1.encryptedPhrase = new char[8];
        game.playerGuess = new char[10];
        game.loadGame(crypto1, Player);

        assertEquals(loadSolution, crypto1.phrase);
        for(int i=0;i<loadPuzzle.length;i++) {
            assertEquals(loadPuzzle[i], crypto1.encryptedPhrase[i]);
        }
        for(int i=0;i<guess.length;i++) {
            assertEquals(guess[i], game.playerGuess[i]);
        }

        //tests loading a number crypto
        Scanner sc2 = new Scanner(System.in);
        numericalCrypto crypto2 = new numericalCrypto();
        generatedString = randomString();
        Player.setUsername(generatedString);
        int[] loadIntPuzzle = crypto2.intEncryptedPhrase;
        loadSolution = crypto2.phrase;
        guess = game.playerGuess;
        game.saveGame(crypto2,Player,sc2);
        crypto1.phrase = "";
        crypto1.intEncryptedPhrase = new int[8];
        game.playerGuess = new char[10];
        game.loadGame(crypto2, Player);

        assertEquals(loadSolution, crypto2.phrase);
        for(int i=0;i<loadIntPuzzle.length;i++) {
            assertEquals(loadIntPuzzle[i], crypto2.intEncryptedPhrase[i]);
        }
        for(int i=0;i<guess.length;i++) {
            assertEquals(guess[i], game.playerGuess[i]);
        }
    }

    @Test
    void savePlayerDetails() {
        this.Player = new Player("testUser");
        Player.setCorrectGuesses(10);
        Player.setGuesses(20);
        Player.setNumCryptogramsPlayed(5);
        Player.setNumCryptogramsCompleted(1);
        Player.setAccuracy(0.5);
        Player.savePlayers(Player);
        String name = "";Double accuracy = -1.0;int guesses = -1;int corrGuesses = -1;int cryptosPlayed = -1;int cryptosCompleted = -1;
        File myObj = new File("savedPlayers.txt");
        List<String> words = new ArrayList<String>();
        try (Scanner sc2 = new Scanner((myObj), StandardCharsets.UTF_8.name())) {
            while (sc2.hasNextLine()) {
                words.add(sc2.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String line : words) {
            String[] split = line.split(",");
            if (split[0].equals("testUser")) {
                name = split[0];
                accuracy = Double.parseDouble(split[1]);
                guesses = Integer.parseInt(split[2]);
                corrGuesses = Integer.parseInt(split[3]);
                cryptosPlayed = Integer.parseInt(split[4]);
                cryptosCompleted = Integer.parseInt(split[5]);
            }
        }
        assertEquals(name, Player.getUsername());
        assertEquals(accuracy, Player.getAccuracy());
        assertEquals(guesses, Player.getGuesses());
        assertEquals(corrGuesses, Player.getCorrectGuesses());
        assertEquals(cryptosPlayed, Player.getNumCryptogramsPlayed());
        assertEquals(cryptosCompleted, Player.getNumCryptogramsCompleted());
    }

    @Test
    void trackCryptosCompleted() {
        int original = Player.getNumCryptogramsCompleted();
        alphabeticalCrypto crypto = new alphabeticalCrypto();
        game.playerGuess = crypto.encryptedPhrase;
        char[] temp = crypto.encryptedPhrase;
        game.checkCorrectness(temp, Player, 1);
        assertEquals(Player.getNumCryptogramsCompleted(),original + 1);
    }

    @Test
    void trackCryptosPlayed() {
        Scanner sc = new Scanner(System.in);
        int original = Player.getNumCryptogramsPlayed();
        game.generateCryptogram(sc,2,Player,1);
        assertEquals(Player.getNumCryptogramsPlayed(),original+1);
    }

    @Test
    void trackCorrectGuesses() {
        alphabeticalCrypto crypto = new alphabeticalCrypto();
        int original = Player.getCorrectGuesses();
        char[] encryption = crypto.getEncryption();
        char guess = crypto.getPhrase().charAt(1);
        char replacer = crypto.encryptedPhrase[1];
        char[] temp = crypto.getPhrase().toUpperCase().toCharArray();
        game.playerGuess = new char[crypto.encryptedPhrase.length];
        game.enterLetterAlphHelper(temp, guess, replacer, encryption, Player);
        assertEquals(Player.getCorrectGuesses(),original + 1);
    }

    @Test
    void loadPlayerDetails(){
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        Player.setUsername(randomString());
        String username = Player.getUsername();
        Player.setAccuracy(35.0);
        Player.setGuesses(100);
        Player.setCorrectGuesses(35);
        Player.setNumCryptogramsPlayed(12);
        Player.setNumCryptogramsCompleted(4);
        Player.writeToFile(Player);
        Player.addPlayer(Player);
        assertEquals(Player.getUsername(),username);
        assertEquals(Player.getAccuracy(),35.0);
        assertEquals(Player.getGuesses(),100);
        assertEquals(Player.getCorrectGuesses(),35);
        assertEquals(Player.getNumCryptogramsPlayed(),12);
        assertEquals(Player.getNumCryptogramsCompleted(),4);
    }

    public String randomString() {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        return generatedString;
    }

}
