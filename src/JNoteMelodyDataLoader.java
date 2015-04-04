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

            data.add(new JNoteMelodyDatum(trio.nt, trio.rt, key));
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

