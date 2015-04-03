import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jolpatrik on 2015-04-03.
 */
public class JNoteMelodyDatumTest {

    @Test
    public void testIsMajor() throws Exception {
        JNoteMelodyDatum test = new JNoteMelodyDatum(new JNote("C5q"), new JNote("C7q"),new int[]{0,1,2},"D-M");
        assertEquals(true, test.isInMajorKey());
    }

    @Test
    public void testNormalizeToCScale() throws Exception {
        JNoteMelodyDatum test = new JNoteMelodyDatum(new JNote("A5q"), new JNote("F4q"), new int[]{0,1,2},"F-M");

        System.out.println(test.toString());

        test = test.normalizedToCScale();
        assertEquals("E5q",test.nt.toString());
    }

    @Test
    public void testKeyToInt() throws Exception {
        JNoteMelodyDatum test = new JNoteMelodyDatum(new JNote("A5q"), new JNote("F4q"), new int[]{0,1,2},"F#-M");

        System.out.println(test.toString());

        test = test.normalizedToCScale();
        assertEquals("D#5q", test.nt.toString());
    }

}