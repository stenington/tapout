package com.mikeandcordelia.tapout;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestFinderTest {

	@Test
	public void findsTests() throws Exception {
		TestFinder tf = new TestFinder("tap.*");
		List<Class<?>> testClasses = tf.find();
		assertEquals(4, testClasses.size());
		List<Class<?>> expectedClasses = new ArrayList<Class<?>>();
		expectedClasses.add(Class.forName("tap.ExampleTest"));
		expectedClasses.add(Class.forName("tap.NoTestTest"));
		expectedClasses.add(Class.forName("tap.PassingIsTest"));
		expectedClasses.add(Class.forName("tap.PassingOkTest"));
		assertEquals(expectedClasses, testClasses);
	}
	
	@Test
	public void findsTestMoreThanOneDirectoryDeep() throws Exception {
		TestFinder tf = new TestFinder("com.mikeandcordelia.*");
		List<Class<?>> testClasses = tf.find();
		assertTrue(testClasses.contains(Class.forName("com.mikeandcordelia.tapout.TestFinderTest")));
	}
}
