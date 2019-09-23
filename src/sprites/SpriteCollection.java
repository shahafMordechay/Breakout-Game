package sprites;

import biuoop.DrawSurface;

import java.util.LinkedList;

/**
 * List of all sprites(drawable objects) in game.
 *
 * @author Shahaf Mordechay
 */
public class SpriteCollection {

    // member
    private LinkedList<Sprite> spriteList;

    /**
     * Constructs and initializes a list that will hold all of the
     * sprite objects.
     */
    public SpriteCollection() {
        this.spriteList = new LinkedList<>();
    }

    /**
     * Add the given sprite to the sprite list.
     *
     * @param s the sprite to add to sprite list.
     */
    public void addSprite(Sprite s) {
        spriteList.add(s);
    }

    /**
     * Remove the given sprite from the sprite list.
     *
     * @param s the sprite to remove from sprite list.
     */
    public void removeSprite(Sprite s) {
        this.spriteList.remove(s);
    }

    /**
     * Call the tamePassed function on all sprites.
     *
     * @param dt the amount of seconds passed since the last call.
     */
    public void notifyAllTimePassed(double dt) {

        for (int i = 0; i < this.spriteList.size(); i++) {
            spriteList.get(i).timePassed(dt);
        }
    }

    /**
     * Call drawSelection function on all sprites.
     *
     * @param d the surface to draw on(screen).
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite sprite : spriteList) {
            sprite.drawOn(d);
        }
    }
}
