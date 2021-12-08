package JogoGalaxian.principal.visao;

import JogoGalaxian.principal.visao.recursos.Spritesheet;

import javax.swing.*;

public class Game extends JFrame {
    public Game() {
        setTitle("Galaxian Game");

        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        new Spritesheet();

        add(new World());

        setVisible(true);
        pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game::new);
    }

}
