package levels;

import other.Velocity;
import sprites.Sprite;
import collidables.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds information about a specific level features.
 *
 * @author Shahaf Mordechay
 */
public class Level implements LevelInformation {

    private List<Velocity> velocities;
    private int paddleSpeed;
    private int paddleWidth;
    private String levelName;
    private Sprite backGround;
    private List<Block> blocks;

    /**
     * Constructs a new level information.
     *
     * @param velocities  each ball velocity.
     * @param paddleSpeed speed of this paddle.
     * @param paddleWidth width of this paddle.
     * @param levelName   name of this level.
     * @param background  this level background.
     * @param blocks      blocks of this level.
     */
    public Level(List<Velocity> velocities,
                 int paddleSpeed, int paddleWidth,
                 String levelName, Sprite background,
                 List<Block> blocks) {

        this.velocities = velocities;
        this.paddleSpeed = paddleSpeed;
        this.paddleWidth = paddleWidth;
        this.levelName = levelName;
        this.backGround = background;
        this.blocks = new ArrayList<>();
        this.blocks.addAll(blocks);
    }

    /**
     * number oof balls to start with.
     *
     * @return the starting number of balls.
     */
    public int numberOfBalls() {
        return this.velocities.size();
    }

    /**
     * The initial velocity of each ball.
     *
     * @return each ball initial velocity.
     */
    public List<Velocity> initialBallVelocities() {
        return this.velocities;
    }

    /**
     * The paddle speed.
     *
     * @return paddle speed.
     */
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    /**
     * The paddle width.
     *
     * @return paddle width.
     */
    public int paddleWidth() {
        return this.paddleWidth;
    }

    /**
     * The level name.
     *
     * @return the name of the level.
     */
    public String levelName() {
        return this.levelName;
    }

    /**
     * Returns a sprite with the background of the level.
     *
     * @return background sprites of the level.
     */
    public Sprite getBackground() {
        return this.backGround;
    }

    /**
     * The Blocks that make up this level, each block contains
     * its size, color and location.
     *
     * @return list of this level blocks.
     */
    public List<Block> blocks() {
        return this.blocks;
    }

    /**
     * Number of blocks that should be removed before the level
     * is considered to be "cleared".
     *
     * @return the amount of blocks to remove.
     */
    public int numberOfBlocksToRemove() {
        return this.blocks.size();
    }
}
