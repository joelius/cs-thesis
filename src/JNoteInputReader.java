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
public class JNoteInputReader {

    public ArrayList<JNoteInfoTrio> lst;

    public String key;
    public String timeSig;
    public String tempo;

    public JNoteInputReader(){
        lst = new ArrayList<JNoteInfoTrio>();
        key = "";
        timeSig = "";
    }


    public void addToLst(String ln){
        String[] parts = ln.split(" ");
        JNote note, root;

        note = new JNote(parts[0]);

        if (parts[1].equalsIgnoreCase("L")){
            root = lst.get((lst.size()-1)).rt;
//            System.out.println("THIS!!!: " + lst.size() + ", :" + lst.get(lst.size()-1));
        } else {
            root = new JNote(parts[1]);
        }

        JNoteInfoTrio nit = new JNoteInfoTrio(note,root,null);

        lst.add(nit);
        System.out.println("n: " + nit.nt + ", r: " + nit.rt + ", key: " + key);
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
            if(parts.length>3){
                tempo = parts[3];
            }
            else {
                tempo = "120";
            }
            System.out.println("Key is: " + key);
            System.out.println("Time signature is: " + timeSig);
            System.out.println("Tempo is: " + tempo);

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
