package ghost;

import processing.core.PApplet;

public class Soda extends Items{

    // ghosts will be frightened and will be invisible for a period 
    // but they can still move
    // when waka hit them, they are not eaten by waka and they do not eat waka
    // after a period, they appear again

    public static final String sprite = "src/main/resources/soda.png";
    protected boolean drunk;
    
    public Soda(int x, int y, String sprite){
        super(x, y, sprite);
    }

    @Override
    public void draw(PApplet app){
        if (!drunk){
            app.image(this.image, this.x, this.y);
        }
    }

    public void drunk(){
        this.drunk = true;
    }

    public boolean checkDrunk(){
        return this.drunk;
    }

    public void reset(){
        this.drunk = false;
    }
}