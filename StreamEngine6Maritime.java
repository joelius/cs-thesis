import org.jfugue.*;
public class StreamEngine6Maritime
{
    public static void main(String[] args)
    {

Pattern song = new Pattern();
Pattern pattern0 = new Pattern("V0 A5q A5q A5q B5q " +
 "| E5q B5q C6q B5q " +
 "| A5q B5q B5q B5q " +
 "| C6q C6q C6q B5q " +
 "| B5q B5q B5q A5q " +
 "| A5q A5q ");
song.add(pattern0, 1);

Pattern pattern1 = new Pattern("V1 A4q A4q A4q E4q " +
 "| E4q E4q A4q A4q " +
 "| A4q G4q G4q G4q " +
 "| C5q C5q C5q G4q " +
 "| G4q G4q A4q A4q " +
 "| A4q A4q ");
song.add(pattern1, 1);

Pattern pattern2 = new Pattern("V2 A5q A5q C5q E5q " +
 "| G4q E5q E5q C5q " +
 "| C5q D5q D5q D5q " +
 "| E5q E5q E5q D5q " +
 "| D5q D5q C5q C5q " +
 "| C5q C5q ");
song.add(pattern2, 1);


// Play the song!\n" +
Player player = new Player();
 player.play(song);


}

}