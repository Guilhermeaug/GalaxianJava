package main.views.screens;

import main.views.helpers.image.Spritesheet;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Porta de entrada do jogo. Exibe a tela inicial de splash.
 */
public class SplashScreen extends JFrame {

    public SplashScreen() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 240));

        JLabel imgLabel = new JLabel();
        imgLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/splash.png"))));
        panel.add(imgLabel);

        Spritesheet.initSpritsheet(); // Inicializa as imagens a serem utilizadas durante o jogo.

        add(panel);
        pack();

        setTitle("Splash Screen");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        initGame();
    }

    /**
     * Após 3 segundos inicia a menu do jogo.
     */
    void initGame() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new MenuScreen();
                setVisible(false);
                dispose();
            }
        }, 3000);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SplashScreen::new); // Thread de execução do Swing
    }
}