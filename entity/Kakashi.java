package entity;

import main.GamePanel;

public class Kakashi extends Entity{

    public Kakashi(GamePanel gp) {
        super(gp);

        name = "Kakashi";
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
        up1 = setUp("/npc/kakashi", gp.tileSize, gp.tileSize);
        up2 = setUp("/npc/kakashi", gp.tileSize, gp.tileSize);
        down1 = setUp("/npc/kakashi", gp.tileSize, gp.tileSize);
        down2 = setUp("/npc/kakashi", gp.tileSize, gp.tileSize);
        left1 = setUp("/npc/kakashi", gp.tileSize, gp.tileSize);
        left2 = setUp("/npc/kakashi", gp.tileSize, gp.tileSize);
        right1 = setUp("/npc/kakashi", gp.tileSize, gp.tileSize);
        right2 = setUp("/npc/kakashi", gp.tileSize, gp.tileSize);
    }

    public void setDialogue() {
        dialogues[0] = "Hurry! Lady Tsunade seeks you! I think it's an important \nsolo mission!";
    }

    public void setAction() {
        if (onPath == true) {

            int goalCol = 25;
            int goalRow = 45;

            searchPath(goalCol, goalRow);
        }
    }

    public void speak() {

        onPath = true;

        super.speak();
    }
}
