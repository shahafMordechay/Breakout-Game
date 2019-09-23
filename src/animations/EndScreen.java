package animations;

import biuoop.DrawSurface;

/**
 * Display a game end screen when game is finished.
 *
 * @author Shahaf Mordechay
 */
public class EndScreen implements Animation {

    // members
    private int numberOfLives;
    private int score;

    /**
     * Constructs a new game end screen, displaying win or lose
     * phrase with the player score.
     *
     * @param numberOfLives player number of lives at game end.
     * @param score         player score at game end.
     */
    public EndScreen(int numberOfLives, int score) {
        this.numberOfLives = numberOfLives;
        this.score = score;
    }

    /**
     * Draw a pause screen.
     *
     * @param d the draw surface to draw on.
     * @param dt the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {

        // player lose
        if (this.numberOfLives == 0) {
            String text = "Game Over. Your score is "
                    + Integer.toString(this.score);

            d.drawText(10, d.getHeight() / 2, text, 32);

        // player win
        } else {
            String text = "You Win! Your score is "
                    + Integer.toString(this.score);

            d.drawText(10, d.getHeight() / 2, text, 32);
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
