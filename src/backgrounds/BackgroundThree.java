package backgrounds;

import biuoop.DrawSurface;
import sprites.Sprite;

import java.awt.Color;

/**
 * Draws the background of level three.
 *
 * @author Shahaf Mordechay
 */
public class BackgroundThree implements Sprite {

    // background colors
    private static final Color DARK_GREEN = new Color(0, 102, 0);
    private static final Color BUILDING_COLOR = new Color(40, 40, 40);
    private static final Color BASE_COLOR = new Color(54, 55, 54);
    private static final Color ANTENNA_COLOR = new Color(70, 70, 70);
    private static final Color LIGHT_RED = new Color(255, 51, 51);
    private static final Color LIGHT_YELLOW = new Color(230, 255, 153);

    // building width and height
    private static final int BUILDING_WIDTH = 111;
    private static final int BUILDING_HEIGHT = 200;

    // members
    private int buildingX;
    private int buildingY;

    /**
     * Constructs level one background bt given game block size.
     *
     * @param buildingX upper left x coordinate.
     * @param buildingY upper left y coordinate.
     */
    public BackgroundThree(int buildingX, int buildingY) {
        this.buildingX = buildingX;
        this.buildingY = buildingY;
    }

    /**
     * Draws the background of level three.
     *
     * @param d the screen to draw on.
     */
    public void drawOn(DrawSurface d) {

        // draw background
        d.setColor(DARK_GREEN);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

        // draw building base
        d.setColor(BUILDING_COLOR);
        d.fillRectangle(this.buildingX, this.buildingY, BUILDING_WIDTH, BUILDING_HEIGHT);

        // draw windows
        int numOfWindows = 5;
        int windowWidth = BUILDING_WIDTH / 9;
        int windowHeight = windowWidth * 2;
        int windowY = this.buildingY + windowWidth;

        for (int i = 0; i < numOfWindows; i++) {

            int windowX = this.buildingX + windowWidth;

            for (int j = 0; j < numOfWindows; j++) {
                d.setColor(Color.white);
                d.fillRectangle(windowX, windowY, windowWidth, windowHeight);
                windowX += windowWidth * 1.6;
            }

            windowY += windowHeight + windowWidth;
        }

        // draw antenna base
        int antennaBaseWidth = BUILDING_WIDTH / 3;
        int antennaBaseHeight = BUILDING_HEIGHT / 3;
        int antennaBaseX = this.buildingX + antennaBaseWidth;
        int antennaBaseY = this.buildingY - antennaBaseHeight;
        d.setColor(BASE_COLOR);
        d.fillRectangle(antennaBaseX, antennaBaseY, antennaBaseWidth,
                antennaBaseHeight);

        // draw antenna
        int antennaWidth = antennaBaseWidth / 3;
        int antennaHeight = antennaBaseHeight * 3;
        int antennaX = antennaBaseX + antennaWidth;
        int antennaY = antennaBaseY - antennaHeight;
        d.setColor(ANTENNA_COLOR);
        d.fillRectangle(antennaX, antennaY, antennaWidth, antennaHeight);

        // draw antenna head
        int headX = antennaX + antennaWidth / 2;
        int headY = antennaY - antennaWidth / 2;
        d.setColor(LIGHT_YELLOW);
        d.fillCircle(headX, headY, antennaWidth);
        d.setColor(LIGHT_RED);
        d.fillCircle(headX, headY, antennaWidth - 4);
        d.setColor(Color.white);
        d.fillCircle(headX, headY, antennaWidth - 8);
    }

    /**
     * does nothing.
     *
     * @param dt the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {

    }
}
