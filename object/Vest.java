package object;

import entity.Entity;
import main.GamePanel;

public class Vest extends Entity{

    public Vest(GamePanel gp) {
        super(gp);

        type = typeShield;
        name = "Vest";
        down1 = setUp("/objects/vest", gp.tileSize, gp.tileSize);
        defenseValue = 1;
        description = "[" + name + "]\nA normal Leaf Village vest.";
    }
    
}
