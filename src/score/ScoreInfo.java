package score;

import java.io.Serializable;

/**
 * Holds the score and the name of the player in a specific
 * game session.
 *
 * @author Shahaf Mordechay
 *
 */
public class ScoreInfo implements Serializable {

    // members
    private String name;
    private int score;

    /**
     * Holds information about player score and name in one game.
     *
     * @param name  player name.
     * @param score game score.
     */
    public ScoreInfo(String name, int score) {
        this.name = name;
        this.score = score;
    }

    /**
     * Return the player name.
     *
     * @return the player name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Return the player score.
     *
     * @return the player score.
     */
    public int getScore() {
        return this.score;
    }
}
