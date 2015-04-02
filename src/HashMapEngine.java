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

    }

    private void prepareEngine(){
        JNoteHashKey hKey;
        for (JNoteHarmonyDatum datum : harmonyDataSet){
            hKey = new JNoteHashKey(datum);
            brain.put(hKey,datum.hmy.noteAsIntegerInCScale());
        }
    }

    public JNote generateHarmony(JNoteMelodyDatum input){
        JNote result;
        String jNoteString;
        int hmyNote;
        int isInNextOctave = 0; //set to one if octave offset is necessary

        int melodyNote = input.nt.noteAsIntegerInCScale();
        JNoteHashKey hKey = new JNoteHashKey(input);

        hmyNote = brain.get(hKey);

        jNoteString = JNote.jNoteStringBuilder(hmyNote,input.key,input.nt);

        result = new JNote(jNoteString);

        return result;
    }

}
