import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by jolpatrik on 2015-04-02.
 */
public class JNoteHarmonyDataLoaderTest {

    @Test
    public void testProcessJNoteInfoTrioArray() throws Exception {
        String pathToFile = "/Users/jolpatrik/IdeaProjects/harmonator/src/data/hornpipe.txt";
        JNoteInfoReader jnir = new JNoteInfoReader();

        try { jnir.readInDataFile(pathToFile);
        } catch (IOException e){System.err.print(e.getMessage());}

//        System.out.println(jnir.lst.toString());

        JNoteHarmonyDataLoader jhdl = new JNoteHarmonyDataLoader();
        jhdl.processJNoteInfoTrioArray(jnir.lst);
        assertNotEquals(null, jhdl.data.toString());

        for (JNoteHarmonyDatum datum : jhdl.data){
            System.out.println(datum.toString());
        }

    }

    @Test
    public void testDataToString() throws Exception {

    }
}