package game;

import animations.AnimationRunner;
import animations.EndScreen;
import animations.HighScoresAnimation;
import animations.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import biuoop.KeyboardSensor;
import levels.LevelInformation;
import other.Counter;
import score.HighScoresTable;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Run all the game levels, end each level or the whole
 * game when needed.
 *
 * @author Shahaf Mordechay
 */
public class GameFlow {

    // player lives
    private static final int PLAYER_LIVES = 7;

    // animation stop key
    private static final String STOP_KEY = KeyboardSensor.SPACE_KEY;

    // members
    private AnimationRunner animationRunner;
    private KeyboardSensor keyboard;
    private Counter score;
    private Counter numberOfLives;

    /**
     * Constructs a new game flow in charge of running the game.
     *
     * @param ar          the game animation runner.
     * @param ks          the player keyboard sensor.
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks) {
        this.animationRunner = ar;
        this.keyboard = ks;
        this.score = new Counter();
        this.numberOfLives = new Counter(PLAYER_LIVES);
    }

    /**
     * running al game levels.
     * Go over a levels information list, initializes each level
     * and runs it.
     * Ends the level when there are no more game blocks.
     * Ends game when player have no life left.
     *
     * @param levels all the game levels information.
     */
    public void runLevels(List<LevelInformation> levels) {

        for (LevelInformation levelInfo : levels) {

            // create level
            GameLevel level = new GameLevel(levelInfo,
                    this.keyboard,
                    this.animationRunner,
                    this.score,
                    this.numberOfLives);

            // initialize level
            level.initialize();

            // run level
            while (level.getRemainingBlocks() != 0
                    && this.numberOfLives.getValue() != 0) {
                level.playOneTurn();
            }

            // end game(no more lives)
            if (this.numberOfLives.getValue() == 0) {
                break;
            }

        }
        // high score name dialog
        DialogManager dialog = this.animationRunner.getDialogManager();
        File highScores = new File("highscores");
        HighScoresTable table = new HighScoresTable(10);
        try {
            if (!highScores.exists()) {
                table.save(highScores);
            } else {
                table = HighScoresTable.loadFromFile(highScores);
                table.addToTable(this.score.getValue(), dialog);
            }
        } catch (IOException e) {
            System.err.println("Failed saving object");
            e.printStackTrace(System.err);
        }

        // end screen
        int playerLives = this.numberOfLives.getValue();
        int playerScore = this.score.getValue();
        EndScreen endScreen = new EndScreen(playerLives, playerScore);
        KeyPressStoppableAnimation stoppableEnd = new
                KeyPressStoppableAnimation(this.keyboard,
                STOP_KEY, endScreen);
        this.animationRunner.run(stoppableEnd);

        // high-scores table screen
        HighScoresAnimation scoresAnimation = new HighScoresAnimation(table);
        KeyPressStoppableAnimation stoppableScore = new
                KeyPressStoppableAnimation(this.keyboard,
                STOP_KEY, scoresAnimation);
        this.animationRunner.run(stoppableScore);
    }
}
