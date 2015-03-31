import org.jfugue.Pattern;

import java.io.IOException;
import java.util.ArrayList;

/**
 * JFugue Data Loader is a bucket into which the data is dumped.
 * After this, it is then turned into a JFugue java file.
 * Created by jolpatrik on 15-02-23.
 */
public class JFugueDataLoader {

    char key;
    final boolean DEBUG = true;
    ArrayList<NoteInfo> data;
    ArrayList<String> patternStringArray;

    public JFugueDataLoader(){
        data = new ArrayList<NoteInfo>();
        patternStringArray = new ArrayList<String>();
    }

    public void populateWithDataFile(String pathToFile){
        int previousSize = data.size();
        int newSize;

        try { NoteInfoReader.readInDataFile(pathToFile);
        } catch (IOException e){System.err.print(e.getMessage());}

        if (DEBUG){System.out.println("loadDataFile");}

        String tempPattern = "";
        float totalBeats = 0;
        float currentNoteLength = 0;

        for ( NoteInfoReader.NoteInfoTetra tetra : NoteInfoReader.lst){

            if (currentNoteLength == 0) {

            }

            currentNoteLength += ( 1 / tetra.ln );

            tempPattern += NoteUtil.getMusicalNote(3,'F', true);

            if (DEBUG){System.out.println("Tetra. Note: " + tetra.nt + " Length: " + tetra.ln + " Root: " + tetra.rt + " Harmony: " + tetra.hmy);}

            currentNoteLength = (1 / tetra.ln);

            int numberOfPreviousNotes = data.size();

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

