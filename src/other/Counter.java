package other;

/**
 * An incrementing and decrementing counter.
 *
 * @author Shahaf Mordechay
 */
public class Counter {

    // member
    private int counter;

    /**
     * Constructs and initializes a counter.
     *
     * @param start the number to start the count from.
     */
    public Counter(int start) {
        this.counter = start;
    }

    /**
     * Constructs and initializes a counter.
     */
    public Counter() {
        this.counter = 0;
    }

    /**
     * Add number to current count.
     *
     * @param number the amount to add.
     */
    public void increase(int number) {
        this.counter += number;
    }

    /**
     * Subtract number from current count.
     *
     * @param number the amount to subtract.
     */
    public void decrease(int number) {
        this.counter -= number;
    }

    /**
     * Get current count.
     *
     * @return the current count.
     */
    public int getValue() {
        return this.counter;
    }
}
