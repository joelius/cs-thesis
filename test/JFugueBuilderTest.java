import org.junit.Assert;
import org.junit.Test;

public class JFugueBuilderTest {

    @Test
    public void testWriter(){

        JFugueBuilder test = new JFugueBuilder("what");

        try {
            test.writeFile("junk");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }


    }

}