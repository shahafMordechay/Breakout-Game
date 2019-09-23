package levels;

import animations.AnimationRunner;
import factories.VelocitiesCreator;
import factories.BlocksLine;
import other.Velocity;
import backgrounds.BackgroundThree;
import collidables.Block;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Holds level three information according to given animations runner.
 * Also creates and returns this level information.
 *
 * @author Shahaf Mordechay
 */
public class LevelThreeInfo {

    // level fixed properties
    private static final int PADDLE_SPEED = 4;
    private static final int PADDLE_WIDTH = 80;
    private static final String LEVEL_NAME = "Green 3";
    private static final int BALL_ANGLE = 330;
    private static final int BALL_SPEED = 5;
    private static final int NUM_OF_BALLS = 2;


    // member
    private int width;
    private int height;
    private int frameSize;

    /**
     * Constructs and initializes a new level one information holder.
     *
     * @param runner this level animations runner.
     */
    public LevelThreeInfo(AnimationRunner runner) {
        this.width = runner.getWidth();
        this.height = runner.getHeight();
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
        int buildingX =  3 * this.frameSize;
        int buildingY = this.height * 5 / 7;
        BackgroundThree background = new BackgroundThree(buildingX, buildingY);

        // balls velocities
        VelocitiesCreator velocities = new VelocitiesCreator(
                BALL_ANGLE, BALL_SPEED);

        List<Velocity> velocityList = velocities.createVelocities(
                NUM_OF_BALLS, false);

        // first block properties
        int blockWidth = (this.width - 2 * this.frameSize) / 15;
        int blockHeight = blockWidth / 2;
        int xCoordinate = this.width - this.frameSize - blockWidth * 10;
        int yCoordinate = (int) (this.height * 0.3);

        // blocks colors
        List<Color> colors = Arrays.asList(Color.gray,  Color.red,
                Color.yellow, Color.blue, Color.white);

        // create game blocks
        List<Block> blocks = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            BlocksLine blocksLine = new BlocksLine(xCoordinate, yCoordinate,
                    blockWidth, blockHeight);

            Color color = colors.get(i);
            int length = 10 - i;
            //blocks.addAll(blocksLine.createBlocksLine(color, 1, length));

            xCoordinate += blockWidth;
            yCoordinate += blockHeight;
        }

        // return this level information
        return new Level(velocityList, PADDLE_SPEED, PADDLE_WIDTH,
                LEVEL_NAME, background, blocks);
    }
}
