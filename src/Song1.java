import org.jfugue.*;
public class Song1
{
    public static void main(String[] args)
    {

//"Frere Jacques"
        Pattern pattern1 = new Pattern("C5q D5q E5q C5h | E5q F5q G5h");

//"Dormez-vous?"
        Pattern pattern2 = new Pattern("E5q F5q G5h");

// "Sonnez les matines"
        Pattern pattern3 = new Pattern("G5i A5i G5i F5i E5q C5q");

// "Ding ding dong"
        Pattern pattern4 = new Pattern("C5q G4q C5h");

// Put all of the patters together to form the song
        Pattern song = new Pattern();
        song.add(pattern1, 2); // Adds 'pattern1' to 'song' twice
        song.add(pattern2, 2); // Adds 'pattern2' to 'song' twice
        song.add(pattern3, 2); // Adds 'pattern3' to 'song' twice
        song.add(pattern4, 2); // Adds 'pattern4' to 'song' twice

// Play the song!
        Player player = new Player(); player.play(song);

    }
}

