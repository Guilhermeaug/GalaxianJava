package principal.visao;

import principal.visao.recursos.Spritesheet;
import principal.visao.screens.World;

import javax.swing.*;

public class Game extends JFrame {
    public Game() {
        setTitle("Galaxian Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        Spritesheet.initSpritsheet();

        add(new World(this));
        pack();

        setVisible(true);
    }

    public void beginGame(){
        remove(getContentPane());
        add(new World(this));
        pack();
        setVisible(true);
    }
}
