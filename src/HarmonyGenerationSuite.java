import java.io.IOException;

/**
 * Created by jolpatrik on 2015-04-06.
 */
public class HarmonyGenerationSuite {

    private boolean generateHarmonies;
    private String pathToMelodyInputFile;
    private String pathToDataBankFile;

    private JNoteHarmonyInfoReader harmonyInfoReader;
    private JNoteHarmonyDataLoader harmonyDataLoader;

    private JNoteInputReader melodyInputReader;
    private JNoteMelodyDataLoader melodyLoader;

    private HarmonyGenerationEngine dte;
    private HarmonyGenerationEngine hme;
    private HarmonyGenerationEngine se;
    private HarmonyGenerationMachine machine;

    private String outputFilename;
    private JFugueInputLoader jil;
    private JFugueBuilder jFugueJavaBuilder;

    public HarmonyGenerationSuite(String pathToMelodyInputFileIn, String pathToDataBankFileIn, String outputFilenameIn ){
        pathToMelodyInputFile = pathToMelodyInputFileIn;
        pathToDataBankFile = pathToDataBankFileIn;
        outputFilename = outputFilenameIn;
    }

    public void run(){

        String pathToFile = "/Users/jolpatrik/IdeaProjects/harmonator/src/data/hashmapfillupCMaj.txt";
        JNoteHarmonyInfoReader jnhir = new JNoteHarmonyInfoReader();

        try { jnhir.readInDataFile(pathToFile);
        } catch (IOException e){System.err.print(e.getMessage());}

        JNoteHarmonyDataLoader jhdl = new JNoteHarmonyDataLoader();
        jhdl.processJNoteInfoTrioArray(jnhir.lst, jnhir.key);
        System.out.println("after jhdl.processJNoteInfoTrioArray. Key: " + jnhir.lst.get(0).nt);

        for (JNoteHarmonyDatum datum : jhdl.data){
            System.out.println(datum.toString());
        }

        HarmonyGenerationEngine hashMapEngine = new HashMapEngine(jhdl.data);

        System.out.println("after hashMapEngine. Key: " + jhdl.data.get(0).key);




        HarmonyGenerationEngine streamEngine = new StreamEngine();

        String pathToInputFile = "/Users/jolpatrik/IdeaProjects/harmonator/src/data/maritime.txt";
        JNoteInputReader jnInputr = new JNoteInputReader();

        try { jnInputr.readInDataFile(pathToInputFile);
        } catch (IOException e){System.err.print(e.getMessage());}

        JNoteMelodyDataLoader melodyLoader = new JNoteMelodyDataLoader();
        melodyLoader.processJNoteInfoTrioArray(jnInputr.lst, jnInputr.key);

        HarmonyGenerationMachine machine = new HarmonyGenerationMachine(melodyLoader.data, streamEngine);

        machine.powerOn();

        machine.run();

        System.out.println(machine.outputToString());

        machine.getOutput();

        String outputFilename = "StreamEngine15MaritimeMargin4NoDubThird";
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
            test.writeFile(jil.patternStringArray, outputFilename);
            // test.writeFile(jil.patternStringArray, filename);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
