
import org.junit.Test;
import org.junit.Assert;

public class HarmonyDataLoaderTest {

    @Test
    public void testHDL(){
        HarmonyDataLoader hdl = new HarmonyDataLoader();

        Assert.assertEquals(0, hdl.getData().size());

        hdl.populateWithDataFile("/Users/jolpatrik/IdeaProjects/jfugue-tst/src/data/input.txt");

        Assert.assertEquals(5, hdl.getData().size());

        Assert.assertEquals(3, hdl.getData().listIterator().next().getHarmony());

        hdl.populateWithDataFile("/Users/jolpatrik/IdeaProjects/jfugue-tst/src/data/input.txt");

        Assert.assertEquals(10, hdl.getData().size());


    }

}