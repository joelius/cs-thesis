import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jolpatrik on 2015-04-02.
 */
public class ChordTest {

    @Test
    public void testTuple (){

        Chord test = new Chord(0,4,7);
        Chord test2 = new Chord(0,4,7);

        assertEquals(true,test.equals(test2));
    }

}