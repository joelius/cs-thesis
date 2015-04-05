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
    private int MAX_MARGIN = 5;

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
//            if (jmd.nt.isSameNoteInScale(jmd.rt)){
//                interval = (jmd.isInMajorKey()) ? JNote.MAJOR_THIRD : JNote.MINOR_THIRD;
//                temp = new JNoteInfoTrio(jmd.nt,jmd.rt,new JNote(jmd.nt,interval));
//            } else {
                temp = new JNoteInfoTrio(jmd.nt,jmd.rt,null);
//            }
            outputList.add(temp);
        }


        System.out.println("Decision Tree ready to go.");
    }

    public void run(){
        JNoteInfoTrio prev;
        JNoteInfoTrio temp;
        JNote hmy;
        int length = outputList.size();

        generateHarmoniesRecursively(0);

        System.out.println(isSolved(outputList) ? "It's solved!!" : "It's not solved. :(");

        //if hmy is null, then look at previous one, then try to make it as close as possible to the next hmy.
    }

    public boolean generateHarmoniesRecursively(int index){

        JNoteInfoTrio jitOrig, jitPrev, jitPrevPrev, jitNew;
        Chord chd;
        int hmyToStayCloseTo, hmyTry1, hmyTry2, hmyTry3;
        if (index > 1){
            jitPrev = outputList.get(index-1);
            jitPrevPrev = outputList.get(index-2);
            if (Math.abs(jitPrev.hmy.asInt()-jitPrevPrev.hmy.asInt()) > MAX_MARGIN){
                return false;
            }
        }

        if (index == outputList.size()){
            return true;
        }
        else {
            jitOrig = outputList.get(index);
            if (index==0){
                jitNew = new JNoteInfoTrio(jitOrig.nt,jitOrig.rt,new JNote(jitOrig.nt,third));
                outputList.add(index, jitNew);
                if (generateHarmoniesRecursively(index + 1)){
                   return true;
                }
                else {
                    jitNew = new JNoteInfoTrio(jitOrig.nt, jitOrig.rt, new JNote(jitOrig.nt, JNote.FIFTH));
                    outputList.add(index, jitNew);
                    if (generateHarmoniesRecursively(index + 1)){
                        return true;
                    }else{
                        jitNew = new JNoteInfoTrio(jitOrig.nt,jitOrig.rt,new JNote(jitOrig.nt,JNote.OCTAVE));
                        outputList.add(index, jitNew);
                        if (generateHarmoniesRecursively(index + 1)){
                            return true;
                        } else{
                            jitNew = new JNoteInfoTrio(jitOrig.nt,jitOrig.rt,jitOrig.nt);
                            outputList.add(index, jitNew);
                            if (generateHarmoniesRecursively(index + 1)){
                                return true;
                            }
                            else {
                                return false;
                            }
                        }
                    }
                }

            }
            else {
                chd = chordSet.get(jitOrig.rt.noteAsIntegerInCScale());

                jitPrev = outputList.get(index - 1);
                hmyToStayCloseTo = jitPrev.hmy.asInt();

                hmyTry1 = jitOrig.nt.asInt() + chd.third-chd.root; // account for both major and minor chords
                hmyTry2 = jitOrig.nt.asInt() + JNote.FIFTH;
                hmyTry3 = jitOrig.nt.asInt() + JNote.OCTAVE;

                int whatNtIs = jitOrig.nt.noteAsIntegerInCScale()-jitOrig.rt.noteAsIntegerInCScale();


                jitNew = new JNoteInfoTrio(jitOrig.nt,jitOrig.rt,new JNote(jitOrig.nt,chd.third-whatNtIs));
                outputList.add(index, jitNew);
                if (generateHarmoniesRecursively(index + 1)){
                    return true;
                }
                else {
                    jitNew = new JNoteInfoTrio(jitOrig.nt, jitOrig.rt, new JNote(jitOrig.nt, chd.fifth-whatNtIs));
                    outputList.add(index, jitNew);
                    if (generateHarmoniesRecursively(index + 1)){
                        return true;
                    }else {
                        jitNew = new JNoteInfoTrio(jitOrig.nt, jitOrig.rt, new JNote(jitOrig.nt, JNote.OCTAVE - whatNtIs));
                        outputList.add(index, jitNew);
                        if (generateHarmoniesRecursively(index + 1)) {
                            return true;
                        } else {
                            jitNew = new JNoteInfoTrio(jitOrig.nt, jitOrig.rt, jitOrig.nt);
                            outputList.add(index, jitNew);
                            if (generateHarmoniesRecursively(index + 1)) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean isSolved(ArrayList<JNoteInfoTrio> list){
        for (JNoteInfoTrio item : list ){
            if (!item.isAFullTriad()){
                return false;
            }
        }
        return true;
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
