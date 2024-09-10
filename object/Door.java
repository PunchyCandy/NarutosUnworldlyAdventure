package object;

import entity.Entity;
import main.GamePanel;

public class Door extends Entity{

    GamePanel gp;
    
    public Door(GamePanel gp) {
        super(gp);
        name = "Door";
        down1 = setUp("/objects/door", gp.tileSize, gp.tileSize);
        collision = true;

        solidArea.x = 0;
        solidArea.y = 30;
        solidArea.width = 120;
        solidArea.height = 90;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
