package readers;

import collidables.Block;
import factories.BlocksFromSymbolsFactory;
import levels.Level;
import levels.LevelInformation;
import other.ColorsParser;
import other.Velocity;
import sprites.Background;
import sprites.Sprite;

import java.awt.Color;
import java.io.InputStream;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Get a level information list from reader.
 *
 * @author Shahaf Mordechay
 */
public class LevelSpecificationReader {

    // members
    private String levelName;
    private List<Velocity> velocities;
    private Integer paddleSpeed;
    private Integer paddleWidth;
    private Sprite background;
    private String blocksDefFile;
    private Integer startX;
    private Integer startY;
    private Integer rowHeight;
    private Integer numOfBlocks;
    private List<Block> blocks;

    /**
     * Constructs new level specification.
     */
    public LevelSpecificationReader() {
        this.levelName = null;
        this.velocities = null;
        this.paddleSpeed = null;
        this.paddleWidth = null;
        this.background = null;
        this.blocksDefFile = null;
        this.startX = null;
        this.startY = null;
        this.rowHeight = null;
        this.numOfBlocks = null;
        this.blocks = new ArrayList<>();
    }

    /**
     * Return list of level information from given reader.
     *
     * @param reader holds information of level information
     * @return list of level information.
     */
    public List<LevelInformation> fromReader(Reader reader) {

        List<LevelInformation> levelInformationList = new ArrayList<>();

        char[] charBuffer = new char[2000];

        try {
            reader.read(charBuffer);
            String levelsInfo = String.valueOf(charBuffer).replace("\r\n", "\n");
            String[] separatedLevels = levelsInfo.split("START_LEVEL");

            for (String level : separatedLevels) {
                if (!level.isEmpty() && !level.substring(0, 2).contains("#")) {
                    levelInformationList.add(getLevelInfoFromString(level));
                }
            }
        } catch (IOException e) {
            System.err.println("Failed reading object");
            e.printStackTrace(System.err);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file");
            }
        }

        return levelInformationList;
    }

    /**
     * Return level information from string.
     *
     * @param level the level information string.
     * @return new level information.
     */
    private LevelInformation getLevelInfoFromString(String level) {

        resetMembers();

        String levelInfo = level.split("START_BLOCKS")[0];
        String blocksInfo = level.split("START_BLOCKS")[1];
        String[] properties = levelInfo.split("\n");
        for (String property : properties) {
            if (!property.isEmpty()) {
                setPropertyValueFromString(property);
            }
        }

        InputStream is = null;
        Reader reader = null;
        try {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(this.blocksDefFile);
            reader = new InputStreamReader(is);
            /*File filename = new File(this.blocksDefFile);
            Reader reader = new BufferedReader(
                                new InputStreamReader(
                                    new FileInputStream(filename)));*/

            BlocksFromSymbolsFactory factory = BlocksDefinitionReader.
                                                    fromReader(reader);

            createAndAddBlocks(blocksInfo, factory);
        } finally {
        try {
            if (is != null) {
                is.close();
            }

            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            System.err.println("Failed closing file");
        }
    }

        return new Level(velocities, paddleSpeed, paddleWidth, levelName, background, blocks);
    }

    /**
     * reset this members.
     */
    private void resetMembers() {
        this.levelName = null;
        this.velocities = null;
        this.paddleSpeed = null;
        this.paddleWidth = null;
        this.background = null;
        this.blocksDefFile = null;
        this.startX = null;
        this.startY = null;
        this.rowHeight = null;
        this.numOfBlocks = null;
        this.blocks.clear();
    }

    /**
     * Set the matching property value from string.
     *
     * @param property string of property key and value.
     */
    private void setPropertyValueFromString(String property) {
        String[] keyAndValue = property.split(":");
        String key = keyAndValue[0];
        String value = keyAndValue[1];

        switch (key) {
            case "level_name":
                this.levelName = value;
                break;
            case "ball_velocities":
                String[] splittedVelocities = value.split(" ");
                this.velocities = velocitiesFromString(splittedVelocities);
                break;
            case "background":
                this.background = backgroundFromString(value);
                break;
            case "paddle_speed":
                this.paddleSpeed = Integer.parseInt(value);
                break;
            case "paddle_width":
                this.paddleWidth = Integer.parseInt(value);
                break;
            case "block_definitions":
                this.blocksDefFile = value;
                break;
            case "blocks_start_x":
                this.startX = Integer.parseInt(value);
                break;
            case "blocks_start_y":
                this.startY = Integer.parseInt(value);
                break;
            case "row_height":
                this.rowHeight = Integer.parseInt(value);
                break;
            case "num_blocks":
                this.numOfBlocks = Integer.parseInt(value);
                break;
            default:
            break;
        }
    }

    /**
     * Return a list of velocities from string.
     *
     * @param velocitiesArray string array of velocities.
     * @return list of velocities.
     */
    private List<Velocity> velocitiesFromString(String[] velocitiesArray) {
        List<Velocity> velocityList = new ArrayList<>();

        for (String velo : velocitiesArray) {
            int angle = Integer.parseInt(velo.split(",")[0]);
            int speed = Integer.parseInt(velo.split(",")[1]);
            velocityList.add(Velocity.fromAngleAndSpeed(angle, speed));
        }

        return velocityList;
    }

    /**
     * Returns a background sprite from string.
     *
     * @param backgroundString the background properties.
     * @return background sprite.
     */
    private Background backgroundFromString(String backgroundString) {
        if (backgroundString.contains("image")) {
            return new Background(ColorsParser.imageFromString(backgroundString));
        } else if (backgroundString.contains("color")) {
            Color color = ColorsParser.colorFromString(backgroundString);
            return new Background(color);
        }

        return null;
    }

    /**
     * Create and add block from string.
     *
     * @param blocksInfo the block information.
     * @param factory return matching block by symbol.
     */
    private void createAndAddBlocks(String blocksInfo,
                                    BlocksFromSymbolsFactory factory) {

        String[] lines = blocksInfo.split("\n");

        for (String line : Arrays.copyOfRange(lines, 1, lines.length)) {

            if (line.equals("END_BLOCKS")) {
                break;
            }

            int lineStartX = this.startX;

            for (int i = 0; i < line.length(); i++) {
                String symbol = line.substring(i, i + 1);
                if (factory.isSpaceSymbol(symbol)) {
                    lineStartX += factory.getSpaceWidth(symbol);
                } else if (factory.isBlockSymbol(symbol)) {
                    Block blockToAdd = factory.getBlock(symbol,
                            lineStartX, this.startY);
                    this.blocks.add(blockToAdd);
                    lineStartX += factory.getBlockWidth(symbol);
                }
            }

            this.startY += this.rowHeight;
        }

    }
}
