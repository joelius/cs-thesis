import org.junit.Test;

import static org.junit.Assert.*;

public class HarmonyGeneratorTest {

    @Test
    public void testHG(){

        HarmonyDataSet hds = new HarmonyDataSet();
        HarmonyDataSet melodies = new HarmonyDataSet();
        HarmonyDataSet output = new HarmonyDataSet();
        HarmonyDataLoader hdl = new HarmonyDataLoader();
        //String file1 = "/Users/jol/Dropbox/IdeaProjects/jfugue-tst/src/data/input.txt";
        String file1 = "/Users/jolpatrik/IdeaProjects/cs-thesis/src/data/input.txt";
        hdl.populateDataSet(hds, file1);

        assertNotEquals(0, hds.dataset.size());
        assertEquals(38, hds.dataset.size());

        hdl.populateDataSet(hds, file1);

        assertEquals(76, hds.dataset.size());

        HarmonyGenerator hg = new HarmonyGenerator(hds, melodies);

        assertEquals(76, hg.getHds().dataset.size());

        hdl.populateDataSet(hg.getHds(), file1);

        assertEquals(114, hg.getHds().dataset.size());
    }

    @Test
    public void testHarmonyOutput(){

        HarmonyDataSet hds = new HarmonyDataSet();
        HarmonyDataSet melodies = new HarmonyDataSet();
        MelodyDataLoader mdl = new MelodyDataLoader();
        //String file2 = "/Users/jol/Dropbox/IdeaProjects/jfugue-tst/src/data/input2.txt";
        String file2 = "/Users/jolpatrik/IdeaProjects/cs-thesis/src/data/input.txt";
        mdl.populateMelodySet(melodies, file2);

        assertNotEquals(0, hds.dataset.size());
        assertEquals(38, hds.dataset.size());

        mdl.populateMelodySet(hds, file2);

        assertEquals(76, hds.dataset.size());

        HarmonyGenerator hg = new HarmonyGenerator(hds, melodies);

        assertEquals(76, hg.getHds().dataset.size());

        mdl.populateMelodySet(hg.getHds(), file2);


        assertEquals(114, hg.getHds().dataset.size());
    }
}