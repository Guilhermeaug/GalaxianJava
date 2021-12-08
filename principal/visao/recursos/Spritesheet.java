package JogoGalaxian.principal.visao.recursos;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Spritesheet {
    public static BufferedImage spritesheet;
    public static BufferedImage spritesheet2;
    public static BufferedImage plater_front;
    public static BufferedImage inimigo;

    public Spritesheet() {
        try {
            spritesheet2 = ImageIO.read(Objects.requireNonNull(getClass().getResource("spritesheet.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        plater_front = Spritesheet.getSprite(0, 11, 16, 16);
        inimigo = Spritesheet.getSprite(57, 224, 16, 16);
    }

    public static BufferedImage getSprite(int x, int y, int w, int h) {
        return spritesheet2.getSubimage(x, y, w, h);
    }

}
