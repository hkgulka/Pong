package main.gui;

import main.model.Game;
import main.model.GameStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PongApp extends JFrame implements ActionListener, KeyListener {

    private static final int WIDTH = Game.WIDTH;
    private static final int HEIGHT = Game.HEIGHT + 40;
    private static final int INTERVAL = 50; // ms
    private Game game;
    private StartPanel stp;
    private GamePanel gp;
    private ScorePanel scp;
    private QuitPopUp qp;
    private Popup popup;
    private GameOverPanel gop;


    private Timer timer;
    private ScheduledExecutorService schedule;

    // EFFECTS: constructs a pong app with a starting window
    public PongApp() {
        super("Pong");
        System.out.println("Starting the app...");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        game = new Game();
        stp = new StartPanel(WIDTH, HEIGHT, this);
        gp = new GamePanel(WIDTH, HEIGHT, game, this);
        scp = new ScorePanel(game);
        gop = new GameOverPanel(WIDTH, HEIGHT, game);
        qp = new QuitPopUp(this);
        PopupFactory pf = new PopupFactory();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        popup = pf.getPopup(this, qp, (screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
        add(stp);
        pack();
        centreOnScreen();
        setVisible(true);

        requestFocus();
        addKeyListener(this);
        timer = new Timer(INTERVAL, e -> {
            updateGame();
            gp.repaint();
            pack();
        });
        timer.start();
    }

    // MODIFIES: this
    // EFFECTS: centres the window on the desktop
    private void centreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }

    // MODIFIES: this
    // EFFECTS: responds to each specific ActionEvent
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("play")) {
            playGame();
        } else if (e.getActionCommand().equals("quit")) {
            System.exit(0);
        } else if (e.getActionCommand().equals("cancel")) {
            returnToGame();
        }
    }

    // MODIFIES: this
    // EFFECTS: plays the game and advances beyond the starting screen
    private void playGame() {
        remove(stp);
        gp = new GamePanel(WIDTH, HEIGHT, game, this);
        scp = new ScorePanel(game);
        add(gp, BorderLayout.SOUTH);
        add(scp, BorderLayout.NORTH);
        game.startRound();
        pack();
    }

    // MODIFIES: this.game
    // EFFECTS: if the game is in progress, updates the object locations
    public void updateGame() {
        if (game.getStatus() == GameStatus.IN_PROGRESS) {
            game.update();
        } else if (game.getStatus() == GameStatus.BETWEEN_ROUNDS) {
            scp.update();
            if (game.checkWinner()) {
                game.setStatus(GameStatus.GAME_OVER);
            } else {
                game.reset();
                // wait three seconds then start next round
                schedule = Executors.newSingleThreadScheduledExecutor();
                schedule.schedule(game::startRound, 2, TimeUnit.SECONDS);

            }
        }
        if (game.getStatus() == GameStatus.GAME_OVER) {
            gameOver();
        }

    }

    // MODIFIES: this
    // EFFECTS: displays an end-game panel with game stats
    private void gameOver() {
        timer.stop();
        remove(gp);
        remove(scp);
        gop = new GameOverPanel(WIDTH, HEIGHT, game);
        add(gop);
        pack();
    }

    // MODIFIES: this
    // EFFECTS: displays a popup panel with options for quitting the game and
    //          returning to the game
    private void quitGame() {
        game.setStatus(GameStatus.PAUSED);
        PopupFactory pf = new PopupFactory();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        popup = pf.getPopup(this, qp, (screen.width - 350) / 2,
                (screen.height - 200) / 2);
        popup.show();
    }

    // MODIFIES: this
    // EFFECTS: hides the popup window displaying quitting options
    private void returnToGame() {
        popup.hide();
        game.setStatus(GameStatus.IN_PROGRESS);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // nothing
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int key = ke.getKeyCode();
        if (game.getStatus() == GameStatus.IN_PROGRESS) {
            if (key == KeyEvent.VK_W) {
                // player 1 move up
                game.redirectPaddle(1, -1);
            } else if (key == KeyEvent.VK_S) {
                // player 1 move down
                game.redirectPaddle(1, 1);
            } else if (key == KeyEvent.VK_UP) {
                // player 2 move up
                game.redirectPaddle(2, -1);
            } else if (key == KeyEvent.VK_DOWN) {
                // player 2 move down
                game.redirectPaddle(2, 1);
            } else if (key == KeyEvent.VK_ESCAPE) {
                quitGame();
            }
        } else if (game.getStatus() == GameStatus.PAUSED && key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int key = ke.getKeyCode();

        if (key == KeyEvent.VK_W) {
            // player 1 move up
            game.redirectPaddle(1, 0);
        } else if (key == KeyEvent.VK_S) {
            // player 1 move down
            game.redirectPaddle(1, 0);
        } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_KP_UP) {
            // player 2 move up
            game.redirectPaddle(2, 0);
        } else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_KP_DOWN) {
            // player 2 move down
            game.redirectPaddle(2, 0);
        }
    }
}
