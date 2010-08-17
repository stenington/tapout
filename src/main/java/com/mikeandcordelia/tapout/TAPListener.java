package com.mikeandcordelia.tapout;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class TAPListener extends RunListener {

  private int testNum = 0;
  private boolean passed;
  private String message;

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
    System.out.println("not ok " + testNum + " - " + message);
    //System.out.println("# " + failure.getTrace());
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
  }
} 

