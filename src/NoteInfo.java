/**
 * Created by jolpatrik on 15-02-23.
 */
public class NoteInfo {

    /**
 * Actual music note in the scale.
 * Integers 1 to 7:
 * For C major: C:1, D:2, E:3, F:4, G:5, A:6, B:7
 * For A minor: A:1, B:2, C:3, D:4, E:5, F:6, G:7
*/
    private int note;
    private int root;
    private int length;
    private int harmony;
    private String key;
    private int[] trend;

    public NoteInfo( int noteIn, int lengthIn, int rootIn, int harmonyIn, String keyIn, int[] trendIn) {
        note = noteIn;
        length = lengthIn;
        root = rootIn;
        harmony = harmonyIn;
        key = keyIn;
        trend = trendIn;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int noteIn) {
        this.note = noteIn;
    }

    public int getRoot() { return root; }

    public void setRoot(int rootIn) {
        this.root = rootIn;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int[] getTrend() {
        return trend;
    }

    public void setTrend(int[] trend) {
        this.trend = trend;
    }

    public int getHarmony() {
        return harmony;
    }

    public void setHarmony(int harmony) {
        this.harmony = harmony;
    }
}
