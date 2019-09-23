package collidables;

import geometry.Point;
import geometry.Rectangle;
import other.Velocity;
import sprites.Ball;

/**
 * An interface of objects that can be collided.
 *
 * @author Shahaf Mordechay
 */
public interface Collidable {

    /**
     * Return the "collision shape" of the object.
     *
     * @return the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with
     * a given velocity.
     * The return is the new velocity expected after the hit (based on
     * the force the object inflicted on us).
     *
     * @param collisionPoint    collision point with object.
     * @param currentVelocity   object current velocity.
     * @param hitter            the object that hit the collidable.
     * @return the new velocity expected after the hit.
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
