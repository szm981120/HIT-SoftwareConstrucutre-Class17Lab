/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    

	/*
	 * Testing strategy
	 * 	This is a practical situation. So the strategy isn't going to test methods but test the results.
	 * 	There are 3 corpus for test. Test them to see if the result meet expectation.
	 * 
	 *  testDefaultCorpus()
	 *  	This tests the default corpus which the lab provides. It's a simple sentence.
	 *  testSpecificCorpus()
	 *  	This tests a specific corpus which is written by myself.
	 *  	The input is a sentence missing a few key words. The missing words are common bridge words in corpus.
	 *  		So this should test if the program can add some common bridge words.
	 *  testLittleCorpus()
	 *  	This tests a situation that there's only one single word in the corpus.
	 *  	It can test if the program can handle one-word corpus.
	 *  testOneWordInput()
	 *  	This tests a situation that the input only has one single word.
	 *  	It can test if the program can handle one-word input.
	 *  
	 */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    @Test
    public void testDefaultCorpus() throws IOException {
    	try {
    		File corpus = new File("src/P1/poet/mugar-omni-theater.txt");
    		String input = "Test the Mugar";
			assertEquals("expected Test of the Mugar", "Test of the Mugar", new GraphPoet(corpus).poem(input));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void testSpecificCorpus() throws IOException {
    	try {
    		File corpus = new File("src/P1/poet/specificCorpus.txt");
    		String input = "I you just the fish water.";
			assertEquals("expected I need you just like the fish needs water", "I need you just like the fish needs water.", new GraphPoet(corpus).poem(input));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    @Test
    public void testLittleCorpus() {
    	try {
    		File corpus = new File("src/P1/poet/LittleCorpus.txt");
    		String input = "Little";
			assertEquals("expected Little", "Little", new GraphPoet(corpus).poem(input));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @Test
    public void testOneWordInput() {
    	try {
    		File corpus = new File("src/P1/poet/mugar-omni-theater.txt");
    		String input = "Test";
			assertEquals("expected Test.", "Test", new GraphPoet(corpus).poem(input));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}
