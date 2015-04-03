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

    public boolean isMajor(){

        return key.contains(("M"));

    }

}
