package backgrounds;

import biuoop.DrawSurface;
import sprites.Sprite;

import java.awt.Color;

/**
 * The background of the game main menu.
 *
 * @author Shahaf Mordechay
 */
public class MenuBackground implements Sprite {

    /**
     * Draws the background of main menu.
     *
     * @param d the screen to draw on.
     */
    public void drawOn(DrawSurface d) {
        int x = 0;
        int y = 0;
        int width = d.getWidth() / 5;

        // draw vertical blocks
        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                d.setColor(Color.white);
            } else {
                d.setColor(Color.BLUE);
            }

            d.fillRectangle(x, y, width, d.getHeight());
            x += width;
        }
    }

    /**
     * does nothing.
     *
     * @param dt the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {

    }
}
