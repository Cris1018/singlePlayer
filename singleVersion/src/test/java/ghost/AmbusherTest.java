package ghost;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AmbusherTest{

    static final String map = "map.txt";

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
        Ambusher a = new Ambusher(160, 160, null);
        a.setMap(convert());
        assertEquals(a.scatterTarget()[0], a.findCorners()[1][0]);
        assertEquals(a.scatterTarget()[1], a.findCorners()[1][1]);
    }

    @Test
    public void testChase(){
        Ambusher a = new Ambusher(160, 160, null);
        a.setMap(convert());
        a.findBounds();

        Waka w0  = new Waka(16, 16, null);
        assert a.chaseTarget(w0)[0] == a.findCorners()[0][0];
        assert a.chaseTarget(w0)[1] == a.findCorners()[0][1];

        Waka w = new Waka(160, 192, null);
        w.setDirection(Direction.R);
        assertEquals(a.chaseTarget(w)[0], 224);
        assertEquals(a.chaseTarget(w)[1], 192);

        w.setX(48);
        w.setY(192);
        w.setDirection(Direction.L);
        assertEquals(a.chaseTarget(w)[0], 0);
        assertEquals(a.chaseTarget(w)[1], 192);

        Waka w1 = new Waka(64, 72, null);
        w1.setDirection(Direction.U);
        assertEquals(a.chaseTarget(w1)[0], 64);
        assertEquals(a.chaseTarget(w1)[1], 48);

        Waka w2 = new Waka(64, 512, null);
        w2.setDirection(Direction.D);
        assertEquals(a.chaseTarget(w2)[0], 64);
        assertEquals(a.chaseTarget(w2)[1], 528);
    }
}