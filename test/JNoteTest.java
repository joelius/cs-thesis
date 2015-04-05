import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jolpatrik on 15-03-31.
 */
public class JNoteTest {

    @Test
    public void testConstructor() throws Exception {
        JNote test0 = new JNote("E2w");
        assertEquals(1,test0.getDuration());
        assertEquals('E',test0.note);
        assertEquals(2,test0.octave);
        assertEquals(false,test0.isSharp);
    }

    @Test
    public void testGetIntegerRepresentation() throws Exception {
        JNote test1 = new JNote("C4q");

    }

    @Test
    public void testToString() throws Exception {

    }

    @Test
    public void testGetDuration() throws Exception {

    }

    @Test
    public void testToCScale() throws Exception {
        JNote test1 = new JNote("C5q");
        assertEquals(0,test1.noteAsIntegerInCScale());

        JNote test2 = new JNote("A5q");
        assertEquals(9,test2.noteAsIntegerInCScale());

        JNote test3 = new JNote("G5q");
        assertEquals(7,test3.noteAsIntegerInCScale());

    }
    @Test
    public void testAugmentThird() throws Exception {
        JNote test1 = new JNote("C#5q");
        JNote test2 = new JNote("F5q");
        assertEquals(true,test2.isIntervalXAboveNoteY(JNote.MAJOR_THIRD, test1));
    }

    @Test
    public void testIntToStringAndBack() throws Exception {
        JNote test1 = new JNote("A5q");

        assertEquals(57,test1.asInt());

        JNote test2 = new JNote(57,'q');

        assertEquals("A5q",test2.toString());

        JNote test3 = new JNote(test1.asInt(), test1.getDurationAsChar());


        assertEquals(test1.toString(), test3.toString());
        assertEquals(test1.asInt(), test3.asInt());
    }

    @Test
    public void testJNoteStringBuilder() throws Exception {
        int hmyInCScale = 4;
        String key = "C-M";
        JNote jn = new JNote("C5q");

        assertEquals("E5q", JNote.jNoteStringBuilder(hmyInCScale,key,jn));

    }
}