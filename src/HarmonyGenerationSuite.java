import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jolpatrik on 2015-04-06.
 */
public class HarmonyGenerationSuite {

    private String pathToMelodyInputFile;
    private String pathToDataBankFile;

    private JNoteHarmonyInfoReader harmonyInfoReader;
    private JNoteHarmonyDataLoader harmonyDataLoader;

    private JNoteInputReader melodyInputReader;
    private JNoteMelodyDataLoader melodyLoader;

    private HarmonyGenerationEngine dte;
    private HarmonyGenerationEngine hme;
    private HarmonyGenerationEngine se;

    private ArrayList<HarmonyGenerationEngine> engines;
    private ArrayList<HarmonyGenerationMachine> machines;

    private String outputFilename;
    private JFugueInputLoader jil;
    private JFugueBuilder jFugueJavaBuilder;

    public HarmonyGenerationSuite(String pathToMelodyInputFileIn, String pathToDataBankFileIn, String outputFilenameIn ){
        pathToMelodyInputFile = pathToMelodyInputFileIn;
        pathToDataBankFile = pathToDataBankFileIn;
        outputFilename = outputFilenameIn;
    }

    public void initializeReadersAndLoaders(){
        harmonyInfoReader = new JNoteHarmonyInfoReader();
        harmonyDataLoader = new JNoteHarmonyDataLoader();
        melodyInputReader = new JNoteInputReader();
        melodyLoader = new JNoteMelodyDataLoader();
    }

    public void loadInHarmonyData(){

        try {
            harmonyInfoReader.readInDataFile(pathToDataBankFile);
        } catch (IOException e){
            System.err.print(e.getMessage());
        }

        harmonyDataLoader.processJNoteInfoTrioArray(harmonyInfoReader.lst, harmonyInfoReader.key);

    }

    public void buildEngines(){
        engines = new ArrayList<HarmonyGenerationEngine>();

        dte = new DecisionTreeEngine();
        se = new StreamEngine();
        hme = new HashMapEngine(harmonyDataLoader.data);

        engines.add(dte);
        engines.add(se);
        engines.add(hme);
    }

    public void loadInMelodyData(){
        try {
            melodyInputReader.readInDataFile(pathToMelodyInputFile);
        } catch (IOException e){
            System.err.print(e.getMessage());
        }

        melodyLoader = new JNoteMelodyDataLoader();
        melodyLoader.processJNoteInfoTrioArray(melodyInputReader.lst, melodyInputReader.key);

    }

    public void buildMachines(ArrayList<JNoteMelodyDatum> melodyData){
        machines = new ArrayList<HarmonyGenerationMachine>();

        for (HarmonyGenerationEngine e : engines){
            machines.add(new HarmonyGenerationMachine(melodyData, e));
        }
    }

    public void runSuiteOfMachines(){
        for (HarmonyGenerationMachine m : machines){
            m.powerOn();
            m.run();
        }
    }

    public void initializeJFugueWriter(){
        jil = new JFugueInputLoader();
        jFugueJavaBuilder = new JFugueBuilder();
    }

    public void outputToJFugue(){
        String noHmies = outputFilename + "-0-NoHarmonies";
        String decisionTree = outputFilename + "-1-DecisionTreeEngine";
        String stream = outputFilename + "-2-StreamEngine";
        String hashMap = outputFilename + "-3-HashMapEngine";

        String filename = outputFilename;

        for (HarmonyGenerationMachine m : machines){

            jil.populateWithJNoteTrioArray(m.getOutput(), melodyInputReader.key, melodyInputReader.timeSig, melodyInputReader.tempo);

            if (m.engine instanceof DecisionTreeEngine){
                filename = decisionTree;
                System.out.println("||OutputToJFugue()|| We have a DecisionTreeEngine!");
            }
            else if (m.engine instanceof StreamEngine){
                filename = stream;
                System.out.println("||OutputToJFugue()|| We have a StreamEngine!");
            }
            else if (m.engine instanceof HashMapEngine){
                filename = hashMap;
                System.out.println("||OutputToJFugue()|| We have a HashMapEngine!");
            }
            else {
                System.out.println("||OutputToJFugue()|| We have an Unrecognized Engine!");
            }

            try {
                jFugueJavaBuilder.writeFile(jil.patternStringArray, filename+"-ERROR");
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
            jil.clearPatternStringArray();
        }

        //write original input, for reference

        jil.populateWithJNoteTrioArray(melodyInputReader.lst, melodyInputReader.key, melodyInputReader.timeSig, melodyInputReader.tempo);
        try {
            jFugueJavaBuilder.writeFile(jil.patternStringArray, noHmies);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        jil.clearPatternStringArray();

    }

    public void run(){

        initializeReadersAndLoaders();
        loadInHarmonyData();
        buildEngines();
        loadInMelodyData();
        buildMachines(melodyLoader.data);
        runSuiteOfMachines();
        initializeJFugueWriter();
        outputToJFugue();
//        String pathToFile = "/Users/jolpatrik/IdeaProjects/harmonator/src/data/hashmapfillupCMaj.txt";
//        JNoteHarmonyInfoReader jnhir = new JNoteHarmonyInfoReader();
//
//        try { jnhir.readInDataFile(pathToFile);
//        } catch (IOException e){System.err.print(e.getMessage());}
//
////        //
////
////        JNoteHarmonyDataLoader jhdl = new JNoteHarmonyDataLoader();
////
////       jhdl.processJNoteInfoTrioArray(jnhir.lst, jnhir.key);
////        System.out.println("after jhdl.processJNoteInfoTrioArray. Key: " + jnhir.lst.get(0).nt);
//////
////        for (JNoteHarmonyDatum datum : jhdl.data){
////            System.out.println(datum.toString());
////        }
////
////        HarmonyGenerationEngine hashMapEngine = new HashMapEngine(jhdl.data);
//
//   //     System.out.println("after hashMapEngine. Key: " + jhdl.data.get(0).key);
//
//
//
//
////        HarmonyGenerationEngine streamEngine = new StreamEngine();
////
////        String pathToInputFile = "/Users/jolpatrik/IdeaProjects/harmonator/src/data/maritime.txt";
////        JNoteInputReader jnInputr = new JNoteInputReader();
////
////        try { jnInputr.readInDataFile(pathToInputFile);
////        } catch (IOException e){System.err.print(e.getMessage());}
////
////        JNoteMelodyDataLoader melodyLoader = new JNoteMelodyDataLoader();
////        melodyLoader.processJNoteInfoTrioArray(jnInputr.lst, jnInputr.key);
//////
////
////        HarmonyGenerationMachine machine = new HarmonyGenerationMachine(melodyLoader.data, streamEngine);
////
////        machine.powerOn();
////
////        machine.run();
////
////        System.out.println(machine.outputToString());
////
////        machine.getOutput();
//
//        String outputFilename = "StreamEngine15MaritimeMargin4NoDubThird";
//        JFugueInputLoader jil = new JFugueInputLoader();
//
//        boolean generateHarmonies = true;
//
//        if (generateHarmonies){
//            jil.populateWithJNoteTrioArray(machine.getOutput(), jnInputr.key, jnInputr.timeSig, jnInputr.tempo);
//        }
//        else{
//            jil.populateWithJNoteTrioArray(jnInputr.lst, jnInputr.key,jnInputr.timeSig, jnInputr.tempo);
//        }
//
//        JFugueBuilder test = new JFugueBuilder();
//
//        try {
//            test.writeFile(jil.patternStringArray, outputFilename);
//            // test.writeFile(jil.patternStringArray, filename);
//        }
//        catch (Exception e){
//            System.out.println(e.getMessage());
//        }
    }

}
