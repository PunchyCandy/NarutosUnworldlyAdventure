package object;

import entity.Projectile;
import main.GamePanel;

public class Rock extends Projectile{
    
    GamePanel gp;

    public Rock(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Fireball";
        speed = 8;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setUp("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        up2 = setUp("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        down1 = setUp("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        down2 = setUp("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        left1 = setUp("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        left2 = setUp("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        right1 = setUp("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
        right2 = setUp("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
    }


}
