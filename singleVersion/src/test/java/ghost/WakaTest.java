package ghost;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WakaTest{

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

    public static List<Soda> setSodas(){
        List<Soda> sodas = new ArrayList<>();
        for (int i=0; i<convert().size(); i++){
            for (int j=0; j<convert().get(0).length(); j++){
                char c = convert().get(i).charAt(j);
                if (c == '9') {
                    Soda soda = new Soda(16*j, 16*i, null);
                    sodas.add(soda);
                }
            }
        }
        return sodas;
    }

    public static List<Wall> setWalls(){
        List<Wall> walls = new ArrayList<>();
        for (int i=0; i<convert().size(); i++){
            for (int j=0; j<convert().get(0).length(); j++){
                char c = convert().get(i).charAt(j);
                
                if (c == '1') {
                    Wall horizontal = new Wall(16*j, 16*i, "src/main/resources/horizontal.png");
                    walls.add(horizontal);
                }
                else if (c == '2'){
                    Wall vertical = new Wall(16*j, 16*i, "src/main/resources/vertical.png");
                    walls.add(vertical);
                }
                else if (c == '3'){
                    Wall upleft = new Wall(16*j, 16*i, "src/main/resources/upLeft.png");
                    walls.add(upleft);
                }
                else if (c == '4'){
                    Wall upright = new Wall(16*j, 16*i, "src/main/resources/upRight.png");
                    walls.add(upright);
                }
                else if (c == '5'){
                    Wall downleft = new Wall(16*j, 16*i, "src/main/resources/downLeft.png");
                    walls.add(downleft);
                }
                else if (c == '6'){
                    Wall downright = new Wall(16*j, 16*i, "src/main/resources/downRight.png");
                    walls.add(downright);
                }
            }
        }
        return walls;
    }

    public static List<Fruit> setFruits(){
        List<Fruit> fruits = new ArrayList<>();
        for (int i=0; i<convert().size(); i++){
            for (int j=0; j<convert().get(0).length(); j++){
                char c = convert().get(i).charAt(j);
                if (c == '7'){
                    Fruit Fruit = new Fruit(16*j, 16*i, "src/main/resources/fruit.png");
                    fruits.add(Fruit);
                }  
            }
        }
        return fruits;
    }

    public static List<SuperFruit> setSupers(){
        List<SuperFruit> superFruits = new ArrayList<>();
        for (int i=0; i<convert().size(); i++){
            for (int j=0; j<convert().get(0).length(); j++){
                char c = convert().get(i).charAt(j);
                if (c == '8') {
                    SuperFruit supers = new SuperFruit(16*j, 16*i, "src/main/resources/SuperF.jpg");
                    superFruits.add(supers);
                } 
            }
        }
        return superFruits;
    }
    
    @Test 
    public void testExist(){
        Waka waka = new Waka(160, 160, null);
        assertNotNull(waka);
    }

    @Test
    public void testCanPass(){
        Waka waka = new Waka(160, 160, null);
        String cannotGo = "123456";
        for (int i=0; i<cannotGo.length(); i++){
            assertFalse(waka.canPass(cannotGo.charAt(i)));
        }

        String canGo = "789";
        for (int i=0; i<canGo.length(); i++){
            assertTrue(waka.canPass(canGo.charAt(i)));
        }
    }

    @Test
    public void testNextMove(){
        Waka waka = new Waka(160, 160, null);
        waka.setNextMove(Direction.U);
        assertEquals(waka.getNextMove(), Direction.U);
    }

    @Test
    public void testMoveAndCollusionAndEating(){

        List<Wall> walls = setWalls();

        Waka w = new Waka(32, 128, null);
        w.setSpeed(1);
        for (int i=0; i<16; i++){
            w.moveRight(walls);
        }
        assert w.getX() == 48;
        assert w.getY() == 128;
        assertTrue(w.colDownWall(walls));
        assertTrue(w.colUpWall(walls));

        List<Fruit> fruits = setFruits();
        w.eating(fruits);
        for (Fruit f: fruits){
            if (f.getX() == 48 && f.getY() == 128){
                assertTrue(f.isEaten);
            }
        }

        Waka w1 = new Waka(16, 144, null);
        w1.setSpeed(1);
        for (int i=0; i<32; i++){
            w1.moveUp(walls);
        }
        assert w1.getX() == 16;
        assert w1.getY() == 112;

        Waka w2 = new Waka(48, 448, null);
        w2.setSpeed(1);
        for (int i=0; i<16; i++){
            w2.moveDown(walls);
        }
        assert w2.getX()==48;
        assert w2.getY()==464;
    }

    @Test
    public void testChangeMouthLeft(){
        Waka w = new Waka(160, 160, null); 
        w.setDirection(Direction.L); 
        w.changeMouth(null);
        assertEquals(w.getSprite(), Waka.leftSprite);
    }

    @Test
    public void testChangeMouthRight(){ 
        Waka w = new Waka(160, 160, null);        
        w.setDirection(Direction.R);
        w.changeMouth(null);
        assertEquals(w.getSprite(), Waka.rightSprite); 
    }

    @Test
    public void testChangeMouthUp(){
        Waka w = new Waka(160, 160, null);  
        w.setDirection(Direction.U);
        w.changeMouth(null);
        assertEquals(w.getSprite(), Waka.upSprite);
    }

    @Test
    public void testChangeMouthDown(){
        Waka w = new Waka(160, 160, null); 
        w.setDirection(Direction.D);
        w.changeMouth(null);
        assertEquals(w.getSprite(), Waka.downSprite); 
    }

    @Test
    public void testSearch(){
        List<Wall> walls = setWalls();
        
        Waka w = new Waka(32, 64, null);
        for (int i=0; i<16; i++){
            w.moveLeft(walls);
            if (i == 12){
                w.setNextMove(Direction.D);
                int[] res = w.search(convert());
                assert res[0] == 1;
                assert res[1] == 4;
                break;
            }
        }

        Waka w1 = new Waka(16, 96, null);
        for (int i=0; i<16; i++){
            w1.moveUp(walls);
            if (i == 6){
                w1.setNextMove(Direction.R);
                int[] res = w1.search(convert());
                assert res[0] == 1;
                assert res[1] == 4;
                break;
            }
        }
    }

    @Test
    public void testSuperEat(){
        Waka w= new Waka(96, 64, null);
        w.setSpeed(1);
        List<Wall> walls = setWalls();
        List<SuperFruit> superfruits = setSupers();

        for (int i=0; i<16; i++){
            w.moveRight(walls);
            w.superEating(superfruits);
        }

        for (SuperFruit sups: superfruits){
            if (sups.getX() == 112 && sups.getY() == 64){
                assertTrue(sups.checkEaten());
            }
        }
    }

    @Test
    public void testDrink(){
        Waka w = new Waka(80, 64, null);
        w.setSpeed(1);
        List<Wall> walls = setWalls();
        List<Soda> sodas = setSodas();

        for (int i=0; i<16; i++){
            w.moveRight(walls);
            w.drinking(sodas);  
        }

        for (Soda s: sodas){
            if (s.getX() == 96 && s.getY() == 64){
                assertTrue(s.checkDrunk());
                s.reset();
                assertFalse(s.checkDrunk());
                break;
            }
        }
    }

    @Test
    public void testRunning(){
        Waka w = new Waka(208, 320, null);
        List<Wall> walls = setWalls();
        w.setDirection(Direction.R);
        w.setSpeed(1);
        for (int i=0; i<16; i++){
            if (i == 0){
                w.setNextMove(Direction.U);
            }
            w.running(convert(), walls);
        }
        assert w.getX() == 224;
        assert w.getY() == 320;

        for (int i=0; i<80; i++){
            w.running(convert(), walls);
        }
        assert w.getX() == 288;
        assert w.getY() == 304;

        w.setNextMove(Direction.D);
        for (int i=0; i<32; i++){
            w.running(convert(), walls);
        }
        assert w.getX() == 288;
        assert w.getY() == 336;

        for (int i=0; i<48; i++){
            if (i == 2){
                w.setNextMove(Direction.L);
            }
            w.running(convert(), walls);
        }
        assert w.getY() == 368;
    }

    @Test
    public void testReset(){
        Waka w = new Waka(160, 160, null);
        w.setX(300);
        w.setY(200);
        w.setDrinkSoda();
        w.setDirection(Direction.U);
        w.setNextMove(Direction.D);
        w.startBrave();
        w.resetAll();

        assertNull(w.getDirection());
        assertNull(w.getNextMove());
        assert w.getX() == 160 && w.getY() == 160;
        assertFalse(w.isdrinkingSoda());
        assertFalse(w.isBrave());
    }

    @Test
    public void testCheckWin(){

        List<SuperFruit> superfruits = setSupers();
        List<Fruit> fruits = setFruits();
        Waka waka = new Waka(160, 160, null);
        assertFalse(waka.checkWin(fruits, superfruits));
    }

    @Test
    public void testEatAndWin(){
        
        List<SuperFruit> superfruits = setSupers();
        List<Fruit> fruits = setFruits();

        Waka w = new Waka(160, 160, null);
        assertFalse(w.checkWin(fruits, superfruits));

        for (Fruit f: fruits){
            f.eaten();
        }
        for (SuperFruit superfruit: superfruits){
            superfruit.eaten();
        }
        assertTrue(w.checkWin(fruits, superfruits));
    }

    @Test
    public void testOpenAndClose(){
        int time;
        Waka w = new Waka(160, 160, null);
        w.setDirection(Direction.L);
        for (time = 0; time<16; time++){
            w.changeMouth(null);

            if (time == 12){
                assertEquals(w.getSprite(), Waka.close);
            }
        }
    }
}