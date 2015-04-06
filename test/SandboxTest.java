import org.junit.Test;

import java.util.Arrays;

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

    @Test
    public void testNegativeIntegers(){
        int things = 3412;
        assertEquals(-3412, things*-1);
    }

    @Test
    public void testArrayStringStuff(){
        String key = "C";
        String key2 = "C#";

        for (int i = 0; i<JNote.notes.length;i++){
            System.out.print(JNote.notes[i] + "|| " );
            if (JNote.notes[i].equalsIgnoreCase(key)){
                System.out.println(key + " is here at " + i);
            }
            else {
                System.out.println(key + " is NOT at " + i);
            }
            if (JNote.notes[i].equalsIgnoreCase(key2)){
                System.out.println(key2 + " is here at " + i);
            }
            else {
                System.out.println(key2 + " is NOT at " + i);
            }
        }

    }

    @Test
    public void testMajorScaleMinorScale(){
        int[] majorScale = {0,2,4,5,7,9,11};
        int[] minorScale = {0,2,3,5,7,8,10};
        int[] temp = new int[minorScale.length];

        for (int i=0;i<minorScale.length;i++){
            minorScale[i] += 12;
            temp[(i+2)%minorScale.length] = minorScale[i];

        }
        minorScale = temp;
        System.out.println(Arrays.toString(minorScale));
    }
}
