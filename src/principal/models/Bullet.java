package principal.models;

import principal.constants.Configuration;
import principal.enums.Owner;

import java.awt.*;

public class Bullet extends Rectangle {
    private final Owner owner;

    public Bullet(int x, int y, Owner owner) {
        super(x, y, Configuration.BULLET_SIZE, Configuration.BULLET_SIZE);
        this.owner = owner;
    }

    public void update() {
        if (owner == Owner.PLAYER) {
            int speed = Configuration.BULLET_SPEED;
            y -= speed;
        } else {
            int speed = Configuration.ENEMY_BULLET_SPEED;
            y += speed;
        }
    }

    public void draw(Graphics g, Color color) {
        g.setColor(color);
        g.drawOval(x, y, width, height);
    }
}
