import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jolpatrik on 2015-04-02.
 */
public class StreamEngine extends HarmonyGenerationEngine{

    private int[] majorScale = {0,2,4,5,7,9,11};
    private int[] minorScale = {0,2,3,5,7,8,10};
    private HashMap<Integer, Chord> acceptableChordsinMajor = new HashMap<Integer, Chord>();
    private HashMap<Integer, Chord> acceptableChordsinMinor = new HashMap<Integer, Chord>();
    private boolean isMajorKey = true;
    private JNote harmony;
    private String key;

    ArrayList<JNoteMelodyDatum> inputList;
    ArrayList<JNoteInfoTrio> outputList;

    public void feedInMelodyInput(ArrayList<JNoteMelodyDatum> input){
        inputList = input;
    }

    public void prepareEngine(){
        outputList = new ArrayList<JNoteInfoTrio>();
        for (int i=0;i<majorScale.length;i++){
            acceptableChordsinMajor.put (majorScale[i], new Chord(majorScale[i],majorScale[(i+2)%majorScale.length],majorScale[(i+4)%majorScale.length]) );
        }
        for (int i=0;i<minorScale.length;i++){
            acceptableChordsinMinor.put (minorScale[i], new Chord(minorScale[i],minorScale[(i+2)%minorScale.length],minorScale[(i+4)%minorScale.length]) );
        }

        JNoteInfoTrio temp;
        int interval;
        for (JNoteMelodyDatum jmd : inputList){
            if (jmd.nt.isSameNoteInScale(jmd.rt)){
                interval = (jmd.isInMajorKey()) ? JNote.MAJOR_THIRD : JNote.MINOR_THIRD;
                temp = new JNoteInfoTrio(jmd.nt,jmd.rt,new JNote(jmd.nt,interval));
            } else {
                temp = new JNoteInfoTrio(jmd.nt,jmd.rt,null);
            }
            outputList.add(temp);
        }
        System.out.println("Decision Tree ready to go.");
    }

    public void run(){
        JNoteInfoTrio prev;
        JNoteInfoTrio temp;
        for (JNoteMelodyDatum jmd : inputList){
            temp = new JNoteInfoTrio(jmd.nt,jmd.rt,generateHarmony(jmd));
            outputList.add(temp);
        }

        //if hmy is null, then look at previous one, then try to make it as close as possible to the next hmy.
    }

    public JNote generateHarmony(JNoteMelodyDatum input){
        JNoteMelodyDatum temp = input.normalizedToCScale();

        if (temp==null){
            outputList.add(new JNoteInfoTrio(temp.nt, temp.rt, null));
        }
        else {
            outputList.add(new JNoteInfoTrio(temp.nt, temp.rt, null));
        }

        key = input.key;
        isMajorKey = input.isInMajorKey();

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

            chord = acceptableChordsinMajor.get(rootInt);
            System.out.println("chord " + chord );

            if(noteInt==chord.root){
                harmony = new JNote(input.nt, chord.third-chord.root);
            }
            else if (noteInt==chord.third){
                harmony = new JNote(input.nt, chord.fifth-chord.third);
            }
            else if (noteInt==chord.fifth){
                harmony = new JNote(input.nt, (chord.root+JNote.OCTAVE)-chord.fifth);
            }
            else if (noteInt==chord.third-2) {
                harmony = new JNote(input.nt, chord.fifth - (chord.third-2));
            }
            else {
                harmony = new JNote(input.rt, -1/**JNote.OCTAVE*/);
            }
        }
        else {
            System.out.print(acceptableChordsinMinor.values());
            chord = acceptableChordsinMinor.get(rootInt);
            System.out.println("chord " + chord );

            if(noteInt==chord.root){
                harmony = new JNote(input.nt, chord.third-chord.root);
            }
            else if (noteInt==chord.third){
                harmony = new JNote(input.nt, chord.fifth-chord.third);
            }
            else if (noteInt==chord.fifth){
                harmony = new JNote(input.nt, (chord.root+ JNote.OCTAVE)-chord.fifth);
            }
            else if (noteInt==chord.third-1) {
                harmony = new JNote(input.nt, chord.fifth - (chord.third-1));
            }
            else {
                harmony = new JNote(input.rt, -1 /**JNote.OCTAVE*/);
            }
        }
        return harmony;
    }

    public ArrayList<JNoteInfoTrio> spitOutHarmonyOutput(){
        return outputList;
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
