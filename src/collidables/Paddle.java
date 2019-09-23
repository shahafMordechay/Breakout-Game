package collidables;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import geometry.Point;
import geometry.Rectangle;
import sprites.Sprite;
import other.Velocity;
import game.GameLevel;
import sprites.Ball;

import java.awt.Color;

/**
 * A block moving left and right according to the user actions.
 *
 * @author Shahaf Mordechay
 */
public class Paddle implements Collidable, Sprite {

    private static final Integer INFINITE_LIFE = -1;
    private static final int NUM_OF_REGIONS = 5;

    // members
    private Block block;
    private KeyboardSensor keyboard;
    private double leftLimit;
    private double rightLimit;
    private int speed;

    /**
     * Constructs and initializes a paddle(rectangle) that moves
     * left and right.
     *
     * @param block      the paddle collidable.
     * @param keyboard   the keyboard to move by.
     * @param leftLimit  the limit to the left.
     * @param rightLimit the limit to the right.
     */
    public Paddle(Block block, KeyboardSensor keyboard, double leftLimit, double rightLimit) {
        this.block = block;
        this.keyboard = keyboard;
        this.leftLimit = leftLimit;
        this.rightLimit = rightLimit;
    }

    /**
     * Set the speed of this paddle.
     *
     * @param paddleSpeed the wanted speed of the paddle.
     */
    public void setSpeed(int paddleSpeed) {
        this.speed = paddleSpeed;
    }

    /**
     * move the paddle to the left.
     *
     * @param dt the amount of seconds passed since the last call.
     */
    public void moveLeft(double dt) {
        double x = block.getUpperLeft().getX() - this.speed * dt;

        if (leftLimit > x) {
            x = leftLimit;
        }

        double y = block.getUpperLeft().getY();
        block.getCollisionRectangle().setUpperLeft(x, y);
        block = new Block(block.getCollisionRectangle(), block.getFill(), INFINITE_LIFE);
    }

    /**
     * move the paddle to the right.
     *
     * @param dt the amount of seconds passed since the last call.
     */
    public void moveRight(double dt) {

        double x = block.getUpperLeft().getX() + this.speed * dt;

        if (rightLimit < x + this.block.getCollisionRectangle().getWidth()) {
            x = rightLimit - this.block.getCollisionRectangle().getWidth();
        }

        double y = block.getUpperLeft().getY();
        block.getCollisionRectangle().setUpperLeft(x, y);
        block = new Block(block.getCollisionRectangle(), block.getFill(), INFINITE_LIFE);
    }

    /**
     * check if left or right key is pressed and move accordingly.
     *
     * @param dt the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)
            && (leftLimit < this.block.getUpperLeft().getX())) {
            moveLeft(dt);
        } else if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)
                   && this.block.getLowerRight().getX() < rightLimit) {
            moveRight(dt);
        }
    }

    /**
     * Draw the paddle on a given surface.
     *
     * @param d the screen to draw on.
     */
    public void drawOn(DrawSurface d) {
        Rectangle thisRec = block.getCollisionRectangle();

        int xCoordinate = (int) thisRec.getUpperLeft().getX();
        int yCoordinate = (int) thisRec.getUpperLeft().getY();
        int width = (int) thisRec.getWidth();
        int height = (int) thisRec.getHeight();

        if (block.getFill().getColor() == null) {
            d.drawImage(xCoordinate, yCoordinate,
                    block.getFill().getImage());
        } else {
            d.setColor(block.getFill().getColor());
            d.fillRectangle(xCoordinate, yCoordinate, width, height);
            d.setColor(Color.black);
            d.drawRectangle(xCoordinate, yCoordinate, width, height);
        }
    }

    /**
     * Return the collidable block of this paddle.
     *
     * @return this paddle collidable block.
     */
    public Rectangle getCollisionRectangle() {
        return this.block.getCollisionRectangle();
    }

    /**
     * Return the new ball velocity angle according to the region
     * of the hit point on the paddle.
     *
     * @param collisionX collision point x coordinate.
     * @param angle      current angle of the ball.
     * @return the new ball angle.
     */
    private double angleByRegion(double collisionX, double angle) {

        // size of each region of the paddle
        double regionSize = block.getCollisionRectangle().getWidth()
                / NUM_OF_REGIONS;

        // the paddle upper left point
        double regionStart = block.getCollisionRectangle().getUpperLeft().getX();

        // return the needed angle by region
        for (int i = 0; i < NUM_OF_REGIONS; i++) {
            double min = i * regionSize + regionStart;
            double max = (i + 1) * regionSize + regionStart;
            if (min < collisionX && collisionX < max) {
                switch (i) {
                    case 0:
                        return 300;
                    case 1:
                        return 330;
                    case 2:
                        return 360 - angle;
                    case 3:
                        return 30;
                    case 4:
                        return 60;
                    default:
                        break;
                }
            }
        }

        return angle;
    }

    /**
     * Notify the object that we collided with it, at collisionPoint,
     * with a given velocity.
     * The return is the new velocity expected after the hit (based on
     * the region the object hit point on this block).
     *
     * @param collisionPoint    collision point with object.
     * @param currentVelocity   object current velocity.
     * @param hitter            the object that hit this block.
     *
     * @return the new velocity expected after the hit.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double x = collisionPoint.getX();
        double y = collisionPoint.getY();
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        double ballAngle = currentVelocity.getAngle();

        // collision with upper edge
        if (block.getUpperEdge().isPointInRange(x, y) && dy > 0) {
            ballAngle = angleByRegion(x, ballAngle);
            double ballSpeed = currentVelocity.getSpeed();
            currentVelocity = Velocity.fromAngleAndSpeed(ballAngle, ballSpeed);

        // collision with lower edge
        } else if (block.getLowerEdge().isPointInRange(x, y) && dy < 0) {
            currentVelocity = new Velocity(dx, -dy);
        }

        // collision with left or right edge
        if ((block.getLeftEdge().isPointInRange(x, y) && dx > 0)
            || (block.getRightEdge().isPointInRange(x, y) && dx < 0)) {

            // negate velocity dx
            currentVelocity = new Velocity(-dx, dy);
        }

        return currentVelocity;
    }

    /**
     * Add this paddle to the game.
     *
     * @param g the game to add the paddle to.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Remove this paddle from the game.
     *
     * @param g the game to remove the paddle from.
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
        g.removeCollidable(this);
    }
}
