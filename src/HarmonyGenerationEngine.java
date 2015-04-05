import java.util.ArrayList;

/**
 * Harmony Generation Engine
 * Created by jolpatrik on 2015-04-01.
 */
public abstract class HarmonyGenerationEngine {
    ArrayList<JNoteMelodyDatum> inputList;
    ArrayList<JNoteInfoTrio> outputList;

    public abstract void prepareEngine();
    public abstract void run();
    public abstract void feedInMelodyInput(ArrayList<JNoteMelodyDatum> inputList);
    public abstract ArrayList<JNoteInfoTrio> spitOutHarmonyOutput();
}
