import java.io.IOException;
import java.util.ArrayList;

/**
 * Harmony Data Loader is a bucket into which the data is dumped.
 * After this, it is then dumped onto whatever Harmony Data Set you wish.
 * Created by jolpatrik on 15-02-23.
 */
public class InputDataLoader {

    //TODO: test this class

    final boolean DEBUG = true;
    ArrayList<NoteInfo> data;

    public InputDataLoader(){
        data = new ArrayList<NoteInfo>();
    }

    public void populateWithDataFile(String pathToFile){
        int previousSize = data.size();
        int newSize;

        try { NoteInfoReader.readInDataFile(pathToFile);
        } catch (IOException e){System.err.print(e.getMessage());}

        if (DEBUG){System.out.println("populateWithDataFile");}

         for ( NoteInfoReader.NoteInfoTetra tetra : NoteInfoReader.lst){

        if (DEBUG){System.out.println("Trio. Note: " + tetra.nt + " Root: " + tetra.rt + " Harmony: " + tetra.hmy);}

            int numberOfPreviousNotes = data.size();

            data.add(new NoteInfo(tetra.nt, tetra.ln, tetra.rt, -1, NoteInfoReader.key, null));
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

    public HarmonyDataSet populateInputSet(HarmonyDataSet hds, String pathToFile) {
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

