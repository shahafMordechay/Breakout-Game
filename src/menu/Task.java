package menu;

/**
 * Menu Task(operation).
 *
 * @param <T> task type.
 *
 * @author Shahaf Mordechay
 */
public interface Task<T> {
    /**
     * Run this task.
     *
     * @return void.
     */
    Void run();
}
