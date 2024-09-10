package object;

import entity.Entity;
import main.GamePanel;

public class Boot extends Entity{

    GamePanel gp;
    
    public Boot(GamePanel gp) {
        super(gp);
        name = "Boot";
        down1 = setUp("/objects/boots", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nYou move faster.";
    }
}
