import org.junit.runner.notification.*;
import org.junit.runner.*;

public class TAPListener extends RunListener {

  int testNum = 1;
  boolean passed;

  public void testFailure(Failure failure){
    passed = false;
    Description desc = failure.getDescription();
    System.out.println("not ok " + testNum + " - " + desc.getDisplayName());
    System.out.println("# " + failure.getTrace());
  }

  public void testFinished(Description description) {
    if( passed ){
      System.out.println("ok " + testNum + " - " + description.getDisplayName());
    }
    testNum++;
  }
  
  public void testStarted(Description description) {
    passed = true;
  }
} 

/*
ok 1 - eez ok
not ok 2 - eez not ok
#   Failed test 'eez not ok'
#   at ./example.t line 9.
ok 3 - yes it is
not ok 4 - no it isn't
#   Failed test 'no it isn't'
#   at ./example.t line 12.
#          got: '1'
#     expected: '2'
ok 5 - force pass
not ok 6 - force fail
#   Failed test 'force fail'
#   at ./example.t line 15.
1..6
# Looks like you failed 3 tests of 6.
*/
