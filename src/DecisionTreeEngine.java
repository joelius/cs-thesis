import java.util.HashSet;
import java.util.Set;

/**
 * Created by jolpatrik on 2015-04-02.
 */
public class DecisionTreeEngine extends HarmonyGenerationEngine{

    int[] majorScale = {0,2,4,5,7,9,11};
    int[] minorScale = {0,2,3,5,7,8,10};
    Set<Chord> acceptableChords = new HashSet<Chord>();
    boolean isMajorKey = true;
    JNote harmony;

    public JNote generateHarmony(JNoteMelodyDatum input){

        isMajorKey = input.isMajor();

        if (isMajorKey){
            if(input.nt.isSameNoteInScale(input.rt)){
                harmony = new JNote(input.nt, JNote.MAJOR_THIRD);
            }
            else if (input.nt.isIntervalXAboveNoteY(JNote.MAJOR_THIRD, input.rt)){
                harmony = new JNote(input.nt, JNote.FIFTH);
            }
            else if (input.nt.isIntervalXAboveNoteY(JNote.FIFTH, input.rt)){
                harmony = new JNote(input.nt, JNote.OCTAVE);
            }
            else {
                harmony = new JNote(input.nt, JNote.FIFTH);
            }
        }
        else {
            if(input.nt.isSameNoteInScale(input.rt)){
                harmony = new JNote(input.nt, JNote.MINOR_THIRD);
            }
            else if (input.nt.isIntervalXAboveNoteY(JNote.MINOR_THIRD, input.rt)){
                harmony = new JNote(input.nt, JNote.FIFTH);
            }
            else if (input.nt.isIntervalXAboveNoteY(JNote.FIFTH, input.rt)){
                harmony = new JNote(input.nt, JNote.OCTAVE);
            }
            else {
                harmony = new JNote(input.nt, JNote.FIFTH);
            }
        }
        
        return harmony;
    }

}
