import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by jolpatrik on 2015-04-06.
 */
public class StreamHashSetEngineTest {

    @Test
    public void testRun() throws Exception {

        String pathToFile = "/Users/jolpatrik/IdeaProjects/harmonator/src/data/hashmapfillupAHA.txt";
        JNoteHarmonyInfoReader jnhir = new JNoteHarmonyInfoReader();

        try { jnhir.readInDataFile(pathToFile);
        } catch (IOException e){System.err.print(e.getMessage());}

        JNoteHarmonyDataLoader jhdl = new JNoteHarmonyDataLoader();
        jhdl.processJNoteInfoTrioArray(jnhir.lst,jnhir.key);

        HarmonyGenerationEngine streamHashSetEngine = new StreamHashSetEngine(jhdl.data);

        String pathToInputFile = "/Users/jolpatrik/IdeaProjects/harmonator/src/data/takeonme.txt";
        JNoteInputReader jnInputr = new JNoteInputReader();

        try { jnInputr.readInDataFile(pathToInputFile);
        } catch (IOException e){System.err.print(e.getMessage());}

        JNoteMelodyDataLoader melodiesIn = new JNoteMelodyDataLoader();
        melodiesIn.processJNoteInfoTrioArray(jnInputr.lst, jnInputr.key);
        assertNotEquals(null, melodiesIn.data.toString());

        HarmonyGenerationMachine machine = new HarmonyGenerationMachine(melodiesIn.data, streamHashSetEngine);

        machine.powerOn();

        machine.run();

        System.out.println(machine.outputToString());

        machine.getOutput();

        String filename = "StreamHashSetEngine10TakeOnMeCustom";
        JFugueInputLoader jil = new JFugueInputLoader();

        boolean generateHarmonies = true;

        if (generateHarmonies){
            jil.populateWithJNoteTrioArray(machine.getOutput(), jnInputr.key, jnInputr.timeSig, jnInputr.tempo);
        }
        else{
            jil.populateWithJNoteTrioArray(jnInputr.lst, jnInputr.key,jnInputr.timeSig, jnInputr.tempo);
        }

        JFugueBuilder test = new JFugueBuilder();

        try {
            test.writeFile(jil.patternStringArray, filename);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}