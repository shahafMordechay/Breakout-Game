package factories;

import collidables.Block;
import geometry.Rectangle;
import other.ColorsParser;
import other.Fill;

import java.awt.Color;
import java.util.Map;
import java.util.TreeMap;

/**
 * Create a block by given properties.
 *
 * @author Shahaf Mordechay
 */
public class BlockCreator {

    // members
    private int width;
    private int height;
    private int hitPoints;
    private Fill filling;
    private Map<Integer, Fill> fillMap;
    private Color stroke;

    /**
     * Constructs the properties of the block to create.
     *
     * @param width     the block width.
     * @param height    the block height.
     * @param hitPoints the block hit points.
     * @param filling   the block default fill.
     * @param fillMap   the block fill-k fills.
     * @param stroke    the block stroke.
     */
    public BlockCreator(int width, int height, int hitPoints,
                            Fill filling, Map<Integer, Fill> fillMap,
                                Color stroke) {
        this.width = width;
        this.height = height;
        this.hitPoints = hitPoints;
        this.filling = filling;
        this.fillMap = fillMap;
        this.stroke = stroke;
    }
    /**
     * Create a block at the specified location.
     *
     * @param xpos the upper left x coordinate of the created block.
     * @param ypos the upper left y coordinate of the created block.
     * @return a new Block in x,y position.
     */
    public Block create(int xpos, int ypos) {

        if (this.fillMap.containsKey(1) && this.filling == null) {
            this.filling = this.fillMap.get(1);
        }

        Rectangle rectangle = new Rectangle(xpos, ypos, this.width, this.height);
        Block newBlock = new Block(rectangle, this.filling, this.hitPoints);
        newBlock.setStroke(this.stroke);

        if (this.fillMap != null) {
            newBlock.addFills(this.fillMap);
        }

        return newBlock;
    }

    /**
     * Construct new ball creator from a given string of properties.
     *
     * @param properties the new ball creator properties.
     * @return a new ball creator with the given properties.
     */
    public static BlockCreator blockCreatorFromString(String[] properties) {
        Integer width = null;
        Integer height = null;
        Integer hitPoints = null;
        Fill fill = null;
        Map<Integer, Fill> fillMap = new TreeMap<>();
        Color stroke = null;

        for (String property : properties) {
            String[] keyAndValue = property.split(":");
            String key = keyAndValue[0];
            String value = keyAndValue[1];
            switch (key) {
                case "height":
                    if (height == null) {
                        height = Integer.parseInt(value);
                    }
                    break;
                case "width":
                    if (width == null) {
                        width = Integer.parseInt(value);
                    }
                    break;
                case "hit_points":
                    if (hitPoints == null) {
                        hitPoints = Integer.parseInt(value);
                    }
                    break;
                case "fill":
                    if (fill == null) {
                        if (value.contains("color")) {
                            fill = new Fill(ColorsParser.colorFromString(value));
                        } else if (value.contains("image")) {
                            fill = new Fill(ColorsParser.imageFromString(value));
                        }
                    }
                    break;
                case "stroke":
                    if (stroke == null) {
                        stroke = ColorsParser.colorFromString(value);
                    }
                    break;
                default:
                    break;
            }

            if (keyAndValue[0].contains("fill-")) {
                Integer fillNum = Integer.parseInt(key.split("-")[1]);
                if (value.contains("color")) {
                    Fill filling = new Fill(ColorsParser.colorFromString(value));
                    fillMap.put(fillNum, filling);
                } else if (value.contains("image")) {
                    Fill filling = new Fill(ColorsParser.imageFromString(value));
                    fillMap.put(fillNum, filling);
                }
            }
        } // end of for loop

        return new BlockCreator(width, height, hitPoints, fill, fillMap, stroke);
    }

    /**
     * Return this ball creator width property.
     *
     * @return this ball creator width property.
     */
    public int getWidth() {
        return this.width;
    }
}
