package animations;

import biuoop.DrawSurface;
import biuoop.Sleeper;
import sprites.SpriteCollection;

import java.awt.Color;

/**
 * Display a countdown on the screen before each turn starts.
 *
 * @author Shahaf Mordechay
 */
public class CountdownAnimation implements Animation {

    private static final Color COLOR = Color.yellow;

    private int countFrom;
    private long displayTime;
    private SpriteCollection gameScreen;
    private boolean stop;
    private Sleeper sleeper;

    /**
     * Constructs and initializes a new countdown animations with
     * a number to count from, and the screen of the turn.
     *
     * @param numOfSeconds the number of seconds of the countdown.
     * @param countFrom    the number to count from.
     * @param gameScreen   the screen to display while the countdown.
     */
    public CountdownAnimation(double numOfSeconds,
                              int countFrom, SpriteCollection gameScreen) {
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.stop = false;
        this.sleeper = new Sleeper();
        this.displayTime = (long) (1000 * numOfSeconds / countFrom);
    }

    /**
     * Draw a frame to the screen. Each time display the next number
     * in the countdown.
     *
     * @param d the draw surface to draw on.
     * @param dt the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {

        gameScreen.drawAllOn(d);
        d.setColor(COLOR);
        d.drawText(d.getWidth() / 2, d.getHeight() / 2, this.countFrom + "...", 32);

        if (this.countFrom < 3) {
            this.sleeper.sleepFor(this.displayTime);
        }

        if (this.countFrom == 0) {
            this.stop = true;
        }

        this.countFrom--;
    }

    /**
     * Tells if the animations should stop.
     *
     * @return the stop condition state.
     */
    public boolean shouldStop() {
        return this.stop;
    }
}
