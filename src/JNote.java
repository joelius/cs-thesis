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
    public static final int MINOR_THIRD = 3;
    public static final int MAJOR_THIRD = 4;
    public static final int FIFTH = 7;
    public static final int OCTAVE = 12;

    public static String[] notes = {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"};


    public JNote (String stringIn){
        indexOffset = 0;

        note = stringIn.charAt(0);

        if (stringIn.contains("#")) {
            isSharp = true;
            indexOffset = 1;
        }

        octave = Character.getNumericValue(stringIn.charAt(1+indexOffset));
        duration = stringIn.charAt(2+indexOffset);
        stringRepresentation = stringIn;
    }

    public JNote (int intIn88, char durationIn){
        duration = durationIn;
        octave = (intIn88/12) + 1;
        String tempNote = notes[(intIn88%12)];
        isSharp = tempNote.contains("#");
        note = tempNote.charAt(0);
        stringRepresentation = (isSharp) ? (note + "#" + octave + duration) : (note + "" + octave + duration);
    }

    public JNote (JNote orig, int intervalToAdd){
        this((orig.asInt() + intervalToAdd), orig.duration);
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

    public boolean isSameNoteInScale(JNote noteIn){
        return (this.note == noteIn.note) && (this.isSharp == noteIn.isSharp);
    }

    public boolean isIntervalXAboveNoteY(int x, JNote noteIn){
        JNote temp = new JNote(noteIn.asInt() + x, noteIn.getDurationAsChar());
        return (this.note == temp.note) && (this.isSharp == temp.isSharp);
    }

}

