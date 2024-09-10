package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class Rasenshuriken extends Projectile{

    GamePanel gp;

    public Rasenshuriken(GamePanel gp) {
        super(gp);
        this.gp = gp;

        name = "Rasenshuriken";
        speed = 16;
        maxLife = 80;
        life = maxLife;
        attack = 2;
        useCost = 1;
        alive = false;
        getImage();
    }

    public void getImage() {
        up1 = setUp("/projectile/rasenshurikenDown1", gp.tileSize, gp.tileSize);
        up2 = setUp("/projectile/rasenshurikenDown2", gp.tileSize, gp.tileSize);
        down1 = setUp("/projectile/rasenshurikenDown1", gp.tileSize, gp.tileSize);
        down2 = setUp("/projectile/rasenshurikenDown2", gp.tileSize, gp.tileSize);
        left1 = setUp("/projectile/rasenshurikenDown1", gp.tileSize, gp.tileSize);
        left2 = setUp("/projectile/rasenshurikenDown2", gp.tileSize, gp.tileSize);
        right1 = setUp("/projectile/rasenshurikenDown1", gp.tileSize, gp.tileSize);
        right2 = setUp("/projectile/rasenshurikenDown2", gp.tileSize, gp.tileSize);
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
