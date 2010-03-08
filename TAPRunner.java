import org.junit.runner.notification.*;
import org.junit.runner.*;

public class TAPRunner {

  public TAPRunner(){
  }

  public static void main(String[] args){
    JUnitCore core = new JUnitCore();
    core.addListener(new TAPListener());
    Class[] classes = {ExampleTest.class};
    core.run(classes);
  }

}

