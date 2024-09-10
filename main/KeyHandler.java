package main;
import java.awt.event.*;

public class KeyHandler implements KeyListener{

    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed, shotKeyPressed1;
    boolean showDebugText = false;

    public KeyHandler(GamePanel g) {
        gp = g;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
        
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        //title state
        if (gp.gameState == gp.titleState) {
            titleState(code);
        }

        //play state
        else if (gp.gameState == gp.playState) {
            playState(code);
        }

        //pause state
        else if (gp.gameState == gp.pauseState) {
            pauseState(code);
        }

        //dialogue state
        else if (gp.gameState == gp.dialogueState) {
            dialogueState(code);
        }

        //character state
        else if (gp.gameState == gp.characterState) {
            characterState(code);
        }

        //option state
        else if (gp.gameState == gp.optionState) {
            optionState(code);
        }

        //Gameover state
        else if (gp.gameState == gp.gameOverState) {
            gameOverState(code);
        }

        //sasuke state
        else if (gp.gameState == gp.killedSasukeState) {
            sasukeState(code);
        }
    }

    public void titleState(int code) {
        if (gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if (gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if (gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    gp.gameState = gp.playState;
                    gp.stopMusic();
                    gp.playMusic(0);
                    // gp.stopMusic();
                }
                if (gp.ui.commandNum == 1) {
                    //add later
                }
                if (gp.ui.commandNum == 2) {
                    System.exit(0);
                }
            }
        }
    }

    public void playState(int code) {
        if (code == KeyEvent.VK_W) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.pauseState;
        }
        if (code == KeyEvent.VK_C) {
            gp.gameState = gp.characterState;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if (code == KeyEvent.VK_SPACE) {
            shotKeyPressed = true;
        }
        if (code == KeyEvent.VK_F) {
            shotKeyPressed1 = true;
        }
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.optionState;
        }

        // if (code == KeyEvent.VK_SHIFT) {
        //     while(gp.player.speed <= 8) {
        //         gp.player.speed += 4;
        //     }
        // }

        //debug
        if (code == KeyEvent.VK_T) {
            if (showDebugText == false) {
                showDebugText = true;
            }
            else if (showDebugText = true) {
                showDebugText = false;
            }
        }
        if (code == KeyEvent.VK_R) {
            gp.tileM.loadMap("/maps/narutoMap.txt");
        }
    
    }

    public void pauseState(int code) {
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.playState;
        }
    }

    public void dialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.playState;
        }
    }

    public void characterState(int code) {
        if (code == KeyEvent.VK_C) {
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_W) {
            if (gp.ui.slotRow != 0) {
                gp.ui.slotRow--;
                gp.playSoundEffect(9);
            }
        }
        if (code == KeyEvent.VK_A) {
            if (gp.ui.slotCol != 0) {
                gp.ui.slotCol--;
                gp.playSoundEffect(9);
            }
        }
        if (code == KeyEvent.VK_S) {
            if (gp.ui.slotRow != 3) {
                gp.ui.slotRow++;
                gp.playSoundEffect(9);
            }
        }
        if (code == KeyEvent.VK_D) {
            if (gp.ui.slotCol != 4) {
                gp.ui.slotCol++;
                gp.playSoundEffect(9);
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
        }
    }

    public void optionState(int code) {
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }

        int maxCommandNum = 0;
        switch(gp.ui.subState) {
            case 0:
            maxCommandNum = 4;
            break;
            case 2:
            maxCommandNum = 1;
            break;
        }

        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            gp.playSoundEffect(9);
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = maxCommandNum;
            }
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            gp.playSoundEffect(9);
            if (gp.ui.commandNum > maxCommandNum) {
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_A) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNum == 0 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSoundEffect(9);
                }
                if (gp.ui.commandNum == 1 && gp.soundEffect.volumeScale > 0) {
                    gp.soundEffect.volumeScale--;
                    gp.playSoundEffect(9);
                }
            }
        }
        if (code == KeyEvent.VK_D) {
            if (gp.ui.subState == 0) {
                if (gp.ui.commandNum == 0 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSoundEffect(9);
                }
                if (gp.ui.commandNum == 1 && gp.soundEffect.volumeScale < 5) {
                    gp.soundEffect.volumeScale++;
                    gp.playSoundEffect(9);
                }
            }
        }
    }

    public void gameOverState(int code) {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            gp.playSoundEffect(9);
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            gp.playSoundEffect(9);
            if (gp.ui.commandNum > 1) {
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
                gp.retry();
                gp.stopMusic();
                gp.playMusic(0);
            }
            else if (gp.ui.commandNum == 1) {
                gp.gameState = gp.titleState;
                gp.restart();
                gp.stopMusic();
                gp.playMusic(13);
            }
        }
    }

    public void sasukeState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_SPACE) {
            shotKeyPressed = false;
        }
        if (code == KeyEvent.VK_F) {
            shotKeyPressed1 = false;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }
        // if (code == KeyEvent.VK_SHIFT) {
        //     gp.player.speed -= 4;
        // }

        
    }
    
}
