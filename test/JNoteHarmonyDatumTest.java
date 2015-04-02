import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by jolpatrik on 2015-04-01.
 */
public class JNoteHarmonyDatumTest {

    JNote n1 = new JNote("C5q");
    JNote n2 = new JNote("D5q");
    JNote n3 = new JNote("E5q");

    @Test
    public void testProcessIntervalTrend() throws Exception {
        Integer[] testA = {3,4,3,4,3};
        int result = 3;
        ArrayList<Integer> test = new ArrayList<Integer>(Arrays.asList(testA));

        JNoteHarmonyDatum test1 = new JNoteHarmonyDatum(n1,n2,n3,test,"");
        assertEquals(result, test1.processIntervalTrend());

        Integer[] testA2 = {1,4,2,4,4};
        int result2 = 4;

        ArrayList<Integer> test2 = new ArrayList<Integer>(Arrays.asList(testA2));

        JNoteHarmonyDatum test3 = new JNoteHarmonyDatum(n1,n3,n2,test2,"");
        assertEquals(result2, test3.processIntervalTrend());
    }
}