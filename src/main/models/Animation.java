package main.models;

/**
 * Essa classe representa uma animação. A cada frame, a animação é atualizada.
 */
public class Animation {
    private int currentFrames;
    private final int targetFrames;
    private int currentAnimation;
    private final int spritesheetLength;

    public int getCurrentAnimation() {
        return currentAnimation;
    }

    public Animation(int targetFrames, int spritesheetLength) {
        this.targetFrames = targetFrames;
        this.spritesheetLength = spritesheetLength;

        currentFrames = 0;
        currentAnimation = 0;
    }

    public void updateAnimationFrames() {
        currentFrames++;
        if (currentFrames == targetFrames) {
            currentFrames = 0;
            currentAnimation++;
            if (currentAnimation == spritesheetLength) {
                currentAnimation = 0;
            }
        }
    }
}