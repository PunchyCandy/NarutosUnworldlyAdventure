package main;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entity.Entity;
import object.Heart;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
    Font anime, indie;
    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public boolean gameFinished = false;
    public String currenDialogue = "";
    BufferedImage heart_full, heart_half, heart_blank;
    BufferedImage background, icon, naruto, sasuke;
    public int commandNum = 0;
    public int slotCol = 0;
    public int slotRow = 0;
    int subState = 0;
    boolean canPlay = true;

    public UI(GamePanel g) {
        gp = g;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/animeace.ttf");
            anime = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/fonts/indiestarbb_bld.ttf");
            indie = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        //create HUD object
        Entity heart = new Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(indie);
        g2.setColor(Color.white);

        //title state
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }
        //play state
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
            drawMessage();
        }
        //pause state
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
            drawPlayerLife();
        }
        //dialogue state
        if (gp.gameState == gp.dialogueState) {
            drawDialogueScreen();
            drawPlayerLife();
        }
        //character state
        if (gp.gameState == gp.characterState) {
            drawCharacterScreen();
            drawInventory();
        }
        //option state
        if (gp.gameState == gp.optionState) {
            drawOptionScreen();
        }
        //game over state
        if (gp.gameState == gp.gameOverState) {
            drawGameoverScreen();
        }
        //kill sasuke state
        if (gp.gameState == gp.killedSasukeState) {
            playVideo();
            drawBackToTitleScreen();
        }
    }

    public void drawPlayerLife() {
        int x = gp.tileSize/2;
        int y = gp.tileSize/3;
        int i = 0;

        //draw max life
        while (i < gp.player.maxLife/2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }

        //reset
        x = gp.tileSize/2;
        y = gp.tileSize/3;
        i = 0;

        //draw current life
        while (i < gp.player.life) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if (i < gp.player.life) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }

        //draw max chakra bar
        x = gp.tileSize/2;
        y = gp.tileSize * 2 - gp.tileSize / 2;
        int width = gp.tileSize / 4 * gp.player.maxChakra;
        int height = gp.tileSize / 4;
        g2.setColor(Color.blue);
        g2.drawRoundRect(x, y, width, height, 0, 0);

        //draw chakra
        x = gp.tileSize/2;
        y = gp.tileSize * 2 - gp.tileSize / 2;
        width = gp.tileSize / 4;
        i = 0;
        while (i < gp.player.chakra) {
            g2.fillRoundRect(x, y, width, height, 0, 0);
            i++;
            x += gp.tileSize / 4;
        }
    }

    public void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 3;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1; 
                messageCounter.set(i, counter);
                messageY += 50;

                if (messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawTitleScreen() {

        // g2.setColor(new Color(242,163,11));
        // g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        //set background
        UtilityTool uTool = new UtilityTool();
        try {
            background = ImageIO.read(getClass().getResourceAsStream("/objects/background.png"));
            background = uTool.scaleImage(background, gp.screenWidth, gp.screenHeight);
            icon = ImageIO.read(getClass().getResourceAsStream("/objects/rasengan.png"));
            icon = uTool.scaleImage(icon, gp.tileSize/2, gp.tileSize/2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2.drawImage(background, 0, 0, gp);
        

        //title name
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,160F));
        String text = "Naruto's Unworldly Adventure!";
        int x = getXForCenteredText(text);
        int y = gp.tileSize * 3;

        //shadow
        g2.setColor(Color.black);
        g2.drawString(text, x + 5, y + 5);
        //title color
        g2.setColor(new Color(12,47,223));
        g2.drawString(text, x, y);

        //menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,60F));

        text = "NEW GAME";
        x = getXForCenteredText(text);
        y += gp.tileSize * 2;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawImage(icon, x - gp.tileSize/2 - 10, y - gp.tileSize/2 + 20, null);
        }

        text = "LOAD GAME";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawImage(icon, x - gp.tileSize/2 - 10, y - gp.tileSize/2 + 20, null);
        }

        text = "QUIT GAME";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 2) {
            g2.drawImage(icon, x - gp.tileSize/2 - 10, y - gp.tileSize/2 + 20, null);
        }
    }

    public void drawPauseScreen() {
        
        g2.setFont(anime);
        String text = "PAUSED";
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,100));
        int x = getXForCenteredText(text);
        int y = gp.screenHeight/2;

        g2. drawString(text, x, y);
    }

    public void drawDialogueScreen() {
        //window
        int x = gp.tileSize * 2;
        int y = gp.screenHeight - gp.tileSize * 3 - gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 3;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,60));
        x += gp.tileSize;
        y += gp.tileSize;

        for (String line: currenDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 60;
        }
    }

    public void drawCharacterScreen() {
        //create a frame
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize / 2;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 8;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //text
        g2.setColor(Color.black);
        g2.setFont(g2.getFont().deriveFont(70F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize/2 + 10;
        final int lineHeight = 70;

        //names
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Chakra", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight + 60;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 30;
        g2.drawString("Vest", textX, textY);
        textY += lineHeight;

        //values
        int tailX = (frameX + frameWidth) - 30;
        //reset textY
        textY = frameY + gp.tileSize/2 + 10;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.chakra + "/" + gp.player.maxChakra);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXForAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - 50, null);
        textY += gp.tileSize;

        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY - 50, null);
    }

    public void drawInventory() {

        //frame
        int frameX = gp.tileSize * 9;
        int frameY = gp.tileSize / 2;
        int frameWidth = gp.tileSize * 6 - gp.tileSize/2;
        int frameHeight = gp.tileSize * 5 - gp.tileSize/2;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //slot
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;

        //draw player items
        for (int i = 0; i < gp.player.inventory.size(); i++) {

            //equip cursor
            if (gp.player.inventory.get(i) == gp.player.currentWeapon || gp.player.inventory.get(i) == gp.player.currentShield) {
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }

            g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);

            slotX += slotSize;

            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        //cursor
        int cursorX = slotXstart + slotSize * slotCol;
        int cursorY = slotYstart + slotSize * slotRow;
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        //draw cursor
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(8));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        //description frame
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gp.tileSize * 3;

        //draw description
        int textX = dFrameX + 20;
        int textY = dFrameY + gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(70F));

        int itemIndex = getItemIndexOnSlot();

        if (itemIndex < gp.player.inventory.size()) {

            drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

            for (String line: gp.player.inventory.get(itemIndex).description.split("\n")) {
                g2.drawString(line, textX, textY);
                textY +=  70;
            }
        }
    }

    public void drawOptionScreen() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(80F));

        //sub window
        int frameX = gp.tileSize * 4;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 7;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch(subState) {
            case 0:
            optionsTop(frameX, frameY);
            break;
            case 1:
            optionsControl(frameX, frameY);
            break;
            case 2:
            optionsEndGameConfirmation(frameX, frameY);
            break;
        }
    }

    public void optionsTop(int frameX, int frameY) {
        int textX;
        int textY;

        //title
        String text = "Options";
        textX = getXForCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        //music
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);
        if (commandNum == 0) {
            g2.drawImage(icon, textX - gp.tileSize / 2, textY - gp.tileSize / 2, null);
        }

        //sound effect
        textY += gp.tileSize;
        g2.drawString("Sound Effect", textX, textY);
        if (commandNum == 1) {
            g2.drawImage(icon, textX - gp.tileSize / 2, textY - gp.tileSize / 2, null);
        }

        //control
        textY += gp.tileSize;
        g2.drawString("Controls", textX, textY);
        if (commandNum == 2) {
            g2.drawImage(icon, textX - gp.tileSize / 2, textY - gp.tileSize / 2, null);
            if (gp.keyH.enterPressed == true) {
                subState = 1;
                commandNum = 0;
            }
            gp.keyH.enterPressed = false;
        }

        //end game
        textY += gp.tileSize;
        g2.drawString("End Game", textX, textY);
        if (commandNum == 3) {
            g2.drawImage(icon, textX - gp.tileSize / 2, textY - gp.tileSize / 2, null);
            if (gp.keyH.enterPressed == true) {
                subState = 2;
                commandNum = 0;
            }
            gp.keyH.enterPressed = false;
        }

        //back
        textY += gp.tileSize + gp.tileSize / 2;
        g2.drawString("Back", textX, textY);
        if (commandNum == 4) {
            g2.drawImage(icon, textX - gp.tileSize / 2, textY - gp.tileSize / 2, null);
            if (gp.keyH.enterPressed == true) {
                gp.gameState = gp.playState;
                commandNum = 0;
            }
            gp.keyH.enterPressed = false;
        }

        //music slider box
        textX = frameX + (int) (gp.tileSize * 4.5);
        textY = frameY + gp.tileSize + gp.tileSize / 2;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, gp.tileSize * 3, gp.tileSize / 2);
        //volume
        int volumeWidth = (gp.tileSize * 3) / 5 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, gp.tileSize / 2);

        //sound effect slider box
        textY += gp.tileSize;
        g2.drawRect(textX, textY, gp.tileSize * 3, gp.tileSize / 2);
        //volume
        volumeWidth = (gp.tileSize * 3) / 5 * gp.soundEffect.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, gp.tileSize / 2);

        gp.config.saveConfig();
    }

    public void drawGameoverScreen() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 200F));

        text = "You died";
        //shadow
        g2.setColor(Color.black);
        x = getXForCenteredText(text);
        y = gp.tileSize * 2;
        g2.drawString(text, x, y);
        //main text
        g2.setColor(Color.white);
        g2.drawString(text, x - 4, y - 4);

        //retry
        g2.setFont(g2.getFont().deriveFont(100F));
        text = "Retry";
        x = getXForCenteredText(text);
        y += gp.tileSize * 3;
        g2.drawString(text, x, y);
        if (commandNum == 0) {
            g2.drawImage(icon, x - gp.tileSize / 2, y - gp.tileSize / 2, null);
        }

        //back to title screen
        text = "Quit";
        x = getXForCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if (commandNum == 1) {
            g2.drawImage(icon, x - gp.tileSize / 2, y - gp.tileSize / 2, null);
        }
    }

    public void playVideo() {
        if (canPlay == true) {
            System.out.println("https://youtu.be/sCiVSBkNIiA");
            try {
                Desktop.getDesktop().browse(new URI("https://youtu.be/sCiVSBkNIiA"));
                gp.stopMusic();
            } catch (Exception e) {
                
            }
            canPlay = false;
        }
    }

    public void drawBackToTitleScreen() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(80F));

        //sub window
        int frameX = gp.tileSize;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 14;
        int frameHeight = gp.tileSize * 7;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        
        //Congrats message
        int textX;
        int textY;
        String text = "Congratulations!";

        textX = getXForCenteredText(text);
        textY = gp.tileSize * 3;
        g2.drawString(text, textX, textY);

        text = "You saved Sasuke and became friends again!";
        textX = getXForCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);

        //exit game button
        text = "Exit Game";
        textX = getXForCenteredText(text);
        textY += gp.tileSize * 2;
        g2.drawString(text, textX, textY);


        UtilityTool uTool = new UtilityTool();
        try {
            naruto = ImageIO.read(getClass().getResourceAsStream("/player/front_1.png"));
            naruto = uTool.scaleImage(naruto, gp.tileSize * 2, gp.tileSize * 2);
            sasuke = ImageIO.read(getClass().getResourceAsStream("/player/sasukeDown1.png"));
            sasuke = uTool.scaleImage(sasuke, gp.tileSize * 2, gp.tileSize * 2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        g2.drawImage(naruto, gp.tileSize * 2, gp.tileSize * 6, null);

        g2.drawImage(sasuke, gp.tileSize * 12, gp.tileSize * 6, null);

        g2.drawImage(icon, textX - gp.tileSize / 2, textY - gp.tileSize / 2, null);
        if (gp.keyH.enterPressed == true) {
            System.exit(0);
        }
    }


    public void optionsControl(int frameX, int frameY) {
        int textX;
        int textY;

        //title
        String text = "Controls";
        textX = getXForCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += 2 * (gp.tileSize / 3);
        g2.drawString("Move", textX, textY);
        textY += 2 * (gp.tileSize / 3);
        g2.drawString("Confirm/Attack", textX, textY);
        textY += 2 * (gp.tileSize / 3);
        g2.drawString("Special Ability", textX, textY);
        textY += 2 * (gp.tileSize / 3);
        g2.drawString("Shurikens", textX, textY);
        textY += 2 * (gp.tileSize / 3);
        g2.drawString("Character Screen", textX, textY);
        textY += 2 * (gp.tileSize / 3);
        g2.drawString("Pause", textX, textY);
        textY += 2 * (gp.tileSize / 3);
        g2.drawString("Options", textX, textY);
        textY += 2 * (gp.tileSize / 3);

        textX = frameX + gp.tileSize * 6;
        textY = frameY + gp.tileSize;
        textY += 2 * (gp.tileSize / 3);
        g2.drawString("WASD", textX, textY);
        textY += 2 * (gp.tileSize / 3);
        g2.drawString("ENTER", textX, textY);
        textY += 2 * (gp.tileSize / 3);
        g2.drawString("SPACE", textX, textY);
        textY += 2 * (gp.tileSize / 3);
        g2.drawString("F", textX, textY);
        textY += 2 * (gp.tileSize / 3);
        g2.drawString("C", textX, textY);
        textY += 2 * (gp.tileSize / 3);
        g2.drawString("P", textX, textY);
        textY += 2 * (gp.tileSize / 3);
        g2.drawString("ESC", textX, textY);
        textY += 2 * (gp.tileSize / 3);

        //back button
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 6 + gp.tileSize / 2;
        g2.drawString("Back", textX, textY);
        if (commandNum == 0) {
            g2.drawImage(icon, textX - gp.tileSize / 2, textY - gp.tileSize / 2, null);
            if (gp.keyH.enterPressed == true) {
                subState = 0;
                commandNum = 2;
            }
            gp.keyH.enterPressed = false;
        }
    }

    public void optionsEndGameConfirmation(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currenDialogue = "Quit the game and \nreturn to the title screen?";

        for (String line: currenDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 2 * (gp.tileSize / 3);
        }

        // //yes
        String text = "Yes";
        textX = getXForCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if (commandNum == 0) {
            g2.drawImage(icon, textX - gp.tileSize / 2, textY - gp.tileSize / 2, null);
            if (gp.keyH.enterPressed == true) {
                subState = 0;
                gp.gameState = gp.titleState;
                gp.stopMusic();
            }
            gp.keyH.enterPressed = false;
        }

        //no
        text = "No";
        textX = getXForCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if (commandNum == 1) {
            g2.drawImage(icon, textX - gp.tileSize / 2, textY - gp.tileSize / 2, null);
            if (gp.keyH.enterPressed == true) {
                subState = 0;
                commandNum = 3;
            }
            gp.keyH.enterPressed = false;
        }
    }

    public int getItemIndexOnSlot() {
        int itemIndex = slotCol + (slotRow * 5);
        return itemIndex;
    }

    //draw window for dialogue
    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(255, 255, 255, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(0, 0, 0);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public int getXForCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }

    public int getXForAlignToRightText(String text, int tailX) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
}
