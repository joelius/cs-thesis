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
    boolean keyIsMajor;
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

        String[] keyAndMajMin = NoteInfoReader.key.split("-");

        key = keyAndMajMin[0].charAt(0);
        if (keyAndMajMin[1].charAt(0)=='M'){
            keyIsMajor = true;
        }
        else {
            keyIsMajor = false;
        }

        String tempPatternV0 = "";
        String tempPatternV1 = "";
        float totalBeats = 0;
        float currentNoteLength = 0;
        float noteLength;


        for ( NoteInfoReader.NoteInfoTetra tetra : NoteInfoReader.lst) {
            noteLength = (float) tetra.ln;

            if (currentNoteLength >= 4) {
                patternStringArray.add(tempPatternV0);
                tempPatternV0 = "";
                currentNoteLength = 0;

                currentNoteLength += (4 / noteLength);
                tempPatternV0 += NoteUtil.getMusicalNote(tetra.nt, key, keyIsMajor);
                tempPatternV0 += "5";
                tempPatternV0 += NoteUtil.getLengthAsMusicalValue(tetra.ln);
                tempPatternV0 += " ";

            }
            else {
                currentNoteLength += (4 / noteLength);
                if (DEBUG) System.out.println("tetra.ln: " + noteLength);
                if (DEBUG) System.out.println("4/tetra.ln: " + (4 / noteLength));
                if (DEBUG) System.out.println("currentNoteLength: " + currentNoteLength);
                if (DEBUG) System.out.println("tempPattern: " + tempPatternV0);

                tempPatternV0 += NoteUtil.getMusicalNote(tetra.nt, key, keyIsMajor);

                tempPatternV0 += "5";

                tempPatternV0 += NoteUtil.getLengthAsMusicalValue(tetra.ln);

                tempPatternV0 += " ";

//                if(DEBUG)System.out.println("Tetra. Note: " + tetra.nt + " Length: " + tetra.ln + " Root: " + tetra.rt + " Harmony: " + tetra.hmy);
            }
        }
        patternStringArray.add(tempPatternV0);


        if (DEBUG){System.out.println("Size of lst: " + NoteInfoReader.lst.size());}

        for (String pattern : patternStringArray){
            System.out.println(pattern);
        }

        if (DEBUG){System.out.println("Clearing lst (Emptying temporary data store).");}
        //clear NoteInfoReader list.
        NoteInfoReader.lst.clear();


        if (DEBUG){System.out.println("Size of lst: " + NoteInfoReader.lst.size());}

    }


    public ArrayList<NoteInfo> getData() {
        return data;
    }
}

