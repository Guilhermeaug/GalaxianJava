package principal.visao.screens;

import principal.visao.Game;

import javax.swing.*;
import java.awt.*;

public class EndScreen extends JFrame {

    public EndScreen(boolean victory) {
        JPanel labelPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();
        JButton restartButton = new JButton("Restart Game");
        JButton exitButton = new JButton("Exit Game");
        JLabel imgLabel = new JLabel();

        if (victory) {
            imgLabel.setText("THE CHAMPIONS");
        } else {
            imgLabel.setText("GAME OVER");
        }

        restartButton.addActionListener(_e -> restartGame());
        exitButton.addActionListener(_e -> endGame());

        labelPanel.add(imgLabel, SwingUtilities.CENTER);
        buttonsPanel.add(restartButton);
        buttonsPanel.add(exitButton);

        add(labelPanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);
        pack();

        setTitle("GAME OVER");
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    void restartGame(){
        dispose();
        new Game();
    }

    void endGame() {
        dispose();
        System.exit(0);
    }
}
