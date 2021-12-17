package principal.items;

import principal.models.Enemy;

import java.util.Timer;
import java.util.TimerTask;

public class TeiaAranha extends Item {

    public TeiaAranha(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void ativaItem() {
        if (!Item.dropControl) {
            Item.dropControl = true;
            Enemy.speedModifier = 0.5;

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Item.dropControl = false;
                    Enemy.speedModifier = 1;
                }
            }, 5000);
        }
    }
}
