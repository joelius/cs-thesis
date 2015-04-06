import org.junit.Test;

import static org.junit.Assert.*;

public class NoteUtilTest {

    @Test
    public void testGetMusicalNote() throws Exception {
        String filename = "ThisNewFile";
        JFugueDataLoader jdl = new JFugueDataLoader();
        jdl.populateWithDataFile("/Users/jolpatrik/IdeaProjects/harmonator/src/data/input.txt");
        JFugueBuilder test = new JFugueBuilder();

        try {
            test.writeFile(jdl.patternStringArray, filename);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testEightyEightConversion() throws Exception {
        String note = "C5";
        assertEquals(48,NoteUtil.getIntFromOctaveNotation(note));
        assertEquals("C5",NoteUtil.getStringFromIntEightyEight(NoteUtil.getIntFromOctaveNotation("C5")));

        for (int i=0;i<89;i++){
            System.out.println(i + ": " + NoteUtil.getStringFromIntEightyEight(i));
        }

        int note2 = 60;
        assertEquals("C6",NoteUtil.getStringFromIntEightyEight(note2));

        String note3 = "F#4";
        assertEquals(note3,NoteUtil.getStringFromIntEightyEight(NoteUtil.getIntFromOctaveNotation(note3)));

        int note4 = 61;
        assertEquals("C#6",NoteUtil.getStringFromIntEightyEight(note4));



    }
}