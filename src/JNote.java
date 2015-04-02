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

    public JNote (){
        note = 'C';
        isSharp = false;
        indexOffset = 0;
        octave = 4;
        duration = 'q';
        stringRepresentation = "C4q";
    }

    public int asInt(){
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
            case 'i' : result = 8; break;
            case 's' : result = 16; break;
            default  : result = 32; break;
        }
        return result;
    }

    public char getDurationAsChar(){
        return duration;
    }

    public int noteAsIntegerInCScale(){
        return this.asInt() % 12;
    }

    public static String jNoteStringBuilder (int hmyNoteInCScale, String inputKey, JNote inputNote){
        String result ="";
        String testKey = inputKey.substring(0,2);
        int keyNoteInCScale = -1;
        int isInNextOctave = 0;

        for (int i=0;i<notes.length;i++){
            if (testKey.equalsIgnoreCase(notes[i])){
                keyNoteInCScale = i;
            }

        }
        if (keyNoteInCScale==-1){ //i.e. hasn't been found yet
            for (int i=0;i<notes.length;i++){
                if (testKey.substring(0,1).equalsIgnoreCase(notes[i])){
                    keyNoteInCScale = i;
                }
            }
        }

        hmyNoteInCScale = (hmyNoteInCScale+keyNoteInCScale)%12;

        result += notes[hmyNoteInCScale];

        if (hmyNoteInCScale < inputNote.note){
            isInNextOctave = 1;
        }
        result += (inputNote.octave+isInNextOctave);
        result += inputNote.getDurationAsChar();

        return result;

    }
}

