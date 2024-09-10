package entity;

import java.util.Random;

import main.GamePanel;
import object.Fireball;
import object.Shuriken;

public class Sasuke extends Entity{

    public Sasuke(GamePanel gp) {
        super(gp);

        name = "Sasuke";

        type = typeMonster;
        maxLife = 20; //20
        life = maxLife;
        attack = 12; //12
        defense = 4; //4
        
        direction = "right";
        speed = 6;
        solidArea.x = 30;
        solidArea.y = 30;
        solidArea.width = 55;
        solidArea.height = 70;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        projectile = new Fireball(gp);
        projectile2 = new Shuriken(gp);
        projectile.attack = 10;
        projectile2.attack = 8;

        getImage();
        setDialogue();
    }

    public void getImage() {

        up1 = setUp("/player/sasukeUp1", gp.tileSize, gp.tileSize);
        up2 = setUp("/player/sasukeUp2", gp.tileSize, gp.tileSize);
        down1 = setUp("/player/sasukeDown1", gp.tileSize, gp.tileSize);
        down2 = setUp("/player/sasukeDown2", gp.tileSize, gp.tileSize);
        left1 = setUp("/player/sasukeLeft1", gp.tileSize, gp.tileSize);
        left2 = setUp("/player/sasukeLeft2", gp.tileSize, gp.tileSize);
        right1 = setUp("/player/sasukeRight1", gp.tileSize, gp.tileSize);
        right2 = setUp("/player/sasukeRight2", gp.tileSize, gp.tileSize);

    }

    public void setDialogue() {
        dialogues[0] = "Is that Sasuke?";
        dialogues[0] = "I will bring you back this time!";
    }

    public void update() {
        super.update();
        if (life <= 0) {
            gp.gameState = gp.killedSasukeState;
        }
    }

    public void setAction() {

        if (gp.eHandler.sasukeEvent == true) {
            direction = "down";
        }

        if (gp.eHandler.sasukeFight == true) {
            onPath = true;
        }

        if (onPath == true) {
            // int goalCol = 65;
            // int goalRow = 90;

            int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.tileSize;

            searchPath(goalCol, goalRow);

            int i = new Random().nextInt(100) + 1;
            if (i > 99 && projectile.alive == false && shotAvailableCounter == 30) {
                projectile.set(worldX, worldY, direction, true, this);
                gp.projectileList.add(projectile);
                shotAvailableCounter = 0;
            }
    
            i = new Random().nextInt(100) + 1;
            if (i > 99 && projectile2.alive == false && shotAvailableCounter == 30) {
                projectile2.set(worldX, worldY, direction, true, this);
                gp.projectileList.add(projectile2);
                shotAvailableCounter = 0;
            }
        }
        else {

        }
    }

    public void speak() {

        super.speak();
    }
}
