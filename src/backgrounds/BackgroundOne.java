package backgrounds;

import biuoop.DrawSurface;
import sprites.Sprite;

import java.awt.Color;

/**
 * Draws the background of level one.
 *
 * @author Shahaf Mordechay
 */
public class BackgroundOne implements Sprite {

    // member
    private int blockSize;

    /**
     * Constructs level one background bt given game block size.
     *
     * @param blockSize the width or height of level one block.
     */
    public BackgroundOne(int blockSize) {
        this.blockSize = blockSize;
    }

    /**
     * Draws the background of level one.
     *
     * @param d the screen to draw on.
     */
    public void drawOn(DrawSurface d) {

        // draw background
        d.setColor(Color.black);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

        // center coordinates
        int xCoordinate = d.getWidth() / 2;
        int yCoordinate = (int) (d.getHeight() * 0.3)
                            + this.blockSize / 2;

        // maximum radius
        final int maxRadius = 100;

        d.setColor(Color.blue);

        // draw target circles
        d.drawCircle(xCoordinate, yCoordinate, maxRadius - 50);
        d.drawCircle(xCoordinate, yCoordinate, maxRadius - 25);
        d.drawCircle(xCoordinate, yCoordinate, maxRadius);

        // distance of start, end of line from center
        int distanceFromCenter = (int) (maxRadius * 1.25);

        // draw vertical line
        int verticalStartY = yCoordinate -  distanceFromCenter;
        int verticalEndY = yCoordinate + distanceFromCenter;
        d.drawLine(xCoordinate, verticalStartY, xCoordinate, verticalEndY);

        // draw horizontal line
        int horizontalStartX = xCoordinate - distanceFromCenter;
        int horizontalEndX = xCoordinate + distanceFromCenter;
        d.drawLine(horizontalStartX, yCoordinate, horizontalEndX, yCoordinate);
    }

    /**
     * does nothing.
     *
     * @param dt the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {

    }
}
