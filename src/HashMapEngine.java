import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by jolpatrik on 2015-04-01.
 */
public class HashMapEngine extends HarmonyGenerationEngine {

    private final int ALL_DATA = 0;
    private final int NO_DURATION = 1;
    private final int NO_INTERVAL_DATA = 2;
    private final int ONLY_ROOT_AND_NOTE = 3;

    public class JNoteHashKey {
        public int noteInScale;
        public char duration;
        public int rootNoteOfCurrentChord;
        public int sizeOfInterval;

        private JNoteHashKey (JNoteHarmonyDatum input, int type){

            JNoteHarmonyDatum temp = input.normalizedToCScale();

            noteInScale = temp.nt.noteAsIntegerInCScale();
            rootNoteOfCurrentChord = temp.rt.noteAsIntegerInCScale();
            duration = (type==ALL_DATA || type==NO_INTERVAL_DATA) ? temp.nt.getDurationAsChar() : 'N';
            sizeOfInterval = (type==ALL_DATA || type==NO_DURATION) ? temp.modeOfPrecedingIntervals : 24;
        }

        private JNoteHashKey (JNoteMelodyDatum input, int type){

            JNoteMelodyDatum temp = input.normalizedToCScale();
            noteInScale = temp.nt.noteAsIntegerInCScale();
            rootNoteOfCurrentChord = temp.rt.noteAsIntegerInCScale();
            duration = (type==ALL_DATA || type==NO_INTERVAL_DATA) ? temp.nt.getDurationAsChar() : 'N';
            sizeOfInterval = (type==ALL_DATA || type==NO_DURATION) ? temp.modeOfPrecedingIntervals : 24;

            //    sizeOfInterval = input.modeOfPrecedingIntervals;
            //    duration = input.nt.getDurationAsChar();

        }

        public boolean equals(Object obj){
            if (this == obj) {return true;}
            if (obj == null) {return false;}
            if (getClass() != obj.getClass()){return false;}

            JNoteHashKey other = (JNoteHashKey) obj;

            if (this.noteInScale!=other.noteInScale){
                return false;
            }
            if (this.rootNoteOfCurrentChord!=other.rootNoteOfCurrentChord){
                return false;
            }
            if (this.duration!=other.duration){
                return false;
            }
            if (this.sizeOfInterval!=other.sizeOfInterval){
                return false;
            }
            return true;
        }

        public String toString(){
            return "(" + noteInScale + ":" + rootNoteOfCurrentChord + ":" + duration + ":" + sizeOfInterval + ")";
        }
    }

    private ArrayList<JNoteHarmonyDatum> harmonyDataSet;
    private HashMap<JNoteHashKey,Integer> brainAllData;
    private HashMap<JNoteHashKey,Integer> brainNoDurationData;
    private HashMap<JNoteHashKey,Integer> brainNoIntervalData;
    private HashMap<JNoteHashKey,Integer> brainOnlyRootAndNoteData;

    public HashMapEngine (ArrayList<JNoteHarmonyDatum> dataIn){
        harmonyDataSet = dataIn;
        brainAllData = new HashMap<JNoteHashKey, Integer>();
        brainNoDurationData = new HashMap<JNoteHashKey, Integer>();
        brainNoIntervalData = new HashMap<JNoteHashKey, Integer>();
        brainOnlyRootAndNoteData = new HashMap<JNoteHashKey, Integer>();
    }

    public void prepareEngine(){
        JNoteHashKey hKeyAllData,hKeyNoDuration,hkeyNoInterval,hKeyOnlyRtAndNtData;
        for (JNoteHarmonyDatum datum : harmonyDataSet){
            System.out.println(datum.toString());
            if (datum.hmy!=null) {
                hKeyAllData = new JNoteHashKey(datum,ALL_DATA);
                hKeyNoDuration = new JNoteHashKey(datum,NO_DURATION);
                hkeyNoInterval = new JNoteHashKey(datum,NO_INTERVAL_DATA);
                hKeyOnlyRtAndNtData = new JNoteHashKey(datum,ONLY_ROOT_AND_NOTE);

                brainAllData.put(hKeyAllData, datum.hmy.noteAsIntegerInCScale());
                brainNoDurationData.put(hKeyNoDuration, datum.hmy.noteAsIntegerInCScale());
                brainNoIntervalData.put(hkeyNoInterval, datum.hmy.noteAsIntegerInCScale());
                brainOnlyRootAndNoteData.put(hKeyOnlyRtAndNtData, datum.hmy.noteAsIntegerInCScale());
            }
        }
    }

    public JNote generateHarmony(JNoteMelodyDatum jmd){
        //System.out.println("generateHarmony. jmd.key = " + jmd.key + ".");
        JNote result;
        String jNoteString;
        int hmyNote;

        int melodyNote = jmd.nt.noteAsIntegerInCScale();
        JNoteHashKey hKeyAllData = new JNoteHashKey(jmd,ALL_DATA);
        JNoteHashKey hKeyNoDurationData = new JNoteHashKey(jmd,NO_DURATION);
        JNoteHashKey hKeyNoIntervalData = new JNoteHashKey(jmd,NO_INTERVAL_DATA);
        JNoteHashKey hKeyOnlyRootAndNoteData = new JNoteHashKey(jmd,ONLY_ROOT_AND_NOTE);

        //System.out.println(jmd.toString());

//        if (!brainAllData.containsKey(hKeyIn)) {
//            if (!brainNoDurationData.containsKey(hKeyIn)) {
//                if(!brainNoIntervalData.containsKey(hKeyIn)) {
//                    if(!brainOnlyRootAndNoteData.containsKey(hKeyIn)) {
//                        hmyNote = 0;
//                    }
//                    else {
//                        hmyNote = brainOnlyRootAndNoteData.get(hKeyIn);
//                    }
//                }
//                else {
//                    hmyNote = brainNoIntervalData.get(hKeyIn);
//                }
//            } else {
//                hmyNote = brainNoDurationData.get(hKeyIn);
//            }
//        } else {
//            hmyNote = brainAllData.get(hKeyIn);
//        }

        System.out.println("|Harmony generation| " + jmd.toString() + "||hashKey:|" + hKeyAllData.toString());

        if (brainAllData.containsKey(hKeyAllData)) {
            hmyNote = brainAllData.get(hKeyAllData);
            System.out.println("Generated from brainAllData!");
        } else {
            if (brainNoDurationData.containsKey(hKeyNoDurationData)) {
                hmyNote = brainNoDurationData.get(hKeyNoDurationData);
                System.out.println("Generated from brainNoDurationData!");
            } else {
                if (brainNoIntervalData.containsKey(hKeyNoIntervalData)) {
                    hmyNote = brainNoIntervalData.get(hKeyNoIntervalData);
                    System.out.println("Generated from brainNoIntervalData!");
                } else {
                    if (brainOnlyRootAndNoteData.containsKey(hKeyOnlyRootAndNoteData)) {
                        hmyNote = brainOnlyRootAndNoteData.get(hKeyOnlyRootAndNoteData);
                        System.out.println("Generated from brainOnlyRootAndNoteData!");
                    } else {
                        hmyNote = 0;
                        System.out.println("---x---Harmony NOT GENERATED---x---");
                    }
                }
            }
        }

        //System.out.println("input.key: " + jmd.key);
        jNoteString = JNote.jNoteStringBuilder(hmyNote,jmd.key,jmd.nt);

        result = new JNote(jNoteString);

        return result;
    }

    public String brainToString(HashMap<JNoteHashKey,Integer> brain){
        String result = "";
            Iterator it = brain.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                result += pair.getKey().toString() + " = " + pair.getValue().toString() + "\n";
                it.remove(); // avoids a ConcurrentModificationException
            }
        return result;
    }

    public String toString2 (){
        String result = "|HashMapEngine toString()| \n";
        result += "|brainAllData|\n" + brainAllData.toString() + "\n";
        result += "|brainNoDurationData|\n" + brainNoDurationData.toString() + "\n";
        result += "|brainNoIntervalData|\n" + brainNoIntervalData.toString() + "\n";
        result += "|brainOnlyRootAndNoteData|\n" + brainOnlyRootAndNoteData.toString() + "\n";
        return result;
    }
    public String toString (){
        String result = "|HashMapEngine toString()| \n";
        result += "|brainAllData|\n" + brainToString(brainAllData) + "\n";
        result += "|brainNoDurationData|\n" + brainToString(brainNoDurationData) + "\n";
        result += "|brainNoIntervalData|\n" + brainToString(brainNoIntervalData) + "\n";
        result += "|brainOnlyRootAndNoteData|\n" + brainToString(brainOnlyRootAndNoteData) + "\n";
        return result;
    }
}
