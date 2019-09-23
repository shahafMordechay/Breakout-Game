package backgrounds;

import biuoop.DrawSurface;
import geometry.Rectangle;
import sprites.Sprite;

import java.awt.Color;

/**
 * Draws the background of level two.
 *
 * @author Shahaf Mordechay
 */
public class BackGroundTwo implements Sprite {

    // colors
    private static final Color VERY_LIGHT_YELLOW = new Color(255, 255, 153);
    private static final Color LIGHT_YELLOW = new Color(255, 255, 102);

    // members
    private double upperX;
    private double upperY;
    private double width;

    /**
     * Constructs level one background bt given game block size.
     *
     * @param farLeft the upper left block in the level.
     */
    public BackGroundTwo(Rectangle farLeft) {
        this.upperX = farLeft.getUpperLeft().getX();
        this.upperY = farLeft.getUpperLeft().getY();
        this.width = farLeft.getWidth();
    }

    /**
     * Draws the background of level two.
     *
     * @param d the screen to draw on.
     */
    public void drawOn(DrawSurface d) {

        // draw background
        d.setColor(Color.white);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

        // center coordinates
        int xCoordinate = (int) (this.width * 2.5);
        int yCoordinate = (int) ((d.getHeight() - this.upperY) / 2 - this.width);

        // maximum radius
        final int maxRadius = 60;

        // draw sun rays
        int startX = (int) this.upperX;
        double endX = d.getWidth() - this.upperX;
        int endY = (int) this.upperY;

        for (int i = startX; i < endX; i += 10) {
            d.setColor(VERY_LIGHT_YELLOW);
            d.drawLine(xCoordinate, yCoordinate, i, endY);
        }

        // draw sun
        d.setColor(VERY_LIGHT_YELLOW);
        d.fillCircle(xCoordinate, yCoordinate, maxRadius);
        d.setColor(LIGHT_YELLOW);
        d.fillCircle(xCoordinate, yCoordinate, maxRadius - 10);
        d.setColor(Color.yellow);
        d.fillCircle(xCoordinate, yCoordinate, maxRadius - 20);
    }

    /**
     * does nothing.
     *
     * @param dt the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {

    }
}
