package main.items;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Item que permite a nave disparar dois tiros ao mesmo tempo.
 */
public class DoubleShot extends Item {
    public static boolean activated = false;

    public DoubleShot(int id, int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void ativaItem() {
        if (!Item.dropControl) {
            Item.dropControl = true; // impede que dois itens sejam ativados ao mesmo tempo
            activated = true; // indica que o item está ativo. Usado na classe Player.

            // inicializa um timer para desativar o efeito do item e também permitir que outros itens sejam ativados
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