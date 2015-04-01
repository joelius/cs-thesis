import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jolpatrik on 2015-04-01.
 */
public class JNoteHarmonyDatumTest {

    @Test
    public void testProcessIntervalTrend() throws Exception {
        int[] test = {3,4,3,4};
        double result = 3.5;
        JNoteHarmonyDatum test1 = new JNoteHarmonyDatum(null,null,null,test);
        assertEquals(result,test1.processIntervalTrend(test),0.0);

        int[] test2 = {1,4,2,4,4};
        double result2 = 3;
        JNoteHarmonyDatum test3 = new JNoteHarmonyDatum(null,null,null,test2);
        assertEquals(result2, test3.processIntervalTrend(test2), 0.0);
    }
}