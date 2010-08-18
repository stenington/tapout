package com.mikeandcordelia.tapout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class TAPListener extends RunListener {

  private int testNum = 0;
  private int failedTotal = 0;
  private boolean passed;
  private String message;
  private Pattern gotExpPattern = Pattern.compile("^expected:<(.*)> but was:<(.*)>$");

  public void testStarted(Description description) {
    passed = true;
    testNum++;
    message = messagize(description.getMethodName());
  }
  
  public void testFinished(Description description) {
    if( passed ){
      System.out.println("ok " + testNum + " - " + message );
    }
  }
  
  public void testFailure(Failure failure){
  	passed = false;
  	failedTotal++;
  	Throwable ex = failure.getException();
  	StackTraceElement[] trace = ex.getStackTrace();
  	StackTraceElement testFrame = null;
  	int i = 0;
  	while( i < trace.length && testFrame == null ){
  		if( trace[i].getClassName().equals(failure.getDescription().getClassName()) ){
  			testFrame = trace[i];
  		}
  		i++;
  	}
    System.out.println("not ok " + testNum + " - " + message);
    System.err.println("#   Failed test '" + message + "'");
    System.err.println("#   at "+testFrame.getFileName()+" line "+testFrame.getLineNumber()+".");
    String explanation = ex.getMessage();
    Matcher m = gotExpPattern.matcher(explanation);
    if( m.matches() ){
    	String got = m.group(2);
    	String exp = m.group(1);
    	System.err.println("#          got: '"+got+"'");
    	System.err.println("#     expected: '"+exp+"'");
    }
  }
  
  private String messagize( String displayName ) {
  	String[] words = displayName.split("(?<=[a-z])(?=[A-Z])");
  	String msg = "";
  	for(int i=0; i < words.length; i++){
  		msg += words[i].toLowerCase();
  		if( i < words.length-1 ){
  			msg += " ";
  		}
  	}
  	return msg;
  }
  
  public void testRunFinished(Result result) {
  	System.out.println("1.." + testNum);
  	if( failedTotal > 0 ){
  		System.err.println("# Looks like you failed "+failedTotal+" tests of "+testNum+".");
  	}
  }
} 

