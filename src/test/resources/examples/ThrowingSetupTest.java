package examples;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class ThrowingSetupTest {
	
	@Before
	public void setUp(){
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
