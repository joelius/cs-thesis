import java.util.ArrayList;

/**
 * Created by jolpatrik on 2015-04-01.
 */
public class HarmonyGenerationMachine {

    private ArrayList<JNoteMelodyDatum> input;
    private ArrayList<JNoteInfoTrio> output;
    public HarmonyGenerationEngine engine;

    public HarmonyGenerationMachine (ArrayList<JNoteMelodyDatum> iIn, HarmonyGenerationEngine eIn ){
        input = iIn;
        output = new ArrayList<JNoteInfoTrio>();
        engine = eIn;
    }

    public void powerOn(){

        engine.feedInMelodyInput(input);
        engine.prepareEngine();
    }

    public void run(){
        System.out.println("machine.run(). input.key = " + input.get(0).key + ".");
        System.out.println("machine.run()");

        engine.run();

        output = engine.spitOutHarmonyOutput();
    }

    public String outputToString(){
        String result = "";
        if (output.isEmpty()){System.out.println("Output arrayList is empty.");}
        else{
            for (JNoteInfoTrio jit : output){
                result += jit.toString() + "\n";
            }
        }
        return result;
    }

    public ArrayList<JNoteInfoTrio> getOutput() {
        return output;
    }

}
