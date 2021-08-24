package ghost;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IgnorantTest{

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
    public void testExist(){
        Ignorant ig = new Ignorant(170, 250, null);
        assertNotNull(ig);
    }

    @Test
    public void testChaseCorner(){
        Ignorant ig = new Ignorant(170, 250, null);
        Waka w1 = new Waka(160, 160, null);
        ig.setMap(convert());
        assertEquals(ig.chaseTarget(w1)[0], ig.findCorners()[2][0]);
        assertEquals(ig.chaseTarget(w1)[1], ig.findCorners()[2][1]);
    }

    @Test
    public void testChaseWaka(){
        Ignorant ig = new Ignorant(16, 250, null);
        Waka w1 = new Waka(320, 160, null);
        ig.setMap(convert());
        assertEquals(ig.chaseTarget(w1)[0], w1.getX());
        assertEquals(ig.chaseTarget(w1)[1], w1.getY());
    }

    @Test
    public void testScatter(){
        Ignorant ig = new Ignorant(16, 250, null);
        ig.setMap(convert());
        assertEquals(ig.scatterTarget()[0], ig.findCorners()[2][0]);
        assertEquals(ig.scatterTarget()[1], ig.findCorners()[2][1]);
    }
}

