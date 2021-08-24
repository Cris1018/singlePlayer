package ghost;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LivingsTest{

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
    public void testCollusion(){

        Waka w = new Waka(32, 128, null);
        
        List<Wall> walls = new ArrayList<>();
        List<Fruit> fruits = new ArrayList<>();
        List<SuperFruit> superFruits = new ArrayList<>();
        
        for (int i=0; i<convert().size(); i++){
            for (int j=0; j<convert().get(0).length(); j++){
                char c = convert().get(i).charAt(j);
                
                if (c == '1') {
                    Wall horizontal = new Wall(16*j, 16*i, null);
                    walls.add(horizontal);
                }
                else if (c == '2'){
                    Wall vertical = new Wall(16*j, 16*i, null);
                    walls.add(vertical);
                }
                else if (c == '3'){
                    Wall upleft = new Wall(16*j, 16*i, null);
                    walls.add(upleft);
                }
                else if (c == '4'){
                    Wall upright = new Wall(16*j, 16*i, null);
                    walls.add(upright);
                }
                else if (c == '5'){
                    Wall downleft = new Wall(16*j, 16*i, null);
                    walls.add(downleft);
                }
                else if (c == '6'){
                    Wall downright = new Wall(16*j, 16*i, null);
                    walls.add(downright);
                }
                else if (c == '7'){
                    Fruit Fruit = new Fruit(16*j, 16*i, null);
                    fruits.add(Fruit);
                }  
                else if (c == '8')   {
                    SuperFruit supers = new SuperFruit(16*j, 16*i, null);
                    superFruits.add(supers);
                }        
            }
        }
        
        assertFalse(w.colUpWall(walls));
        assertFalse(w.colDownWall(walls));
        assertFalse(w.colLeftWall(walls));
        assertFalse(w.colRightWall(walls));
    }
}