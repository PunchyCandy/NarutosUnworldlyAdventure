package tile;

import java.awt.image.BufferedImage;

public class Tile {
    public BufferedImage image;
    public boolean collision = false;

    Tile parent;
    int col;
    int row; 
    int gCost;
    int hCost;
    int fCost;
    boolean start;
    boolean goal;
    boolean solid;
    boolean open;
    boolean checked;

    public void setAsStart() {
        start = true;
    }

    public void setAsGoal() {
        goal = true;
    }
}
