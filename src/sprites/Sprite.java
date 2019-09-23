package sprites;

import biuoop.DrawSurface;

/**
 * an interface of drawable objects.
 *
 * @author Shahaf Mordechay
 */
public interface Sprite {
    /**
     * draw the sprite to the screen.
     *
     * @param d the screen to draw on.
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     *
     * @param dt the amount of seconds passed since the last call.
     */
    void timePassed(double dt);
}
