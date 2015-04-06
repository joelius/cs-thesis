import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class InputToOutputTestHmy {

    @Test
    public void testCreateJFugueFileFromInput() throws Exception {
        String filename = "ThisNewFile";
        String inputPath = "/Users/jolpatrik/IdeaProjects/harmonator/src/data/input4.txt";
        JFugueDataLoaderSingle jdls = new JFugueDataLoaderSingle();
        jdls.populateWithDataFile(inputPath);
        JFugueBuilder test = new JFugueBuilder();

        try {
            test.writeFile(jdls.patternStringArray, filename);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        assertEquals(true, new File(inputPath).exists());
    }
}