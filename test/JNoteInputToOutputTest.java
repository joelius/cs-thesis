import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class JNoteInputToOutputTest {

    @Test
    public void testCreateJFugueFileFromInput() throws Exception {
        String filename = "JFugueTestHornpipe8";
        String inputPath = "/Users/jolpatrik/IdeaProjects/harmonator/src/data/hornpipev2.txt";
        JFugueInputLoader jil = new JFugueInputLoader();
        jil.populateWithDataFile(inputPath);
        JFugueBuilder test = new JFugueBuilder();

        try {
            test.writeFile(jil.patternStringArray, filename);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        assertEquals(true, new File(inputPath).exists());
    }
}