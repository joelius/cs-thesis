import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jolpatrik on 2015-04-06.
 */
public class HarmonyGenerationSuiteTest {

    @Test
    public void testRun() throws Exception {
        String melody = "/Users/jolpatrik/IdeaProjects/harmonator/src/data/usoldghosts.txt";
        String dataToUse = "/Users/jolpatrik/IdeaProjects/harmonator/src/data/hashmapfillupAmin.txt";
        String output = "HGSGhostsvquarter";
        HarmonyGenerationSuite hgs = new HarmonyGenerationSuite(melody,dataToUse,output);

        hgs.run();
    }
}