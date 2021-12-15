package principal.utils;

import principal.enums.Direction;
import principal.modelo.Enemy;
import principal.visao.screens.World;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class KeyboardListener implements KeyListener {
    private final World world;
    private boolean active;

    public KeyboardListener(World world) {
        this.world = world;
        this.active = true;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (active) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
                world.notifyMovementObserver(e.getKeyCode() == KeyEvent.VK_RIGHT ? Direction.RIGHT : Direction.LEFT);
            }

            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                world.notifyShotPlayerObserver();

                Random random = new Random();

                //70% de chance do inimigo disparar quanto o jogador dispara
                if (random.nextInt(100) < 70) {
                    Enemy enemy = world.getRandomEnemy();
                    world.notifyShotEnemyObserver(enemy.x, enemy.y);
                }
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_P) {
            world.setPauseControl(!world.isPauseControl());
            active = !active;
        }

        if (e.getKeyCode() == KeyEvent.VK_R) {
            world.startGame();
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }
}
