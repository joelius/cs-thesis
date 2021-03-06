import java.io.IOException;
import java.util.ArrayList;

/**
 * Harmony Data Loader is a bucket into which the data is dumped.
 * After this, it is then dumped onto whatever Harmony Data Set you wish.
 * Created by jolpatrik on 15-02-23.
 */
public class JNoteHarmonyDataLoader {

    final boolean DEBUG = true;
    ArrayList<JNoteHarmonyDatum> data;
    String key;

    public JNoteHarmonyDataLoader(){
        data = new ArrayList<JNoteHarmonyDatum>();
    }

    public void processJNoteInfoTrioArray(ArrayList<JNoteInfoTrio> lst, String keyIn){

        key = keyIn;

        ArrayList<Integer> trend;

        JNoteHarmonyDatum temp;

        for ( JNoteInfoTrio trio : lst){

            trend = new ArrayList<Integer>();

            if (DEBUG){System.out.println("JNoteHarmonyDataLoader: " + trio.toString());}

            int numberOfPreviousNotes = data.size();
            if (numberOfPreviousNotes < 1){ // if statements to set trend of previous harmonies
                trend.add(0);
            }
            else if (numberOfPreviousNotes == 1) {
                temp = data.get(0);
                trend.add(temp.currentInterval);
            }
            else {
                temp = data.get(numberOfPreviousNotes-1);
                trend.addAll(temp.intervalTrend);
                trend.add(temp.currentInterval);
            }
            data.add(new JNoteHarmonyDatum(trio.nt, trio.rt, trio.hmy, trend, key));
            if (DEBUG) {System.out.println("Size of data: " + data.size());}
        }

    }

    public String dataToString(){
        String result = "";
        for (JNoteHarmonyDatum datum : data){
            result += datum.toString();
        }
        return result;
    }

    public ArrayList<JNoteHarmonyDatum> getData() {
        return data;
    }
}

