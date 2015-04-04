/**
 * Created by jolpatrik on 15-02-23.
 */
public class JNoteMelodyDatum extends JNoteInfoTrio {

    public int modeOfPrecedingIntervals;
    public String key;

    public JNoteMelodyDatum(JNote nt, JNote rt, String keyIn){
        super (nt,rt,null);
        key = keyIn;
    }

    public boolean isInMajorKey(){
        return key.contains(("M"));

    }


    public JNoteMelodyDatum normalizedToCScale(){
        JNoteMelodyDatum result;
        JNote ntTemp, rtTemp, prevHmyTemp;
        int keyInt = JNote.getIntegerValueOfKeyInCScale(this.key);
        System.out.println("keyInt: " + keyInt);
        ntTemp = new JNote(this.nt, -keyInt);
        rtTemp = new JNote(this.rt, -keyInt);

        String newKey = (this.isInMajorKey()) ? "C-M" : "C-m";

        result = new JNoteMelodyDatum(ntTemp,rtTemp,newKey);

        return result;
    }

    public String toString(){
        String result="";
        return "|JNoteMelodyDatum| Nt: " + this.nt.toString() + " | Rt: " + this.rt.toString() + " | Key: " + this.key;
    }
}
