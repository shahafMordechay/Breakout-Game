package sprites;

import biuoop.DrawSurface;
import geometry.Line;
import geometry.Point;
import other.Velocity;
import game.GameEnvironment;
import game.CollisionInfo;
import game.GameLevel;

import java.awt.Color;

/**
 * A circle moving in square with coordinates,
 * Bounces off the walls upon collision.
 *
 * @author Shahaf Mordechay
 */
public class Ball implements Sprite {

    private Point center;
    private int r;
    private java.awt.Color color;
    private Velocity velocity;
    private GameEnvironment environment;

    /**
     * Constructs and initializes a ball with a specified
     * center geometry. Point, radius and color.
     *
     * @param center  the center point of this ball.
     * @param r       the radius of this ball.
     * @param color   the color of this ball.
     * @param environment collection of sprites.collidables.
     */
    public Ball(Point center, int r, Color color, GameEnvironment environment) {
        this.center = center;
        this.r = r;
        this.color = color;
        this.environment = environment;
    }

    /**
     * Constructs and initializes a ball with a specified
     * center point(represented by X,Y), radius and color.
     *
     * @param x       the X coordinate of the center point.
     * @param y       the Y coordinate of the center point.
     * @param r       the radius of this ball.
     * @param color   the color of this ball.
     * @param environment collection of sprites.collidables.
     */
    public Ball(int x, int y, int r, Color color, GameEnvironment environment) {
        this.center = new Point(x, y);
        this.r = r;
        this.color = color;
        this.environment = environment;
    }

    /**
     * Sets the velocity of this ball.
     *
     * @param v the wanted velocity of this ball.
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * Sets the velocity of this ball by dx and dy.
     *
     * @param dx the wanted velocity dx of this ball.
     * @param dy the wanted velocity dy of this ball.
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * Returns the radius of this ball.
     *
     * @return this ball radius.
     */
    public int getSize() {
        return this.r;
    }

    /**
     * Returns the color of this ball.
     *
     * @return this ball color.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Draws this ball on the draw surface.
     *
     * @param d the surface for the ball to be drawn on.
     */
    public void drawOn(DrawSurface d) {
        int xCoordinate = (int) this.center.getX();
        int yCoordinate = (int) this.center.getY();

        d.setColor(this.color);
        d.fillCircle(xCoordinate, yCoordinate, this.r);
        d.setColor(Color.black);
        d.drawCircle(xCoordinate, yCoordinate, this.r);

    }

    /**
     * Moving the ball by changing this ball center coordinates
     * by adding the velocity dx, dy.
     * negates the dx, dy in case of collision.
     *
     * @param dt the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {
        double velocityDx = this.velocity.getDx() * dt;
        double velocityDy = this.velocity.getDy() * dt;

        // velocity not set
        if (velocity == null) {
            this.velocity = new Velocity(1, 1);
        }

        int dxR = this.r;
        if (this.velocity.getDx() < 0) {
            dxR = -dxR;
        }

        int dyR = this.r;
        if (this.velocity.getDy() < 0) {
            dyR = -dyR;
        }

        // trajectory end point
        double trajPointX = this.center.getX() + velocityDx + dxR;
        double trajPointY = this.center.getY() + velocityDy + dyR;
        Point trajectoryPoint = new Point(trajPointX, trajPointY);

        // trajectory line
        Line trajectory = new Line(this.center, trajectoryPoint);

        CollisionInfo collisionInfo = this.environment.getClosestCollision(trajectory);

        // no collision
        if (collisionInfo == null) {
            this.center = new Velocity(velocityDx, velocityDy).applyToPoint(this.center);

        // collision detected
        } else {
            // move until ball touches the collidable
            double dx = collisionInfo.collisionPoint().getX() - (this.center.getX() + dxR);
            double dy = collisionInfo.collisionPoint().getY() - (this.center.getY() + dyR);
            Velocity remainder = new Velocity(dx * dt, dy * dt);
            this.center = remainder.applyToPoint(this.center);

            // bounce to opposite direction
            this.velocity = collisionInfo.collisionObject().hit(this, collisionInfo.collisionPoint(), this.velocity);
        }
    }

    /**
     * Add this ball to game sprites.
     *
     * @param g the game to add this ball to.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * Remove this ball from game sprites.
     *
     * @param gameLevel the game to remove the ball from.
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
    }
}
