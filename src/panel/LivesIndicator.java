package panel;

import biuoop.DrawSurface;
import game.GameLevel;
import other.Counter;
import sprites.Sprite;
import collidables.Block;

import java.awt.Color;

/**
 * A block with the player lives indicator.
 *
 * @author Shahaf Mordechay
 */
public class LivesIndicator implements Sprite {

    // player lives color
    private static final Color LIVES_COLOR = Color.black;

    // members
    private Block panel;
    private Counter numOfLives;

    /**
     * Constructs and initializes a new score panel.
     *
     * @param panel      the panel block.
     * @param numOfLives the number of lives to show.
     */
    public LivesIndicator(Block panel, Counter numOfLives) {
        this.panel = panel;
        this.numOfLives = numOfLives;
    }

    /**
     * draw a panel with current game score on the top of the screen.
     *
     * @param d the screen to draw on.
     */
    public void drawOn(DrawSurface d) {
        String scoreText = "Lives: " + Integer.toString(this.numOfLives.getValue());
        int xCoordinate = (int) this.panel.getCollisionRectangle().getWidth() / 4;
        int yCoordinate = (int) this.panel.getCollisionRectangle().getHeight() / 4 * 3;
        d.setColor(LIVES_COLOR);
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
     * Add this lives indicator to game sprites.
     *
     * @param g the game to add this lives indicator to.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
