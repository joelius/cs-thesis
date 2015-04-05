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
        return (nt!=null) && (rt!=null) && (hmy!=null);
    }

}