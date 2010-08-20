package examples;

import static org.junit.Assert.*;
import org.junit.Test;

public class ThrowingInitializerTest {

	public ThrowingInitializerTest(){
		throw new RuntimeException("barf");
	}
	
	@Test
	public void iWillRunAndFail(){
		assertTrue(true);
	}
	
	@Test
	public void soWillI(){
		assertTrue(true);
	}
	
}
