package hitlisteners;

import sprites.Ball;
import collidables.Block;

/**
 * Notify the object on a hit event.
 *
 * @author Shahaf Mordechay
 */
public interface HitListener {

    /**
     * This method is called whenever the beingHit object is hit.
     * The hitter parameter is the Ball that's doing the hitting.
     *
     * @param beingHit the object that is notified on a hit event.
     * @param hitter   the object that hit the beingHit object.
     */
    void hitEvent(Block beingHit, Ball hitter);
}
