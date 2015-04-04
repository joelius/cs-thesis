/**
 * Created by jolpatrik on 2015-04-04.
 */
public class JNoteHashKey {

    public static final int ALL_DATA = 0;
    public static final int NO_DURATION = 1;
    public static final int NO_INTERVAL_DATA = 2;
    public static final int ONLY_ROOT_AND_NOTE = 3;

    public int noteInScale;
    public char duration;
    public int rootNoteOfCurrentChord;
    public int sizeOfInterval;

    public JNoteHashKey (JNoteHarmonyDatum input, int type){

        JNoteHarmonyDatum temp = input.normalizedToCScale();

        noteInScale = temp.nt.noteAsIntegerInCScale();
        rootNoteOfCurrentChord = temp.rt.noteAsIntegerInCScale();
        duration = (type==ALL_DATA || type==NO_INTERVAL_DATA) ? temp.nt.getDurationAsChar() : 'N';
        sizeOfInterval = (type==ALL_DATA || type==NO_DURATION) ? temp.modeOfPrecedingIntervals : 24;
    }

    public JNoteHashKey (JNoteMelodyDatum input, int type){

        JNoteMelodyDatum temp = input.normalizedToCScale();
        noteInScale = temp.nt.noteAsIntegerInCScale();
        rootNoteOfCurrentChord = temp.rt.noteAsIntegerInCScale();
        duration = (type==ALL_DATA || type==NO_INTERVAL_DATA) ? temp.nt.getDurationAsChar() : 'N';
        sizeOfInterval = (type==ALL_DATA || type==NO_DURATION) ? temp.modeOfPrecedingIntervals : 24;

        //    sizeOfInterval = input.modeOfPrecedingIntervals;
        //    duration = input.nt.getDurationAsChar();

    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) {return true;}
        if (obj == null) {return false;}
        if (getClass() != obj.getClass()){return false;}

        JNoteHashKey other = (JNoteHashKey) obj;

        if (this.noteInScale!=other.noteInScale){
            return false;
        }
        if (this.rootNoteOfCurrentChord!=other.rootNoteOfCurrentChord){
            return false;
        }
        if (this.duration!=other.duration){
            return false;
        }
        if (this.sizeOfInterval!=other.sizeOfInterval){
            return false;
        }
        return true;
    }

    public String toString(){
        return "(" + noteInScale + ":" + rootNoteOfCurrentChord + ":" + duration + ":" + sizeOfInterval + ")";
    }
}