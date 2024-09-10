package entity;

import main.GamePanel;
import main.KeyHandler;
import object.Kunai;
import object.Rasenshuriken;
import object.Vest;
import object.Shuriken;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Player extends Entity{

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    int standCounter = 0;
    public boolean attackCanceled = false;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;

    public Player(GamePanel g, KeyHandler k) {

        super(g);

        keyH = k;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        // solidArea used to check for collision
        solidArea = new Rectangle();
        solidArea.x = 30;
        solidArea.y = 30;
        solidArea.width = 55;
        solidArea.height = 74;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        //attack range
        // attackArea.width = 0;
        // attackArea.height = 0;

        setDefaultValue();
        setDefaultPositions();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    public void setDefaultValue() {
        //set default spawn
        setDefaultPositions();

        //player status
        level = 1;
        maxLife = 6;
        life = maxLife;
        maxChakra = 8;
        chakra = maxChakra;
        strength = 3;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        speed = 6;
        direction = "down";

        invincible = false;
        currentWeapon = new Kunai(gp);
        currentShield = new Vest(gp);
        attack = getAttack(); //Total value is decided by strength and weapon
        defense = getDefense(); //Total defense is decided by dexirity and weapon

        projectile = new Rasenshuriken(gp);
        projectile2 = new Shuriken(gp);
    }

    public void setDefaultPositions() {
        invincible = false;
        worldX = gp.tileSize * 25;
        worldY = gp.tileSize * 72;
        // worldX = gp.tileSize * 80;
        // worldY = gp.tileSize * 73;
        direction = "down";
    }

    public void restoreLifeChakra() {
        life = maxLife;
        chakra = maxChakra;
        invincible = false;
    }

    public void setItems() {
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
    }

    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }

    public int getDefense() {
        return defense = dexterity * currentShield.defenseValue;
    }

    public void getPlayerImage() {

        up1 = setUp("/player/back_1", gp.tileSize, gp.tileSize);
        up2 = setUp("/player/back_2", gp.tileSize, gp.tileSize);
        down1 = setUp("/player/front_1", gp.tileSize, gp.tileSize);
        down2 = setUp("/player/front_2", gp.tileSize, gp.tileSize);
        left1 = setUp("/player/left_1", gp.tileSize, gp.tileSize);
        left2 = setUp("/player/left_2", gp.tileSize, gp.tileSize);
        right1 = setUp("/player/right_1", gp.tileSize, gp.tileSize);
        right2 = setUp("/player/right_2", gp.tileSize, gp.tileSize);

    }

    public void getPlayerAttackImage() {

        if (currentWeapon.type == typeSword) {
            attackUp1 = setUp("/player/attackBack1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setUp("/player/attackBack2", gp.tileSize, gp.tileSize * 2);
            attackDown1 = setUp("/player/attackFront1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setUp("/player/attackFront2", gp.tileSize, gp.tileSize * 2);
            attackLeft1 = setUp("/player/attackLeft1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setUp("/player/attackLeft2", gp.tileSize * 2, gp.tileSize);
            attackRight1 = setUp("/player/attackRight1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setUp("/player/attackRight2", gp.tileSize * 2, gp.tileSize);
        }
    }

    public void update() {

        if (attacking == true) {
            attacking();
        }
        else if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true || keyH.enterPressed == true) {
            if (keyH.upPressed == true) {
                direction = "up";
            }
            else if (keyH.downPressed == true) {
                direction = "down";
            }
            else if (keyH.leftPressed == true) {
                direction = "left";
            }
            else if (keyH.rightPressed == true) {
                direction = "right";
            }

            //check tile collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //check object collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //check npc collision
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            //check monster collision
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);


            //check event
            gp.eHandler.checkEvent();

            //if collision is false, player can move
            if (collisionOn == false && keyH.enterPressed == false) {
                switch(direction) {
                    case "up":
                    worldY -= speed;
                    break;
                    case "down":
                    worldY += speed;
                    break;
                    case "left":
                    worldX -= speed;
                    break;
                    case "right":
                    worldX += speed;
                    break;
                }
            }

            if (keyH.enterPressed == true && attackCanceled == false) {
                gp.playSoundEffect(7);
                attacking = true;
                spriteCounter = 0;
            }

            attackCanceled = false;
            gp.keyH.enterPressed = false;

    
            spriteCounter++;
            if(spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                }
                else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        if (gp.keyH.shotKeyPressed == true && projectile.alive == false && shotAvailableCounter == 30 && projectile.haveResource(this) == true) {

            //set default coordinates, direction, and user
            projectile.set(worldX, worldY, direction, true, this);

            //subtract chakra
            projectile.subtractResouce(this);

            //add to the list
            gp.projectileList.add(projectile);

            shotAvailableCounter = 0;

            gp.playSoundEffect(11);
        }

        if (gp.keyH.shotKeyPressed1 == true && projectile2.alive == false && shotAvailableCounter == 30 && projectile2.haveResource(this) == true) {

            //set default coordinates, direction, and user
            projectile2.set(worldX, worldY, direction, true, this);

            //subtract chakra
            projectile2.subtractResouce(this);

            //add to the list
            gp.projectileList.add(projectile2);

            shotAvailableCounter = 0;

            gp.playSoundEffect(10);
        }

        //outside of key if statement
        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }

        if (life <= 0) {
            gp.gameState = gp.gameOverState;
        }
    }

    public void attacking() {
        spriteCounter++;

        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            //save the current worldX, worldY, solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solideAreaHeight = solidArea.height;

            //adjust player's worldX/Y for the attackArea
            switch(direction) {
                case "up":
                worldY -= attackArea.height;
                break;
                case "down":
                worldY += attackArea.height;
                break;
                case "left":
                worldX -= attackArea.height;
                break;
                case "right":
                worldX += attackArea.height;
                break;
            }
            //attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            //check monster collision with the updated worldX/Y and solidArea
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            damageMonster(monsterIndex, attack);

            //after checking collison, restore the original date
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solideAreaHeight;
        }
        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {
            String text = "";
            if (inventory.size() != maxInventorySize) {
                if (gp.obj[i].name != "Door") {
                    inventory.add(gp.obj[i]);
                    text = "Got a " + gp.obj[i].name + "!";
                    gp.playSoundEffect(1);
                }
                if (gp.obj[i].name.equals("Boot")) {
                    inventory.add(gp.obj[i]);
                    text = "A pair of " + gp.obj[i].name + "s! You are now faster!";
                    gp.playSoundEffect(2);
                    speed += 4;
                }
                if (gp.obj[i].name.equals("Door")) {
                    boolean hasKey = false;
                    for (int k = 0; k < inventory.size(); k++) {
                        if (inventory.get(k).name.equals("Key")) {
                            hasKey = true;
                            text = "You opened the door!";
                            inventory.remove(k);
                            gp.obj[i] = null;
                        }
                    }
                    if (hasKey != true) {
                        text = "You need to find a key!";
                    }

                }
            }
            else {
                text = "Your inventory is full!";
            }
            gp.ui.addMessage(text);
            if (text.substring(0, 3).equals("Got") || text.substring(0, 3).equals("A p")) {
                gp.obj[i] = null;
            }
        }
    }

    // public void pickUpObject(int i) {
    //     if (i != 999) {
    //         String text;
    //         if (inventory.size() != maxInventorySize) {
    //             inventory.add(gp.obj[i]);
    //             gp.playSoundEffect(1);
    //             text = "Got a " + gp.obj[i].name + "!";
    //             // gp.obj[i] = null;
    //         }
    //         else {
    //             text = "Your inventory is full!";
    //         }
    //         gp.ui.addMessage(text);
    //         gp.obj[i] = null;
    //     }
    // }

    public void interactNPC(int i) {
        if (gp.keyH.enterPressed == true) {
            if (i != 999) {
                attackCanceled = true;
                gp.gameState = gp.dialogueState;
                gp.npc[i].setDialogue();
                gp.npc[i].speak();
            }
        }
    }

    public void contactMonster(int i) {
        if (i != 999) {
            if (invincible == false && gp.monster[i].dying == false) {
                gp.playSoundEffect(6);

                int damage = gp.monster[i].attack - defense;
                if (damage < 0) {
                    damage = 0;
                }

                life -= damage;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i, int attack) {
        if (i != 999) {
            if (gp.monster[i].invincible == false) {
                gp.playSoundEffect(5);

                int damage = attack - gp.monster[i].defense;
                if (damage < 0) {
                    damage = 0;
                }

                gp.monster[i].life -= damage;
                gp.ui.addMessage(damage + " damage!");
                gp.monster[i].invincible = true;
                gp.monster[i].damageReaction();

                if (gp.monster[i].life <= 0) {
                    gp.monster[i].dying = true;
                    gp.ui.addMessage("Killed the " + gp.monster[i].name + "!");
                    gp.ui.addMessage("Exp + " + gp.monster[i].exp);
                    exp += gp.monster[i].exp;
                    checkLevelUp();
                } 
            }
        }
    }

    public void checkLevelUp() {
        if (level <= 7) {
            if (exp >= nextLevelExp) {
                level++;
                nextLevelExp += 2 * 10;
                maxLife += 2;
                maxChakra += 4;
                strength++;
                if (level > 3) {
                    dexterity++;
                }
                if (level % 2 == 0) {
                    projectile2.attack++;
                }
                projectile.attack++;
                attack = getAttack();
                defense = getDefense();
                life = maxLife;
                chakra = maxChakra;
    
                gp.gameState = gp.dialogueState;
                gp.ui.currenDialogue = "You are level " + level + "now!\n" + "You feel stronger!";
            }
        }
    }

    public void selectItem() {
        int itemIndex = gp.ui.getItemIndexOnSlot();

        if (itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);

            if (selectedItem.type == typeSword || selectedItem.type == typeAxe) {
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if (selectedItem.type == typeShield) {
                currentShield = selectedItem;
                defense = getDefense();
            }
            if (selectedItem.type == typeConsumable) {
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        
        switch(direction) {
        case "up":
        if (attacking == false) {
            if (spriteNum == 1) {
                image = up1;
            }
            if (spriteNum == 2) {
                image = up2;
            }
        }
        if (attacking == true) {
            tempScreenY = screenY - gp.tileSize;
            if (spriteNum == 1) {
                image = attackUp1;
            }
            if (spriteNum == 2) {
                image = attackUp2;
            }
        }
            break;
        case "down":
        if (attacking == false) {
            if (spriteNum == 1) {
                image = down1;
            }
            if (spriteNum == 2) {
                image = down2;
            }
        }
        if (attacking == true) {
            if (spriteNum == 1) {
                image = attackDown1;
            }
            if (spriteNum == 2) {
                image = attackDown2;
            }
        }
            break;
        case "left":
        if (attacking == false) {
            if (spriteNum == 1) {
                image = left1;
            }
            if (spriteNum == 2) {
                image = left2;
            }
        }
        if (attacking == true) {
            tempScreenX = screenX - gp.tileSize;
            if (spriteNum == 1) {
                image = attackLeft1;
            }
            if (spriteNum == 2) {
                image = attackLeft2;
            }
        }
            break;
        case "right":
        if (attacking == false) {
            if (spriteNum == 1) {
                image = right1;
            }
            if (spriteNum == 2) {
                image = right2;
            }
        }
        if (attacking == true) {
            if (spriteNum == 1) {
                image = attackRight1;
            }
            if (spriteNum == 2) {
                image = attackRight2;
            }
        }
            break;
        }

        if (invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);

        //reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //debug
        // g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        // System.out.println("Invincible Counter:" + invincibleCounter);
    }
}
