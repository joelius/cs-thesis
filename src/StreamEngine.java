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
    private HashMap<Integer, Chord> chordSet;
    private JNote harmony;
    private boolean isInMajorKey;
    private String key;
    private int third;
    private int MARGIN = 5;

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

        key = inputList.get(0).key;
        isInMajorKey = inputList.get(0).isInMajorKey();
        chordSet = (isInMajorKey) ? acceptableChordsinMajor : acceptableChordsinMinor;
        third = (isInMajorKey) ? JNote.MAJOR_THIRD : JNote.MINOR_THIRD;

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
        JNote hmy;
        int length = outputList.size();
        for (int i=0; i<length-1; i++){
            prev = (i==0) ? outputList.get(i) : outputList.get(i-1);
            temp = outputList.get(i);

            hmy = generateHarmoniesRecursively(i, temp);

            temp = new JNoteInfoTrio(jit.nt,jit.rt,generateHarmony(jit));
            outputList.add(temp);
        }

        //if hmy is null, then look at previous one, then try to make it as close as possible to the next hmy.
    }

    public boolean generateHarmonyRecursively(int index){
        JNoteInfoTrio jitOrig = outputList.get(index);
        JNoteInfoTrio jitPrev, jitNew;
        Chord chd;
        int hmyToStayCloseTo, hmyTry1, hmyTry2, hmyTry3;


        if (index == outputList.size()){
            return true;
        }
        else {
            if (jitOrig.isAFullTriad()){
                generateHarmonyRecursively(index+1);
            }
            else {
                if (index==0){
                    jitNew = new JNoteInfoTrio(jitOrig.nt,jitOrig.rt,new JNote(jitOrig.nt,third));
                    outputList.add(index,jitNew);
                    generateHarmonyRecursively(index + 1);
                    jitNew = new JNoteInfoTrio(jitOrig.nt,jitOrig.rt,new JNote(jitOrig.nt,JNote.FIFTH));
                    outputList.add(index, jitNew);
                    generateHarmonyRecursively(index + 1);
                    jitNew = new JNoteInfoTrio(jitOrig.nt,jitOrig.rt,new JNote(jitOrig.nt,JNote.OCTAVE));
                    outputList.add(index, jitNew);
                    generateHarmonyRecursively(index + 1);
                }
                else {
                    chd = chordSet.get(jitOrig.rt.noteAsIntegerInCScale());

                    jitPrev = outputList.get(index - 1);
                    hmyToStayCloseTo = jitPrev.hmy.asInt();

                    hmyTry1 = jitOrig.nt.asInt() + chd.third-chd.root; // account for both major and minor chords
                    hmyTry2 = jitOrig.nt.asInt() + JNote.FIFTH;
                    hmyTry3 = jitOrig.nt.asInt() + JNote.OCTAVE;

                    if (Math.abs(hmyTry1-hmyToStayCloseTo)<=MARGIN){
                        //generateHmy
                    }
                    else if (Math.abs(hmyTry2-hmyToStayCloseTo)<=MARGIN){
                        // generateHMy with try2
                    }
                    else if (Math.abs(hmyTry3-hmyToStayCloseTo)<=MARGIN){
                        // generateHmy with Try3
                    }
                    else {
                        return false; // i.e. this track is a dead end.
                    }



                            jitNew = new JNoteInfoTrio()
                }
            }

            generateHarmonyRecursively(i+)
        }
    }

    public JNote generateHarmony(JNoteInfoTrio input){
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
