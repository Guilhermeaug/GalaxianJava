package principal.views.screens;

import principal.views.helpers.sound.Player;

import javax.swing.*;

public class Game extends JFrame {
    private final Player backgroundMusic;

    public Game() {
        backgroundMusic = new Player("/src/resources/sounds/starwars.wav");
        backgroundMusic.play();
        backgroundMusic.loop();

        setTitle("Galaxian Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        add(new World(this));
        pack();

        setVisible(true);
    }

    public Player getBackgroundMusic() {
        return backgroundMusic;
    }
}
