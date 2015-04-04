import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Created by jolpatrik on 2015-04-04.
 */
public class JNoteHashKeyTest {

    @Test
    public void testEquals() throws Exception {

        ArrayList<Integer> templist = new ArrayList<Integer>();
        templist.add(3);
        templist.add(4);
        templist.add(6);

        HashMap<JNoteHashKey,Integer> test = new HashMap<JNoteHashKey, Integer>();
        JNoteHarmonyDatum jnhd = new JNoteHarmonyDatum(new JNote("C4q"),new JNote("C3q"),new JNote("E4q"),templist,"C-M");
        JNoteHarmonyDatum jnhd2 = new JNoteHarmonyDatum(new JNote("G4q"),new JNote("F3q"),new JNote("E4q"),templist,"D-M");

        JNoteHashKey test1 = new JNoteHashKey(jnhd,0);
        JNoteHashKey test2 = new JNoteHashKey(jnhd,0);
        JNoteHashKey test3 = new JNoteHashKey(jnhd2,0);


        test.put(test1,10);

        System.out.println(test1.toString());
        System.out.println(test2.toString());
        System.out.println(test3.toString());

        System.out.println(test.get(test1));
        System.out.println(test.get(test2));

        System.out.println(test1.equals(test2));
        System.out.println(test1.equals(test3));

        assertEquals(true,test1.equals(test2));
        assertEquals(true,test1.equals(test3));
    }

    @Test
    public void testToString() throws Exception {

    }
}