import org.jfugue.Note;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jolpatrik on 15-02-23.
 */
public class HarmonyGenerator {

    private HarmonyDataSet hds;
    private HarmonyDataSet inputMelodies;
    private HarmonyDataSet output;

    private HashMap<NoteInfo, Integer> dataMap;

    public HarmonyGenerator(HarmonyDataSet hdsIn, HarmonyDataSet inputMelodiesIn){
        hds = hdsIn;
        inputMelodies = inputMelodiesIn;
        output = new HarmonyDataSet();
        dataMap = new HashMap<NoteInfo, Integer>();
    }


    // TODO: test this method
    public void processData(){
        NoteInfo noHarmony; // populating the hashmap without the harmony info in the index,
                            // so can index on all the data except the harmony data, which is
                            // what we're trying to generate
        NoteInfo noTrend;

        for (NoteInfo item : hds.dataset){

            noHarmony = new NoteInfo(item.getNote(), item.getLength(), item.getRoot(), -1, item.getKey(), item.getTrend());

            dataMap.put(noHarmony, item.getHarmony());
        }
    }


    // TODO: test this method
    public void processDataWithoutLengthInfo(){
       NoteInfo noLength;

        for (NoteInfo item : hds.dataset){

            noLength = new NoteInfo(item.getNote(), -1 , item.getRoot(), -1, item.getKey(), item.getTrend());

            dataMap.put(noLength, item.getHarmony());
        }
    }


    // TODO: test this method
    public void processDataWithoutTrendInfo(){
        NoteInfo noHarmony; // populating the hashmap without the harmony info in the index,
        // so can index on all the data except the harmony data, which is
        // what we're trying to generate
        NoteInfo noTrend;

        for (NoteInfo item : hds.dataset){

            noHarmony = new NoteInfo(item.getNote(), item.getLength(), item.getRoot(), -1, item.getKey(), item.getTrend());
            noTrend = new NoteInfo(item.getNote(), item.getLength(), item.getRoot(), -1, item.getKey(), null);

            // this puts all possible variations of data input, in the case that the note we're trying
            // to generate doesn't have trend information

            dataMap.put(noHarmony, item.getHarmony());
            dataMap.put(noTrend, item.getHarmony());
        }
    }

    // TODO: test this method
    public void processDataWithNeitherLengthNorTrendInfo(){
        NoteInfo neitherLengthNorTrend;

        for (NoteInfo item : hds.dataset){

            neitherLengthNorTrend = new NoteInfo(item.getNote(), -1 , item.getRoot(), -1, item.getKey(), null);

            dataMap.put(neitherLengthNorTrend, item.getHarmony());
        }
    }


    // TODO: test this method
    public void generateHarmonies(){
        NoteInfo temp;
        Integer harmony;
        for (NoteInfo item : inputMelodies.dataset){

            harmony = dataMap.get(item);
            temp = new NoteInfo(item.getNote(), item.getLength(), item.getRoot(),harmony,item.getKey(),null);
            output.dataset.add(temp);
        }
    }

    public HarmonyDataSet getHds() {
        return hds;
    }

    public HarmonyDataSet getInputMelodies() {
        return inputMelodies;
    }

    public HarmonyDataSet getOutput() {
        return output;
    }
}
