import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by jolpatrik on 2015-04-03.
 */
public class HashMapEngineTest {

    @Test
    public void testGenerateHarmony() throws Exception {

        String pathToFile = "/Users/jolpatrik/IdeaProjects/harmonator/src/data/hashmapfillupCMaj.txt";
        JNoteHarmonyInfoReader jnhir = new JNoteHarmonyInfoReader();

        try { jnhir.readInDataFile(pathToFile);
        } catch (IOException e){System.err.print(e.getMessage());}

        JNoteHarmonyDataLoader jhdl = new JNoteHarmonyDataLoader();
        jhdl.processJNoteInfoTrioArray(jnhir.lst,jnhir.key);
        System.out.println("after jhdl.processJNoteInfoTrioArray. Key: " + jnhir.lst.get(0).nt);

        assertNotEquals(null, jhdl.data.toString());

        for (JNoteHarmonyDatum datum : jhdl.data){
            System.out.println(datum.toString());
        }

        HarmonyGenerationEngine hashMapEngine = new HashMapEngine(jhdl.data);

        System.out.println("after hashMapEngine. Key: " + jhdl.data.get(0).key);

        String pathToInputFile = "/Users/jolpatrik/IdeaProjects/harmonator/src/data/barrett.txt";
        JNoteInputReader jnInputr = new JNoteInputReader();

        try { jnInputr.readInDataFile(pathToInputFile);
        } catch (IOException e){System.err.print(e.getMessage());}

        JNoteMelodyDataLoader jmdl = new JNoteMelodyDataLoader();
        jmdl.processJNoteInfoTrioArray(jnInputr.lst, jnInputr.key);
        assertNotEquals(null, jmdl.data.toString());

        HarmonyGenerationMachine machine = new HarmonyGenerationMachine(jmdl.data, hashMapEngine);

        machine.powerOn();

        machine.run();

        //System.out.println(machine.outputToString());


        JFugueInputLoader jil = new JFugueInputLoader();


        boolean generateHarmonies = true;

        if (generateHarmonies){
            jil.populateWithJNoteTrioArray(machine.getOutput(), jnInputr.key, jnInputr.timeSig, jnInputr.tempo);
        }
        else{
            jil.populateWithJNoteTrioArray(jnInputr.lst, jnInputr.key,jnInputr.timeSig, jnInputr.tempo);
        }

        JFugueBuilder test = new JFugueBuilder();
        String filename = "HMETest4Barrett";

        System.out.println(machine.engine.toString());

        try {
            test.writeFile(jil.patternStringArray, filename);
            // test.writeFile(jil.patternStringArray, filename);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

}