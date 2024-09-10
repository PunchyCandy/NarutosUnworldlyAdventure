package entity;

import main.GamePanel;

public class Sakura extends Entity{
    
    public Sakura(GamePanel gp) {
        super(gp);

        name = "Sakura";
        direction = "down";
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
        up1 = setUp("/npc/sakura", gp.tileSize, gp.tileSize);
        up2 = setUp("/npc/sakura", gp.tileSize, gp.tileSize);
        down1 = setUp("/npc/sakura", gp.tileSize, gp.tileSize);
        down2 = setUp("/npc/sakura", gp.tileSize, gp.tileSize);
        left1 = setUp("/npc/sakura", gp.tileSize, gp.tileSize);
        left2 = setUp("/npc/sakura", gp.tileSize, gp.tileSize);
        right1 = setUp("/npc/sakura", gp.tileSize, gp.tileSize);
        right2 = setUp("/npc/sakura", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {
        dialogues[0] = "Hi Naruto! How was your mission? \nWe'll bring Sasuke back together!";
    }

    public void speak() {
        super.speak();
    }
}
