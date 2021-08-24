package ghost;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ChaserTest{

    static final String map = "map1.txt";

    public static List<String> convert(){
        List<String> res = new ArrayList<>();    
        try{
            Scanner sc = new Scanner(new File(map));
            while (sc.hasNextLine()){
                String line = sc.nextLine();
                res.add(line);
            }
            return res;
        } catch (FileNotFoundException e){
            return null;
        } 
    }

    @Test
    public void testScatter(){
        Chaser c = new Chaser(150, 140, null);
        c.setMap(convert());
        assertEquals(c.scatterTarget()[0], c.findCorners()[0][0]);
        assertEquals(c.scatterTarget()[1], c.findCorners()[0][1]);
    }

    @Test
    public void testChaser(){
        Chaser c = new Chaser(150, 140, null);
        Waka w = new Waka(160, 160, null);
        c.setMap(convert());
        assertEquals(c.chaseTarget(w)[0], w.getX());
        assertEquals(c.chaseTarget(w)[1], w.getY());
    }
}