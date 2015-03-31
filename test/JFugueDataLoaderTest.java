import org.junit.Test;

import static org.junit.Assert.*;

public class JFugueDataLoaderTest {

    @Test
    public void testPopulateWithDataFile() throws Exception {
        JFugueDataLoader jdl = new JFugueDataLoader();
        jdl.populateWithDataFile("/Users/jolpatrik/IdeaProjects/harmonator/src/data/input.txt");
        assertEquals(0,jdl.patternStringArray.size() );
    }

    @Test
    public void testPopulateDataSet() throws Exception {

    }

    @Test
    public void testGetData() throws Exception {

    }
}