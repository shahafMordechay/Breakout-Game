package backgrounds;

import biuoop.DrawSurface;
import sprites.Sprite;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

/**
 * Draws the background of level four.
 *
 * @author Shahaf Mordechay
 */
public class BackgroundFour implements Sprite {

    private static final Color LIGHT_BLUE = new Color(4, 130, 255);
    private static final Color GRAY1 = new Color(204, 204, 204);
    private static final Color GRAY2 = new Color(195, 195, 195);
    private static final Color GRAY3 = new Color(190, 190, 190);
    private static final Color GRAY4 = new Color(188, 188, 188);
    private static final Color GRAY5 = new Color(170, 170, 170);

    // member
    private int frameSize;

    /**
     * Constructs level one background bt given game block size.
     *
     * @param frameSize the size of gui screen.
     */
    public BackgroundFour(int frameSize) {
        this.frameSize = frameSize;
    }

    /**
     * Draw a background cloud that stretches from minX to maxX
     * and from minY to maxY.
     *
     * @param minX the horizontal left end of drawn cloud.
     * @param maxX the horizontal right end of drawn cloud.
     * @param minY the vertical upper end of drawn cloud.
     * @param maxY the vertical lower end of drawn cloud.
     * @param d    the draw surface to draw on.
     */
    private void drawCloud(int minX, int maxX, int minY, int maxY,
                           DrawSurface d) {

        int middleY = minY + maxY - minY;

        int rainX = minX;
        int rainEndY = d.getHeight();
        int rainChangeInX = (maxX - minX) / 10;


        for (int i = 0; i < 10; i++) {
            int rainEndX = rainX - 10;
            d.setColor(GRAY2);
            d.drawLine(rainX, middleY, rainEndX, rainEndY);
            rainX += rainChangeInX;
        }

        int changeInX = (maxX - minX) / 5;
        List<Integer> yCoordinates = Arrays.asList(middleY, minY, maxY, minY, maxY);
        List<Color> colors = Arrays.asList(GRAY1, GRAY2, GRAY3, GRAY4, GRAY5);

        for (int i = 0; i < 5; i++) {
            int xCoordinate = minX + changeInX * i;
            int yCoordinate = yCoordinates.get(i);
            int radius = i * 2 + this.frameSize;

            d.setColor(colors.get(i));
            d.fillCircle(xCoordinate, yCoordinate, radius);
        }
    }

    /**
     * Draws the background of level four.
     *
     * @param d the screen to draw on.
     */
    public void drawOn(DrawSurface d) {

        // draw background
        d.setColor(LIGHT_BLUE);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

        // draw clouds
        int minX1 = this.frameSize * 4;
        int maxX1 = minX1 * 2;
        int minY1 = d.getHeight() - this.frameSize * 8;
        int maxY1 = minY1 + this.frameSize;
        drawCloud(minX1, maxX1, minY1, maxY1, d);

        int minX2 = d.getWidth() - this.frameSize * 8;
        int maxX2 = minX2 + this.frameSize * 4;
        int minY2 = d.getHeight() - this.frameSize * 4;
        int maxY2 = minY2 + this.frameSize;
        drawCloud(minX2, maxX2, minY2, maxY2, d);
    }

    /**
     * does nothing.
     *
     * @param dt the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {
    }

}
