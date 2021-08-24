package ghost;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WhimTest{

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
        Whim whim = new Whim(160, 160, null);
        whim.setMap(convert());
        assertEquals(whim.scatterTarget()[0], whim.findCorners()[3][0]);
        assertEquals(whim.scatterTarget()[1], whim.findCorners()[3][1]);
    }

    @Test
    public void testInBound(){
        Whim whim = new Whim(160, 160, null);
        whim.setMap(convert());
        whim.findBounds();
        assertTrue(whim.xInBound(160));
        assertTrue(whim.yInBound(192));
        assertFalse(whim.yInBound(16));
    }

    @Test
    public void testNullChase(){
        Whim w = new Whim(192, 192, null);
        w.setMap(convert());
        assertEquals(w.chaseTarget(null)[0], w.findCorners()[3][0]);
        assertEquals(w.chaseTarget(null)[1], w.findCorners()[3][1]);
    }

    @Test
    public void testChaserIsNull(){
        Whim whim = new Whim(80, 80, null);
        whim.setMap(convert());
        whim.findBounds();
        
        Waka waka = new Waka(64, 336, null);
        waka.setDirection(Direction.L);

        assert whim.chaseTarget(waka, null)[0] == whim.getRightBound();
        assert whim.chaseTarget(waka, null)[1] == whim.getLowerBound();
        assert whim.processTarget(waka, null)[0] == whim.getRightBound();
        assert whim.processTarget(waka, null)[1] == whim.getLowerBound();
    }

    @Test
    public void testChaseAndProcess(){
        Whim whim = new Whim(80, 80, null);
        whim.setMap(convert());
        whim.findBounds();
        
        Waka w = new Waka(160, 320, null);
        w.setDirection(Direction.R);
        Chaser chaser = new Chaser(80, 336, null);
        assertEquals(whim.chaseTarget(w, chaser)[0], 304);
        assertEquals(whim.chaseTarget(w, chaser)[1], 304);
        assertEquals(whim.processTarget(w, chaser)[0], 304);
        assertEquals(whim.processTarget(w, chaser)[1], 304);
        whim.findBounds();
        
        Waka w2 = new Waka(64, 336, null);
        w2.setDirection(Direction.L);
        Chaser chaser2= new Chaser(400, 336, null);
        assertEquals(whim.chaseTarget(w2, chaser2)[0], -336);
        assertEquals(whim.chaseTarget(w2, chaser2)[1], 336);
        assert whim.processTarget(w2, chaser2)[0] == 0;
        assert whim.processTarget(w2, chaser2)[1] == 336;

        Waka w3 = new Waka(64, 432, null);
        w3.setDirection(Direction.D);
        Chaser chaser3 = new Chaser(48, 80, null);
        assertEquals(whim.chaseTarget(w3, chaser3)[0], 80);
        assertEquals(whim.chaseTarget(w3, chaser3)[1], 848);
        assertEquals(whim.processTarget(w3, chaser3)[0], 80);
        assertEquals(whim.processTarget(w3, chaser3)[1], whim.getLowerBound());

        Waka w4 = new Waka(400, 432, null);
        w4.setDirection(Direction.R);
        assertEquals(whim.chaseTarget(w4, chaser3)[0], 816);
        assertEquals(whim.chaseTarget(w4, chaser3)[1], 784);
        assertEquals(whim.processTarget(w4, chaser3)[0], whim.getRightBound());
        assertEquals(whim.processTarget(w4, chaser3)[1], whim.getLowerBound());

        
        // left and lower bound 
        Waka w5 = new Waka(32, 512, null);
        w5.setDirection(Direction.L);
        Chaser chaser5 = new Chaser(400, 64, null);
        assertEquals(whim.processTarget(w5, chaser5)[0], whim.getLeftBound());
        assertEquals(whim.processTarget(w5, chaser5)[1], whim.getLowerBound());

        Waka w6 = new Waka(32, 64, null);
        w6.setDirection(Direction.U);
        chaser5.setY(400);
        assert whim.processTarget(w6, chaser5)[0] == whim.getLeftBound();
        assert whim.processTarget(w6, chaser5)[1] == whim.getUpperBound();

        Waka w7 = new Waka(400, 64, null);
        w7.setDirection(Direction.R);
        chaser5.setX(32);
        assert whim.processTarget(w7, chaser5)[0] == whim.getRightBound();
        assert whim.processTarget(w7, chaser5)[1] == whim.getUpperBound();      
    }
}