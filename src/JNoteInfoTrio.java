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
}