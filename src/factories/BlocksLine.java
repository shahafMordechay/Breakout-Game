package factories;

import geometry.Rectangle;
import collidables.Block;
import other.Fill;

import java.util.ArrayList;
import java.util.List;

/**
 * Create a line of blocks by given collision rectangle properties,
 * and each line blocks properties.
 *
 * @author Shahaf Mordechay
 */
public class BlocksLine {

    // members
    private int xCoordinate;
    private int yCoordinate;
    private int width;
    private int height;

    /**
     * Constructs and initializes the block collision
     * rectangle properties.
     *
     * @param x      first block upper left x coordinate.
     * @param y      first block upper left y coordinate.
     * @param width  single block width.
     * @param height single block height.
     */
    public BlocksLine(int x, int y, int width, int height) {
        this.xCoordinate = x;
        this.yCoordinate = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Creates a line of blocks according to given color, hit points
     * and line length.
     *
     * @param color     this line blocks color.
     * @param hitPoints this line blocks hit points.
     * @param length    this line length.
     *
     * @return list of all blocks in line(from first to last).
     */
    public List<Block> createBlocksLine(Fill color, int hitPoints, int length) {

        List<Block> blocks = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            Rectangle shape = new Rectangle(this.xCoordinate,
                    this.yCoordinate, this.width, this.height);

            Block newBlock = new Block(shape, color, hitPoints);

            blocks.add(newBlock);

            // next block x coordinate
            this.xCoordinate += this.width;
        }

        return blocks;
    }

    /**
     * Set this x coordinate according to given value.
     *
     * @param x the new x value
     */
    public void setXCoordinate(int x) {
        this.xCoordinate = x;
    }
}
