import java.io.*;
import java.util.ArrayList;

/**
 * Created by jolpatrik on 15-02-23.
 * might have to turn this all into non-static, for simplicity.
 * Can then call it all in one big driver file.
 */
public class NoteInfoReader {

    public static ArrayList<NoteInfoTetra> lst = new ArrayList<NoteInfoTetra>();
    public static String key = "";

    public static class NoteInfoTetra
    {
        public int nt; // note
        public int ln; // length
        public int rt; // root note of current chord
        public int hmy; // harmony of note

        private NoteInfoTetra(int ntIn, int lnIn, int rtIn, int hmyIn)
        {
            nt = ntIn;
            ln = lnIn;
            rt = rtIn;
            hmy= hmyIn;
        }
    }

    public static void addToLst(String ln){
        String[] parts = ln.split(" ");

        int n = Integer.parseInt(parts[0]);
        int l = Integer.parseInt(parts[1]);
        int r = Integer.parseInt(parts[2]);
        int h = Integer.parseInt(parts[3]);
        NoteInfoTetra nit = new NoteInfoTetra(n,l,r,h);
        lst.add(nit);
        System.out.println("n: " + n + ", l: " + l + ", r: " + r + ", h: " + h + ", key: " + key);
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
