package animations;

import biuoop.DrawSurface;
import score.HighScoresTable;
import score.ScoreInfo;

import java.awt.Color;

/**
 * Display the high-scores table.
 *
 * @author Shahaf Mordechay
 */
public class HighScoresAnimation implements Animation {

    private HighScoresTable scores;

    /**
     * Constructs a new game high-scores table animation.
     * Display the high score table.
     *
     * @param scores the high score table.
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.scores = scores;
    }

    /**
     * Draw a high-scores table.
     *
     * @param d the draw surface to draw on.
     * @param dt the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {

        int titleX = d.getWidth() / 3;
        int titleY = d.getHeight() / 10;
        d.setColor(Color.blue);
        d.drawText(titleX, titleY, "High Scores", 50);

        int frameWidth = d.getWidth() * 8 / 10;
        int frameHeight = (d.getHeight() * 8 / 10) / (scores.size() + 1);
        int lineFrameX = (d.getWidth() - frameWidth) / 2;
        int lineFrameY = titleY + frameHeight;



        for (int i = 0; i <= scores.size(); i++) {
            int currentY = lineFrameY + frameHeight * i;
            d.setColor(Color.black);
            d.drawRectangle(lineFrameX, currentY, frameWidth, frameHeight);
        }

        int nameX = (int) (lineFrameX * 1.25);
        int scoreX = nameX + frameWidth / 2;
        int textY = lineFrameY + frameHeight / 2;
        int textSize = frameWidth / frameHeight;

        d.setColor(Color.red);
        d.drawText(nameX, textY, "Name", textSize);
        d.drawText(scoreX, textY, "Score", textSize);
        textY += frameHeight;

        for (ScoreInfo info: this.scores.getHighScores()) {
            d.setColor(Color.black);
            d.drawText(nameX, textY, info.getName(), textSize);
            d.drawText(scoreX, textY,
                    Integer.toString(info.getScore()), textSize);

            textY += frameHeight;
        }
    }

    /**
     * Tells if the animations should stop.
     *
     * @return the stopping condition state.
     */
    public boolean shouldStop() {
        return false;
    }
}
