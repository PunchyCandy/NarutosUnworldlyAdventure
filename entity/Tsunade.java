package entity;

import main.GamePanel;

public class Tsunade extends Entity{

    public Tsunade(GamePanel gp) {
        super(gp);

        name = "Tsunade";
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
        up1 = setUp("/npc/tsunade", gp.tileSize, gp.tileSize);
        up2 = setUp("/npc/tsunade", gp.tileSize, gp.tileSize);
        down1 = setUp("/npc/tsunade", gp.tileSize, gp.tileSize);
        down2 = setUp("/npc/tsunade", gp.tileSize, gp.tileSize);
        left1 = setUp("/npc/tsunade", gp.tileSize, gp.tileSize);
        left2 = setUp("/npc/tsunade", gp.tileSize, gp.tileSize);
        right1 = setUp("/npc/tsunade", gp.tileSize, gp.tileSize);
        right2 = setUp("/npc/tsunade", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {
        dialogues[0] = "NARUTO! You're finally back! I have an important mission \nfor you! Come closer.";
        dialogues[1] = "I want you to set out immediately to find a very important \nsecret weapon everyone is looking for. Hurry! The location \nis east of the village. \nGood luck!";
    }

    public void speak() {
        super.speak();
    }
}
