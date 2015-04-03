import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jolpatrik on 2015-04-02.
 */
public class DecisionTreeEngine extends HarmonyGenerationEngine{

    int[] majorScale = {0,2,4,5,7,9,11};
    int[] minorScale = {0,2,3,5,7,8,10};
    HashMap<Integer, Chord> acceptableChordsinMajor = new HashMap<Integer, Chord>();
    HashMap<Integer, Chord> acceptableChordsinMinor = new HashMap<Integer, Chord>();
    boolean isMajorKey = true;
    JNote harmony;

    public void prepareEngine(){
        for (int i=0;i<majorScale.length;i++){
            acceptableChordsinMajor.put (majorScale[i], new Chord(majorScale[i],majorScale[i+2],majorScale[i+4]) );
        }
        for (int i=0;i<minorScale.length;i++){
            acceptableChordsinMinor.put (minorScale[i], new Chord(minorScale[i],minorScale[i+2],minorScale[i+4]) );
        }

        System.out.println("Decision Tree ready to go.");
    }

    public JNote generateHarmony(JNoteMelodyDatum input){

        isMajorKey = input.isMajor();

        if (isMajorKey){
            if(input.nt.isSameNoteInScale(input.rt)){
                harmony = new JNote(input.nt, JNote.MAJOR_THIRD);
            }
            else if (input.nt.isIntervalXAboveNoteY(JNote.MAJOR_THIRD, input.rt)){
                harmony = new JNote(input.nt, JNote.FIFTH-JNote.MAJOR_THIRD);
            }
            else if (input.nt.isIntervalXAboveNoteY(JNote.FIFTH, input.rt)){
                harmony = new JNote(input.nt, JNote.OCTAVE-JNote.FIFTH);
            }
            else {
                harmony = new JNote(input.rt, JNote.FIFTH+JNote.OCTAVE);
            }
        }
        else {
            if(input.nt.isSameNoteInScale(input.rt)){
                harmony = new JNote(input.nt, JNote.MINOR_THIRD);
            }
            else if (input.nt.isIntervalXAboveNoteY(JNote.MINOR_THIRD, input.rt)){
                harmony = new JNote(input.nt, JNote.FIFTH-JNote.MINOR_THIRD);
            }
            else if (input.nt.isIntervalXAboveNoteY(JNote.FIFTH, input.rt)){
                harmony = new JNote(input.nt, JNote.OCTAVE-JNote.FIFTH);
            }
            else {
                harmony = new JNote(input.nt, JNote.FIFTH+JNote.OCTAVE);
            }
        }

        return harmony;
    }

}
