import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
    private int MAX_MARGIN = 4;

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

    public void fillOutputListWithGeneratedHarmonies(){
        outputList = new ArrayList<JNoteInfoTrio>(Arrays.asList(fullSong));
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

        generateHarmoniesRecursively(0, fullSong);

        System.out.println("All done, apparently!");

        for (int i = 0; i<fullSong.length;i++){
            System.out.print(fullSong[i] + "\n" );
        }


        fillOutputListWithGeneratedHarmonies();

        System.out.println(isSolved(outputList) ? "It's solved!!" : "It's not solved. :(");



        //if hmy is null, then look at previous one, then try to make it as close as possible to the next hmy.
    }

    public void generateHarmoniesRecursively(int index, JNoteInfoTrio[] song){

        if (index >= song.length){
            System.out.println("||generateHarmoniesRecursively|| index >= song.length! (index: " +
                    index + ". Song.length: " + song.length);
        } else {

            if (isSolved(song)) {
                System.out.println("||generateHarmoniesRecursively|| isSolved()!");
            }
            else {

                System.out.println("||generateHarmoniesRecursively|| At index " + index);

                JNoteInfoTrio jitOrig, jitPrev, jitPrevPrev, jitNew;
                Chord chd;
                if (index < 2) {
                    jitOrig = song[index];
                    int[] offsetsToTry = new int[]{third, JNote.FIFTH, JNote.OCTAVE, 0};
                    for (int i = 0; i < offsetsToTry.length; i++) {
                        jitNew = new JNoteInfoTrio(jitOrig.nt, jitOrig.rt, new JNote(jitOrig.nt, offsetsToTry[i]));
                        song[index] = jitNew;
                        System.out.println("||generateHarmoniesRecursively|| index=0, offset: " + offsetsToTry[i]);
                        generateHarmoniesRecursively((index + 1), song);
                    }
                } else {
                    jitPrev = song[index - 1];
                    jitPrevPrev = song[index - 2];
                    if (Math.abs(jitPrev.hmy.asInt() - jitPrevPrev.hmy.asInt()) > MAX_MARGIN) {
                        System.out.println("||generateHarmoniesRecursively|| jitPrev.hmy-jitPrevPrev > MAX_MARGIN. Return false.");
                        System.out.println("||generateHarmoniesRecursively|| " + jitPrev.hmy.asInt() + " - " + jitPrevPrev.hmy.asInt() + " > MAX_MARGIN. Return false.");
                    } else {
                        System.out.println("WATCH IT: " + index);
                        jitOrig = song[index];
                        chd = chordSet.get(jitOrig.rt.noteAsIntegerInCScale());
                        int[] hmyOffsetTries = harmonyOffsetSet(jitOrig.nt, chd);

                        System.out.println("||generateHarmoniesRecursively|| jitOrig" + jitOrig.toNormalizedNumString());

                        for (int i = 0; i < hmyOffsetTries.length; i++) {
                            jitNew = new JNoteInfoTrio(jitOrig.nt, jitOrig.rt, new JNote(jitOrig.nt, hmyOffsetTries[i]));
                            song[index] = jitNew;
                            System.out.println("||generateHarmoniesRecursively|| trying offset " + i + ": " + hmyOffsetTries[i]);
                            System.out.println("||generateHarmoniesRecursively|| = " + jitNew.toString());

                            generateHarmoniesRecursively((index + 1), song);

                        }
                        System.out.println("||generateHarmoniesRecursively|| After offsetTries forloop. Return false.");
                    }
                }
            }
        }
    }

//    public boolean generateHarmoniesSortOfRecursively(int index){
//
//        if (index == song.length) {
//            System.out.println("||generateHarmoniesRecursively|| index == song.length. Return false.");
//            return false;
//        }
//
//        if (isSolved()){
//            System.out.println("||generateHarmoniesRecursively|| isSolved()!");
//            return true;
//        }
//
//        System.out.println("||generateHarmoniesRecursively|| At index " + index);
//
//        JNoteInfoTrio jitOrig, jitPrev, jitPrevPrev, jitNew;
//        Chord chd;
//        if (index<2) {
//            jitOrig = song[index];
//            int[] offsetsToTry = new int[]{third,JNote.FIFTH,JNote.OCTAVE,0};
//            for (int i = 0; i < offsetsToTry.length; i++) {
//                jitNew = new JNoteInfoTrio(jitOrig.nt, jitOrig.rt, new JNote(jitOrig.nt, offsetsToTry[i]));
//                song[index] = jitNew;
//                System.out.println("||generateHarmoniesRecursively|| index=0, offset: " + offsetsToTry[i]);
//                return generateHarmoniesRecursively(index + 1);
//            }
//            return false;
//        }
//        else {
//            jitPrev = song[index-1];
//            jitPrevPrev = song[index-2];
//            if (Math.abs(jitPrev.hmy.asInt()-jitPrevPrev.hmy.asInt()) > MAX_MARGIN){
//                System.out.println("||generateHarmoniesRecursively|| jitPrev.hmy-jitPrevPrev > MAX_MARGIN. Return false.");
//                System.out.println("||generateHarmoniesRecursively|| " + jitPrev.hmy.asInt() + " - " + jitPrevPrev.hmy.asInt() + " > MAX_MARGIN. Return false.");
//                return false;
//            }
//            else {
//                jitOrig = song[index];
//                chd = chordSet.get(jitOrig.rt.noteAsIntegerInCScale());
//                int[] hmyOffsetTries = harmonyOffsetSet(jitOrig.nt,chd);
//
//                System.out.println("||generateHarmoniesRecursively|| jitOrig" + jitOrig.toNormalizedNumString());
//
//                for (int i = 0;i<hmyOffsetTries.length;i++){
//                    jitNew = new JNoteInfoTrio(jitOrig.nt,jitOrig.rt,new JNote(jitOrig.nt,hmyOffsetTries[i]));
//                    song[index] = jitNew;
//                    System.out.println("||generateHarmoniesRecursively|| trying offset " + i + ": " + hmyOffsetTries[i]);
//                    System.out.println("||generateHarmoniesRecursively|| = " + jitNew.toString());
//
//                    return generateHarmoniesRecursively(index + 1);
//
//                }
//                System.out.println("||generateHarmoniesRecursively|| After offsetTries forloop. Return false.");
//                return false;
//            }
//        }
//    }

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
        System.out.println("||checking isSolved()||");

        for (int i=0;i<song.length;i++){
            if (!song[i].isAFullTriad()){
                System.out.println("||isSolved() is false||");
                return false;
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
                System.out.println("Key found: key " + key + " at " + i + " which is " + JNote.notes[i]);
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
