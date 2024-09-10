package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.Rock;

public class GreenSlime extends Entity{

    GamePanel gp;
    
    public GreenSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;
        
        type = typeMonster;
        name = "Green Slime";
        speed = 2;
        maxLife = 4;
        life = maxLife;
        attack = 4;
        defense = 0;
        exp = 2;
        projectile = new Rock(gp);

        solidArea.x = 11;
        solidArea.y = 66;
        solidArea.width = 109;
        solidArea.height = 75;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {

        up1 = setUp("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        up2 = setUp("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        down1 = setUp("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        down2 = setUp("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        left1 = setUp("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        left2 = setUp("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
        right1 = setUp("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
        right2 = setUp("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
    }

    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter == 120) { //time npc changes direction
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
        }

        int i = new Random().nextInt(100) + 1;
        if (i > 99 && projectile.alive == false && shotAvailableCounter == 30) {
            projectile.set(worldX, worldY, direction, true, this);
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;
        }
    }

    public void damageReaction() {
        actionLockCounter = 0;
        direction = gp.player.direction; //moves away from player
    }
}
