package main.utils;

import main.enums.Direction;
import main.models.Enemy;
import main.views.screens.World;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/**
 * Cuida dos eventos do teclado. Recebe um objeto da classe principal para chamar os observadores
 * e executar ações.
 */
public class KeyboardListener implements KeyListener {
    private final World world;
    private boolean active; // Indica se o jogo está pausado

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
                world.notifyShotPlayerObserver(); // Indica ao Player que é preciso realizar um tiro

                Random random = new Random();
                // Os inimigos têm 70% de chance de disparar quando o jogador dispara
                if (random.nextInt(100) < 70) {
                    Enemy enemy = world.getRandomEnemy(); // seleciona um inimigo aleatório da lista
                    world.notifyShotEnemyObserver(enemy.x, enemy.y); // Indica ao inimigo que é preciso realizar um tiro
                }
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_P) { // Pausa o jogo
            world.setPauseControl(!world.isPauseControl());
            active = !active;
        }

        if (e.getKeyCode() == KeyEvent.VK_R) { // Reinicia o jogo
            world.startGame();
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { // Sai do jogo
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    public void keyTyped(KeyEvent e) {}
}