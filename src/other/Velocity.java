package other;

import geometry.Point;

/**
 * A velocity of an object represented by dx and dy.
 *
 * @author Shahaf Mordechay
 */
public class Velocity {

    // members
    private double dx;
    private double dy;

    /**
     * Constructs and initializes the other.Velocity with specified
     * horizontal (dx) and vertical (dy) motion.
     *
     * @param dx the wanted horizontal motion.
     * @param dy the wanted vertical motion.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Returns this velocity dx.
     *
     * @return the dx of this velocity.
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * Returns this velocity dy.
     *
     * @return the dy of this velocity
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Return the angle of the direction, according to dx, dy.
     *
     * @return the angle of the direction.
     */
    public double getAngle() {
        return (180 / Math.PI) * Math.atan(-dx / dy);
    }

    /**
     * Return the speed, according to dx, dy and angle.
     *
     * @return the speed.
     */
    public double getSpeed() {
        return dx / Math.sin(Math.atan(-dx / dy));
    }

    /**
     * sets the dx and dy of this velocity by given angle and speed.
     *
     * @param angle the direction of the object.
     * @param speed the speed of the object.
     * @return the wanted velocity.
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {

        if (angle == 0) {
            angle = 360;
        }

        double dx = -Math.sin(Math.toRadians(angle)) * speed;
        double dy = Math.cos(Math.toRadians(angle)) * speed;
        return new Velocity(dx, dy);
    }

    /**
     * Takes the given point position (x,y) and returns a new point
     * with position (x + dx, y + dy). hence moves the object.
     *
     * @param point the point to move.
     * @return new point (x + dx, y + dy)
     */
    public Point applyToPoint(Point point) {
        return new Point(point.getX() + this.dx, point.getY() + this.dy);
    }
}
