package principal.items;

import principal.constants.Configuration;
import principal.views.helpers.image.Spritesheet;

import java.awt.*;

public abstract class Item extends Rectangle {
    public static boolean dropControl;

    public Item(int x, int y, int width, int height) {
        super(x, y, width, height);
        dropControl = false;
    }

    public abstract void ativaItem();

    public void update() {
        int speed = Configuration.ITEM_SPEED;

        if (y > 580 - height) y = 580 + height;
        else y += speed;
    }

    public void draw(Graphics g, Item item) {
        if(item instanceof TeiaAranha) {
            System.out.println("TeiaAranha");
            g.drawImage(Spritesheet.spiderImage, x, y, width, height, null);
        } else {
            System.out.println("Outro");
            g.drawImage(Spritesheet.glassesImage, x, y, width, height, null);
        }
    }
}
