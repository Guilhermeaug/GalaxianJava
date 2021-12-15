package principal.items;

import principal.constants.Configuration;

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
}
