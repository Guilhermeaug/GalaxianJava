package principal.views.screens;

import principal.views.helpers.image.Spritesheet;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends JFrame {

    public SplashScreen() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(300, 240));

        JLabel imgLabel = new JLabel();
        imgLabel.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/resources/images/splash.png"))));
        panel.add(imgLabel);

        Spritesheet.initSpritsheet();

        add(panel);
        pack();

        setTitle("Splash Screen");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

        initGame();
    }

    void initGame() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Game();
                setVisible(false);
                dispose();
            }
        }, 3000);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SplashScreen::new);
    }
}
