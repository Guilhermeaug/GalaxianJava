package principal.items;

import java.util.Timer;
import java.util.TimerTask;

public class DoubleShot extends Item {
    public static boolean activated = false;

    public DoubleShot(int id, int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void ativaItem() {
        if (!Item.dropControl) {
            Item.dropControl = true;
            activated = true;

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Item.dropControl = false;
                    activated = false;
                }
            }, 5000);
        }
    }
}
