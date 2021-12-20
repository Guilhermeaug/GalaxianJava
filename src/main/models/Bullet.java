package main.models;

import main.constants.Configuration;
import main.enums.Owner;

import java.awt.*;

/**
 * Representa um tiro.
 */
public class Bullet extends Rectangle {
    private final Owner owner; // Representa o dono do disparo.

    public Bullet(int x, int y, Owner owner) {
        super(x, y, Configuration.BULLET_SIZE, Configuration.BULLET_SIZE);
        this.owner = owner;
    }

    /**
     * A cada iteração do objeto, o tiro é deslocado para cima no caso do Player, e para baixo no caso dos inimigos.
     */
    public void update() {
        if (owner == Owner.PLAYER) {
            int speed = Configuration.BULLET_SPEED;
            y -= speed;
        } else {
            int speed = Configuration.ENEMY_BULLET_SPEED;
            y += speed;
        }
    }

    /**
     * @param g O objeto Graphics que será utilizado para desenhar o tiro.
     * @param color A cor que será utilizada para desenhar o tiro. Varia de jogador ou inimigo.
     */
    public void draw(Graphics g, Color color) {
        g.setColor(color);
        g.drawOval(x, y, width, height);
    }
}