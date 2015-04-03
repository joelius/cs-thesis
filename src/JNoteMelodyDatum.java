/**
 * Created by jolpatrik on 15-02-23.
 */
public class JNoteMelodyDatum extends JNoteInfoTrio {

    public int modeOfPrecedingIntervals;
    public String key;

    public JNoteMelodyDatum(JNote nt, JNote rt, int[] intervalTrend, String keyIn){
        super (nt,rt,null);
        modeOfPrecedingIntervals = processIntervalTrend(intervalTrend);
        key = keyIn;
    }

    public int processIntervalTrend(int[] trendIn){

        int t = 0;
        for(int i=0; i<trendIn.length; i++){
            for(int j=1; j<trendIn.length-i; j++){
                if(trendIn[j-1] > trendIn[j]){
                    t = trendIn[j-1];
                    trendIn[j-1] = trendIn[j];
                    trendIn[j] = t;
                }
            }
        }

        int mode = trendIn[0];
        int temp = 1;
        int temp2 = 1;
        for(int i=1;i<trendIn.length;i++){
            if(trendIn[i-1] == trendIn[i]){
                temp++;
            }
            else {
                temp = 1;
            }
            if(temp >= temp2){
                mode = trendIn[i];
                temp2 = temp;
            }
        }
        return mode;
    }

    public boolean isInMajorKey(){
        return key.contains(("M"));

    }


    public JNoteMelodyDatum normalizedToCScale(){
        JNoteMelodyDatum result;
        JNote ntTemp, rtTemp;
        int keyInt = JNote.getIntegerValueOfKeyInCScale(this.key);
        System.out.println("keyInt: " + keyInt);
        ntTemp = new JNote(this.nt, -keyInt);
        rtTemp = new JNote(this.rt, -keyInt);

        String newKey = (this.isInMajorKey()) ? "C-M" : "C-m";

        result = new JNoteMelodyDatum(ntTemp,rtTemp,new int[]{0,0,0},newKey);

        return result;
    }

    public String toString(){
        String result="";
        return "|JNoteMelodyDatum| Nt: " + this.nt.toString() + " | Rt: " + this.rt.toString() + " | Key: " + this.key;
    }
}
