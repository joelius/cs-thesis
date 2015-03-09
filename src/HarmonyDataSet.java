import java.util.ArrayList;

/**
 * Created by jolpatrik on 15-02-24.
 */
public class HarmonyDataSet {

    public ArrayList<NoteInfo> dataset;
    public String filename;

    private HarmonyDataLoader hdl;

    public HarmonyDataSet(){
        dataset = new ArrayList<NoteInfo>();
        filename = "";
    }

}
