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
public class JNoteInfoReader {

    public static ArrayList<JNoteInfoTrio> lst = new ArrayList<JNoteInfoTrio>();
    public static String key = "";

    public static class JNoteInfoTrio
    {
        public JNote nt; // note
        public JNote rt; // root note of current chord
        public JNote hmy; // harmony of note

        private JNoteInfoTrio(JNote ntIn, JNote rtIn, JNote hmyIn)
        {
            nt = ntIn;
            rt = rtIn;
            hmy= hmyIn;
        }
    }

    public static void addToLst(String ln){
        String[] parts = ln.split(" ");

        JNoteInfoTrio nit = new JNoteInfoTrio(new JNote(parts[0]),new JNote(parts[1]),new JNote(parts[2]));

        lst.add(nit);
        System.out.println("n: " + nit.nt + ", r: " + nit.rt + ", h: " + nit.hmy + ", key: " + key);
    }

    public static void main(String args[]){

        String file1 = "/Users/jolpatrik/IdeaProjects/jfugue-tst/src/data/input.txt";
        try{
            readInDataFile(file1);
        }
        catch (IOException ioe){
            System.err.print(ioe.getMessage());
        }


    }

    public static void readInDataFile(String path) throws IOException {
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);

        try
        {

            String line;
            while ((line = br.readLine()).startsWith("#")){
                System.out.println("Comment: " + line);
            }

            String parts[] = line.split(": ");
            System.out.println("Key is in: " + parts[1]);
            key = parts[1];
            while ((line = br.readLine())!=null){
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
