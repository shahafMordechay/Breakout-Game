package panel;

import biuoop.DrawSurface;
import game.GameLevel;
import sprites.Sprite;
import collidables.Block;

import java.awt.Color;

/**
 * A block with the name of the level.
 *
 * @author Shahaf Mordechay
 */
public class LevelName implements Sprite {

    // game name color
    private static final Color NAME_COLOR = Color.black;

    // members
    private Block panel;
    private String name;

    /**
     * Constructs and initializes a new score panel.
     *
     * @param panel the panel block.
     * @param name  the level name.
     */
    public LevelName(Block panel, String name) {
        this.panel = panel;
        this.name = name;
    }

    /**
     * draw a panel with current game name on the top of the screen.
     *
     * @param d the screen to draw on.
     */
    public void drawOn(DrawSurface d) {
        String nameText = this.name;
        int xCoordinate = (int) this.panel.getCollisionRectangle().getWidth() / 4 * 3;
        int yCoordinate = (int) this.panel.getCollisionRectangle().getHeight() / 4 * 3;
        d.setColor(NAME_COLOR);
        d.drawText(xCoordinate, yCoordinate, nameText, 15);
    }

    /**
     * notify the sprite that time has passed.
     *
     * @param dt the amount of seconds passed since the last call.
     */
    public void timePassed(double dt) {

    }

    /**
     * Add this ball to game sprites.
     *
     * @param g the game to add this ball to.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }
}
