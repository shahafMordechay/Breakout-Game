package menu;

import java.io.Reader;
import java.io.LineNumberReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Return a list of level sets by a given reader.
 *
 * @author Shahaf Mordechay
 */
public class LevelSets {

    /**
     * Return a list of level sets from given reader.
     *
     * @param reader the information of level set.
     * @return a list of level sets from given reader.
     */
    public static List<LevelSet> levelSetsFromReader(Reader reader) {
        LineNumberReader lineReader = null;

        try {
            lineReader = new LineNumberReader(reader);

            String line;
            String key = null;
            String message = null;
            String file;
            List<LevelSet> levelSetList = new ArrayList<>();

            while ((line = lineReader.readLine()) != null) {
                if (lineReader.getLineNumber() % 2 == 0) {
                    file = line.trim();
                    levelSetList.add(new LevelSet(key, message, file));
                } else {
                    String[] splittedLine = line.trim().split(":");
                    key = splittedLine[0];
                    message = splittedLine[1];
                }
            }

            if (levelSetList.isEmpty()) {
                return null;
            }

            return levelSetList;
        } catch (IOException e) { // Some other problem
            System.err.println("Failed reading object");
            e.printStackTrace(System.err);
            return null;
        } finally {
            try {
                if (lineReader != null) {
                    lineReader.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file");
            }
        }
    }
}