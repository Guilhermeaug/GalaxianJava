package main.items;

import main.models.Enemy;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Item que diminui a velocidade dos inimigos
 */
public class TeiaAranha extends Item {

    public TeiaAranha(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void ativaItem() {
        if (!Item.dropControl) { // caso seja possível ativar o efeito do item
            Item.dropControl = true; // impede outros itens de serem ativados
            Enemy.speedModifier = 0.5; // diminui a velocidade dos inimigos pela metade

            // inicializa um timer para desativar o efeito do item e também permitir que outros itens sejam ativados
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