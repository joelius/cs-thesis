import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by jolpatrik on 2015-04-02.
 */
public class JNoteInfoReaderTest {

    @Test
    public void testAddToLst() throws Exception {

        JNoteInfoReader jir = new JNoteInfoReader();
            String file1 = "/Users/jolpatrik/IdeaProjects/jfugue-tst/src/data/input.txt";
            try{
                jir.readInDataFile(file1);
            }
            catch (IOException ioe){
                System.err.print(ioe.getMessage());
            }

    }

    @Test
    public void testReadInDataFile() throws Exception {

    }
}