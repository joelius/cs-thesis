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

        private JNoteHashKey (JNoteMelodyDatum input){
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
        brain = new HashMap<JNoteHashKey, Integer>();

    }

    public void prepareEngine(){
        JNoteHashKey hKey;
        for (JNoteHarmonyDatum datum : harmonyDataSet){
            System.out.println(datum.toString());
            if (datum.hmy!=null) {
                hKey = new JNoteHashKey(datum);
                brain.put(hKey, datum.hmy.noteAsIntegerInCScale());
            }
        }
    }

    public JNote generateHarmony(JNoteMelodyDatum jmd){
        System.out.println("generateHarmony. jmd.key = " + jmd.key + ".");
        JNote result;
        String jNoteString;
        int hmyNote;
        int isInNextOctave = 0; //set to one if octave offset is necessary

        int melodyNote = jmd.nt.noteAsIntegerInCScale();
        JNoteHashKey hKey = new JNoteHashKey(jmd);

        System.out.println(jmd.toString());

        if (!brain.containsKey(hKey)){
            hmyNote = 0;
        }
        else {
            hmyNote = brain.get(hKey);
        }
        System.out.println("input.key: " + jmd.key);
        jNoteString = JNote.jNoteStringBuilder(hmyNote,jmd.key,jmd.nt);

        result = new JNote(jNoteString);

        return result;
    }

}
