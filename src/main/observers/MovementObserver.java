package main.observers;

import main.enums.Direction;

/**
 * Interface para indicar o movimento do jogador.
 */
public interface MovementObserver {
    void updateMovement(Direction direction);
}