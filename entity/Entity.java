package entity;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;
import java.awt.*;

import main.GamePanel;
import main.UtilityTool;

import java.awt.Rectangle;

public class Entity {

    GamePanel gp;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public BufferedImage image, image2, image3;
    public Rectangle solidArea = new Rectangle(0, 30, 120, 90);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public boolean collision = false;
    public int solidAreaDefaultX, solidAreaDefaultY;
    String dialogues[] = new String[20];

    //state
    public int worldX, worldY;
    public int spriteNum = 1;
    public String direction = "down";
    int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;
    public boolean onPath = false;
    public boolean followPlayer = false;

    //counter
    public int spriteCounter = 0;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;
    public int shotAvailableCounter = 0;

    //character attributes
    public int speed;
    public String name;
    public int maxLife;
    public int life;
    public int maxChakra;
    public int chakra;
    public Projectile projectile; //fireball
    public Projectile projectile2; //shuriken

    public int useCost;

    //extras
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public Entity currentWeapon;
    public Entity currentShield;

    //item attributes
    public int attackValue;
    public int defenseValue;
    public String description = "";

    //type
    public int type; // 0 = player, 1 = npc, 2 = monster
    public final int typePlayer = 0;
    public final int typeNPC = 1;
    public final int typeMonster = 2;
    public final int typeSword = 3;
    public final int typeAxe = 4;
    public final int typeShield = 5;
    public final int typeConsumable = 6;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {}

    public void damageReaction() {}

    public void setDialogue() {}

    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        
        gp.ui.currenDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch(gp.player.direction) {
            case "up":
            direction = "down";
            break;
            case "down":
            direction = "up";
            break;
            case "left":
            direction = "right";
            break;
            case "right":
            direction = "left";
            break;
        }
    }

    public void use(Entity entity) {}

    public void checkCollision() {

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monster);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if (this.type == typeMonster && contactPlayer == true) {
            damagePlayer(attack);
        }

    }

    public void update() {
        
        setAction();

        checkCollision();

        //if collision is false, player can move
        if (collisionOn == false) {
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

        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
        
    }

    public void damagePlayer(int attack) {
        if (gp.player.invincible == false) {
            gp.playSoundEffect(6);

            int damage = attack - gp.player.defense;
            if (damage < 0) {
                damage = 0;
            }

            gp.player.life -= damage;
            gp.player.invincible = true;
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY ) {

                    switch(direction) {
                        case "up":
                            if (spriteNum == 1) {
                                image = up1;
                            }
                            if (spriteNum == 2) {
                                image = up2;
                            }
                            break;
                        case "down":
                            if (spriteNum == 1) {
                                image = down1;
                            }
                            if (spriteNum == 2) {
                                image = down2;
                            }
                            break;
                        case "left":
                            if (spriteNum == 1) {
                                image = left1;
                            }
                            if (spriteNum == 2) {
                                image = left2;
                            }
                            break;
                        case "right":
                            if (spriteNum == 1) {
                                image = right1;
                            }
                            if (spriteNum == 2) {
                                image = right2;
                            }
                            break;
                        }

                        //monster HP bar
                        if (type == 2 && hpBarOn == true) {
                            double oneScale = (double) gp.tileSize / maxLife;
                            double hpBarValue = oneScale * life;

                            g2.setColor(new Color(35, 35, 35));
                            g2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 12);

                            g2.setColor(new Color(255, 0, 30));
                            g2.fillRect(screenX, screenY - 15, (int) hpBarValue, 10);

                            hpBarCounter++;

                            if (hpBarCounter > 600) {
                                hpBarCounter = 0;
                                hpBarOn = false;
                            }
                        }

                        if (invincible == true) {
                            hpBarOn = true;
                            hpBarCounter = 0;
                            changeAlpha(g2, 0.3f);
                        }
                        if (dying == true) {
                            if (name.equals("Sasuke")) {
                                gp.gameState = gp.killedSasukeState;
                            }
                            dyingAnimation(g2);
                        }
                
                        g2.drawImage(image, screenX, screenY, null);
                
                        //solideArea
                        // g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);

                        //reset alpha
                        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                }
    }

    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;

        int i = 5;

        if (dyingCounter <= i) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i && dyingCounter <= i * 2) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 2 && dyingCounter <= i * 3) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 3 && dyingCounter <= i * 4) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 4 && dyingCounter <= i * 5) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 5 && dyingCounter <= i * 6) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 6 && dyingCounter <= i * 7) {
            changeAlpha(g2, 0f);
        }
        if (dyingCounter > i * 7 && dyingCounter <= i * 8) {
            changeAlpha(g2, 1f);
        }
        if (dyingCounter > i * 8) {
            alive = false;
        }
    }

    public void changeAlpha(Graphics2D g2, float alphaValue) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    }

    public BufferedImage setUp(String imageName, int width, int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage scaledImage = null;

        try {
            scaledImage = ImageIO.read(getClass().getResourceAsStream(imageName + ".png"));
            scaledImage = uTool.scaleImage(scaledImage, width, height);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return scaledImage;
    }

    public void searchPath(int goalCol, int goalRow) {

        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow, this);

        if (gp.pFinder.search() == true) {

            //next worldX and worldX
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            //entity's solidArea position
            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if (enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "up";
            }
            else if (enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
                direction = "down";
            }
            else if (enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
                //left or right
                if (enLeftX > nextX) {
                    direction = "left";
                }
                if (enLeftX < nextX) {
                    direction = "right";
                }
            }
            else if (enTopY > nextY && enLeftX > nextX) {
                //up or left
                direction = "up";
                checkCollision();
                if (collisionOn == true) {
                    direction = "left";
                }
            }
            else if (enTopY > nextY && enLeftX < nextX) {
                //up or right
                direction = "up";
                checkCollision();
                if (collisionOn == true) {
                    direction = "right";
                }
            }
            else if (enTopY < nextY && enLeftX > nextX) {
                //down or left
                direction = "down";
                checkCollision();
                if (collisionOn == true) {
                    direction = "left";
                }
            }
            else if (enTopY < nextY && enLeftX < nextX) {
                //down or right
                direction = "down";
                checkCollision();
                if (collisionOn == true) {
                    direction = "right";
                }
            }

            //if reaches goal stop search
            int nextCol = gp.pFinder.pathList.get(0).col;
            int nextRow = gp.pFinder.pathList.get(0).row;
            if (nextCol == goalCol && nextRow == goalCol) {
                onPath = false;
            }
        }
    }
}