import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by jolpatrik on 2015-04-03.
 */
public class DecisionTreeEngineTest {
    boolean generateHarmonies;
    @Test
    public void testGenerateHarmony() throws Exception {

        HarmonyGenerationEngine decisionTreeEngine = new DecisionTreeEngine();

        String pathToInputFile = "/Users/jolpatrik/IdeaProjects/harmonator/src/data/bagOspuds.txt";
        JNoteInputReader jnInputr = new JNoteInputReader();

        try { jnInputr.readInDataFile(pathToInputFile);
        } catch (IOException e){System.err.print(e.getMessage());}

        JNoteMelodyDataLoader melodiesIn = new JNoteMelodyDataLoader();
        melodiesIn.processJNoteInfoTrioArray(jnInputr.lst, jnInputr.key);
        assertNotEquals(null, melodiesIn.data.toString());

        HarmonyGenerationMachine machine = new HarmonyGenerationMachine(melodiesIn.data, decisionTreeEngine);

        machine.powerOn();

        machine.run();

        System.out.println(machine.outputToString());

        machine.getOutput();

        String filename = "DecisionTreeTest17SpudsHarmonies";
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