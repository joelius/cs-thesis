import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertNotEquals;

/**
 * Created by jolpatrik on 2015-04-03.
 */
public class StreamEngineTest {
    boolean generateHarmonies;
    @Test
    public void testGenerateHarmony() throws Exception {

        HarmonyGenerationEngine streamEngine = new StreamEngine();

        String pathToInputFile = "/Users/jolpatrik/IdeaProjects/harmonator/src/data/maritime.txt";
        JNoteInputReader jnInputr = new JNoteInputReader();

        try { jnInputr.readInDataFile(pathToInputFile);
        } catch (IOException e){System.err.print(e.getMessage());}

        JNoteMelodyDataLoader melodiesIn = new JNoteMelodyDataLoader();
        melodiesIn.processJNoteInfoTrioArray(jnInputr.lst, jnInputr.key);
        assertNotEquals(null, melodiesIn.data.toString());

        HarmonyGenerationMachine machine = new HarmonyGenerationMachine(melodiesIn.data, streamEngine);

        machine.powerOn();

        machine.run();

        System.out.println(machine.outputToString());

        machine.getOutput();

        String filename = "StreamEngine15MaritimeMargin4NoDubThird";
        JFugueInputLoader jil = new JFugueInputLoader();

        generateHarmonies = true;

        if (generateHarmonies){
            jil.populateWithJNoteTrioArray(machine.getOutput(), jnInputr.key, jnInputr.timeSig, jnInputr.tempo);
        }
        else{
            jil.populateWithJNoteTrioArray(jnInputr.lst, jnInputr.key,jnInputr.timeSig, jnInputr.tempo);
        }

        JFugueBuilder test = new JFugueBuilder();

        try {
            test.writeFile(jil.patternStringArray, filename);
           // test.writeFile(jil.patternStringArray, filename);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
//        assertEquals(true, new File(inputPath).exists());

    }

    @Test
    public void testPrepareEngine() throws Exception {
        DecisionTreeEngine dte = new DecisionTreeEngine();
        dte.prepareEngine();
        System.out.println(dte.acceptableChordSetToString(dte.getMajorChords()));
        System.out.println(dte.acceptableChordSetToString(dte.getMinorChords()));
    }
}