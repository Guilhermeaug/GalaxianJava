package JogoGalaxian.principal.modelo;

import JogoGalaxian.constants.Configuration;
import JogoGalaxian.enums.Owner;

import java.awt.Rectangle;

public class Bullet extends Rectangle {
    private Owner owner;

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Bullet(int x, int y, Owner owner) {
        super(x, y, Configuration.BULLET_SIZE, Configuration.BULLET_SIZE);
        this.owner = owner;
    }

    public void update() {
        if(owner == Owner.PLAYER) {
            int speed = Configuration.BULLET_SPEED;
            y -= speed;
        } else{
            int speed = Configuration.ENEMY_BULLET_SPEED;
            y += speed;
        }
    }

}
