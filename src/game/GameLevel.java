package game;

import animations.Animation;
import animations.AnimationRunner;
import animations.KeyPressStoppableAnimation;
import animations.PauseScreen;
import animations.CountdownAnimation;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import hitlisteners.HitListener;
import hitlisteners.BlockRemover;
import hitlisteners.BallRemover;
import hitlisteners.ScoreTrackingListener;
import levels.LevelInformation;
import other.Counter;
import other.Velocity;
import sprites.Sprite;
import panel.LivesIndicator;
import panel.ScoreIndicator;
import sprites.Ball;
import panel.LevelName;
import collidables.Block;
import collidables.Collidable;
import collidables.Paddle;
import geometry.Point;
import geometry.Rectangle;
import sprites.SpriteCollection;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

/**
 * Creates all the level objects and properties, according to
 * given information.
 *
 * @author Shahaf Mordechay
 */
public class GameLevel implements Animation {

    // game fixed properties
    private static final Color PANEL_COLOR = Color.lightGray;
    private static final Color FRAME_COLOR = Color.darkGray;
    private static final int PADDLE_HEIGHT = 15;
    private static final int RADIUS = 5;

    // animation stop key
    private static final String STOP_KEY = KeyboardSensor.SPACE_KEY;

    // members
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private Paddle paddle;
    private Counter remainingBlocks;
    private Counter remainingBalls;
    private Counter score;
    private Counter numberOfLives;
    private AnimationRunner runner;
    private boolean running;
    private KeyboardSensor keyboard;
    private LevelInformation information;

    /**
     * Constructs and initializes a new game.
     *
     * @param information   the level game information.
     * @param ks            the user keyboard sensor.
     * @param runner        this level animations runner.
     * @param score         counter of the player game score.
     * @param numberOfLives counter of the player lives.
     */
    public GameLevel(LevelInformation information, KeyboardSensor ks,
                     AnimationRunner runner, Counter score,
                     Counter numberOfLives) {

        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.remainingBlocks = new Counter();
        this.remainingBalls = new Counter();
        this.score = score;
        this.numberOfLives = numberOfLives;
        this.runner = runner;
        this.running = false;
        this.keyboard = ks;
        this.information = information;
    }

    /**
     * Return this level remaining game blocks.
     *
     * @return this level remaining blocks.
     */
    public int getRemainingBlocks() {
        return this.remainingBlocks.getValue();
    }

    /**
     * Add given collidable to the game environment.
     *
     * @param c the collidable to add.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Add given sprite to the game sprites.
     *
     * @param s the sprite to add.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Remove given collidable from the game environment.
     *
     * @param c the collidable to remove.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Remove given sprite from the game sprites.
     *
     * @param s the sprite to remove.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Create the frame blocks of the game including
     * the bottom death-region.
     */
    private void createFrame() {

        int screenWidth = this.runner.getWidth();
        int screenHeight = this.runner.getHeight();
        int frameSize = this.runner.getFrameSize();

        this.sprites.addSprite(this.information.getBackground());

        // create frame (left, right, top)
        Rectangle leftSideBlock = new Rectangle(0, 0, frameSize, screenHeight);
        Block leftFrame = new Block(leftSideBlock, FRAME_COLOR);
        leftFrame.addToGame(this);

        Rectangle rightSideBlock = new Rectangle(screenWidth - frameSize, 0,
                frameSize, screenHeight);
        Block rightFrame = new Block(rightSideBlock, FRAME_COLOR);
        rightFrame.addToGame(this);

        Rectangle upperBlock = new Rectangle(0, frameSize, screenWidth, frameSize);
        Block upperFrame = new Block(upperBlock, FRAME_COLOR);
        upperFrame.addToGame(this);

        // death-region
        Rectangle lowerBlock = new Rectangle(0, screenHeight + 2 * RADIUS, screenWidth, frameSize);
        Block deathRegion = new Block(lowerBlock, FRAME_COLOR);
        BallRemover ballRemover = new BallRemover(this, this.remainingBalls);
        deathRegion.addHitListener(ballRemover);
        deathRegion.addToGame(this);

        // life, score and level name panel
        Rectangle panelShape = new Rectangle(0, 0, screenWidth, frameSize);
        Block panel = new Block(panelShape, PANEL_COLOR);
        ScoreTrackingListener scoreTracking = new ScoreTrackingListener(this.score);
        panel.addHitListener(scoreTracking);
        ScoreIndicator scoreIndicator = new ScoreIndicator(panel, this.score);
        scoreIndicator.addToGame(this);
        LivesIndicator livesIndicator = new LivesIndicator(panel, this.numberOfLives);
        livesIndicator.addToGame(this);
        LevelName name = new LevelName(panel, this.information.levelName());
        name.addToGame(this);
    }

    /**
     * Create a new paddle and add it to the game.
     */
    private void createPaddleAndAddToGame() {

        int screenWidth = this.runner.getWidth();
        int frameSize = this.runner.getFrameSize();
        int paddleWidth = this.information.paddleWidth();
        Color paddleColor = Color.yellow;

        // paddle start position
        int paddleX = screenWidth / 2 - paddleWidth / 2;
        int paddleY = (int) (this.runner.getHeight() * 0.9);

        // create paddle rectangle
        Rectangle paddleRectangle = new Rectangle(paddleX, paddleY,
                                        paddleWidth, PADDLE_HEIGHT);

        // create paddle block
        Block paddleBlock = new Block(paddleRectangle, paddleColor);

        // create paddle
        this.paddle = new Paddle(paddleBlock, this.keyboard,
                frameSize, screenWidth - frameSize);

        // set paddle speed
        this.paddle.setSpeed(this.information.paddleSpeed());

        // add paddle to game
        this.paddle.addToGame(this);
    }

    /**
     * Create a new ball and add it to the game.
     *
     * @param velocity ball wanted velocity.
     */
    private void createBallAndAddToGame(Velocity velocity) {

        // ball start point
        double centerX = this.runner.getWidth() / 2;
        double centerY = this.runner.getHeight() * 0.9 - RADIUS;
        Color ballColor = Color.white;

        Point center = new Point(centerX, centerY);
        Ball ball = new Ball(center, RADIUS, ballColor, this.environment);
        ball.setVelocity(velocity);
        ball.addToGame(this);
        this.remainingBalls.increase(1);
    }

    /**
     * Create balls according to given parameter.
     *
     * @param numOfBalls the number of balls to create.
     */
    private void createBalls(int numOfBalls) {

        for (int i = 0; i < numOfBalls; i++) {
            createBallAndAddToGame(this.information.initialBallVelocities().get(i));
        }
    }

    /**
     * Initialize a new game: create the Blocks (and Paddle) and Ball
     * and add them to the game.
     */
    public void initialize() {
        BlockRemover blockRemover = new BlockRemover(this, remainingBlocks);
        ScoreTrackingListener scoreTracking = new ScoreTrackingListener(this.score);
        List<HitListener> hitListeners = Arrays.asList(blockRemover, scoreTracking);

        // create frame
        createFrame();


        int numOfBlocks = this.information.numberOfBlocksToRemove();
        for (int i = 0; i < numOfBlocks; i++) {
            for (HitListener hitListener : hitListeners) {
                this.information.blocks().get(i).addHitListener(hitListener);
            }
            this.information.blocks().get(i).addToGame(this);
            this.remainingBlocks.increase(1);
        }

        // create paddle
        createPaddleAndAddToGame();
    }

    /**
     * Stop the animations running if there are no more balls
     * or no more blocks.
     *
     * @return this running member opposite state.
     */
    public boolean shouldStop() {
        if (this.remainingBalls.getValue() == 0) {
            this.numberOfLives.decrease(1);
            this.paddle.removeFromGame(this);
            createPaddleAndAddToGame();
            this.running = false;
        }

        if (this.remainingBlocks.getValue() == 0) {
            this.score.increase(100);
            this.running = false;
        }

        return !this.running;
    }

    /**
     * Draw a game frame to the screen.
     *
     * @param d  the draw surface to draw on.
     * @param dt the amount of seconds passed since the last call.
     */
    public void doOneFrame(DrawSurface d, double dt) {

        // display pause screen
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(
                    this.keyboard, STOP_KEY, new PauseScreen()));
        }

        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed(dt);
    }

    /**
     * Run a game session: generates a gui and starts the animations loop.
     */
    public void playOneTurn() {
        createBalls(this.information.numberOfBalls());
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));
        this.running = true;
        this.runner.run(this);
    }
}
