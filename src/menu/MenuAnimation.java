package menu;

import backgrounds.MenuBackground;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Game menu animation.
 *
 * @param <T> the menu type.
 *
 * @author Shahaf Mordechay
 */
public class MenuAnimation<T> implements Menu<T> {

    // members
    private String title;
    private KeyboardSensor keyboard;
    private T status;
    private Menu<T> subStatus;
    private List<Selection<T>> selections;
    private List<Selection<Menu<T>>> subMenus;
    private boolean stop;

    /**
     * Constructs new menu animation.
     *
     * @param title    the menu title.
     * @param keyboard the player keyboard.
     */
    public MenuAnimation(String title, KeyboardSensor keyboard) {
        this.title = title;
        this.keyboard = keyboard;
        this.status = null;
        this.selections = new ArrayList<>();
        this.subMenus = new ArrayList<>();
        this.stop = false;
    }

    /**
     * Add selection to this menu.
     *
     * @param key       the key to press.
     * @param message   the message of the selection.
     * @param returnVal the operation to do.
     */
    public void addSelection(String key, String message, T returnVal) {
        this.selections.add(new Selection<>(key, message, returnVal));
    }

    /**
     * Add submenu to this menu.
     *
     * @param key     the key to press.
     * @param message the message of the selection.
     * @param subMenu the sub menu to show.
     */
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.subMenus.add(new Selection<>(key, message, subMenu));
    }

    /**
     * Return this menu status.
     *
     * @return this menu status.
     */
    public T getStatus() {
        return this.status;
    }

    /**
     * Set this menu status.
     *
     * @param newStatus this menu new status.
     */
    public void setStatus(T newStatus) {
        this.status = newStatus;
    }

    /**
     * Return this sub menu status.
     *
     * @return this sub menu status.
     */
    public Menu<T> getSubStatus() {
        return this.subStatus;
    }

    /**
     * Set this sub menu status.
     *
     * @param subMenu the sub menu.
     */
    public void setSubStatus(Menu<T> subMenu) {
        this.subStatus = subMenu;
    }

    /**
     * Set this menu status or sub status.
     */
    private void setStatusVal() {
        for (Selection<T> selection: this.selections) {
            if (this.keyboard.isPressed(selection.getKey())) {
                this.status = selection.getReturnVal();
                this.stop = true;
                return;
            }
        }

        for (Selection<Menu<T>> selection : this.subMenus) {
            if (this.keyboard.isPressed(selection.getKey())) {
                this.subStatus = selection.getReturnVal();
                this.stop = true;
                return;
            }
        }
    }

    /**
     * Draw this menu frame.
     *
     * @param d the draw surface to draw on.
     * @param dt the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {

        MenuBackground background = new MenuBackground();
        background.drawOn(d);

        int titleX = d.getWidth() / 3;
        int titleY = d.getHeight() * 3 / 20;
        d.setColor(Color.orange);
        d.drawText(titleX, titleY, this.title, 100);

        int height = (d.getHeight() * 8 / 10)
                / (this.selections.size() + this.subMenus.size());

        int xCoordinate = d.getWidth() / 6;
        int yCoordinate = d.getHeight() * 2 / 10;

        for (Selection selection : this.subMenus) {
            selection.drawSelection(d, xCoordinate, yCoordinate, height);
            yCoordinate += height * 2 / 3;
        }

        for (Selection selection: this.selections) {
            selection.drawSelection(d, xCoordinate, yCoordinate, height);
            yCoordinate += height * 2 / 3;
        }

        setStatusVal();
    }

    /**
     * Tells if the animations should stop.
     *
     * @return the stopping condition state.
     */
    public boolean shouldStop() {
        if (this.status == null && this.subStatus == null) {
            this.stop = false;
        }

        return this.stop;
    }
}
