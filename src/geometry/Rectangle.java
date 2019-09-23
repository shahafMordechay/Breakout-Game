package geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * A rectangle represented by upper left point, width and height.
 *
 * @author Shahaf Mordechay
 */
public class Rectangle {

    // members
    private Point upperLeft;
    private double width;
    private double height;

    /**
     * Constructs and initializes a rectangle with specified
     * location(upper left point), width and height.
     *
     * @param upperLeft the upper left point of this rectangle.
     * @param width     the width of this rectangle.
     * @param height    the height of this rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    /**
     * Constructs and initializes a rectangle with specified
     * location(upper left point), width and height.
     *
     * @param x      the x coordinate of this rectangle upper left point.
     * @param y      the y coordinate of this rectangle upper left point.
     * @param width  the width of this rectangle.
     * @param height the height of this rectangle.
     */
    public Rectangle(int x, int y, double width, double height) {
        this.upperLeft = new Point(x, y);
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the width of this rectangle.
     *
     * @return this rectangle width.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Returns the height of this rectangle.
     *
     * @return this rectangle height.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns the upper-left point of this rectangle.
     *
     * @return this rectangle upper-left point.
     */
    public Point getUpperLeft() {
        return upperLeft;
    }

    /**
     * Returns the lower-left point of this rectangle.
     *
     * @return this rectangle lower-left point.
     */
    public Point getLowerLeft() {
        return new Point(this.upperLeft.getX(),
                        this.upperLeft.getY() + height);
    }

    /**
     * Returns the upper-right point of this rectangle.
     *
     * @return this rectangle upper-right point.
     */
    public Point getUpperRight() {
        return new Point(this.upperLeft.getX() + width,
                        this.upperLeft.getY());
    }

    /**
     * Returns the lower-right point of this rectangle.
     *
     * @return this rectangle lower-right point.
     */
    public Point getLowerRight() {
        return new Point(this.getUpperRight().getX(),
                        this.getLowerLeft().getY());
    }

    /**
     * set the upper left point of this rectangle.
     * @param x this upper left point x coordinate.
     * @param y this upper left point y coordinate.
     */
    public void setUpperLeft(double x, double y) {
        this.upperLeft = new Point(x, y);
    }

    /**
     * Returns a list of this rectangle intersection point with
     * a given line.
     *
     * @param line the line to check for intersection points.
     * @return a list of intersection points.
     */
    public List<Point> intersectionPoints(Line line) {

        ArrayList<Point> interList = new ArrayList<>();

        // rectangle corners points array
        Point[] pointArray = {upperLeft, this.getUpperRight(),
                this.getLowerRight(), this.getLowerLeft()};

        // check for intersection of line with rectangle edges
        for (int i = 0; i < pointArray.length; i++) {

            int next = (i + 1) % (pointArray.length); // next in array

            // rectangle edge as 2 points from pointArray
            Line recLine = new Line(pointArray[i], pointArray[next]);

            // intersection point of this rectangle with line
            Point interPoints = recLine.intersectionWith(line);

            if (interPoints != null) {
                interList.add(interPoints);
            }
        }

        return interList;
    }
}
