package com.mikeandcordelia.tapout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.runner.notification.Failure;

public class Test {

	private static Pattern gotExpPattern = Pattern
			.compile("^expected:<(.*)> but was:<(.*)>$");

	private int testNumber;
	private boolean passed;
	private String testName;
	private Failure failure;

	public Test(int num, String testMethod) {
		testNumber = num;
		passed = true;
		testName = messagize(testMethod);
	}

	private String messagize(String displayName) {
		String[] words = displayName.split("(?<=[a-z])(?=[A-Z])");
		String msg = "";
		for (int i = 0; i < words.length; i++) {
			msg += words[i].toLowerCase();
			if (i < words.length - 1) {
				msg += " ";
			}
		}
		return msg;
	}

	public void fail(Failure f) {
		passed = false;
		failure = f;
	}

	public void printResult() {
		if( passed ) {
			printPass();
		}
		else {
			printFail();
		}
	}

	public void printPass() {
		System.out.println("ok " + testNumber + " - " + testName);
	}
	
	private void printFail() {
		printBasicFailure(failure);
		maybePrintAdditionalFailure(failure);
	}

	private void printBasicFailure(Failure failure) {
		StackTraceElement testFrame = findPointOfFailure(failure);
    String fileName = testFrame.getFileName();
    int lineNo = testFrame.getLineNumber();
    System.out.println("not ok " + testNumber + " - " + testName);
    System.err.println("#   Failed test '" + testName + "'");
    System.err.println("#   at " + fileName + " line " + lineNo + ".");
	}

	private StackTraceElement findPointOfFailure(Failure failure) {
		StackTraceElement testFrame = null;
		Throwable ex = failure.getException();
		StackTraceElement[] trace = ex.getStackTrace();
		int i = 0;
		while (i < trace.length && testFrame == null) {
			if (trace[i].getClassName().equals(
					failure.getDescription().getClassName())) {
				testFrame = trace[i];
			}
			i++;
		}
		return testFrame;
	}

	private void maybePrintAdditionalFailure(Failure failure) {
		String explanation = failure.getException().getMessage();
    if( explanation != null ) {
      Matcher m = gotExpPattern.matcher(explanation);
      if (m.matches()) {
        String got = m.group(2);
        String exp = m.group(1);
        System.err.println("#          got: '" + got + "'");
        System.err.println("#     expected: '" + exp + "'");
      }
    }
	}

}
