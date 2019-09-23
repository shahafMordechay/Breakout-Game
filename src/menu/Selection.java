package menu;

import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Menu selection.
 *
 * @param <T> the selection type.
 *
 * @author Shahaf Mordechay
 */
public class Selection<T> {

    // members
    private String key;
    private String message;
    private T returnVal;

    /**
     * Constructs a new selection.
     *
     * @param key       the key to press.
     * @param message   the message to show.
     * @param returnVal the selection operation to do.
     */
    public Selection(String key, String message, T returnVal) {
        this.key = key;
        this.message = message;
        this.returnVal = returnVal;
    }

    /**
     * Return this key.
     *
     * @return this key value.
     */
    public String getKey() {
        return key;
    }

    /**
     * Return this return value.
     *
     * @return this return value.
     */
    public T getReturnVal() {
        return returnVal;
    }

    /**
     * Write the selection key and message.
     *
     * @param d      draw surface.
     * @param x      x coordinate.
     * @param y      y coordinate.
     * @param height frame height.
     */
    public void drawSelection(DrawSurface d, int x, int y, int height) {

        int maxHeight = d.getHeight() * 2 / 15;
        if (height > maxHeight) {
            height = maxHeight;
        }

        int width = x * 4;
        d.setColor(Color.orange);
        d.fillRectangle(x, y, x * 4, height);
        d.setColor(Color.black);
        d.drawRectangle(x, y, x * 4, height);

        int textX = x + width / 10;
        int textY = y + height * 2 / 3;
        d.setColor(Color.black);
        d.drawText(textX, textY, this.key + ".  " + this.message, 40);

    }
}
