package ghost;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GhostTest{

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
        Ambusher ambusher = new Ambusher(80, 80, null);
        assertNotNull(ambusher);
    }
    
    @Test
    public void hitByWaka(){
        Whim whim = new Whim(192, 160, null);
        Waka waka = new Waka(190, 161, null);
        waka.startBrave();
        assertTrue(whim.hitByWaka(waka));
        waka.disableBrave();
        assertFalse(whim.hitByWaka(waka));
        assertTrue(whim.eatWaka(waka));
    }

    @Test
    public void testFindingBound(){
        Ghost chaser = new Chaser(160, 192, null);
        List<String> res = convert();
        chaser.setMap(res);
        chaser.findBounds();
        assertEquals(chaser.getLeftBound(), 0);
        assertEquals(chaser.getRightBound(), 432);
    }

    @Test
    public void testMoveUp(){
        Ignorant igo = new Ignorant(176, 160, null);
        igo.setSpeed(2);
        igo.availableToMove();
        int i=0;
        while (i<8){
            igo.moveUp();
            i++;
        }
        assertEquals(igo.getY(), 144);
        assertFalse(igo.getCanUp());
    }

    @Test
    public void testMoveDown(){
        Ignorant igo = new Ignorant(176, 160, null);
        igo.setSpeed(1);
        igo.availableToMove();
        int i=0;
        while (i<16){
            igo.moveDown();
            i++;
        }
        assertEquals(igo.getY(), 176);
        assertFalse(igo.getCanDown());
    }

    @Test
    public void testMoveLeft(){
        Ignorant igo = new Ignorant(176, 160, null);
        igo.setSpeed(1);
        igo.availableToMove();
        int i=0;
        while (i<16){
            igo.moveLeft();
            i++;
        }
        assertEquals(igo.getY(), 160);
        assertFalse(igo.getCanLeft());
    }

    @Test
    public void testMoveRight(){
        Ignorant igo = new Ignorant(176, 160, null);
        igo.setSpeed(2);
        igo.availableToMove();
        int i=0;
        while (i<8){
            igo.moveRight();
            i++;
        }
        assertEquals(igo.getX(), 192);
        assertFalse(igo.getCanRight());
    }

    @Test
    public void testMoveRandomly(){
        Ignorant igo = new Ignorant(176, 160, null);
        igo.setDirection(Direction.D);
        
        igo.setSpeed(1);
        igo.setMap(convert());
        igo.availableToMove();

        for (int i=0; i<16; i++){
            igo.moveRandomly();
        }
        assert igo.getY() == 144;
    }

    @Test
    public void testHitByWaka(){
        Ignorant igo = new Ignorant(176, 160, null);
        Waka w = new Waka(173, 162, null);
        w.setDrinkSoda();
        assertTrue(igo.hitByDrunkWaka(w));

        Waka w1 = new Waka(170, 162, null);
        w1.setDrinkSoda();
        assertFalse(igo.hitByDrunkWaka(w1));
    }
}