package main.items;

import main.constants.Configuration;
import main.views.helpers.image.Spritesheet;

import java.awt.*;

/**
 * Classe abstrata que representa os detalhes de um Item.
 */
public abstract class Item extends Rectangle {
    public static boolean dropControl; // controla se o item está impedido de ser coletado

    public Item(int x, int y, int width, int height) {
        super(x, y, width, height);
        dropControl = false;
    }

    /**
     * Lida com a funcionalidade de cada Item.
     */
    public abstract void ativaItem();

    /**
     * Atualiza a posição do Item.
     * Os itens são desenhados inicialmente na posição do inimigo abatido e são movidos para baixo aos poucos.
     */
    public void update() {
        int speed = Configuration.ITEM_SPEED;

        // evita que os itens ultrapassem o chão
        if (y > 580 - height) y = 580 + height;
        else y += speed;
    }

    /**
     * Desenha o Item na tela.
     * @param g o objeto Graphics que será utilizado para desenhar o Item.
     * @param item o Item que será desenhado.
     */
    public void draw(Graphics g, Item item) {
        if (item instanceof TeiaAranha) { // se o item for uma teia de aranha
            g.drawImage(Spritesheet.spiderImage, x, y, width, height, null);
        } else { // se o item for um tiro duplo
            g.drawImage(Spritesheet.glassesImage, x, y, width, height, null);
        }
    }
}