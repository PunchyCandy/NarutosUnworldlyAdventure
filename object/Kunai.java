package object;

import entity.Entity;
import main.GamePanel;

public class Kunai extends Entity{

    public Kunai(GamePanel gp) {
        super(gp);

        type = typeSword;
        name = "Kunai";
        down1 = setUp("/objects/kunaiWeapon", gp.tileSize, gp.tileSize);
        attackValue = 1;
        description = "[" + name + "]\nA regular kunai.";
        attackArea.width = 90;
        attackArea.height = 90;
    }
    
    
}
