import java.util.ArrayList;

/**
 * Created by jolpatrik on 15-02-23.
 */
public class JNoteHarmonyDatum extends JNoteInfoTrio {

    public int modeOfPrecedingIntervals;
    public int currentInterval;
    public String key;
    public ArrayList<Integer> intervalTrend;

    public JNoteHarmonyDatum(JNote nt, JNote rt, JNote hmy, ArrayList<Integer> intervalTrendIn, String keyIn){
        super (nt,rt,hmy);
        intervalTrend = intervalTrendIn;
        modeOfPrecedingIntervals = processIntervalTrend();
        currentInterval = processCurrentInterval();
        key = keyIn;
    }

    public int processCurrentInterval(){

        return (hmy == null || nt == null) ? 0 : (hmy.asInt() - nt.asInt());

    }

    public int processIntervalTrend(){

        Integer[] trendIn = intervalTrend.toArray(new Integer[intervalTrend.size()]);

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

    public String toString(){
        String note, root, harmony;

        note = (nt==null) ? "-x-" : nt.toString();
        root = (rt==null) ? "-x-" : rt.toString();
        harmony = (hmy==null) ? "-x-" : hmy.toString();

        return "nt: " + note + ", rt: " + root + ", hmy: " + harmony +
                ", modePI: " + modeOfPrecedingIntervals + ", amtPI: " + intervalTrend.size() +
                ", key: " + key + ", curIvl" + currentInterval;

    }

}
