import java.util.ArrayList;

/**
 * Harmony Data Loader is a bucket into which the data is dumped.
 * After this, it is then dumped onto whatever Harmony Data Set you wish.
 * Created by jolpatrik on 15-02-23.
 */
public class JNoteMelodyDataLoader {

    final boolean DEBUG = true;
    ArrayList<JNoteMelodyDatum> data;
    String key;

    public JNoteMelodyDataLoader(){
        data = new ArrayList<JNoteMelodyDatum>();
    }

    public void processJNoteInfoTrioArray(ArrayList<JNoteInfoTrio> lst, String keyIn){
        key = keyIn;
//        ArrayList<Integer> trend;
        int[] trend;
        JNoteMelodyDatum temp;

        for ( JNoteInfoTrio trio : lst){

//            trend = new ArrayList<Integer>();

            trend = new int[]{0,0,0};
//
//            if (DEBUG){System.out.println(trio.toString());}
//
//            int numberOfPreviousNotes = data.size();
//            if (numberOfPreviousNotes < 1){ // if statements to set trend of previous harmonies
//                trend.add(0);
//            }
//            else if (numberOfPreviousNotes == 1) {
//                temp = data.get(0);
//                trend.add(temp.currentInterval);
//            }
//            else {
//                temp = data.get(numberOfPreviousNotes-1);
//                trend.addAll(temp.intervalTrend);
//                trend.add(temp.currentInterval);
//            }
            data.add(new JNoteMelodyDatum(trio.nt, trio.rt, trend, key));
            if (DEBUG) {System.out.println("Size of data: " + data.size());}
        }
        if (DEBUG){System.out.println("Size of lst: " + NoteInfoReader.lst.size());}

    }

    public String dataToString(){
        String result = "";
        for (JNoteMelodyDatum datum : data){
            result += datum.toString();
        }
        return result;
    }

    public ArrayList<JNoteMelodyDatum> getData() {
        return data;
    }
}

