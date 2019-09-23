package menu;

import animations.Animation;

/**
 * Game menu animation.
 *
 * @param <T> menu type.
 *
 * @author Shahaf Mordechay
 */
public interface Menu<T> extends Animation {

    /**
     * Add selection option to menu.
     *
     * @param key       the key to press.
     * @param message   the message of the selection.
     * @param returnVal the operation to do.
     */
    void addSelection(String key, String message, T returnVal);

    /**
     * Add sub menu to this menu.
     *
     * @param key     the key to press.
     * @param message the message of the selection.
     * @param subMenu the sub menu to show.
     */
    void addSubMenu(String key, String message, Menu<T> subMenu);

    /**
     * Return this menu status.
     *
     * @return this menu status.
     */
    T getStatus();
}
