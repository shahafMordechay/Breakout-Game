package levels;

import other.Velocity;
import sprites.Sprite;
import collidables.Block;

import java.util.List;

/**
 * Contains information about each level.
 * initial number of balls,
 * ball velocities,
 * paddle speed and width,
 * level name,
 * level background,
 * level blocks,
 * amount of blocks to remove.
 *
 * @author Shahaf Mordechay
 */
public interface LevelInformation {

    /**
     * number oof balls to start with.
     *
     * @return the starting number of balls.
     */
    int numberOfBalls();

    /**
     * The initial velocity of each ball.
     *
     * @return each ball initial velocity.
     */
    List<Velocity> initialBallVelocities();

    /**
     * The paddle width.
     *
     * @return paddle width.
     */
    int paddleWidth();

    /**
     * The paddle speed.
     *
     * @return paddle speed.
     */
    int paddleSpeed();

    /**
     * The level name.
     *
     * @return the name of the level.
     */
    String levelName();

    /**
     * Returns a sprite with the background of the level.
     *
     * @return background sprites of the level.
     */
    Sprite getBackground();

    /**
     * The Blocks that make up this level, each block contains
     * its size, color and location.
     *
     * @return list of this level blocks.
     */
    List<Block> blocks();

    /**
     * Number of blocks that should be removed before the level
     * is considered to be "cleared".
     *
     * @return the amount of blocks to remove.
     */
    int numberOfBlocksToRemove();
}
