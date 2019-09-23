package readers;

import factories.BlocksFromSymbolsFactory;

import java.io.Reader;
import java.io.IOException;

/**
 * Create a block factory from a given reader.
 *
 * @author Shahaf Mordechay
 */
public class BlocksDefinitionReader {

    /**
     * Return a new block factory from a given reader.
     *
     * @param reader create the factory by this reader.
     * @return a new block factory.
     */
    public static BlocksFromSymbolsFactory fromReader(Reader reader) {

        BlocksFromSymbolsFactory blocksFactory = new BlocksFromSymbolsFactory();

        try {
            char[] charBuffer = new char[3000];
            reader.read(charBuffer);
            String blocksDef = new String(charBuffer).replace("\r\n", "\n");

            String[] lines = blocksDef.split("\n");
            String defaultsProperties = "";

            for (String line : lines) {

                String[] splittedLine = line.split(" ");

                if (!splittedLine[0].contains("#") && !line.isEmpty()) {
                    switch (splittedLine[0]) {
                        case "default":
                            for (int i = 1; i < splittedLine.length; i++) {
                                defaultsProperties += " " + splittedLine[i];
                            }
                            break;
                        case "bdef":
                            line += defaultsProperties;
                            String blockDef = line.substring(5);
                            blocksFactory.addBlockToFactory(blockDef);
                            break;
                        case "sdef":
                            String spacerDef = line.substring(5);
                            blocksFactory.addSpacerToFactory(spacerDef);
                            break;
                        default:
                            break;
                    }
                }
            } // end of for loop
        } catch (IOException e) { // Some other problem
            System.err.println("Failed reading object");
            e.printStackTrace(System.err);
            return null;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file");
            }
        }

        return blocksFactory;
    }
}
