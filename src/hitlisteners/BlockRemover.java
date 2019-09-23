package hitlisteners;

import game.GameLevel;
import other.Counter;
import sprites.Ball;
import collidables.Block;

/**
 * BlockRemover is in charge of removing blocks from the game,
 * as well as keeping count of the number of blocks that remain.
 *
 * @author Shahaf Mordechay
 */
public class BlockRemover implements HitListener {

    // members
    private GameLevel gameLevel;
    private Counter remainingBlocks;

    /**
     * Constructs and initializes a block remover with a specified
     * game to track on.
     *
     * @param gameLevel            the current played game.
     * @param remainingBlocks the number of remaining blocks in the game.
     */
    public BlockRemover(GameLevel gameLevel, Counter remainingBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = remainingBlocks;
    }

    /**
     * Blocks that are hit and reach 0 hit-points are removed
     * from the game.
     *
     * @param beingHit the object that is notified on a hit event.
     * @param hitter   the object that hit the beingHit object.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (beingHit.getHitPoints() == 0) {
            beingHit.removeFromGame(this.gameLevel);
            this.remainingBlocks.decrease(1);
            beingHit.removeHitListener(this);
        }
    }
}
