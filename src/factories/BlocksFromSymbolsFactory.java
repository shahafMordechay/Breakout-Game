package factories;

import collidables.Block;

import java.util.Map;
import java.util.TreeMap;

/**
 * Factory of blocks.
 * Create block by symbol.
 *
 * @author Shahaf Mordechay
 */
public class BlocksFromSymbolsFactory {

    // members
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * Constructs a block factory.
     */
    public BlocksFromSymbolsFactory() {
        this.spacerWidths = new TreeMap<>();
        this.blockCreators = new TreeMap<>();
    }

    /**
     * Returns true if given string 's' is a valid space symbol,
     * return false otherwise.
     *
     * @param s space symbol.
     * @return true if 's' is a valid space symbol.
     */
    public boolean isSpaceSymbol(String s) {
        return this.spacerWidths.containsKey(s);
    }

    /**
     * Returns true if given string 's' is a valid block symbol,
     * return false otherwise.
     *
     * @param s block symbol.
     * @return true if 's' is a valid block symbol.
     */
    public boolean isBlockSymbol(String s) {
        return this.blockCreators.containsKey(s);
    }

    /**
     * Return a block according to the definitions associated with symbol s.
     * The block will be located at position (xpos, ypos).
     *
     * @param s    block symbol.
     * @param xpos x coordinate of block upper left point.
     * @param ypos y coordinate of block upper left point.
     * @return a new Block in x,y position.
     */
    public Block getBlock(String s, int xpos, int ypos) {
        return this.blockCreators.get(s).create(xpos, ypos);
    }

    /**
     * Returns the width in pixels associated with the given spacer-symbol.
     *
     * @param s space symbol.
     * @return the with of this space symbol.
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }

    /**
     * Returns the width in pixels associated with the given block-symbol.
     *
     * @param s block symbol.
     * @return the with of this block symbol.
     */
    public int getBlockWidth(String s) {
        return this.blockCreators.get(s).getWidth();
    }

    /**
     * Add block with a specific symbol to this factory.
     *
     * @param s the symbol and block properties.
     */
    public void addBlockToFactory(String s) {

        String symbol = null;
        String[] properties = s.split(" ");

        for (String property : properties) {
            String[] keyAndValue = property.split(":");
            String key = keyAndValue[0];
            String value = keyAndValue[1];

            if (key.equals("symbol")) {
                symbol = value;
                break;
            }
        }

        BlockCreator blockCreator = BlockCreator.
                blockCreatorFromString(properties);

        this.blockCreators.put(symbol, blockCreator);
    }

    /**
     * Add space with a specific symbol to this factory.
     *
     * @param s the symbol and space width.
     */
    public void addSpacerToFactory(String s) {
        String[] properties = s.split(" ");
        String symbol = properties[0].split(":")[1];
        String size = properties[1].split(":")[1].trim();
        Integer spaceSize = Integer.parseInt(size);
        this.spacerWidths.put(symbol, spaceSize);
    }
}
