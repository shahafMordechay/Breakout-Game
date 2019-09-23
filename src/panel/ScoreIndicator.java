package panel;

import biuoop.DrawSurface;
import game.GameLevel;
import other.Counter;
import sprites.Sprite;
import collidables.Block;

import java.awt.Color;

/**
 * A block with a game score indicator.
 *
 * @author Shahaf Mordechay
 */
public class ScoreIndicator implements Sprite {

    // game score color
    private static final Color SCORE_COLOR = Color.black;

    // members
    private Block scorePanel;
    private Counter score;

    /**
     * Constructs and initializes a new score panel.
     *
     * @param scorePanel the panel block.
     * @param score      the score to show.
     */
    public ScoreIndicator(Block scorePanel, Counter score) {
        this.scorePanel = scorePanel;
        this.score = score;
    }

    /**
     * draw a panel with current game score on the top of the screen.
     *
     * @param d the screen to draw on.
     */
    public void drawOn(DrawSurface d) {
        this.scorePanel.drawOn(d);
        String scoreText = "Score: " + Integer.toString(this.score.getValue());
        int xCoordinate = (int) this.scorePanel.getCollisionRectangle().getWidth() / 2;
        int yCoordinate = (int) this.scorePanel.getCollisionRectangle().getHeight() / 4 * 3;
        d.setColor(SCORE_COLOR);
        d.drawText(xCoordinate, yCoordinate, scoreText, 15);
    }

    /**
     * notify the sprite that time has passed.
     *
     * @param dt the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {

    }

    /**
     * Add this score indicator to game sprites.
     *
     * @param g the game to add this score indicator to.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
