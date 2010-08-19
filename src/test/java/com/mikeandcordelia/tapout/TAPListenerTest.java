package com.mikeandcordelia.tapout;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.output.ByteArrayOutputStream;
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
			if (arg0.isFile() && testPattern.matcher(arg0.getName()).matches()) {
				return true;
			}
			return false;
		}

		public boolean accept(File arg0, String arg1) {
			if (testPattern.matcher(arg1).matches()) {
				return true;
			}
			return false;
		}
	}

	/* NO @Before here! We're calling this manually. */
	public void setUp() {
		// set listener
		tapListener = new TAPListener();
		core.addListener(tapListener);
		// reset out & err catchers
		out.reset();
		err.reset();
	}

	/* NO @After here! We're calling this manually. */
	public void tearDown() {
		core.removeListener(tapListener);
	}

	/* This test is actually going to manually run a bunch of examples,
	 * and fail if any example fails. 
	 */
	@Test
	public void testExamples() {
		File searchDir = new File("src/test/resources/tap");
		@SuppressWarnings("unchecked")
		Iterator<File> exampleTests = FileUtils.iterateFiles(searchDir,
				new TestFilter(), TrueFileFilter.INSTANCE);
		assertTrue(foundExamples(exampleTests));
		int examplesRun = 0;
		while (exampleTests.hasNext()) {
			File exampleTest = exampleTests.next();
			setUp();
			testExample(exampleTest);
			tearDown();
			examplesRun++;
		}
		assertTrue(examplesRun >= 4);
	}
	
	private boolean foundExamples(Iterator<File> it){
		return it.hasNext();
	}

	private void testExample(File exampleTest) {
		try {
			File expectedTapFile = getTapFileFor(exampleTest);
			String expectedTap = FileUtils.readFileToString(expectedTapFile);
			File expectedTapErrFile = new File(expectedTapFile.getAbsoluteFile()
					+ ".err");
			String expectedErr = FileUtils.readFileToString(expectedTapErrFile);
			grabOutAndErr();
			runTestWithTAPListener(exampleTest);
			assertEquals(expectedTap, out.toString());
			assertCloseEnough(expectedErr, err.toString());
			releaseOutAndErr();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void releaseOutAndErr() {
		System.setOut(realOut);
		System.setErr(realErr);
	}

	private void grabOutAndErr() {
		System.setOut(new PrintStream(this.out));
		System.setErr(new PrintStream(this.err));
	}

	private void runTestWithTAPListener(File exampleTest) {
		String pkg = "tap.";
		String className = pkg + exampleTest.getName().replaceAll("\\.java", "");
		try {
			Class<?>[] classes = { loader.loadClass(className) };
			core.run(classes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private File getTapFileFor(File exampleTest) {
		String name = exampleTest.getName();
		name = name.replaceAll("Test\\.java", ".tap");
		name = name.toLowerCase();
		return new File("src/test/resources/tap/", name);
	}

	private void assertCloseEnough(String expErr, String gotErr) {
		String file = "(?<=\\bat ).*?(?= line\\b)";
		String lineNo = "(?<=\\bline )\\d+(?=\\.)";
		expErr = expErr.replaceAll(file, "");
		expErr = expErr.replaceAll(lineNo, "");
		gotErr = gotErr.replaceAll(file, "");
		gotErr = gotErr.replaceAll(lineNo, "");
		assertEquals(expErr, gotErr);
	}
}
