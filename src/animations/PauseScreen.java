package animations;

import biuoop.DrawSurface;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;

/**
 * Display a pause screen if player pressed p.
 * return to game if player pressed space.
 *
 * @author Shahaf Mordechay
 */
public class PauseScreen implements Animation {

    /**
     * Constructs and initializes a pause screen animations.
     */
    public PauseScreen() {
    }

    /**
     * Draw a pause screen.
     *
     * @param d the draw surface to draw on.
     * @param dt the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d , double dt) {
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("background_images/pause_screen.png");

        try {
            Image background = ImageIO.read(is);
            d.drawImage(0, 0, background);
        } catch (IOException e) {
            System.err.println("Failed reading image");
            e.printStackTrace(System.err);
        }
    }

    /**
     * Tells if the animations should stop.
     *
     * @return the stop condition state.
     */
    public boolean shouldStop() {
        return false;
    }
}
