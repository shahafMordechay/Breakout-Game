package game;

import collidables.Collidable;
import geometry.Point;

/**
 * Holds information about a collision with a collidable object.
 *
 * @author Shahaf Mordechay
 */
public class CollisionInfo {

    private Point collisionPoint;
    private Collidable collidable;

    /**
     * Constructs and initializes collision information about collision point
     * and the collided object.
     *
     * @param collisionPoint the collision point with the collidable.
     * @param collidable     the collidable object.
     */
    public CollisionInfo(Point collisionPoint, Collidable collidable) {
        this.collisionPoint = collisionPoint;
        this.collidable = collidable;
    }

    /**
     * The point at which the collision occurs.
     *
     * @return the point of collision.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * The collidable object involved in the collision.
     *
     * @return the collided object.
     */
    public Collidable collisionObject() {
        return this.collidable;
    }
}
