package levels;

import animations.AnimationRunner;
import factories.VelocitiesCreator;
import factories.BlocksLine;
import geometry.Rectangle;
import other.Velocity;
import backgrounds.BackGroundTwo;
import collidables.Block;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Holds level two information according to given animations runner.
 * Also creates and returns this level information.
 *
 * @author Shahaf Mordechay
 */
public class LevelTwoInfo {

    // level fixed properties
    private static final int PADDLE_SPEED = 4;
    private static final int PADDLE_WIDTH = 600;
    private static final String LEVEL_NAME = "Wide Easy";
    private static final int HIT_POINTS = 1;

    //members
    private int width;
    private int height;
    private int frameSize;

    /**
     * Constructs and initializes a new level one information holder.
     *
     * @param runner this level animations runner.
     */
    public LevelTwoInfo(AnimationRunner runner) {
        this.width = runner.getWidth();
        this.height = runner.getHeight();
        this.frameSize = runner.getFrameSize();
    }

    /**
     * Returns a different color according to given number.
     *
     * @param i the variable to return color by.
     * @return  color according to given number.
     */
    private Color currentColor(int i) {
        if (i < 2) {
            return Color.red;
        } else if (i < 4) {
            return Color.orange;
        } else if (i < 6) {
            return Color.yellow;
        } else if (i < 9) {
            return Color.green;
        } else if (i < 11) {
            return Color.blue;
        } else if (i < 13) {
            return Color.pink;
        } else {
            return Color.cyan;
        }
    }

    /**
     * Creates this level objects and properties.
     * Return this level information.
     *
     * @return this level information.
     */
    public LevelInformation getInformation() {

        // balls velocities
        VelocitiesCreator velocities = new VelocitiesCreator(310, 5);
        List<Velocity> velocityList = velocities.createVelocities(10, false);

        // create game blocks
        int xCoordinate = this.frameSize;
        int yCoordinate = (int) (this.height * 0.4);
        int blockWidth = (this.width - 2 * this.frameSize) / 15;
        int blockHeight = blockWidth / 2;
        BlocksLine blocksLine = new BlocksLine(xCoordinate, yCoordinate, blockWidth, blockHeight);
        List<Block> blocks = new ArrayList<>();

        // add blocks with different colors
        for (int i = 0; i < 15; i++) {
            Color current = currentColor(i);
            //blocks.addAll(blocksLine.createBlocksLine(current, HIT_POINTS, 1));

            xCoordinate += blockWidth;
            blocksLine.setXCoordinate(xCoordinate);
        }

        // level background
        Rectangle leftest = blocks.get(0).getCollisionRectangle();
        BackGroundTwo background = new BackGroundTwo(leftest);

        // return this level information
        return new Level(velocityList, PADDLE_SPEED, PADDLE_WIDTH, LEVEL_NAME, background, blocks);
    }
}
