package menu;

import animations.AnimationRunner;
import biuoop.KeyboardSensor;
import game.GameFlow;
import readers.LevelSpecificationReader;

import java.io.InputStream;
import java.io.Reader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Task of game levels.
 *
 * @param <T>  the task type.
 */
public class GameFlowTask<T> implements Task<T> {

    // members
    private String file;
    private AnimationRunner runner;
    private KeyboardSensor keyboard;

    /**
     * Constructs a game flow task.
     *
     * @param file   the file to run by.
     * @param runner animation runner.
     * @param kb     player keyboard.
     */
    public GameFlowTask(String file, AnimationRunner runner, KeyboardSensor kb) {
        this.file = file;
        this.runner = runner;
        this.keyboard = kb;
    }

    /**
     * Run level by given file.
     *
     * @return void.
     */
    @Override
    public Void run() {
        GameFlow gameFlow = new GameFlow(this.runner, this.keyboard);
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(this.file);
        Reader reader = null;

        try {
            reader = new InputStreamReader(is);

            LevelSpecificationReader levels = new LevelSpecificationReader();
            gameFlow.runLevels(levels.fromReader(reader));
        } finally {
            try {
                if (is != null) {
                    is.close();
                }

                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file");
            }
        }

        return null;
    }
}
