import java.io.IOException;
import java.util.ArrayList;

/**
 * Harmony Data Loader is a bucket into which the data is dumped.
 * After this, it is then dumped onto whatever Harmony Data Set you wish.
 * Created by jolpatrik on 15-02-23.
 */
public class HarmonyDataLoader {

    final boolean DEBUG = true;
    ArrayList<NoteInfo> data;

    public HarmonyDataLoader(){
        data = new ArrayList<NoteInfo>();
    }

    public void populateWithDataFile(String pathToFile){
        int previousSize = data.size();
        int newSize;

        try { NoteInfoReader.readInDataFile(pathToFile);
        } catch (IOException e){System.err.print(e.getMessage());}

        if (DEBUG){System.out.println("loadDataFile");}

        int[] trend;

        for ( NoteInfoReader.NoteInfoTetra tetra : NoteInfoReader.lst){

        if (DEBUG){System.out.println("Tetra. Note: " + tetra.nt + " Root: " + tetra.rt + " Harmony: " + tetra.hmy);}

            int numberOfPreviousNotes = data.size();
            if (numberOfPreviousNotes < 1){ // if statements to set trend of previous harmonies
                trend = new int[] {0,0,0};
            }
            else if (numberOfPreviousNotes == 1) {
                NoteInfo temp = data.get(0);
                trend = new int[] {0,0, temp.getHarmony()-temp.getNote()};
            }
            else if (numberOfPreviousNotes == 2) {
                NoteInfo temp = data.get(0);
                NoteInfo temp2 = data.get(1);
                trend = new int[] {0, temp.getHarmony()-temp.getNote(), temp2.getHarmony()-temp2.getNote()};
            }
            else{
                NoteInfo temp = data.get(numberOfPreviousNotes-3);
                NoteInfo temp2 = data.get(numberOfPreviousNotes-2);
                NoteInfo temp3 = data.get(numberOfPreviousNotes-1);
                trend = new int[] {temp.getHarmony()-temp.getNote(), temp2.getHarmony()-temp2.getNote(), temp3.getHarmony()-temp3.getNote()};
            }
            data.add(new NoteInfo(tetra.nt, tetra.ln, tetra.rt, tetra.hmy, NoteInfoReader.key, trend));
            if (DEBUG) {System.out.println("Size of data: " + data.size());}
        }
        if (DEBUG){System.out.println("Size of lst: " + NoteInfoReader.lst.size());}

        newSize = data.size();

        if ((newSize-previousSize)==NoteInfoReader.lst.size())
        {
            if (DEBUG){System.out.println("Totals match. Clearing lst");}
            //clear NoteInfoReader list.
            NoteInfoReader.lst.clear();
        }
        else{
            System.out.println("Totals do not match. Discrepancy between lst and data.");

        }

        if (DEBUG){System.out.println("Size of lst: " + NoteInfoReader.lst.size());}

    }

    public HarmonyDataSet populateDataSet(HarmonyDataSet hds, String pathToFile) {
        populateWithDataFile(pathToFile);
        System.out.println("DATASET SIZE: " + hds.dataset.size());
        hds.dataset.addAll(data);

        System.out.println("DATASET SIZE after add all: " + hds.dataset.size());
        data.clear();

        return hds;
    }

    public ArrayList<NoteInfo> getData() {
        return data;
    }
}

