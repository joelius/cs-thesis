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
    private int tonus;
    private int rootOfChord;
    private int harmony;
    private String key;
    private int[] trend;

    public NoteInfo( int tone, int root, int harmonyIn, String keyIn, int[] trendIn) {
        tonus = tone;
        rootOfChord=root;
        harmony = harmonyIn;
        key = keyIn;
        trend = trendIn;
    }

    public int getTonus() {
        return tonus;
    }

    public void setTonus(int tonus) {
        this.tonus = tonus;
    }

    public int getRootOfChord() {
        return rootOfChord;
    }

    public void setRootOfChord(int rootOfChord) {
        this.rootOfChord = rootOfChord;
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
