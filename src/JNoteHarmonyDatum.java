/**
 * Created by jolpatrik on 15-02-23.
 */
public class JNoteHarmonyDatum extends JNoteInfoTrio {

    public int modeOfPrecedingIntervals;
    public int currentInterval;

    public JNoteHarmonyDatum(JNote nt, JNote rt, JNote hmy, int[] intervalTrend){
        super (nt,rt,hmy);
        modeOfPrecedingIntervals = processIntervalTrend(intervalTrend);
        currentInterval = hmy.asInt() - nt.asInt();
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

}
