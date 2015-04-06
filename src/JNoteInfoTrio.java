/**
 * Created by jolpatrik on 2015-04-01.
 */
public class JNoteInfoTrio
{
    public JNote nt; // note
    public JNote rt; // root note of current chord
    public JNote hmy; // harmony of note

    public JNoteInfoTrio(JNote ntIn, JNote rtIn, JNote hmyIn)
    {
        nt = ntIn;
        rt = rtIn;
        hmy= hmyIn;
    }

    public JNoteInfoTrio(){
        nt = null;
        rt = null;
        hmy = null;
    }

    public String toString(){
        String note, root, harmony;

        note = (nt==null) ? "-x-" : nt.toString();
        root = (rt==null) ? "-x-" : rt.toString();
        harmony = (hmy==null) ? "-x-" : hmy.toString();

        return "JNoteInfoTrio: Note: " + note + " Root: " + root + " Harmony: " + harmony;
    }

    public boolean isAFullTriad(){
        System.out.println("isAFullTriad: " + this.toString());
        return (nt!=null) && (rt!=null) && (hmy!=null) && (!hmy.toString().contains("x"));
    }

    public String toNormalizedNumString(){
        int note, root, harmony;

        note = (nt==null) ? -99 : nt.noteAsIntegerInCScale();
        root = (rt==null) ? -99 : rt.noteAsIntegerInCScale();
        harmony = (hmy==null) ? -99 : hmy.noteAsIntegerInCScale();

        return "JNoteInfoTrio: Note: " + note + " Root: " + root + " Harmony: " + harmony;
    }

}