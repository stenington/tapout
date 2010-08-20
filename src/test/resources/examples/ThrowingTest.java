package examples;

import static org.junit.Assert.*;
import org.junit.Test;

public class ThrowingTest {

	@Test
	public void thisTestThrows(){
		assertTrue(true);
		throw new RuntimeException("barf");
	}
}
