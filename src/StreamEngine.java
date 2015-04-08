import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by jolpatrik on 2015-04-02.
 */
public class StreamEngine extends HarmonyGenerationEngine{

    private static final boolean DEBUG = false;
    private int[] majorScale = {0,2,4,5,7,9,11};
    private int[] minorScale = {0,2,3,5,7,8,10};
    private HashMap<Integer, Chord> acceptableChordsinMajor = new HashMap<Integer, Chord>();
    private HashMap<Integer, Chord> acceptableChordsinMinor = new HashMap<Integer, Chord>();
    private HashMap<Integer, Chord> chordSet;
    private JNote harmony;
    private boolean isInMajorKey;
    private String key;
    private int third;
    private int MAX_MARGIN = 4;
    private boolean CAN_DOUBLE_NOTES = true;

    ArrayList<JNoteMelodyDatum> inputList;
    ArrayList<JNoteInfoTrio> outputList;

    JNoteInfoTrio[] fullSong;

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
                JNoteMelodyDatum tempJMD = jmd.normalizedToCScale();
                temp = new JNoteInfoTrio(tempJMD.nt,tempJMD.rt,null);
//            }
            outputList.add(temp);
        }


        System.out.println("||prepareEngine()|| StreamEngine ready to go.");
        System.out.println("||prepareEngine()|| outputList.size() = " + outputList.size());

        fillSongArrayForRecursion();

        System.out.println("||prepareEngine()|| song.length = " + fullSong.length);

    }

    public void fillSongArrayForRecursion (){
        fullSong = new JNoteInfoTrio[outputList.size()];
        fullSong = outputList.toArray(fullSong);
    }

    public void fillOutputListWithGeneratedHarmonies(JNoteInfoTrio[] hmies){
        outputList = new ArrayList<JNoteInfoTrio>(Arrays.asList(hmies));
        for (int i=0;i<outputList.size();i++){
            outputList.set(i, returnedToKey(outputList.get(i)));
        }
    }

    public void run(){
        JNoteInfoTrio prev;
        JNoteInfoTrio temp;
        JNote hmy;
        int length = outputList.size();

        System.out.println("run()|| About to generateHarmoniesRecursively");

        JNoteInfoTrio[] harmonies = fullSong;

        fullSong = generateHarmoniesRecursively(0, harmonies);

        System.out.println("All done, apparently!");

        for (int i = 0; i<fullSong.length;i++){
            System.out.print(fullSong[i] + "\n" );
        }

        fillOutputListWithGeneratedHarmonies(harmonies);

        System.out.println(isSolved(outputList) ? "It's solved!!" : "It's not solved. :(");



        //if hmy is null, then look at previous one, then try to make it as close as possible to the next hmy.
    }

    public JNoteInfoTrio[] generateHarmoniesRecursively(int index, JNoteInfoTrio[] song){

        if (isSolved(song)) {
            if(DEBUG)System.out.println("||generateHarmoniesRecursively|| isSolved()!");
            return song;
        }
        if (index < song.length){
                JNoteInfoTrio jitOrig, jitPrev, jitNew;
                Chord chd;
                if (index == 0) {
                    jitOrig = song[index];
//                    int[] offsetsToTry = new int[]{third, JNote.FIFTH, JNote.OCTAVE, 0};
                    int[] offsetsToTry = new int[]{third, JNote.FIFTH, JNote.OCTAVE, 0};
                    for (int i = 0; i < offsetsToTry.length; i++) {
                        jitNew = new JNoteInfoTrio(jitOrig.nt, jitOrig.rt, new JNote(jitOrig.nt, offsetsToTry[i]));
                        song[index] = jitNew;
                        if(DEBUG)    System.out.println("||generateHarmoniesRecursively|| index=0, offset: " + offsetsToTry[i]);
                        return generateHarmoniesRecursively((index + 1), song);
                    }
                } else {
                    JNoteInfoTrio[] temp = null;
                    jitPrev = song[index - 1];

                    if(DEBUG)System.out.println("WATCH IT: " + index);
                    jitOrig = song[index];
                    chd = chordSet.get(jitOrig.rt.noteAsIntegerInCScale());
                    int[] hmyOffsetTries = harmonyOffsetSet(jitOrig.nt, chd);

                    if(DEBUG)System.out.println("||generateHarmoniesRecursively|| jitOrig" + jitOrig.toNormalizedNumString());

                    for (int i = 0; i < hmyOffsetTries.length; i++) {
                        jitNew = new JNoteInfoTrio(jitOrig.nt, jitOrig.rt, new JNote(jitOrig.nt, hmyOffsetTries[i]));
                        song[index] = jitNew;
                        if(DEBUG)System.out.println("||generateHarmoniesRecursively|| trying offset " + i + ": " + hmyOffsetTries[i]);
                        if(DEBUG)System.out.println("||generateHarmoniesRecursively|| = " + jitNew.toString());
                        if (Math.abs(jitNew.hmy.asInt() - jitPrev.hmy.asInt()) <= MAX_MARGIN){
                            if (!CAN_DOUBLE_NOTES){
                                if (!(jitNew.hmy.asInt()==jitPrev.hmy.asInt())){
                                    temp = generateHarmoniesRecursively((index + 1), song);
                                }
                            }else {
                                temp = generateHarmoniesRecursively((index + 1), song);
                            }
                        }
                        if (temp!=null){
                            return temp;

                        }
                     }

//                   return false;
            }
        }
        return null;

    }

    public void generateHarmoniesDecentlyRecursively(int index, JNoteInfoTrio[] song){

        if (isSolved(song)) {
            if(DEBUG)System.out.println("||generateHarmoniesRecursively|| isSolved()!");
            return;
        }
        else {
            if (index < song.length){
                if(DEBUG) System.out.println("||generateHarmoniesRecursively|| index >= song.length! (index: " +
                        index + ". Song.length: " + song.length);

                if(DEBUG)System.out.println("||generateHarmoniesRecursively|| At index " + index);

                JNoteInfoTrio jitOrig, jitPrev, jitNew;
                Chord chd;
                if (index == 0) {
                    jitOrig = song[index];
                    int[] offsetsToTry = new int[]{third, JNote.FIFTH, JNote.OCTAVE, 0};
                    for (int i = 0; i < offsetsToTry.length; i++) {
                        jitNew = new JNoteInfoTrio(jitOrig.nt, jitOrig.rt, new JNote(jitOrig.nt, offsetsToTry[i]));
                        song[index] = jitNew;
                        if(DEBUG)    System.out.println("||generateHarmoniesRecursively|| index=0, offset: " + offsetsToTry[i]);
                        generateHarmoniesRecursively((index + 1), song);
                    }
                } else {
                    jitPrev = song[index - 1];

                    if(DEBUG)System.out.println("WATCH IT: " + index);
                    jitOrig = song[index];
                    chd = chordSet.get(jitOrig.rt.noteAsIntegerInCScale());
                    int[] hmyOffsetTries = harmonyOffsetSet(jitOrig.nt, chd);

                    if(DEBUG)System.out.println("||generateHarmoniesRecursively|| jitOrig" + jitOrig.toNormalizedNumString());

                    for (int i = 0; i < hmyOffsetTries.length; i++) {
                        jitNew = new JNoteInfoTrio(jitOrig.nt, jitOrig.rt, new JNote(jitOrig.nt, hmyOffsetTries[i]));
                        song[index] = jitNew;
                        if(DEBUG)System.out.println("||generateHarmoniesRecursively|| trying offset " + i + ": " + hmyOffsetTries[i]);
                        if(DEBUG)System.out.println("||generateHarmoniesRecursively|| = " + jitNew.toString());
                        if (Math.abs(jitNew.hmy.asInt() - jitPrev.hmy.asInt()) <= MAX_MARGIN){
                            generateHarmoniesRecursively((index + 1), song);
                        }
                    }
                }
//                return false;
            }
        }
    }

    public int[] harmonyOffsetSet(JNote j, Chord c){
        int o1, o2, o3, o4, o5, o6, note, tonic, octaveTonic, upperThird, upperFifth, lowerThird, lowerFifth, octave, octaveThird;
        note = j.noteAsIntegerInCScale();

        tonic = c.root;
        upperThird = c.third;
        upperFifth = c.fifth;
        octave = JNote.OCTAVE;

        if (upperThird<tonic){ // adding twelve (an octave) ensures correct subtraction
            upperThird += 12;
            upperFifth += 12;
        }
        else if (upperFifth<tonic){
            upperFifth += 12;
        }

        lowerThird = upperThird - 12;
        lowerFifth = upperFifth - 12;
        octaveTonic = tonic + 12;
        octaveThird = upperThird + 12;

        if (note == c.root){ // it's the tonic
            o1 = upperThird - tonic; //3 or 4
            o2 = upperFifth - tonic; //7
            o3 = octave;   // 12
            o4 = 0; // itself;
            o5 = lowerFifth - tonic; //-5;
            o6 = lowerThird - tonic; //-9 or -8
        }
        else if (note == c.third){
            o1 = upperFifth - upperThird; //3 or 4
            o2 = octaveTonic - upperThird;//8 or 9
            o3 = octave; // 12
            o4 = 0;
            o5 = tonic - upperThird; // -3
            o6 = lowerFifth - upperThird; // -8 or -9
        } else if (note == c.fifth){
            o1 = octaveTonic - upperFifth; // 5
            o2 = octaveThird - upperFifth; // 8 or 9
            o3 = octave; // 12
            o4 = 0;
            o5 = upperThird - upperFifth; // -4 or -3
            o6 = tonic - upperFifth; // -7
        } else {
            o1 = upperThird - tonic - note; //3 or 4
            o2 = upperFifth - tonic - note; //7
            o3 = octave;   // 12
            o4 = 0; // itself;
            o5 = lowerFifth - tonic - note; //-5;
            o6 = lowerThird - tonic - note; //-9 or -8
            System.out.println("||harmonyOffsetSet|| =====ERROR==== NOTE NOT IN CHORD. We pretend it's the root.");
        }
        return new int[]{o1,o2,o3,o4,o5,o6};
    }

    public boolean isSolved(ArrayList<JNoteInfoTrio> list){
        for (JNoteInfoTrio item : list ){
            if (!item.isAFullTriad()){
                return false;
            }
        }
        return true;
    }

    public boolean isSolved(JNoteInfoTrio[] song){
        for (int i=0;i<song.length;i++){
            if (!song[i].isAFullTriad()){
                if(DEBUG)System.out.println("||isSolved() is false. Not Full Triads.||");
                return false;
            }
        }
        for (int i=1;i<song.length;i++){
            if (Math.abs(song[i].hmy.asInt()-song[i-1].hmy.asInt())>MAX_MARGIN){
                if(DEBUG)System.out.println("||isSolved() is false. Not proper interval margins|| Index " + i);
                return false;
            }
        }
        if (!CAN_DOUBLE_NOTES){
            for (int i=1;i<song.length;i++){
                if (song[i].hmy.asInt()==song[i-1].hmy.asInt()){
                    if(DEBUG)System.out.println("||isSolved() is false. Two adjacent notes|| Index " + i);
                    return false;
                }
            }
        }
        System.out.println("||isSolved() is true||");
        return true;
    }

    public JNoteInfoTrio returnedToKey(JNoteInfoTrio jit) {
        int returnOffset = -1;
        String searchString = (key.contains("#")) ? key.substring(0,2) : key.substring(0,1);
        for (int i=0;i<JNote.notes.length;i++){

            if (JNote.notes[i].equalsIgnoreCase(searchString)){
                returnOffset = i;
                if(DEBUG)System.out.println("Key found: key " + key + " at " + i + " which is " + JNote.notes[i]);
                break;
            }
        }

        JNote note,root,harmony;
        note = new JNote(jit.nt,returnOffset);
        root = new JNote(jit.rt,returnOffset);
        if (jit.hmy==null){
            harmony = new JNote("c1q");
            System.out.println("||JNoteInfoTrio.returnedToKey()|| HARMONY WAS NULL. Fixed it.");
        }
        else {
            harmony = new JNote(jit.hmy, returnOffset);
        }
        return new JNoteInfoTrio(note,root,harmony);

    }

    public ArrayList<JNoteInfoTrio> spitOutHarmonyOutput(){
        //todo: turn song into arrayList
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
