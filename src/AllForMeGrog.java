import org.jfugue.Pattern;
import org.jfugue.Player;
import org.jfugue.Rhythm;
import org.jfugue.Tempo;

public class AllForMeGrog
{
    public static void main(String[] args)
    {
        int tempoInBPM = 100;


//        Rhythm rhythm = new Rhythm();
//        rhythm.setLayer(1, "O...O...O...O...");
//        rhythm.addSubstitution('O', "[BASS_DRUM]i");
//        rhythm.addSubstitution('.', "Ri");

//        Pattern pattern0 = new Pattern("V0 G4q G4q G4q G4q V1 Rw");
        Pattern pattern1 = new Pattern("V0 Rq Rq Rq D6q V1 G4w");

//"Dormez-vous?"
        Pattern pattern2 = new Pattern("V0 E6q D6i E6i B5q A5i G5i " +
                                       "V1 C3h E3h");

// "Sonnez les matines"
        Pattern pattern3 = new Pattern("V0 G5i D6i B5i G5i E5q D6q " +
                                       "V1 G3h C3h");

// "Ding ding dong"
        Pattern pattern4 = new Pattern("V0 E6q D6i E6i B5q A5i G5i " +
                                       "V1 E3w ");

        Pattern pattern5 = new Pattern("V0 G5i D6i Rh " +
                                       "V1 G3w");


// Put all of the patters together to form the song
        Pattern song = new Pattern();
    //    song.add(rhythm.getPattern(), 12);
//        song.add(pattern0, 1); // Adds 'pattern1' to 'song' twice
        song.add(pattern1, 1); // Adds 'pattern1' to 'song' twice
        song.add(pattern2, 1); // Adds 'pattern2' to 'song' twice
        song.add(pattern3, 1); // Adds 'pattern3' to 'song' twice
        song.add(pattern4, 1); // Adds 'pattern4' to 'song' twice
        song.add(pattern5, 1); // Adds 'pattern4' to 'song' twice

// Play the song!
     //   Tempo tempo = new Tempo(50);
     //   song.addElement(tempo);
        Player player = new Player();
        player.play(song);
   //     player.play("V0 E5s D#5s | E5s D#5s E5s B4s D5s C5s " +
   //                    "V1 E3q      | G#4q Riii                     ");
    }
}

