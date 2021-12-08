package JogoGalaxian.principal.modelo;

import JogoGalaxian.constants.Configuration;
import JogoGalaxian.enums.Direction;
import JogoGalaxian.enums.Owner;
import JogoGalaxian.observers.ShotFiredByEnemyObserver;
import JogoGalaxian.principal.visao.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Enemy extends Rectangle implements ShotFiredByEnemyObserver {
    private int speed = Configuration.ENEMY_SPEED;

    public static final List<Bullet> bullets = new ArrayList<>();

    public List<Bullet> getBullets() {
        return bullets;
    }

    public Enemy(int x, int y, World world) {
        super(x, y, Configuration.ENEMY_SIZE, Configuration.ENEMY_SIZE);

        world.addShotEnemyObserver(this);
    }

    public void update() {
        x += speed;

        if (x <= Configuration.BLOCK_SIZE || x >= Configuration.WIDTH - Configuration.ENEMY_SIZE - Configuration.BLOCK_SIZE) {
            speed = -speed;
            y += Configuration.ENEMY_SIZE;
        }
    }

    @Override
    public void shotFiredByEnemy(int xEnemy, int yEnemy) {
        bullets.add(new Bullet(xEnemy, yEnemy, Owner.ENEMY));
    }
}

