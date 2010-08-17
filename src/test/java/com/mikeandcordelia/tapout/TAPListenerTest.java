package com.mikeandcordelia.tapout;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;

public class TAPListenerTest {

	private ClassLoader loader = ClassLoader.getSystemClassLoader();
	private JUnitCore core = new JUnitCore();
	private TAPListener tapListener;
	private ByteArrayOutputStream out = new ByteArrayOutputStream();
	private ByteArrayOutputStream err = new ByteArrayOutputStream();
	private PrintStream realOut = System.out;
	private PrintStream realErr = System.err;
	
	private class TestFilter implements IOFileFilter {
		
		private Pattern testPattern = Pattern.compile(".*Test\\.java");
		
		public boolean accept(File arg0) {
			if( arg0.isFile() && testPattern.matcher(arg0.getName()).matches() ){
				return true;
			}
			return false;
		}

		public boolean accept(File arg0, String arg1) {
			if( testPattern.matcher(arg1).matches() ){
				return true;
			}
			return false;
		}
	}
	
	@Before
	public void setUp() {
		tapListener = new TAPListener();
		core.addListener(tapListener);
		out.reset();
		err.reset();
	}
	
	@After
	public void reset() {
		core.removeListener(tapListener);
		setUp();
	}
	
	@Test
	public void testExamples() throws Exception {
		File searchDir = new File("src/test/tap");
    @SuppressWarnings("unchecked")
		Iterator<File> exampleTests = FileUtils.iterateFiles(searchDir, new TestFilter(), TrueFileFilter.INSTANCE);
    while(exampleTests.hasNext()) {
    	File exampleTest = exampleTests.next();
    	testExample(exampleTest);
    }
	}
	
	private void testExample(File exampleTest) throws Exception {
		System.setOut(new PrintStream(this.out));
		System.setErr(new PrintStream(this.err));
		runTest(exampleTest);
  	System.setOut(realOut);
  	System.setErr(realErr);
  	File expectedTapFile = getTapFileFor( exampleTest );
  	String expectedTap = FileUtils.readFileToString(expectedTapFile);
  	assertEquals(expectedTap, out.toString());
	}
	
	private void runTest(File exampleTest) throws Exception {
	 	String className = exampleTest.getName().replaceAll("\\.java", "");
  	Class[] classes = {loader.loadClass(className)};
  	core.run(classes);
	}
	
	private File getTapFileFor( File exampleTest ) {
		String name = exampleTest.getName();
		name = name.replaceAll("Test\\.java", ".tap");
		name = name.toLowerCase();
		return new File("src/test/resources/tap/", name);
	}
}
