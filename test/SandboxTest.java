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
}
