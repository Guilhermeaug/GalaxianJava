package main.views.screens;

import main.views.helpers.image.Spritesheet;

import javax.swing.*;
import java.awt.*;

/**
 * Janela para o menu principal.
 */
public class MenuScreen extends JFrame {

    public MenuScreen() {
        //Botão que inicia o jogo
        JButton startButton = new JButton("Começar o jogo");
        startButton.addActionListener(e -> {
            setVisible(false);
            dispose();
            new Game();
        });

        // Criando um painel para desenhar os comandos do jogo na tela, além de uma imagem.
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Font font = new Font("Arial", Font.BOLD, 20);
                g.setFont(font);
                g.setColor(Color.WHITE);

                g.drawImage(Spritesheet.menuImage, 0, 0, 500, 500, null);

                g.drawString("Use as setas do teclado para mover o personagem", 15, 150);
                g.drawString("P - Pausar", 15, 200);
                g.drawString("R - Reiniciar", 15, 250);
                g.drawString("Espaço - Atirar", 15, 300);
                g.drawString("Escape - Sair", 15, 350);
            }
        };

        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(500, 500));
        panel.add(startButton, BorderLayout.SOUTH);

        add(panel);
        pack();

        setTitle("Menu Screen");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}
