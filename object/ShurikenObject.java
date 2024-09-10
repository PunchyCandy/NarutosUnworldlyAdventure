package object;

import entity.Entity;
import main.GamePanel;

public class ShurikenObject extends Entity{

    GamePanel gp;
    
    public ShurikenObject(GamePanel gp) {
        super(gp);
        name = "Shuriken";
        down1 = setUp("/objects/shuriken", gp.tileSize, gp.tileSize);
        description = "[" + name + "]\nIt's for the mission.";
    }
}