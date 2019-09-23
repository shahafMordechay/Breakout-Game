package sprites;

import biuoop.DrawSurface;

import java.awt.Color;
import java.awt.Image;

/**
 * Level background.
 *
 * @author Shahaf Mordechay
 */
public class Background implements Sprite {

    // members
    private Color color;
    private Image image;

    /**
     * Constructs a background by color.
     *
     * @param color the background color.
     */
    public Background(Color color) {
        this.color = color;
        this.image = null;
    }

    /**
     * Constructs a background by image.
     *
     * @param image the background image.
     */
    public Background(Image image) {
        this.color = null;
        this.image = image;
    }

    /**
     * Draw the background on the screen.
     *
     * @param d the screen to draw on.
     */
    public void drawOn(DrawSurface d) {
        if (this.image == null) {
            d.setColor(this.color);
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        } else {
            d.drawImage(0, 0, this.image);
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
