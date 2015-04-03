import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created by jolpatrik on 2015-04-03.
 */
public class SandboxTest {

    @Test
    public void testStringManipulation(){
        String test = "123456";
        assertEquals("12",test.substring(0,2));
    }
}
