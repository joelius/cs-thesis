import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class InputToOutputTest {

    @Test
    public void testCreateJFugueFileFromInput() throws Exception {
        String filename = "ThisOtherNewFile";
        String inputPath = "/Users/jolpatrik/IdeaProjects/harmonator/src/data/input3.txt";
        JFugueDataLoader jdl = new JFugueDataLoader();
        jdl.populateWithDataFile(inputPath);
        JFugueBuilder test = new JFugueBuilder();

        try {
            test.writeFile(jdl.patternStringArray, filename);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        assertEquals(true, new File(inputPath).exists() );
    }
}