import static org.junit.Assert.*;
import org.junit.Test;

public class PassingTest {
  public PassingTest() {
  }

  @Test
  public void fine(){
  	assertTrue(true);
  }
  
  @Test
  public void oneIsOne(){
  	assertEquals(1, 1);
  }
}
