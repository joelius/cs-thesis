import java.util.HashMap;

/**
 * Created by jolpatrik on 2015-04-02.
 */
public class DecisionTreeEngine extends HarmonyGenerationEngine{

    private int[] majorScale = {0,2,4,5,7,9,11};
    private int[] minorScale = {0,2,3,5,7,8,10};
    private HashMap<Integer, Chord> acceptableChordsinMajor = new HashMap<Integer, Chord>();
    private HashMap<Integer, Chord> acceptableChordsinMinor = new HashMap<Integer, Chord>();
    private boolean isMajorKey = true;
    private JNote harmony;
    private String key;

    public void prepareEngine(){
        for (int i=0;i<majorScale.length;i++){
            acceptableChordsinMajor.put (majorScale[i], new Chord(majorScale[i],majorScale[(i+2)%majorScale.length],majorScale[(i+4)%majorScale.length]) );
        }
        for (int i=0;i<minorScale.length;i++){
            acceptableChordsinMinor.put (minorScale[i], new Chord(minorScale[i],minorScale[(i+2)%minorScale.length],minorScale[(i+4)%minorScale.length]) );
        }

        System.out.println("Decision Tree ready to go.");
    }

    public JNote generateHarmony(JNoteMelodyDatum input){

        key = input.key;
        isMajorKey = input.isInMajorKey();
        JNoteMelodyDatum temp = input.normalizedToCScale();

        System.out.println(temp.rt.toString());
        System.out.println(temp.rt.asInt());

        Chord chord;
        int noteInt, rootInt, hmyInt;

        noteInt = temp.nt.noteAsIntegerInCScale();
        rootInt = temp.rt.noteAsIntegerInCScale();

        System.out.println("DTE.java/genHarm: " + input.toString());
        System.out.println("DTE.java/genHarm: " + temp.toString());

        if (isMajorKey){

            //whichever one "Note is" in chord, harmony is the other one.

            chord = acceptableChordsinMajor.get(temp.rt.noteAsIntegerInCScale());
            System.out.println("chord " + chord );

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
            System.out.print(acceptableChordsinMinor.values());
            chord = acceptableChordsinMinor.get(temp.rt.noteAsIntegerInCScale());
            System.out.println("chord " + chord );



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

    public String acceptableChordSetToString(HashMap<Integer,Chord> acs){
        String result = "";
        for (Chord c : acs.values()){
            result += c.toString() + "\n";
        }
        return result;
    }

    public HashMap<Integer, Chord> getMajorChords(){
        return acceptableChordsinMajor;
    }

    public HashMap<Integer, Chord> getMinorChords(){
        return acceptableChordsinMinor;
    }

}
