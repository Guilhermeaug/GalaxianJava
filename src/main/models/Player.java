package main.models;

import main.constants.Configuration;
import main.enums.Direction;
import main.enums.Owner;
import main.items.DoubleShot;
import main.observers.MovementObserver;
import main.observers.ShotFiredByPlayerObserver;
import main.views.helpers.image.Spritesheet;
import main.views.helpers.sound.SoundPlayer;
import main.views.screens.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um jogador.
 * Implementa as interfaces MovementObserver e ShotFiredByPlayerObserver.
 * Para indicar quando é preciso realizar um movimento ou um disparo.
 */
public class Player extends Rectangle implements MovementObserver, ShotFiredByPlayerObserver {
    private Direction currentDirection; // Direção atual do jogador

    private final List<Bullet> bullets; // Lista de tiros do jogador

    private final SoundPlayer shotPlayer; // Som de tiro do jogador

    public Animation animation; // Animação do jogador

    public List<Bullet> getBullets() {
        return bullets;
    }

    public Player(int x, int y, World world) {
        super(x, y, Configuration.PLAYER_SIZE, Configuration.PLAYER_SIZE); // inicializa o Rectangle

        bullets = new ArrayList<>();
        shotPlayer = new SoundPlayer("/src/resources/sounds/shot.wav"); // indica o som do disparo

        animation = new Animation(Configuration.PLAYER_TARGET_FRAMES, Spritesheet.platerFront.length);

        // Adiciona os observadores de movimento e disparo. Indicando à instância de world que é preciso notificar das mudanças.
        world.addMovementObserver(this);
        world.addShotPlayerObserver(this);
    }

    /**
     * Cuida da movimentação do jogador.
     */
    public void handleMovement() {
        int speed = Configuration.PLAYER_SPEED;

        if (currentDirection == Direction.RIGHT) {
            x += speed;
        } else if (currentDirection == Direction.LEFT) {
            x -= speed;
        }

        keepPlayerWithinScreen();
    }

    /**
     * Usado para manter o jogador dentro da tela.
     */
    private void keepPlayerWithinScreen() {
        if (x < 0) {
            x = 0;
        }

        if (x + width > Configuration.WIDTH) {
            x = Configuration.WIDTH - width;
        }
    }

    /**
     * Desenha o jogador na posição atual na tela.
     * @param g objeto Graphics que é usado para desenhar na tela.
     */
    public void draw(Graphics g) {
        g.drawImage(Spritesheet.platerFront[animation.getCurrentAnimation()], x, y, width, height, null);
    }

    /**
     * Evento disparado. É preciso movimentar o jogador.
     * @param direction direção a ser movimentado.
     */
    @Override
    public void updateMovement(Direction direction) {
        currentDirection = direction;
        handleMovement();
    }

    /**
     * Evento de disparo. Indica que é preciso instanciar uma nova Bullet.
     */
    @Override
    public void shotFiredByPlayer() {
        bullets.add(new Bullet(x + 20, y + 40, Owner.PLAYER));
        if (DoubleShot.activated) { // Caso o item de tiro duplo esteja ativado, adiciona uma nova Bullet.
            bullets.add(new Bullet(x + 60, y + 40, Owner.PLAYER));
        }
        shotPlayer.play(); // Toca o som de tiro.
    }
}