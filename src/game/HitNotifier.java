package game;

import hitlisteners.HitListener;

/**
 * Indicate that objects that implement it send notifications
 * when they are being hit.
 *
 * @author Shahaf Mordechay
 */
public interface HitNotifier {

    /**
     * Add given hit listener to this hit listeners list.
     *
     * @param hl the listener to add to hit listeners list.
     */
    void addHitListener(HitListener hl);

    /**
     * Remove given hit listener from this hit listeners list.
     *
     * @param hl the listener to remove from hit listeners list.
     */
    void removeHitListener(HitListener hl);
}
