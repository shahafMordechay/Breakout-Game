package other;

import java.awt.Color;
import java.awt.Image;

/**
 * A block filling color or image.
 *
 * @author Shahaf Mordechay.
 */
public class Fill {

    // members
    private Color color;
    private Image image;

    /**
     * Constructs a fill by color.
     *
     * @param color the fill color.
     */
    public Fill(Color color) {
        this.color = color;
        this.image = null;
    }

    /**
     * Constructs a fill by image.
     *
     * @param image the fill image.
     */
    public Fill(Image image) {
        this.image = image;
        this.color = null;
    }

    /**
     * Return the fill color.
     *
     * @return the fill color.
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Return the fill image.
     *
     * @return the fill image.
     */
    public Image getImage() {
        return this.image;
    }
}
