package ghost;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameManagerTest{

    @Test
    public void testNoWinner(){
        assertNull(new GameManager("config2.json").getWinner());
    }

    @Test
    public void testHelpingFunctions(){
        GameManager manager = new GameManager("config.json");
        
        manager.setUp();
        assert manager.getWall().size()>0;
        assert manager.getFruits().size()>0;
        assert manager.getSuperFruits().size()>0;
        assert manager.getChasers().size()>0;

        manager.putEverything();
        assert manager.getItems().size()>0;
        assert manager.getGhosts().size()>0;
        assert manager.getChance() == 6;
    }

    @Test
    public void testCheckWinnerAndRestart(){
        GameManager manager = new GameManager("config.json");
        manager.setUp();
        manager.putEverything();
        App app = new App();
        app.manager = manager;

        for (int i=0; i<600; i++){
            manager.delayForTenAndRestore();
            if (i == 599){
                assertNull(manager.getWinner());
                for (Soda soda: manager.getSoda()){
                    assertFalse(soda.checkDrunk());
                }
                assert manager.getGhosts().size()>0;
            }
        }
    }

    @Test
    public void testCursor(){
        GameManager manager = new GameManager("config.json");
        manager.setCursor();
        assert manager.getCursor() == 1;
    }

    @Test
    public void testWakaEatSuperfruits(){
        GameManager manager = new GameManager("config.json");
        manager.setUp();
        manager.putEverything();
        
        for (Ghost g: manager.getGhosts()){
            g.setMap(manager.mapToListString());
        }
        manager.getWaka().setSpeed(1);
        
        // test waka is loaded;
        assert manager.getWaka().getX() == 208 && manager.getWaka().getY() == 320;
        assert manager.getChasers().get(0).chaseTarget(manager.getWaka())[0] == manager.getWaka().getX();

        for (int i=0; i<32; i++){
            manager.getWaka().moveLeft(manager.getWall());
            manager.getWaka().superEating(manager.getSuperFruits());
            
            if (i == 32){
                assertTrue(manager.getWaka().isBrave());  
                assertEquals(manager.getGhosts().get(0).getSprite(), Ghost.getFrightenedSprite());
                break;
            }
        }
    }

    @Test
    public void testWakaWinsBySuperfruit(){
        GameManager manager = new GameManager("config4.json");
        
        manager.setUp();
        manager.putEverything();
        
        assert manager.getAmbushers().size() == 1;
        
        for (Ghost g: manager.getGhosts()){
            g.setMap(manager.mapToListString());
        }
        manager.getWaka().setSpeed(1);

        for (int i=0; i<60; i++){

            manager.getWaka().moveLeft(manager.getWall());
            manager.getWaka().superEating(manager.getSuperFruits());
            
            if (i == 46){
                assertTrue(manager.getWaka().isBrave());

                manager.ghostFrightening(null);
                
                assert manager.getHitbyWaka().size()>0;
                
                assertNotNull(manager.getWinner());
                assertEquals(manager.getWinner(), Winner.WAKA);
            }
        }
    }
}