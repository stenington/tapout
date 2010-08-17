import static org.junit.Assert.*;
import org.junit.Test;

public class ExampleTest {
  public ExampleTest() {
  }

  @Test
  public void eezOk(){
    assertTrue(1 == 1);
  }

  @Test
  public void eezNotOk(){
    assertTrue(0 == 1);
  }
  
  @Test
  public void yesItIs(){
  	assertEquals(1,1);
  }
  
  @Test 
  public void noItIsnt(){
  	assertEquals(2,1);
  }
  
  @Test
  public void forcePass(){
  	assertTrue(true);
  }
  
  @Test
  public void forceFail(){
  	fail();
  }
}
