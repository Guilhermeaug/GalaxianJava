package main.views.helpers.image;

import main.constants.Configuration;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * Classe de utilidades para carregar as imagens do jogo e preencher os vetores de animação.
 */
public class Spritesheet {
    public static BufferedImage spritesheet;
    public static BufferedImage[] platerFront;
    public static BufferedImage[] enemy;
    public static Image backgroundImage;
    public static BufferedImage menuImage;
    public static BufferedImage spiderImage;
    public static BufferedImage glassesImage;

    public static void initSpritsheet() {
        try {
            spritesheet = ImageIO.read(Objects.requireNonNull(Spritesheet.class.getResource("/resources/images/spritesheet.png")));
            spiderImage = ImageIO.read(Objects.requireNonNull(Spritesheet.class.getResource("/resources/images/spider.png")));
            menuImage = ImageIO.read(Objects.requireNonNull(Spritesheet.class.getResource("/resources/images/galaxia.jpg")));
            glassesImage = ImageIO.read(Objects.requireNonNull(Spritesheet.class.getResource("/resources/images/glass.png")));
            backgroundImage = Toolkit.getDefaultToolkit().getImage(new URL(Configuration.BACKGROUND_IMAGE));
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Carregando as sprites do jogador
        platerFront = new BufferedImage[3];
        platerFront[0] = getSprite(0, 0, 16, 16);
        platerFront[1] = getSprite(16, 0, 16, 16);
        platerFront[2] = getSprite(32, 0, 16, 16);

        // Carregando as sprites dos inimigos
        enemy = new BufferedImage[2];
        enemy[0] = getSprite(48, 64, 16, 16);
        enemy[1] = getSprite(64, 64, 16, 16);
    }

    public static BufferedImage getSprite(int x, int y, int w, int h) {
        return spritesheet.getSubimage(x, y, w, h);
    }
}