import org.jfugue.Pattern;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jol on 15-03-09.
 */
public class JFugueBuilder {

    private static String jfugueFile;
    String file, header, body = "";

    final String footer = "\n" +
            "// Play the song!\\n\" +\n" +
            "Player player = new Player();\n player.play(song);\n" +
            "\n" + "\n" +
            "}\n" + "\n" +
            "}";


    public JFugueBuilder (){
    }

    // TODO: test this method & finish it, too
    public void writeFile(ArrayList<String> patternArrayListIn, String filename) throws IOException {

        generateFilename(filename);
        generateHeader(filename);
        generateBody(patternArrayListIn);

        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(header);
        bw.write(body);
        bw.write(footer);

        /** bw.write("import org.jfugue.*;\n" +
                "public class SongTest\n" +
                "{\n" +
                "    public static void main(String[] args)\n" +
                "    {\n" +
                "\n" +
                "//\"Frere Jacques\"\n" +
                "        Pattern pattern1 = new Pattern(\"C5q D5q E5q C5q\");\n" +
                "\n" +
                "//\"Dormez-vous?\"\n" +
                "        Pattern pattern2 = new Pattern(\"E5q F5q G5h\");\n" +
                "\n" +
                "// \"Sonnez les matines\"\n" +
                "        Pattern pattern3 = new Pattern(\"G5i A5i G5i F5i E5q C5q\");\n" +
                "\n" +
                "// \"Ding ding dong\"\n" +
                "        Pattern pattern4 = new Pattern(\"C5q G4q C5h\");\n" +
                "\n" +
                "// Put all of the patters together to form the song\n" +
                "        Pattern song = new Pattern();\n" +
                "        song.add(pattern1, 2); // Adds 'pattern1' to 'song' twice\n" +
                "        song.add(pattern2, 2); // Adds 'pattern2' to 'song' twice\n" +
                "        song.add(pattern3, 2); // Adds 'pattern3' to 'song' twice\n" +
                "        song.add(pattern4, 2); // Adds 'pattern4' to 'song' twice\n" +
                "\n" +
                "// Play the song!\n" +
                "        Player player = new Player(); player.play(song);\n" +
                "\n" +
                "    }\n" +
                "}\n"); */
        bw.close();

    }

    private void generateFilename(String name){
        file = name + ".java";
    }

    private void generateHeader(String name){
        header = "import org.jfugue.*;\n" +
                "public class " + name + "\n" +
                "{\n" +
                "    public static void main(String[] args)\n" +
                "    {\n" +
                "\n" + "Pattern song = new Pattern();\n";

    }

    private void generateBody(ArrayList<String> patternStringArrayIn){
        String temp = "";
        int i = 0;
        for (String pattern : patternStringArrayIn){
            temp += "Pattern pattern" + i + " = new Pattern(\"" + pattern + "\");\n" +
                    "song.add(pattern" + i + ", 1);\n\n";

            i++;
        }
        body = temp;
    }
}
