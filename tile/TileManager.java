package tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];
    boolean drawPath = true;

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

    public TileManager(GamePanel g) {

        gp = g;

        tile = new Tile[100];

        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];

        getTileImage();
        loadMap("/maps/narutoMap.txt");
        // loadMap("/maps/worldV2.txt");
    }

    public void getTileImage() {
        setUp(0,"grass",false);
        setUp(1,"grass",false);
        setUp(2,"grass",false);
        setUp(3,"grass",false);
        setUp(4,"grass",false);
        setUp(5,"grass",false);
        setUp(6,"grass",false);
        setUp(7,"grass",false);
        setUp(8,"grass",false);
        setUp(9,"grass",false);

        setUp(10,"grass",false);
        setUp(11,"treeTopLeft",true);
        setUp(12,"roadMid",false);
        setUp(13,"water00",true);
        setUp(14,"wall",true);
        setUp(15,"officeTile",false);
        setUp(16,"wall",true);
        setUp(17,"blueTile",true);
        setUp(18,"villageFloor",false);
        setUp(19,"earth",true);
        setUp(20,"topLeftDesk",false);
        setUp(21,"topRightDesk",false);
        setUp(22,"bottomLeftDesk",false);
        setUp(23,"bottomRightDesk",false);
        setUp(24,"middleDesk",false);
        setUp(25,"chair",false);
        setUp(26, "officeWallWithWindow", true);
        setUp(27, "topLeftOfficeWall", true);
        setUp(28, "topRightOfficeWall", true);
        setUp(29, "bottomLeftOfficeWall", false);
        setUp(30, "bottomRightOfficeWall", false);
        setUp(31, "bridge", false);
        setUp(32, "bridge", false);
        setUp(33, "bridge", false);
        setUp(34, "building1", true);
        setUp(35, "building2", true);
        setUp(36, "building3", true);
        setUp(37, "building4", true);
        setUp(38, "building5", true);
        setUp(39, "building6", true);
        setUp(40, "building7", true);
        setUp(41, "building8", true);
        setUp(42, "building9", true);
        setUp(43, "building10", true);
        setUp(44, "building11", true);
        setUp(45, "building12", true);
        setUp(46, "building13", true);
        setUp(47, "building14", true);
        setUp(48, "topLeftStore", true);
        setUp(49, "topRightStore", true);
        setUp(50, "bottomLeftStore", true);
        setUp(51, "bottomRightStore", true);
        setUp(52, "bench", true);
        setUp(53, "blueTileUnder", true);
        setUp(54, "sand", false);
        setUp(55, "fountain", true);
        setUp(56, "roadLeft", false);
        setUp(57, "roadRight", false);
        setUp(58, "grass2", false);
        setUp(59, "grass3", false);
        setUp(60, "roadGrass", false);
        setUp(61,"treeTopRight",true);
        setUp(62,"treeBotLeft",true);
        setUp(63,"treeBotRight",true);
        setUp(64, "roadTop", false);
        setUp(65, "roadBot", false);

    }

    public void setUp(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath) {

        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();

                while (col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Load map error");
        }
    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;


        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            //Stop moving cam at edge
            if (gp.player.screenX > gp.player.worldX) {
                screenX = worldX;
            }
            if (gp.player.screenY > gp.player.worldY) {
                screenY = worldY;
            }
            int rightOffset = gp.screenWidth - gp.player.screenX;
            if(rightOffset > gp.worldWidth - gp.player.worldX) {
                screenX = gp.screenWidth - (gp.worldWidth - worldX);
            }
            int bottomOffset = gp.screenHeight - gp.player.screenY;
            if(bottomOffset > gp.worldHeight - gp.player.worldY) {
                screenY = gp.screenHeight - (gp.worldHeight - worldY);
            }

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY ) {
                    g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                }
            else if (gp.player.screenX > gp.player.worldX ||
                    gp.player.screenY > gp.player.worldY ||
                    rightOffset > gp.worldWidth - gp.player.worldX ||
                    bottomOffset > gp.worldHeight - gp.player.worldY) {
                        g2.drawImage(tile[tileNum].image, screenX, screenY, null);
                    }

            worldCol++;

            if (worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }


        // if (drawPath == true) {
        //     g2.setColor(new Color(255, 0, 0, 70));

        //     for (int i = 0; i < gp.pFinder.pathList.size(); i++) {
        //         int worldX = gp.pFinder.pathList.get(0).col * gp.tileSize;
        //         int worldY = gp.pFinder.pathList.get(0).row * gp.tileSize;
        //         int screenX = worldX - gp.player.worldX + gp.player.screenX;
        //         int screenY = worldY - gp.player.worldY + gp.player.screenY;

        //         g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
        //     }
        // }
    }
}
