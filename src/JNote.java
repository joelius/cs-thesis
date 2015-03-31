/**
 * Created by jolpatrik on 15-03-31.
 */
public class JNote {

    public char note;
    public boolean isSharp;
    public int indexOffset;
    public int octave;
    private char duration;
    private String stringRepresentation;

    public static String[] notes = {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"};


    public JNote (String stringIn){
        indexOffset = 0;

        note = stringIn.charAt(0);

        if (stringIn.length() > 3) {
            isSharp = true;
            indexOffset = 1;
        }

        octave = Character.getNumericValue(stringIn.charAt(1+indexOffset));
        duration = stringIn.charAt(2+indexOffset);
        stringRepresentation = stringIn;
    }

    public int getIntegerRepresentation(){
        int result = 0;
        int octaveOffset;

        for (int i=0;i<notes.length;i++){
            if (this.note==notes[i].charAt(0)){
                result += i;
                break;
            }
        }
        if(isSharp) result +=1;

        octaveOffset = (this.octave-1)*12;
        result += octaveOffset;

        return result;
    }

    public String toString(){
        return stringRepresentation;
    }

    public int getDuration(){
        int result;
        switch (this.duration){
            case 'w' : result = 1; break;
            case 'h' : result = 2; break;
            case 'q' : result = 4; break;
            case 'e' : result = 8; break;
            case 's' : result = 16; break;
            default  : result = 32; break;
        }
        return result;
    }
}

