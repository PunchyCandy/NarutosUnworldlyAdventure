package object;

import entity.Entity;
import main.GamePanel;

public class Heart extends Entity{

    GamePanel gp;
    
    public Heart(GamePanel gp) {
        super(gp);
        name = "Heart";
        image = setUp("/objects/heart_full", gp.tileSize, gp.tileSize);
        image2 = setUp("/objects/heart_half", gp.tileSize, gp.tileSize);
        image3 = setUp("/objects/heart_blank", gp.tileSize, gp.tileSize);
    }
}
