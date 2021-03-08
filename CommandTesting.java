import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Before;
import org.junit.jupiter.api.Test;

class CommandTesting {
	
	@Test
public void genABC() { //Checks if Letter Crypto is generated
		
		String input = "1";
		InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    
		assertNotNull(Cryptogram.getPhrase());
	}
	
	@Test
public void genNum() { //Checks if Number Crypto is Generated
		
		String input = "2";
		InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    
		assertNotNull(Cryptogram.getPhrase());
	}


	@Test
public void Enter() { //Enters Value and checks if it is input to playerGuess
		
		String input = "1";
		InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "1";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "A";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "A";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    
		assertEquals("A", Game.playerGuess);
	}
	
	@Test
public void EnterAssignedCrypto() { //Enters Value and checks if it overwrites
		
		String input = "1";
		InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "1";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "A";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "A";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "1";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "A";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "B";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
		assertNotNull(Game.playerGuess);
	}

	@Test
public void EnterAssignedUser() {
		
		String input = "1";
		InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "1";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "A";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "A";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "1";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "B";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "A";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    
		assertNotNull(Game.playerGuess);
	}
	
	@Test
public void EnterFinish() {
		
		String input = "1";
		InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "1";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "A";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "A";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    
		assertEquals("Congratulations! You got the answer!", outputStreamCaptor.toString().trim());
	}
	
	@Test
public void EnterUnusable() {
		
		String input = "1";
		InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "1";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "BANANA";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "A";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    
	    assertEquals("The letter you tried to replace was not in the puzzle", outputStreamCaptor.toString().trim());
	}
	
	@Test
public void undo() { //Undoes value
		
		String input = "1";
		InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "1";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "A";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "A";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "2";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "A";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
		assertNull(Game.playerGuess);
	}
	
	@Test
public void undoUnmap() { //Undoes value
		
		String input = "1";
		InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "1";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "A";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "A";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "2";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	    input = "Apple";
	    in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
		assertNull(Game.playerGuess);
	}
	@Before
	public void setUp() {
	    System.setOut(new PrintStream(outputStreamCaptor));
	}
}
