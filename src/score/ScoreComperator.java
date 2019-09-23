package score;

import java.util.Comparator;

/**
 * Compare two scores(numbers).
 */
public class ScoreComperator implements Comparator {

    @Override
    public int compare(Object first, Object second) {
        if (first instanceof ScoreInfo && second instanceof ScoreInfo) {
            ScoreInfo score1 = (ScoreInfo) first;
            ScoreInfo score2 = (ScoreInfo) second;
            if (score1.getScore() < score2.getScore()) {
                return 1;
            } else if (score1.getScore() > score2.getScore()) {
                return -1;
            } else {
                return 0;
            }
        } else {
            return -1;
        }
    }
}
