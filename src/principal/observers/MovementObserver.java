package principal.observers;

import principal.enums.Direction;

public interface MovementObserver {
    void updateMovement(Direction direction);
}
