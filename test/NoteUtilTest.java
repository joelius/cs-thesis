import junit.framework.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class NoteUtilTest {

    @Test
    public void testGetMusicalNote() throws Exception {
        String filename = "ThisNewFile";
        JFugueDataLoader jdl = new JFugueDataLoader();
        jdl.populateWithDataFile("/Users/jolpatrik/IdeaProjects/harmonator/src/data/input.txt");
        JFugueBuilder test = new JFugueBuilder("what");

        try {
            test.writeFile(jdl.patternStringArray, filename);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}