import java.util.HashMap;

/**
 * Created by jol on 15-03-30.
 */

// For C major: C:1, D:2, E:3, F:4, G:5, A:6, B:7
// For A minor: A:1, B:2, C:3, D:4, E:5, F:6, G:7
public final class NoteUtil {

    public static String[] notes = {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"};
    public static String[] offsetNotes = {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"};
    public static int[] convertDiatonicToChromaticMajor = {0,2,4,5,7,9,11};
    public static int[] convertDiatonicToChromaticMinor = {0,2,3,5,7,8,10};

    public static String getMusicalNote(int num, char key, boolean isMajorKey) {

        buildChromaticScale(key);

        num -= 1;

        if (isMajorKey){
            num = convertDiatonicToChromaticMajor[num];
        }
        else {
            num = convertDiatonicToChromaticMinor[num];
        }
        return offsetNotes[num];
    }

    public static void buildChromaticScale(char key){
        int offset = 0;
        String[] temp = new String[12];

        switch (key){
            case 'A': offset = 3; break;
            case 'B': offset = 1; break;
            case 'D': offset = 10; break;
            case 'E': offset = 8; break;
            case 'F': offset = 7; break;
            case 'G': offset = 5; break;
            default : offset = 0; break;
        }

        for (int i=0;i<12;i++){
            offsetNotes[(i+offset)%12]=notes[i];
        }

    }

    public static String getLengthAsMusicalValue(int length){
        String result;
        switch (length){
            case 4: result = "q"; break;
            case 2: result = "h"; break;
            case 1: result = "w"; break;
            case 8: result = "e"; break;
            case 16: result = "s"; break;
            default: result = "X"; break;
        }
        return result;
    }

}
