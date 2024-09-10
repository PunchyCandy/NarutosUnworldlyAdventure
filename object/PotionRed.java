package object;

import entity.Entity;
import main.GamePanel;

public class PotionRed extends Entity{

    GamePanel gp;
    int value = 6;

    public PotionRed(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        type = typeConsumable;
        name = "Red Potion";
        down1 = setUp("/objects/potion_red", gp.tileSize, gp.tileSize);
        description = "[Red Potion] \nHeals your life and \nchakra by " + value;
    }

    public void use(Entity entity) {
        gp.gameState = gp.dialogueState;
        gp.ui.currenDialogue = "You drink the " + name + "!\nYour life has been recovered by " + value;
        entity.life += value;
        entity.chakra += value;
        if (gp.player.life > gp.player.maxLife) {
            gp.player.life = gp.player.maxLife;
        }
        if (gp.player.chakra > gp.player.maxChakra) {
            gp.player.chakra = gp.player.maxChakra;
        }
        gp.playSoundEffect(2);
    }
    
}
