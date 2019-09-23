package game;

import collidables.Collidable;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * List of all collidable objects in game.
 *
 * @author Shahaf Mordechay
 */
public class GameEnvironment {

    private List<Collidable> collidableList;

    /**
     * Constructs and initializes a list that will hold all of the
     * collidable objects in this environment.
     */
    public GameEnvironment() {
        collidableList = new LinkedList<>();
    }

    /**
     * Return a list of all sprites.collidables objects in the environment.
     *
     * @return a list of sprites.collidables.
     */
    public List<Collidable> getCollidableList() {
        return this.collidableList;
    }

    /**
     * Add the given collidable to the environment(collidableList).
     *
     * @param c collidable to add to environment.
     */
    public void addCollidable(Collidable c) {
        this.collidableList.add(c);
    }

    /**
     * Remove the given collidable from the environment(collidableList).
     *
     * @param c collidable to remove from environment.
     */
    public void removeCollidable(Collidable c) {
        this.collidableList.remove(c);
    }

    /**
     * Assume an object moving from line.getStart() to line.getEnd().
     * If this object will not collide with any of the sprites.collidables
     * in this collection, return null. Else, return the information
     * about the closest collision that is going to occur.
     *
     * @param trajectory the line that signifies the route.
     * @return           closest collision information.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {

        // intersected sprites.collidables with given line
        List<Collidable> interList = new ArrayList<>();

        // add intersected sprites.collidables to interList
        for (Collidable collidable : collidableList) {
            // current [i] collidable
            Rectangle currRec = collidable.getCollisionRectangle();

            Point point = trajectory.closestIntersectionToStartOfLine(currRec);

            // if intersected
            if (point != null) {
                interList.add(collidable);
            }
        }

        // no intersected sprites.collidables with given line
        if (interList.isEmpty()) {
            return null;
        }

        // given line start point
        Point start = trajectory.getStart();

        // closest collidable and point to start of line
        Rectangle firstRec = interList.get(0).getCollisionRectangle();
        Point closestPoint = trajectory.closestIntersectionToStartOfLine(firstRec);
        Collidable closestCol = interList.get(0);

        /* check if there is any closer intersection point with collidable
         * to the start of given line.
         * if so apply it to closestPoint and closestCol values*/
        for (int i = 1; i < interList.size(); i++) {
            Rectangle currRec = interList.get(i).getCollisionRectangle();
            Point currPoint = trajectory.closestIntersectionToStartOfLine(currRec);

            if (currPoint.distance(start) < closestPoint.distance(start)) {
                closestPoint = currPoint;
                closestCol = interList.get(i);
            }
        }

        return new CollisionInfo(closestPoint, closestCol);
    }
}
