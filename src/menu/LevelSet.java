package menu;

/**
 * Holds information of a specific level set.
 *
 * @author Shahaf Mordechay
 */
public class LevelSet {

    // members
    private String key;
    private String message;
    private String filename;

    /**
     * Constructs a level set.
     *
     * @param key     the key of this level set.
     * @param message the level set message.
     * @param file    the file of this level set.
     */
    public LevelSet(String key, String message, String file) {
        this.key = key;
        this.message = message;
        this.filename = file;
    }

    /**
     * Return this key.
     *
     * @return this level set key.
     */
    public String getKey() {
        return key;
    }

    /**
     * Return this message.
     *
     * @return this level set message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Return this file.
     *
     * @return this level set file.
     */
    public String getFilename() {
        return filename;
    }
}
