package main;

import entity.Entity;

public class EventHandler {
    
    GamePanel gp;
    EventRect eventRect[][];

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    public boolean sasukeEvent = false;
    public boolean sasukeFight = false;

    public EventHandler(GamePanel g) {
        gp = g;
        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = gp.tileSize;
            eventRect[col][row].y = gp.tileSize;
            eventRect[col][row].width = gp.tileSize;
            eventRect[col][row].height = gp.tileSize;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
            }
        }
    }

    public void checkEvent() {

        //check if the player character is more than 1 tile away from the last event
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);
        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }

        if (canTouchEvent == true) {
            if (hit(22, 10, "up") || hit(24, 10, "up") || hit(23, 10, "up")) {
                storyDialogue(22, 10, gp.dialogueState, gp.npc[0]);
                storyDialogue(24, 10, gp.dialogueState, gp.npc[0]);
                storyDialogue(23, 10, gp.dialogueState, gp.npc[0]);
            }
            if (hit(22, 9, "up") || hit(24, 9, "up") || hit(23, 9, "up")) {
                storyDialogue(22, 9, gp.dialogueState, gp.npc[0]);
                storyDialogue(24, 9, gp.dialogueState, gp.npc[0]);
                storyDialogue(23, 9, gp.dialogueState, gp.npc[0]);
            }
            if (hit(22, 52, "up") || hit(24, 52, "up") || hit(23, 52, "up")) {
                storyDialogue(22, 52, gp.dialogueState, gp.npc[2]);
                storyDialogue(24, 52, gp.dialogueState, gp.npc[2]);
                storyDialogue(23, 52, gp.dialogueState, gp.npc[2]);
            }
            if (hit(22, 69, "up") || hit(24, 69, "up") || hit(23, 69, "any")) {
                storyDialogue(22, 69, gp.dialogueState, gp.npc[1]);
                storyDialogue(24, 69, gp.dialogueState, gp.npc[1]);
                storyDialogue(23, 69, gp.dialogueState, gp.npc[1]);
            }
            if (hit(70, 72, "any")) {
                gp.stopMusic();
                gp.playMusic(12);
                storyDialogue(70, 72, gp.dialogueState, gp.monster[49]);
                sasukeEvent = true;
            }
            if (hit(64, 83, "any") || hit(65, 83, "any")) {
                sasukeFight = true;
                storyDialogue(64, 83, gp.dialogueState, gp.monster[49]);
                storyDialogue(65, 83, gp.dialogueState, gp.monster[49]);
            }
        }
    }

    public boolean hit(int col, int row, String reqDirection) {
        boolean hit = false;

        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[col][row].x = col * gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row * gp.tileSize + eventRect[col][row].y;

        if (gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false) {
            if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;

                previousEventX = gp.player.worldX;
                previousEventY = gp.player.worldY;
            }
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }

    public void storyDialogue(int col, int row, int gameState, Entity entity) {
        gp.gameState = gameState;
        if (entity.name.equals("Sasuke")) {
            gp.monster[49].speak();
        }
        else {
            entity.speak();
        }
        eventRect[col][row].eventDone = true;
    }

    public void teleport(int col, int row, int gameState) {
        gp.gameState = gameState;
        gp.ui.currenDialogue = "Teleport!";
        gp.player.worldX = gp.tileSize * 37;
        gp.player.worldY = gp.tileSize * 10;
    }

    public void damagePit(int col, int row, int gameState) {
        gp.gameState = gameState;
        gp.playSoundEffect(6);
        gp.ui.currenDialogue = "You fall into a pit!";
        gp.player.life --;
        // eventRect[col][row].eventDone = true; //can only damage once
        canTouchEvent = false; //can damage multiple times
    }

    public void healingPool(int col, int row, int gameState) {
        if (gp.keyH.enterPressed == true) {
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            gp.playSoundEffect(2);
            gp.ui.currenDialogue = "You drink the water. \nYour life and chakra has been recovered!";
            gp.player.life = gp.player.maxLife;
            gp.player.chakra = gp.player.maxChakra;
            gp.aSetter.setMonster();
        }
    }
}
