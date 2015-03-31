import java.io.IOException;
import java.util.ArrayList;

/**
 * JFugue Data Loader is a bucket into which the data is dumped.
 * After this, it is then turned into a JFugue java file.
 * Created by jolpatrik on 15-02-23.
 */
public class JFugueInputLoader {

    char key;
    boolean keyIsMajor;
    final boolean DEBUG = true;
    ArrayList<NoteInfo> data;
    ArrayList<String> patternStringArray;

    public JFugueInputLoader(){
        data = new ArrayList<NoteInfo>();
        patternStringArray = new ArrayList<String>();
    }

    public void populateWithDataFile(String pathToFile){
        int previousSize = data.size();
        int newSize;

        try { JNoteInfoReader.readInDataFile(pathToFile);
        } catch (IOException e){System.err.print(e.getMessage());}

        if (DEBUG){System.out.println("loadDataFile");}

        String[] keyAndMajMin = JNoteInfoReader.key.split("-");

        key = keyAndMajMin[0].charAt(0);
        if (keyAndMajMin[1].charAt(0)=='M'){
            keyIsMajor = true;
        }
        else {
            keyIsMajor = false;
        }

        String tempV0Melody = "V0 ";
        String tempV1RootNotes = "V1 ";
        String tempV2Harmony = "V2 ";
        float currentNoteLength = 0;
        float noteLength;


        for ( JNoteInfoReader.JNoteInfoTrio trio : JNoteInfoReader.lst) {
            noteLength = (float) trio.nt.getDuration();

            if (currentNoteLength >= 4) {
                tempV0Melody += "\" +\n \"| ";
                tempV1RootNotes += "\" +\n \"| ";
                tempV2Harmony += "\" +\n \"| ";
                currentNoteLength = 0;
            } 

            currentNoteLength += (4 / noteLength);
            if (DEBUG) System.out.println("trio.ln: " + noteLength);
            if (DEBUG) System.out.println("4/trio.ln: " + (4 / noteLength));
            if (DEBUG) System.out.println("currentNoteLength: " + currentNoteLength);
            if (DEBUG) System.out.println("tempPattern: " + tempV0Melody);

            tempV0Melody += trio.nt.toString() + " ";

            if (trio.rt!=null) {
                tempV1RootNotes += trio.rt.toString() + " ";
            }
            if (trio.hmy!=null) {
                tempV2Harmony += trio.hmy.toString() + " ";
            }
            else {
                tempV2Harmony += "R ";
            }

//                if(DEBUG)System.out.println("trio. Note: " + trio.nt + " Length: " + trio.ln + " Root: " + trio.rt + " Harmony: " + trio.hmy);


        }
        patternStringArray.add(tempV0Melody);
        patternStringArray.add(tempV1RootNotes);
        patternStringArray.add(tempV2Harmony);


        if (DEBUG){System.out.println("Size of lst: " + JNoteInfoReader.lst.size());}

        for (String pattern : patternStringArray){
            System.out.println(pattern);
        }

        if (DEBUG){System.out.println("Clearing lst (Emptying temporary data store).");}
        //clear JNoteInfoReader list.
        JNoteInfoReader.lst.clear();


        if (DEBUG){System.out.println("Size of lst: " + JNoteInfoReader.lst.size());}

    }


    public ArrayList<NoteInfo> getData() {
        return data;
    }
}

