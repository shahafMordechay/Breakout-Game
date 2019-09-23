package main;

import animations.AnimationRunner;
import animations.HighScoresAnimation;
import animations.KeyPressStoppableAnimation;
import biuoop.KeyboardSensor;
import menu.LevelSets;
import menu.LevelSet;
import menu.MenuAnimation;
import menu.Task;
import menu.GameFlowTask;
import score.HighScoresTable;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 * Generates a new game.
 *
 * @author Shahaf Mordechay
 */
public class Ass6Game {

    /**
     * Generates a new game.
     *
     * @param args arguments passed to main.
     */
    public static void main(String[] args) {

        AnimationRunner runner = new AnimationRunner();
        KeyboardSensor kb = runner.getKeyboard();

        Task<Void> exit = new Task<Void>() {
            @Override
            public Void run() {
                runner.closeGUI();
                System.exit(1);
                return null;
            }
        };

        Task<Void> hsTable = new Task<Void>() {
            @Override
            public Void run() {

                File highScores = new File("highscores");
                HighScoresTable table = new HighScoresTable(10);
                try {
                    if (!highScores.exists()) {
                        table.save(highScores);
                    } else {
                        table = HighScoresTable.loadFromFile(highScores);
                    }
                } catch (IOException e) {
                    System.err.println("Failed saving object");
                    e.printStackTrace(System.err);
                }

                HighScoresAnimation scoresAnimation = new
                        HighScoresAnimation(table);

                String endKey = KeyboardSensor.SPACE_KEY;
                runner.run(new KeyPressStoppableAnimation(kb, endKey, scoresAnimation));
                return null;
            }
        };

        MenuAnimation<Task<Void>> menu = new MenuAnimation<>("Menu", kb);
        menu.addSelection("h", "High-Scores table", hsTable);
        menu.addSelection("q", "close", exit);

        MenuAnimation<Task<Void>> subMenu = new MenuAnimation<>("SubMenu", kb);

        String levelSetsFile = "level_sets.txt";
        InputStream is = null;
        Reader reader = null;
        try {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(levelSetsFile);
            reader = new InputStreamReader(is);


            for (LevelSet levelSet : LevelSets.levelSetsFromReader(reader)) {
                GameFlowTask<Void> gameFlowTask = new GameFlowTask<Void>(levelSet.getFilename(), runner, kb);
                subMenu.addSelection(levelSet.getKey(), levelSet.getMessage(), gameFlowTask);
            }
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

        menu.addSubMenu("s", "Start game", subMenu);


        while (true) {
            menu.setStatus(null);
            menu.setSubStatus(null);
            subMenu.setStatus(null);
            runner.run(menu);
            Task<Void> task = menu.getStatus();
            if (menu.getSubStatus() != null) {
                runner.run(subMenu);
                task = subMenu.getStatus();
            }

            task.run();
        }
    }
}
