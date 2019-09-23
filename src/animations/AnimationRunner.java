package animations;

import biuoop.Sleeper;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.DrawSurface;
import biuoop.DialogManager;

/**
 * Creates a gui and runs a given animations.
 *
 * @author Shahaf Mordechay
 */
public class AnimationRunner {

    // gui properties
    private static final String NAME = "BreakOut";
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    // members
    private GUI gui;
    private int framesPerSecond;
    private Sleeper sleeper;

    /**
     * Constructs and initializes a new animations runner.
     */
    public AnimationRunner() {
        this.gui = new GUI(NAME, WIDTH, HEIGHT);
        this.framesPerSecond = 60;
        this.sleeper = new Sleeper();
    }

    /**
     * Returns the screen width.
     *
     * @return the width of the screen.
     */
    public int getWidth() {
        return this.gui.getDrawSurface().getWidth();
    }

    /**
     * Returns the screen height.
     *
     * @return the height of the screen.
     */
    public int getHeight() {
        return this.gui.getDrawSurface().getHeight();
    }

    /**
     * Returns the size of the frame and indicator panel.
     *
     * @return the size of the frame.
     */
    public int getFrameSize() {
        return this.getWidth() / 32;
    }

    /**
     * Return this gui keyboard sensor.
     *
     * @return this gui keyboard sensor.
     */
    public KeyboardSensor getKeyboard() {
        return this.gui.getKeyboardSensor();
    }

    /**
     * Return a dialog manager of this gui.
     *
     * @return a dialog manager.
     */
    public DialogManager getDialogManager() {
        return this.gui.getDialogManager();
    }

    /**
     * Closes this gui.
     */
    public void closeGUI() {
        this.gui.close();
    }

    /**
     * Running the given animations until the animations should stop.
     *
     * @param animation the animations to run.
     */
    public void run(Animation animation) {

        int millisecondsPerFrame = 1000 / framesPerSecond;

        // running the animations loop
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();

            double dt = 1.0 / this.framesPerSecond;
            animation.doOneFrame(d, dt);

            gui.show(d);

            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }


    }
}
