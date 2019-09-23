package levels;

import animations.AnimationRunner;
import factories.VelocitiesCreator;
import factories.BlocksLine;
import other.Velocity;
import backgrounds.BackgroundFour;
import collidables.Block;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Holds level four information according to given animations runner.
 * Also creates and returns this level information.
 *
 * @author Shahaf Mordechay
 */
public class LevelFourInfo {

    // level fixed properties
    private static final int PADDLE_SPEED = 4;
    private static final int PADDLE_WIDTH = 80;
    private static final String LEVEL_NAME = "Green 3";
    private static final int BALL_ANGLE = 330;
    private static final int BALL_SPEED = 5;
    private static final int NUM_OF_BALLS = 3;
    private static final int NUM_OF_LINES = 7;

    // member
    private int width;
    private int frameSize;

    /**
     * Constructs and initializes a new level one information holder.
     *
     * @param runner this level animations runner.
     */
    public LevelFourInfo(AnimationRunner runner) {
        this.width = runner.getWidth();
        this.frameSize = runner.getFrameSize();
    }

    /**
     * Creates this level objects and properties.
     * Return this level information.
     *
     *
     * @return this level information.
     */
    public LevelInformation getInformation() {

        // level background
        BackgroundFour background = new BackgroundFour(this.frameSize);

        // balls velocities
        VelocitiesCreator velocities = new VelocitiesCreator(
                BALL_ANGLE, BALL_SPEED);

        List<Velocity> velocityList = velocities.createVelocities(
                NUM_OF_BALLS, true);

        // first block properties
        int blockWidth = (this.width - 2 * this.frameSize) / 15;
        int blockHeight = blockWidth / 2;
        int xCoordinate = this.frameSize;
        int yCoordinate = this.frameSize * 4;

        // blocks colors
        List<Color> colors = Arrays.asList(Color.gray,
                Color.red, Color.yellow, Color.green,
                Color.white, Color.pink, Color.cyan);

        // create game blocks
        List<Block> blocks = new ArrayList<>();

        for (int i = 0; i < NUM_OF_LINES; i++) {
            BlocksLine blocksLine = new BlocksLine(xCoordinate, yCoordinate,
                    blockWidth, blockHeight);

            Color color = colors.get(i);
            //blocks.addAll(blocksLine.createBlocksLine(color, 1, 15));

            yCoordinate += blockHeight;
        }

        // return this level information
        return new Level(velocityList, PADDLE_SPEED, PADDLE_WIDTH,
                LEVEL_NAME, background, blocks);
    }
}
