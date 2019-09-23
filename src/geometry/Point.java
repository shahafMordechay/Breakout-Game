package geometry;

/**
 * A geometry.Point representing a location in (x,y) coordinate space.
 *
 * @author Shahaf Mordechay
 */
public class Point {

    /**
     * The X coordinate of this point.
     */
    private double x;

    /**
     * The Y coordinate of this point.
     */
    private double y;

    /**
     * Constructs and initializes a geometry.Point at the specified (x,y)
     * location in the coordinate space.
     *
     * @param x the X coordinate of this point.
     * @param y the Y coordinate of this point.
     */
    public Point(double x, double y) {
        this.x = Math.round(x);
        this.y = Math.round(y);
    }

    /**
     * Calculates the distance of this point from another point.
     *
     * @param other the other point object.
     * @return the distance between the 2 point.
     */
    public double distance(Point other) {
        double dis =  Math.round(Math.sqrt(((this.x - other.x) * (this.x - other.x))
                        + ((this.y - other.y) * (this.y - other.y))));
        return dis;
    }

    /**
     * Determines if 2 point are equal.
     *
     * @param other the other point object.
     * @return true if the 2 point are equal, false otherwise.
     */
    public boolean equals(Point other) {
        return (other != null)
                && (this.x == other.x) && (this.y == other.y);
    }

    /**
     * returns the X coordinate of this point.
     *
     * @return this point X coordinate.
     */
    public double getX() {
        return this.x;
    }

    /**
     * returns the Y coordinate of this point.
     *
     * @return this point Y coordinate.
     */
    public double getY() {
        return this.y;
    }
}
