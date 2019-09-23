package collidables;

import game.HitNotifier;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import other.Fill;
import sprites.Ball;
import sprites.Sprite;
import other.Velocity;
import game.GameLevel;
import hitlisteners.HitListener;

import biuoop.DrawSurface;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * A collidable rectangle that returns a new velocity when collided.
 *
 * @author Shahaf Mordechay
 */
public class Block implements Collidable, Sprite, HitNotifier {

    private static final Integer INDESTRUCTIBLE = -1;
    private static final Integer PADDLE_LIFE = -2;

    private Rectangle rectangle;
    private Fill fill;
    private Integer hitPoints;
    private Color stroke;
    private Line upperEdge;
    private Line lowerEdge;
    private Line leftEdge;
    private Line rightEdge;
    private List<HitListener> hitListeners;
    private Map<Integer, Fill> fillsMap;

    /**
     * Constructs and initializes a block in a specified size,
     * location(rectangle location and size) and color.
     *
     * @param rectangle this block location and size.
     * @param fill     this block filling.
     * @param hitPoints number of times this block can be hit.
     */
    public Block(Rectangle rectangle, Fill fill, Integer hitPoints) {
        this.rectangle = rectangle;
        this.fill = fill;
        this.hitPoints = hitPoints;
        this.stroke = null;

        this.upperEdge = new Line(this.rectangle.getUpperLeft(),
                                 this.rectangle.getUpperRight());

        this.lowerEdge = new Line(this.rectangle.getLowerLeft(),
                                 this.rectangle.getLowerRight());

        this.leftEdge = new Line(this.rectangle.getUpperLeft(),
                                this.rectangle.getLowerLeft());

        this.rightEdge = new Line(this.rectangle.getUpperRight(),
                                 this.rectangle.getLowerRight());

        this.hitListeners = new ArrayList<>();
        this.fillsMap = new TreeMap<>();
    }

    /**
     * Constructs and initializes an indestructible block in a specified size,
     * location(rectangle location and size) and color.
     *
     * @param rectangle this block location and size.
     * @param color     this block color.
     */
    public Block(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.fill = new Fill(color);
        this.hitPoints = INDESTRUCTIBLE;
        this.stroke = null;

        this.upperEdge = new Line(this.rectangle.getUpperLeft(),
                this.rectangle.getUpperRight());

        this.lowerEdge = new Line(this.rectangle.getLowerLeft(),
                this.rectangle.getLowerRight());

        this.leftEdge = new Line(this.rectangle.getUpperLeft(),
                this.rectangle.getLowerLeft());

        this.rightEdge = new Line(this.rectangle.getUpperRight(),
                this.rectangle.getLowerRight());

        this.hitListeners = new ArrayList<>();
        this.fillsMap = new TreeMap<>();
    }

    /**
     * Return the shape of this block.
     *
     * @return the shape of this block.
     */
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }

    /**
     * Return the color of this block.
     *
     * @return the color of this block.
     */
    public Fill getFill() {
        return this.fill;
    }

    /**
     * Return the upper edge of this block.
     *
     * @return the upper edge of this block.
     */
    public Line getUpperEdge() {
        return this.upperEdge;
    }

    /**
     * Return the lower edge of this block.
     *
     * @return the lower edge of this block.
     */
    public Line getLowerEdge() {
        return this.lowerEdge;
    }

    /**
     * Return the left edge of this block.
     *
     * @return the left edge of this block.
     */
    public Line getLeftEdge() {
        return this.leftEdge;
    }

    /**
     * Return the right edge of this block.
     *
     * @return the right edge of this block.
     */
    public Line getRightEdge() {
        return this.rightEdge;
    }

    /**
     * Return the upper left point of this block rectangle.
     *
     * @return upper left point of this block rectangle.
     */
    public Point getUpperLeft() {
        return this.getCollisionRectangle().getUpperLeft();
    }

    /**
     * Return the lower right point of this block rectangle.
     *
     * @return lower right point of this block rectangle.
     */
    public Point getLowerRight() {
        return this.getCollisionRectangle().getLowerRight();
    }

    /**
     * Return the remaining hit points of this block.
     *
     * @return the remaining hit points of this block.
     */
    public Integer getHitPoints() {
        return this.hitPoints;
    }

    /**
     * Set this block stroke color.
     *
     * @param color this block stroke color.
     */
    public void setStroke(Color color) {
        this.stroke = color;
    }

    /**
     * Notify all of this block hit listeners on a hit event.
     *
     * @param hitter the object that hit this block.
     */
    private void notifyHit(Ball hitter) {

        // Make a copy of the hitlisteners list before iterating over them
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);

        // Notify all listeners about a hit event
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * Notify the object that we collided with it, at collisionPoint,
     * with a given velocity.
     * The return is the new velocity expected after the hit (based on
     * the force the object inflicted on this block).
     *
     * @param collisionPoint    collision point with object.
     * @param currentVelocity   object current velocity.
     * @param hitter            the object that hit this block.
     * @return the new velocity expected after the hit.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double x = collisionPoint.getX();
        double y = collisionPoint.getY();
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();

        // collision with upper or lower edge
        if ((upperEdge.isPointInRange(x, y) && dy > 0)
             || ((lowerEdge.isPointInRange(x, y) && dy < 0) && (hitPoints != PADDLE_LIFE))) {

            // negate velocity dy
            currentVelocity = new Velocity(dx, -dy);
        }

        // collision with left or right edge
        if ((leftEdge.isPointInRange(x, y) && dx > 0)
            || (rightEdge.isPointInRange(x, y) && dx < 0)) {

            // negate velocity dx
            currentVelocity = new Velocity(-dx, dy);
        }

        // reduce 1 hitPoints for every hit
        if (this.hitPoints > 0) {
            this.hitPoints -= 1;
        }

        this.notifyHit(hitter);

        return currentVelocity;
    }

    /**
     * Draw the block on a given surface.
     *
     * @param d the surface to draw the block on.
     */
    public void drawOn(DrawSurface d) {

        // draw rectangle
        int xCoordinate = (int) this.rectangle.getUpperLeft().getX();
        int yCoordinate = (int) this.rectangle.getUpperLeft().getY();
        int width = (int) this.rectangle.getWidth();
        int height = (int) this.rectangle.getHeight();

        // draw fill-k
        if (this.fillsMap.containsKey(this.hitPoints)) {
            // draw fill-k image
            if (this.fillsMap.get(this.hitPoints).getColor() == null) {
                d.drawImage(xCoordinate, yCoordinate,
                        this.fillsMap.get(this.hitPoints).getImage());
                // draw fill-k color
            } else {
                d.setColor(this.fillsMap.get(this.hitPoints).getColor());
                d.fillRectangle(xCoordinate, yCoordinate, width, height);
            }
        // draw fill image
        } else if (this.fill.getColor() == null) {
            d.drawImage(xCoordinate, yCoordinate, this.fill.getImage());
        // draw fill color
        } else {
            d.setColor(this.fill.getColor());
            d.fillRectangle(xCoordinate, yCoordinate, width, height);
        }

        if (this.stroke != null && this.hitPoints != INDESTRUCTIBLE) {
            d.setColor(this.stroke);
            d.drawRectangle(xCoordinate, yCoordinate, width, height);
        }
    }

    /**
     * currently do nothing(as instructed).
     *
     * @param dt the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {

    }

    /**
     * Add this block to game sprites and environment.
     *
     * @param g the game to add the block to.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Remove this block from game sprites and environment.
     *
     * @param gameLevel the game to remove the block from.
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
        gameLevel.removeCollidable(this);
    }

    /**
     * Add given hit listener to this hitlisteners list.
     *
     * @param hl the listener to add to hitlisteners list.
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * Remove given hit listener from this hitlisteners list.
     *
     * @param hl the listener to remove from hitlisteners list.
     */
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * Add a given map of fill-k to this block fills map.
     *
     * @param fillingMap a map of fills.
     */
    public void addFills(Map<Integer, Fill> fillingMap) {
        this.fillsMap.putAll(fillingMap);
    }
}
