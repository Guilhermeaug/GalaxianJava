package main.models;

import main.constants.Configuration;
import main.enums.Owner;
import main.observers.ShotFiredByEnemyObserver;
import main.views.helpers.image.Spritesheet;
import main.views.screens.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um inimigo do jogo.
 * Implementa a interface ShotFiredByEnemyObserver para ser notificado quando um tiro deve ser disparado.
 */
public class Enemy extends Rectangle implements ShotFiredByEnemyObserver {
    private int speed = Configuration.ENEMY_SPEED;
    // Modificador de velocidade, utilizado para diminuir a velocidade dos tiros com o item TeiaAranha
    public static double speedModifier = 1;

    // lista de tiros dos inimigos. É estático para representar que a lista de tiros é global para todos os inimigos
    // uma vez que não importa quem efetua o disparo, apenas seu tipo
    public static final List<Bullet> bullets = new ArrayList<>();

    Animation animation; // objeto para controlar a animação

    public Enemy(int x, int y, World world) {
        super(x, y, Configuration.ENEMY_SIZE, Configuration.ENEMY_SIZE); // inicializa o Rectangle

        animation = new Animation(Configuration.ENEMY_TARGET_FRAMES, Spritesheet.enemy.length);

        world.addShotEnemyObserver(this); // indica ao mundo que este objeto deve ser notificado quando um tiro é disparado
    }

    /**
     * Método para atualizar a posição do inimigo ao longo das passagens.
     */
    public void update() {
        animation.updateAnimationFrames();

        x += speed * speedModifier;

        // mantém os inimigos dentro da tela e diminui a sua altura
        if (x <= 40 || x >= Configuration.WIDTH - Configuration.ENEMY_SIZE - 40) {
            speed = -speed;
            y += Configuration.ENEMY_SIZE;
        }
    }

    /**
     * Desenha o inimigo na tela.
     * @param g objeto Graphics para desenhar na tela
     */
    public void draw(Graphics g) {
        g.drawImage(Spritesheet.enemy[animation.getCurrentAnimation()], x, y, width, height, null);
    }

    /**
     * @param xEnemy posição atual do inimigo que foi selecionado para atirar no eixo X
     * @param yEnemy posição atual do inimigo que foi selecionado para atirar no eixo Y
     */
    @Override
    public void shotFiredByEnemy(int xEnemy, int yEnemy) {
        bullets.add(new Bullet(xEnemy, yEnemy, Owner.ENEMY)); // adiciona um novo tiro na lista de tiros
    }
}