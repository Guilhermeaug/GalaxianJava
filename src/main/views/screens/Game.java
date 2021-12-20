package main.views.screens;

import main.views.helpers.sound.SoundPlayer;

import javax.swing.*;

/**
 * Prepara a exibição da janela principal do jogo.
 */
public class Game extends JFrame {
    private final SoundPlayer backgroundMusic;

    public Game() {
        // Inicializando a música de fundo
        backgroundMusic = new SoundPlayer("/src/resources/sounds/starwars.wav");
        backgroundMusic.play();
        backgroundMusic.loop();

        // Configurações da janela
        setTitle("Galaxian Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        add(new World(this));
        pack();

        setVisible(true);
    }

    public SoundPlayer getBackgroundMusic() {
        return backgroundMusic;
    }
}