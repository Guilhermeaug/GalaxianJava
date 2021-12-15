package principal.visao.recursos;

import principal.constants.Configuration;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class Spritesheet {
    public static BufferedImage spritesheet;
    public static BufferedImage spritesheet2;
    public static BufferedImage plater_front;
    public static BufferedImage inimigo;
    public static Image backgroundImage;

    public static void initSpritsheet() {
        try {
            spritesheet2 = ImageIO.read(Objects.requireNonNull(Spritesheet.class.getResource("spritesheet.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        plater_front = Spritesheet.getSprite(0, 11, 16, 16);
        inimigo = Spritesheet.getSprite(57, 224, 16, 16);

        try {
            backgroundImage = Toolkit.getDefaultToolkit().getImage(new URL(Configuration.BACKGROUND_IMAGE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage getSprite(int x, int y, int w, int h) {
        return spritesheet2.getSubimage(x, y, w, h);
    }

}
