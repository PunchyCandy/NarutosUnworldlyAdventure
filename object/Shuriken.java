package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class Shuriken extends Projectile{

    GamePanel gp;

    public Shuriken(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "ShurikenThrow";
        speed = 16;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 0;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setUp("/projectile/shuriken1", gp.tileSize / 2, gp.tileSize / 2);
        up2 = setUp("/projectile/shuriken2", gp.tileSize / 2, gp.tileSize / 2);
        down1 = setUp("/projectile/shuriken1", gp.tileSize / 2, gp.tileSize / 2);
        down2 = setUp("/projectile/shuriken2", gp.tileSize / 2, gp.tileSize / 2);
        left1 = setUp("/projectile/shuriken1", gp.tileSize / 2, gp.tileSize / 2);
        left2 = setUp("/projectile/shuriken2", gp.tileSize / 2, gp.tileSize / 2);
        right1 = setUp("/projectile/shuriken1", gp.tileSize / 2, gp.tileSize / 2);
        right2 = setUp("/projectile/shuriken2", gp.tileSize / 2, gp.tileSize / 2);
    }

    public boolean haveResource(Entity user) {
        boolean haveResource = false;
        if (user.chakra >= useCost) {
            haveResource = true;
        }
        return haveResource;
    }

    public void subtractResouce(Entity user) {
        user.chakra -= useCost;
    }
}
