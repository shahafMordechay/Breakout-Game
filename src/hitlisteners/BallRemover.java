package hitlisteners;

import game.GameLevel;
import other.Counter;
import sprites.Ball;
import collidables.Block;

/**
 * BlockRemover is in charge of removing balls from the game,
 * as well as keeping count of the number of balls that remain.
 *
 * @author Shahaf Mordechay
 */
public class BallRemover implements HitListener {

    // members
    private GameLevel gameLevel;
    private Counter remainingBalls;

    /**
     * Constructs and initializes a ball remover with a specified
     * game to track on.
     *
     * @param gameLevel           the current played game.
     * @param remainingBalls the number of remaining balls in the game.
     */
    public BallRemover(GameLevel gameLevel, Counter remainingBalls) {
        this.gameLevel = gameLevel;
        this.remainingBalls = remainingBalls;
    }

    /**
     * Balls that hit the beingHit object are removed from the game.
     *
     * @param beingHit the object that is notified on a hit event.
     * @param hitter   the object that hit the beingHit object.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.gameLevel);
        remainingBalls.decrease(1);
    }
}
