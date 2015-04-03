import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by jolpatrik on 2015-04-03.
 */
public class JNoteMelodyDatumTest {

    @Test
    public void testIsMajor() throws Exception {
        JNoteMelodyDatum test = new JNoteMelodyDatum(new JNote("C5q"), new JNote("C7q"),new int[]{0,1,2},"D-M");
        assertEquals(true, test.isMajor());
    }
}