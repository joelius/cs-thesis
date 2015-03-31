import junit.framework.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class NoteUtilTest {

    @Test
    public void testGetMusicalNote() throws Exception {
        assertEquals("D",NoteUtil.getMusicalNote(1,'D',true));
        assertEquals("E",NoteUtil.getMusicalNote(3,'C',true));
        assertEquals("A",NoteUtil.getMusicalNote(3,'F',true));
        assertEquals("F#",NoteUtil.getMusicalNote(3,'D',true));
        assertEquals("C",NoteUtil.getMusicalNote(3,'A',false));
        assertEquals("D",NoteUtil.getMusicalNote(5,'G',false));
    }
}