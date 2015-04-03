import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jolpatrik on 15-02-23.
 * Takes input in the form of JFugue Notes C5q, for example
 * might have to turn this all into non-static, for simplicity.
 * Can then call it all in one big driver file.
 */
public class JNoteHarmonyInfoReader {

    public ArrayList<JNoteInfoTrio> lst;

    public String key;
    public String timeSig;

    public JNoteHarmonyInfoReader(){
        lst = new ArrayList<JNoteInfoTrio>();
        key = "";
        timeSig = "";
    }


    public void addToLst(String ln){
        String[] parts = ln.split(" ");
        JNote note, root, harmony;

        note = new JNote(parts[0]);

        if (parts[1].equalsIgnoreCase("L")){
                root = lst.get(lst.size()-1).rt;
        } else {
            root = new JNote(parts[1]);
        }

        if (parts[2].equalsIgnoreCase("N")){
            harmony = null;
        } else {
            harmony = new JNote(parts[2]);
        }

        JNoteInfoTrio nit = new JNoteInfoTrio(note,root,harmony);

        lst.add(nit);
        System.out.println("n: " + nit.nt + ", r: " + nit.rt + ", h: " + nit.hmy + ", key: " + key);
    }


    public void readInDataFile(String path) throws IOException {
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);

        try
        {

            String line;
            while ((line = br.readLine()).startsWith("#")){
                System.out.println("Comment: " + line);
            }

            String parts[] = line.split(": ");
            key = parts[1];
            timeSig = parts[2];
            System.out.println("Key is: " + key);
            System.out.println("Time signature is: " + timeSig);

            while ((line = br.readLine())!=null){
                System.out.println(line);
                addToLst(line);
            }


        } catch ( FileNotFoundException fnf)
        {
            System.err.print(fnf.getMessage());
        } catch (IOException ioe){
            System.err.print(ioe.getMessage());
        } finally{
            br.close();
            fr.close();
        }

    }
}
