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
    private HarmonyGenerationEngine shse;

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
        shse = new StreamHashSetEngine(harmonyDataLoader.data);

        engines.add(dte);
        engines.add(se);
        engines.add(hme);
        engines.add(shse);
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
        String filename;
        String noHmiesFileName = outputFilename + "0NoHarmonies";
        String decisionTreeFileName = outputFilename + "1DecisionTreeEngine";
        String streamFileName = outputFilename + "2StreamEngine";
        String hashMapFileName = outputFilename + "3HashMapEngine";
        String streamHashSetFilename = outputFilename + "4StreamHashSetEngine";

        for (HarmonyGenerationMachine m : machines){

            jil.populateWithJNoteTrioArray(m.getOutput(), melodyInputReader.key, melodyInputReader.timeSig, melodyInputReader.tempo);

            if (m.engine instanceof DecisionTreeEngine){
                filename = decisionTreeFileName;
                System.out.println("||OutputToJFugue()|| We have a DecisionTreeEngine!");
            }
            else if (m.engine instanceof StreamEngine){
                filename = streamFileName;
                System.out.println("||OutputToJFugue()|| We have a StreamEngine!");
            }
            else if (m.engine instanceof HashMapEngine){
                filename = hashMapFileName;
                System.out.println("||OutputToJFugue()|| We have a HashMapEngine!");
            }
            else if (m.engine instanceof StreamHashSetEngine){
                filename = streamHashSetFilename;
                System.out.println("||OutputToJFugue()|| We have a StreamHashSetEngine!");
            }
            else {
                filename = outputFilename+"ERROR:UnrecognizedEngine";
                System.out.println("||OutputToJFugue()|| We have an Unrecognized Engine!");
            }

            try {
                jFugueJavaBuilder.writeFile(jil.patternStringArray, filename);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
            jil.clearPatternStringArray();
        }

        //write original melody input (no harmony), for reference

        jil.populateWithJNoteTrioArray(melodyInputReader.lst, melodyInputReader.key, melodyInputReader.timeSig, melodyInputReader.tempo);
        try {
            jFugueJavaBuilder.writeFile(jil.patternStringArray, noHmiesFileName);
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
    }

}
