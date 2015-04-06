import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by jolpatrik on 2015-04-01.
 */
public class HashMapEngine extends HarmonyGenerationEngine {

    ArrayList<JNoteMelodyDatum> inputList;
    ArrayList<JNoteInfoTrio> outputList;

    private ArrayList<JNoteHarmonyDatum> harmonyDataSet;
    private HashMap<String,Integer> brainAllData;
    private HashMap<String,Integer> brainNoDurationData;
    private HashMap<String,Integer> brainNoIntervalData;
    private HashMap<String,Integer> brainOnlyRootAndNoteData;

    public HashMapEngine (ArrayList<JNoteHarmonyDatum> dataIn){
        harmonyDataSet = dataIn;
        brainAllData = new HashMap<String, Integer>();
        brainNoDurationData = new HashMap<String, Integer>();
        brainNoIntervalData = new HashMap<String, Integer>();
        brainOnlyRootAndNoteData = new HashMap<String, Integer>();
    }

    public void feedInMelodyInput(ArrayList<JNoteMelodyDatum> input){
      inputList = input;
    }


    public void prepareEngine(){
        outputList = new ArrayList<JNoteInfoTrio>();
        JNoteHashKey hKeyAllData,hKeyNoDuration,hkeyNoInterval,hKeyOnlyRtAndNtData;
        JNoteHarmonyDatum temp;
        for (JNoteHarmonyDatum datum : harmonyDataSet){
            temp = datum.normalizedToCScale();

            System.out.println(datum.toString());
            if (datum.hmy!=null) {
                hKeyAllData = new JNoteHashKey(temp,JNoteHashKey.ALL_DATA);
                hKeyNoDuration = new JNoteHashKey(temp,JNoteHashKey.NO_DURATION);
                hkeyNoInterval = new JNoteHashKey(temp,JNoteHashKey.NO_INTERVAL_DATA);
                hKeyOnlyRtAndNtData = new JNoteHashKey(temp,JNoteHashKey.ONLY_ROOT_AND_NOTE);

                brainAllData.put(hKeyAllData.toString(), temp.hmy.noteAsIntegerInCScale());
                brainNoDurationData.put(hKeyNoDuration.toString(), temp.hmy.noteAsIntegerInCScale());
                brainNoIntervalData.put(hkeyNoInterval.toString(), temp.hmy.noteAsIntegerInCScale());
                brainOnlyRootAndNoteData.put(hKeyOnlyRtAndNtData.toString(), temp.hmy.noteAsIntegerInCScale());
            }
        }
    }

    public void run(){
        JNoteInfoTrio temp;
        for (JNoteMelodyDatum jmd : inputList){
            temp = new JNoteInfoTrio(jmd.nt,jmd.rt,generateHarmony(jmd));
            outputList.add(temp);
        }
    }


    public JNote generateHarmony(JNoteMelodyDatum jmd){
        //System.out.println("generateHarmony. jmd.key = " + jmd.key + ".");
        JNote result;
        String jNoteString;
        int hmyNote;

        int melodyNote = jmd.nt.noteAsIntegerInCScale();
        JNoteHashKey hKeyAllData = new JNoteHashKey(jmd,JNoteHashKey.ALL_DATA);
        JNoteHashKey hKeyNoDurationData = new JNoteHashKey(jmd,JNoteHashKey.NO_DURATION);
        JNoteHashKey hKeyNoIntervalData = new JNoteHashKey(jmd,JNoteHashKey.NO_INTERVAL_DATA);
        JNoteHashKey hKeyOnlyRootAndNoteData = new JNoteHashKey(jmd,JNoteHashKey.ONLY_ROOT_AND_NOTE);

        System.out.println("|Harmony generation| " + jmd.toString() + "||hashKey:|" + hKeyAllData.toString());
        System.out.println(brainAllData.keySet());
        System.out.println("|Harmony generation ROOT AND NOTE| " + jmd.toString() + "||hashKey:|" + hKeyOnlyRootAndNoteData.toString());
        System.out.println(brainOnlyRootAndNoteData.keySet());

        if (brainAllData.containsKey(hKeyAllData.toString())) {
            hmyNote = brainAllData.get(hKeyAllData.toString());
            System.out.println("Generated from brainAllData!");
        } else {
            if (brainNoDurationData.containsKey(hKeyNoDurationData.toString())) {
                hmyNote = brainNoDurationData.get(hKeyNoDurationData.toString());
                System.out.println("Generated from brainNoDurationData!");
            } else {
                if (brainNoIntervalData.containsKey(hKeyNoIntervalData.toString())) {
                    hmyNote = brainNoIntervalData.get(hKeyNoIntervalData.toString());
                    System.out.println("Generated from brainNoIntervalData!");
                } else {
                    if (brainOnlyRootAndNoteData.containsKey(hKeyOnlyRootAndNoteData.toString())) {
                        hmyNote = brainOnlyRootAndNoteData.get(hKeyOnlyRootAndNoteData.toString());
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

    public ArrayList<JNoteInfoTrio> spitOutHarmonyOutput(){
        return outputList;
    }

    public String brainToString(HashMap<String,Integer> brain){
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
