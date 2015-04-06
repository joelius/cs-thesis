import org.junit.Assert;
import org.junit.Test;

public class JFugueBuilderTest {

    @Test
    public void testWriter(){

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

}