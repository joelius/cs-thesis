import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jolpatrik on 15-03-31.
 */
public class JFugueInputLoaderTest {

    @Test
    public void testPopulateWithDataFile() throws Exception {
        JFugueInputLoader jil = new JFugueInputLoader();
        jil.populateWithDataFile("/Users/jolpatrik/IdeaProjects/harmonator/src/data/jinput6bpm.txt");
        assertEquals(10, jil.patternStringArray.size());
        assertEquals("C5q D5q E5q D5q ", jil.patternStringArray.get(0));
    }

    @Test
    public void testGetData() throws Exception {

    }
}