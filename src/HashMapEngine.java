import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jolpatrik on 2015-04-01.
 */
public class HashMapEngine extends HarmonyGenerationEngine {

    private class JNoteHashKey {
        public int noteInScale;
        public char duration;
        public int rootNoteOfCurrentChord;
        public int sizeOfInterval;

        private JNoteHashKey (JNoteHarmonyDatum input){
            noteInScale = input.nt.noteAsIntegerInCScale();
            duration = input.nt.getDurationAsChar();
            rootNoteOfCurrentChord = input.rt.noteAsIntegerInCScale();
            sizeOfInterval = input.modeOfPrecedingIntervals;
        }
    }

    private ArrayList<JNoteHarmonyDatum> harmonyDataSet;
    private HashMap<JNoteHashKey,Integer> brain;


    public HashMapEngine (ArrayList<JNoteHarmonyDatum> dataIn){
        harmonyDataSet = dataIn;

    }

    private void prepareEngine(){
        JNoteHashKey hKey;
        for (JNoteHarmonyDatum datum : harmonyDataSet){
            hKey = new JNoteHashKey(datum);
            brain.put(hKey,datum.hmy.noteAsIntegerInCScale());
        }
    }

    public JNote generateHarmony(JNoteMelodyDatum input){
        JNote result = null;
        JNoteHarmonyDatum datum = null;
        return result;
    }

}
