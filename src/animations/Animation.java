package animations;

import biuoop.DrawSurface;

/**
 * A specific animations drawing and stopping condition.
 *
 * @author Shahaf Mordechay
 */
public interface Animation {

    /**
     * Draw a specific game frame.
     *
     * @param d the draw surface to draw on.
     * @param dt the amount of seconds passed since the last call.
     */
    void doOneFrame(DrawSurface d, double dt);

    /**
     * Tells if the animations should stop.
     *
     * @return the stopping condition state.
     */
    boolean shouldStop();
}
