package principal.modelo;

import principal.constants.Configuration;
import principal.enums.Direction;
import principal.enums.Owner;
import principal.items.DoubleShot;
import principal.observers.MovementObserver;
import principal.observers.ShotFiredByPlayerObserver;
import principal.visao.screens.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Player extends Rectangle implements MovementObserver, ShotFiredByPlayerObserver {
    private Direction currentDirection;

    private final List<Bullet> bullets;

    public List<Bullet> getBullets() {
        return bullets;
    }

    public Player(int x, int y, World world) {
        super(x, y, Configuration.PLAYER_SIZE, Configuration.PLAYER_SIZE);

        bullets = new ArrayList<>();

        world.addMovementObserver(this);
        world.addShotPlayerObserver(this);
    }

    public void handleMovement() {

        int speed = Configuration.PLAYER_SPEED;
        if(currentDirection == Direction.RIGHT){
            x += speed;
        } else if(currentDirection == Direction.LEFT){
            x -= speed;
        }

        keepPlayerWithinScreen();
    }

    private void keepPlayerWithinScreen(){
        if(x < 0){
            x = 0;
        }
        if(x + width > Configuration.WIDTH){
            x = Configuration.WIDTH - width;
        }
    }

    @Override
    public void updateMovement(Direction direction) {
        currentDirection = direction;

        handleMovement();
    }

    @Override
    public void shotFiredByPlayer() {
        bullets.add(new Bullet(x + 20, y + 40, Owner.PLAYER));
        if(DoubleShot.activated){
            bullets.add(new Bullet(x + 60, y + 40, Owner.PLAYER));
        }
    }
}
