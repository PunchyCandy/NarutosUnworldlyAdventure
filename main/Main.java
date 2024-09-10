package main;
import java.io.IOException;

import javax.swing.JFrame;

public class Main{

    public static JFrame window;
    public static void main (String[] args) throws IOException{

        window = new JFrame ();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable (false);
        window.setTitle ("Naruto's Unwolrdly Adventure");
        window.setUndecorated(true);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        gamePanel.playMusic(13);

        gamePanel.config.loadConfig();

        window.pack();

        window.setLocationRelativeTo (null);
        window.setVisible (true);

        gamePanel.setupGame();
        gamePanel.startGameThread();

        new Requirements();
    }
}