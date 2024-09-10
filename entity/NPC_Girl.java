package entity;

import java.util.Random;

import main.GamePanel;


public class NPC_Girl extends Entity{
    
    public NPC_Girl(GamePanel gp) {
        super(gp);

        name = "Girl";
        direction = "down";
        speed = 1;
        solidArea.x = 30;
        solidArea.y = 30;
        solidArea.width = 55;
        solidArea.height = 70;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
        setDialogue();
    }

    public void getImage() {
        
        up1 = setUp("/npc/girlLeft1", gp.tileSize, gp.tileSize);
        up2 = setUp("/npc/girlLeft2", gp.tileSize, gp.tileSize);
        down1 = setUp("/npc/girlRight1", gp.tileSize, gp.tileSize);
        down2 = setUp("/npc/girlRight2", gp.tileSize, gp.tileSize);
        left1 = setUp("/npc/girlLeft1", gp.tileSize, gp.tileSize);
        left2 = setUp("/npc/girlLeft2", gp.tileSize, gp.tileSize);
        right1 = setUp("/npc/girlRight1", gp.tileSize, gp.tileSize);
        right2 = setUp("/npc/girlRight2", gp.tileSize, gp.tileSize);

    }

    public void setDialogue() {
        dialogues[0] = "Hello Naruto!";
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
    }

    public void speak() {
        super.speak();
    }
}
