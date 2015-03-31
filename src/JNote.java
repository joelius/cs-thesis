/**
 * Created by jolpatrik on 15-03-31.
 */
public class JNote {

    private char note;
    private boolean isSharp;
    private int indexOffset;
    private int octave;
    private char length;
    private String stringRepresentation;

    public static String[] notes = {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"};

    public JNote (String stringIn){
        indexOffset = 0;

        note = stringIn.charAt(0);

        if (stringIn.length() > 2) {
            isSharp = true;
            indexOffset = 1;
        }

        octave = Character.getNumericValue(stringIn.charAt(1+indexOffset));
        length = stringIn.charAt(2+indexOffset);
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


    public char getNote() {
        return note;
    }

    public void setNote(char note) {
        this.note = note;
    }

    public boolean isSharp() {
        return isSharp;
    }

    public void setIsSharp(boolean isSharp) {
        this.isSharp = isSharp;
    }

    public int getIndexOffset() {
        return indexOffset;
    }

    public void setIndexOffset(int indexOffset) {
        this.indexOffset = indexOffset;
    }

    public int getOctave() {
        return octave;
    }

    public void setOctave(int octave) {
        this.octave = octave;
    }

    public char getLength() {
        return length;
    }

    public void setLength(char length) {
        this.length = length;
    }


}

