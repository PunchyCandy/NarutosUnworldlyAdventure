package object;

import entity.Entity;
import main.GamePanel;

public class Key extends Entity{

    GamePanel gp;
    
    public Key(GamePanel gp) {
        super(gp);
        name = "Key";
        down1 = setUp("/objects/key", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nIt opens a door.";
    }
}