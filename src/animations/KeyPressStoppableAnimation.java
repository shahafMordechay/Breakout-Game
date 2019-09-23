package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * An animation decorator that tells when to stop the animation
 * running.
 *
 * @author Shahaf Mordechay
 */
public class KeyPressStoppableAnimation implements Animation {

    // members
    private KeyboardSensor keyboard;
    private String key;
    private Animation animation;
    private boolean isAlreadyPressed;
    private boolean stop;

    /**
     * Constructs a new game stoppable animation.
     * Display the given animation until a given key is pressed.
     *
     * @param sensor    the player keyboard.
     * @param key       the key that tells to stop the animation running.
     * @param animation the animation to run.
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor,
                                        String key, Animation animation) {

        this.keyboard = sensor;
        this.key = key;
        this.animation = animation;
        this.isAlreadyPressed = true;
        this.stop = false;
    }

    /**
     * Draw a specific game frame.
     *
     * @param d the draw surface to draw on.
     * @param dt the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {
        this.animation.doOneFrame(d, dt);

        if (!this.keyboard.isPressed(this.key)) {
            this.isAlreadyPressed = false;
        }

        if (this.keyboard.isPressed(this.key) && !this.isAlreadyPressed) {
            this.stop = true;
        }
    }

    /**
     * Tells if the animations should stop.
     *
     * @return the stopping condition state.
     */
    public boolean shouldStop() {
        return this.stop;
    }
}
