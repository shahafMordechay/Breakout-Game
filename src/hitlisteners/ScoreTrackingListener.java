package hitlisteners;

import other.Counter;
import sprites.Ball;
import collidables.Block;

/**
 * ScoreTrackingListener is in charge of keeping track on the current
 * game score.
 * Increasing the score for each block hit(by 5)
 * and block destruction(by 10).
 *
 * @author Shahaf Mordechay
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /**
     * Constructs and initializes a score tracking panel with a specified
     * score to track on.
     *
     * @param scoreCounter the game score.
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * Increasing 5 points for each block hit and 10 points for each
     * block destruction.
     *
     * @param beingHit the object that is notified on a hit event.
     * @param hitter   the object that hit the beingHit object.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 0) {
            this.currentScore.increase(10);
            return;
        }

        this.currentScore.increase(5);
    }

}
