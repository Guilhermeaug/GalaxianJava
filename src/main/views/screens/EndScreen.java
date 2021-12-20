package main.views.screens;

import javax.swing.*;
import java.awt.*;

/**
 * Tela final do jogo.
 */
public class EndScreen extends JFrame {

    public EndScreen(boolean victory) {
        // Botões de reinciar e sair
        JButton restartButton = new JButton("Restart Game");
        JButton exitButton = new JButton("Exit Game");

        restartButton.addActionListener(_e -> restartGame());
        exitButton.addActionListener(_e -> endGame());

        JPanel panel = new JPanel(){
            /**
             * Desenhando as informações finais do jogo.
             * @param g Graphics
             */
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Font font = new Font("Arial", Font.BOLD, 20);
                g.setFont(font);
                g.setColor(Color.WHITE);

                if(victory) {
                    g.drawString("THE CHAMPIONS", 100, 100);
                } else {
                    g.drawString("GAME OVER", 120, 100);
                }
            }
        };

        // Criando um painel separado para agrupar os dois botões
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(restartButton);
        buttonPanel.add(exitButton);

        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(400, 200));
        panel.setBackground(Color.BLACK);
        panel.add(buttonPanel, BorderLayout.SOUTH); // Adicionando os botões na parte de baixo da tela

        add(panel);
        pack();

        setTitle("GAME OVER");
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * Reinicia o jogo
     */
    void restartGame(){
        setVisible(false);
        dispose();
        new Game();
    }

    /**
     * Fecha o jogo por completo
     */
    void endGame() {
        setVisible(false);
        dispose();
        System.exit(0);
    }
}