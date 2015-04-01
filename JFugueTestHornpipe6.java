import org.jfugue.*;
public class JFugueTestHornpipe6
{
    public static void main(String[] args)
    {

Pattern song = new Pattern();
Pattern pattern0 = new Pattern("T160 V0 C5h F5h G5h " +
 "| A5q F5h G5q A5q F5q " +
 "| G5q C6h G5q A5q G5i F5i " +
 "| G5q C6h G5q A5q G5i F5i " +
 "| E5h C5h ");
song.add(pattern0, 1);

Pattern pattern1 = new Pattern("V1 F2h A2h C3h " +
 "| F3q A3h C4q F4q A3q " +
 "| C4q E3h C3q F3q A2q " +
 "| C3q E3h C3q F3q A3q " +
 "| C3h ");
song.add(pattern1, 1);

Pattern pattern2 = new Pattern("V2 A4h C5h E5h " +
 "| F5q C5h E5q F5q C5q " +
 "| E5q G4h E5q F5q C5q " +
 "| E5q G4h E5q F5q C5q " +
 "| G5q ");
song.add(pattern2, 1);


// Play the song!\n" +
Player player = new Player();
 player.play(song);


}

}