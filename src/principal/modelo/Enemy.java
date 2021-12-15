package principal.modelo;

import principal.constants.Configuration;
import principal.enums.Owner;
import principal.observers.ShotFiredByEnemyObserver;
import principal.visao.screens.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Enemy extends Rectangle implements ShotFiredByEnemyObserver {
    private int speed = Configuration.ENEMY_SPEED;
    public static double speedModifier = 1;

    public static final List<Bullet> bullets = new ArrayList<>();

    public Enemy(int x, int y, World world) {
        super(x, y, Configuration.ENEMY_SIZE, Configuration.ENEMY_SIZE);

        world.addShotEnemyObserver(this);
    }

    public void update() {
        x += speed * speedModifier;

        if (x <= 40 || x >= Configuration.WIDTH - Configuration.ENEMY_SIZE - 40) {
            speed = -speed;
            y += Configuration.ENEMY_SIZE;
        }
    }

    @Override
    public void shotFiredByEnemy(int xEnemy, int yEnemy) {
        bullets.add(new Bullet(xEnemy, yEnemy, Owner.ENEMY));
    }
}

