package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SodaExtensionTest {

    @Test
    public void testDrinking(){
        GameManager manager = new GameManager("config3.json");
        
        manager.setUp();
        manager.putEverything();        
        manager.getWaka().setDirection(Direction.L);
        manager.getWaka().setSpeed(1);
        
        for (Ghost g: manager.getGhosts()){
            g.setMap(manager.mapToListString());
        }
        
        for (int i=0; i<64; i++){
            manager.getWaka().moveLeft(manager.getWall());
            manager.getWaka().drinking(manager.getSoda());

            if (i == 61){
                assertTrue(manager.getWaka().isdrinkingSoda());
                
                manager.ghostFrighteningByDrunkWaka(null);
                assertFalse(manager.getChasers().get(0).isAppearing());
                
                manager.getChasers().get(0).appearAgain();
                assertTrue(manager.getChasers().get(0).isAppearing());
                break;
            }
        } 
    }
}