package score;

import biuoop.DialogManager;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Table to store the historic high scores.
 * Saving and loading the table to a file.
 *
 * @author Shahaf Mordechay
 */
public class HighScoresTable implements Serializable {

    // members
    private int maxSize;
    private List<ScoreInfo> highScores;

    /**
     * Create an empty high-scores table with the specified size.
     *
     * @param size maximum elements in high-scores table.
     */
    public HighScoresTable(int size) {
        this.maxSize = size;
        this.highScores = new ArrayList<>();
    }

    /**
     * Add high-score to table.
     *
     * @param score the score to add.
     */
    public void add(ScoreInfo score) {
        if (!isHighEnough(score.getScore())) {
            return;
        }

        if (this.highScores.size() < maxSize) {
            this.highScores.add(score);
        } else if (this.highScores.size() == maxSize) {
            this.highScores.set(maxSize - 1, score);
        }
    }

    /**
     * Return the size of high-scores table.
     *
     * @return high-scores table size.
     */
    public int size() {
        return this.maxSize;
    }

    /**
     * Return the current high scores from highest to lowest.
     *
     * @return current high scores.
     */
    public List<ScoreInfo> getHighScores() {
        this.sortByScore();
        return this.highScores;
    }

    /**
     * Return the rank of the current score where highest is first.
     *
     * @param score the score to return rank of.
     * @return the given score rank.
     */
    public int getRank(int score) {

        this.sortByScore();

        for (int i = 0; i < this.highScores.size(); i++) {
            if (score == this.highScores.get(i).getScore()) {
                return i + 1;
            }
        }

        return 0;
    }

    /**
     * Clears the table.
     */
    public void clear() {
        this.highScores.clear();
    }

    /**
     * Load table data from file.
     * Current table data is cleared.
     *
     * @param filename     the file to load from.
     * @throws IOException file loading exception.
     */
    public void load(File filename) throws IOException {

        this.clear();

        ObjectInputStream inputStream = null;

        try {

            inputStream = new ObjectInputStream(
                            new FileInputStream(filename));

            HighScoresTable table = (HighScoresTable) inputStream.readObject();
            this.highScores = table.getHighScores();

        } catch (FileNotFoundException e) { // Can't find file to open
            System.err.println("Unable to find file: " + filename);
            return;
        } catch (ClassNotFoundException e) { // The class in the stream is unknown to the JVM
            System.err.println("Unable to find class for object in file: " + filename);
            return;
        } catch (IOException e) { // Some other problem
            System.err.println("Failed reading object");
            e.printStackTrace(System.err);
            return;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename);
            }
        }
    }

    /**
     * Save table data to the specified file.
     *
     * @param filename     the file to save to.
     * @throws IOException file writing exception.
     */
    public void save(File filename) throws IOException {

        ObjectOutputStream outputStream = null;

        try {
            outputStream = new ObjectOutputStream(
                            new FileOutputStream(filename));

            outputStream.writeObject(this);
        } catch (IOException e) {
            System.err.println("Failed saving object");
            e.printStackTrace(System.err);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename);
            }
        }
    }

    /**
     * Read a table from file and return it.
     * If the file does not exist, or there is a problem with
     * reading it, an empty table is returned.
     *
     * @param filename the file to load from.
     * @return high scores table or null.
     */
    public static HighScoresTable loadFromFile(File filename) {

        ObjectInputStream inputStream = null;

        try {

            inputStream = new ObjectInputStream(
                    new FileInputStream(filename));

            return (HighScoresTable) inputStream.readObject();

        } catch (FileNotFoundException e) { // Can't find file to open
            System.err.println("Unable to find file: " + filename);
            return null;
        } catch (ClassNotFoundException e) { // The class in the stream is unknown to the JVM
            System.err.println("Unable to find class for object in file: " + filename);
            return null;
        } catch (IOException e) { // Some other problem
            System.err.println("Failed reading object");
            e.printStackTrace(System.err);
            return null;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename);
            }
        }
    }

    /**
     * Tells if given score is high enough.
     *
     * @param score the score to check.
     * @return true if the score is high enough.
     */
    private boolean isHighEnough(int score) {

        if (this.getHighScores().isEmpty()) {
            return true;
        }

        this.sortByScore();
        int last = this.highScores.size() - 1;
        int lowestScore = this.highScores.get(last).getScore();

        return (this.highScores.size() == this.maxSize && score > lowestScore)
                || this.highScores.size() < this.maxSize;
    }

    /**
     * Add the given score to the table.
     *
     * @param score  the score to add.
     * @param dialog the dialog to write player name.
     */
    public void addToTable(int score, DialogManager dialog) {
        File highScoresFile = new File("highscores");
        try {
            if (!highScoresFile.exists()) {
                this.save(highScoresFile);
            }
        } catch (IOException e) {
            System.err.println("Failed saving object");
            e.printStackTrace(System.err);
        }

        try {
            this.load(highScoresFile);
        } catch (IOException e) {
            System.out.println("Failed loading high-scores table");
        }

        if (isHighEnough(score)) {
            String name = dialog.showQuestionDialog("High Score", "Enter your name:", "");
            ScoreInfo info = new ScoreInfo(name, score);
            this.add(info);
        }

        try {
            this.save(highScoresFile);
        } catch (IOException e) {
            System.out.println("Failed to save high-scores table");
        }
    }


    /**
     * Sort the high-scores table from highest to lowest.
     */
    private void sortByScore() {
        this.highScores.sort(new ScoreComperator());
    }
}
