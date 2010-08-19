package com.mikeandcordelia.tapout;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class TAPListener extends RunListener {

	private Test currentTest;
	private int testNum = 0;
	private int failedTotal = 0;
	private boolean noTests = false;

	@Override
	public void testStarted(Description description) {
		testNum++;
		currentTest = new Test(testNum, description.getMethodName());
	}

	@Override
	public void testFinished(Description description) {
		if( !noTests ){
			currentTest.printResult();
		}
	}

	@Override
	public void testFailure(Failure failure) {
		failedTotal++;
		String msg = failure.getMessage();
		if (msg != null && msg.matches("No runnable methods")) {
			noTests = true;
		}
		else {
			currentTest.fail(failure);
		}
	}
	
	@Override
	public void testRunFinished(Result result) {
		if (noTests) {
			System.err.println("# No tests run!");
		} else {
			System.out.println("1.." + testNum);
			if (failedTotal > 0) {
				System.err.println("# Looks like you failed " + failedTotal
						+ " tests of " + testNum + ".");
			}
		}
	}

}
