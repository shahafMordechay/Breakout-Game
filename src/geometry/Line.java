package geometry;

import java.util.List;

/**
 * A 2d line section from one point to another.
 *
 * @author Shahaf Mordechay
 */
public class Line {

    // members
    private Point start;
    private Point end;

    /**
     * Constructs and initializes a geometry.Line section with specified
     * getStart and getEnd points.
     *
     * @param start the getStart point of this line section.
     * @param end   the getEnd point of this line section.
     */
    public Line(Point start, Point end) {
        this.start = new Point(start.getX(), start.getY());
        this.end = new Point(end.getX(), end.getY());
    }

    /**
     * Constructs and initializes a line section with specified
     * getStart (represented by x1, y1) and
     * getEnd (represented by x1, y1) points.
     *
     * @param x1 getStart point x coordinate.
     * @param y1 getStart point y coordinate.
     * @param x2 getEnd point x coordinate.
     * @param y2 getEnd point y coordinate.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Returns the length of this line section.
     *
     * @return this line length.
     */
    public double length() {
        return Math.sqrt(((this.start.getX() - this.end.getX()) * (this.start.getX() - this.end.getX()))
                + (this.start.getY() - this.end.getY()) * (this.start.getY() - this.end.getY()));
    }

    /**
     * Calculates the middle point of this line section.
     *
     * @return this line middle point.
     */
    public Point middle() {
        double midX = (this.start.getX() + this.end.getX()) / 2;
        double midY = (this.start.getY() + this.end.getY()) / 2;

        return new Point(midX, midY);
    }

    /**
     * Returns the getStart point of this line section.
     *
     * @return this line getStart point.
     */
    public Point getStart() {
        return this.start;
    }

    /**
     * Returns the getEnd point of this line section.
     *
     * @return this line getEnd point.
     */
    public Point getEnd() {
        return this.end;
    }

    /**
     * States the direction route of 3 points, from a to b to c.
     *
     * @param a the getStart point.
     * @param b the second point.
     * @param c the last point.
     * @return 1 if the route is clockwise, -1 if counterclockwise,
     *          0 if collinear.
     */
    private int orientation(Point a, Point b, Point c) {
        double inclDiff = (b.getY() - a.getY()) * (c.getX() - b.getX())
                - (c.getY() - b.getY()) * (b.getX() - a.getX());

        if (inclDiff > 0) {
            return 1;
        } else if (inclDiff < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Determines if this line intersect with other line.
     *
     * @param other the other line section.
     * @return true if line sections are intersecting, false otherwise.
     */
    public boolean isIntersecting(Line other) {
        double thisIncline = (this.start.getY() - this.end.getY()) / (this.start.getX() - this.end.getX());

        double otherIncline = (other.start.getY() - other.end.getY()) / (other.start.getX() - other.end.getX());

        if (thisIncline == otherIncline) {
            return false;
        }

        int or11 = orientation(this.start, this.end, other.start);
        int or12 = orientation(this.start, this.end, other.end);
        int or21 = orientation(other.start, other.end, this.start);
        int or22 = orientation(other.start, other.end, this.end);

        return (or11 != or12) && (or21 != or22);
    }

    /**
     * if given x,y are not bigger nor smaller than this line x,y
     * values return true, otherwise return false.
     *
     * @param x point x coordinate
     * @param y point y coordinate
     * @return true if x,y is between this line x,y, false otherwise.
     */
    public boolean isPointInRange(double x, double y) {
        double maxX = Math.max(this.start.getX(), this.end.getX());

        double maxY = Math.max(this.start.getY(), this.end.getY());

        double minX = Math.min(this.start.getX(), this.end.getX());

        double minY = Math.min(this.start.getY(), this.end.getY());

        /*if (x > minX && x < maxX && y > minY && y < maxY) {
            return false;
        }*/

        return !(x < minX || x > maxX || y < minY || y > maxY);
    }

    /**
     * Returns the intersection point if the lines intersect,
     * and null otherwise.
     *
     * @param other the other line section.
     * @return intersection point if lines intersect, null otherwise.
     */
    public Point intersectionWith(Line other) {
        if (!isIntersecting(other)) {
            return null;
        }

        // this geometry.Line incline
        double thisIncline = (this.start.getY() - this.end.getY()) / (this.start.getX() - this.end.getX());

        // other line incline
        double otherIncline = (other.start.getY() - other.end.getY()) / (other.start.getX() - other.end.getX());

        // the constant number of the this geometry.Line equation
        double thisConst = (-(thisIncline * this.start.getX())) + this.start.getY();

        // the constant number of other geometry.Line equation
        double otherConst = (-(otherIncline * other.start.getX())) + other.start.getY();

        // x coordinate of intersection geometry.Point
        double interX = (otherConst - thisConst) / (thisIncline - otherIncline);

        // y coordinate of intersection geometry.Point
        double interY = (thisIncline * interX) + thisConst;

        // if this incline is infinite
        if (this.start.getX() == this.end.getX()) {
            interX = this.start.getX();
            interY = (otherIncline * interX) + otherConst;

        // if other incline is infinite
        } else if (other.start.getX() == other.end.getX()) {
            interX = other.start.getX();
            interY = (thisIncline * interX) + thisConst;
        }

        /* checks if interX, interY are in range of intersected
         * lines x,y */
        if (!this.isPointInRange(interX, interY)
                || !other.isPointInRange(interX, interY)) {
            return null;
        }

        //intersection geometry.Point
        return new Point(interX, interY);
    }

    /**
     * Return true is the line sections are equal, false otherwise.
     *
     * @param other the other line section.
     * @return true if the lines are equal, false otherwise.
     */
    public boolean equals(Line other) {
        return (this.start.getX() == other.start.getX()) && (this.start.getY() == other.start.getY())
                && (this.end.getX() == other.end.getX()) && (this.end.getY() == other.end.getY());
    }

    /**
     * If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the
     * getStart of the line.
     *
     * @param rect the rectangle to check for intersection points.
     * @return the closest intersection point to line getStart point.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {

        // intersection points of line with given rectangle
        List<Point> interPoints = rect.intersectionPoints(this);

        // no intersections
        if (interPoints.isEmpty()) {
            return null;
        }

        // the closest point to start of line
        Point closest = interPoints.get(0);

        /* compare points distances to start of line and puts the
         * closes one to be closest value */
        for (int i = 1; i < interPoints.size(); i++) {
            if (this.start.distance(closest) > this.start.distance(interPoints.get(i))) {
                closest = interPoints.get(i);
            }
        }

        return closest;
    }
}
