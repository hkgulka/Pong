package main.gui;

import main.model.Game;
import main.model.GameStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PongApp extends JFrame implements ActionListener, KeyListener {

    private static final int WIDTH = Game.WIDTH;
    private static final int HEIGHT = Game.HEIGHT;
    private static final int INTERVAL = 30; // ms
    private Game game;
    private StartPanel stp;
    private GamePanel gp;
    private ScorePanel scp;
    private QuitPopUp qp;
    private Popup popup;
    private GameOverPanel gop;

    private Timer timer;
    private ScheduledExecutorService schedule;


    // EFFECTS: constructs a pong app with all of its panels, timer, schedule, and key listener
    public PongApp() {
        super("Pong");
        System.out.println("Starting the app...");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        game = new Game();
        stp = new StartPanel(WIDTH, HEIGHT, this);
        gp = new GamePanel(WIDTH, HEIGHT, game, this);
        scp = new ScorePanel(game);
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
        schedule = Executors.newSingleThreadScheduledExecutor();
        addTimer();
    }

    // MODIFIES: this
    // EFFECTS: centres the window on the desktop
    private void centreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }

    // MODIFIES: this
    // EFFECTS: adds a timer that updates and repaints the game every INTERVAL ms
    private void addTimer() {
        timer = new Timer(INTERVAL, e -> {
            updateGame();
            gp.repaint();
        });
    }

    // MODIFIES: this
    // EFFECTS: responds to each specific button press
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("play")) {
            playGame();
        } else if (e.getActionCommand().equals("quit")) {
            System.exit(0);
        } else if (e.getActionCommand().equals("cancel")) {
            returnToGame();
        } else if (e.getActionCommand().equals("play again")) {
            playAgain();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // nothing
    }

    // EFFECTS: responds to each given key press
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
            } else if (key == KeyEvent.VK_UP || key == KeyEvent.VK_KP_UP) {
                // player 2 move up
                game.redirectPaddle(2, -1);
            } else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_KP_DOWN) {
                // player 2 move down
                game.redirectPaddle(2, 1);
            } else if (key == KeyEvent.VK_ESCAPE) {
                quitGame();
            }
        }
    }

    // EFFECTS: responds to each given key release
    @Override
    public void keyReleased(KeyEvent ke) {
        int key = ke.getKeyCode();
        if (game.getStatus() == GameStatus.IN_PROGRESS) {
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

    // MODIFIES: this
    // EFFECTS: plays the game and advances beyond the starting screen
    private void playGame() {
        remove(stp);
        add(gp, BorderLayout.SOUTH);
        add(scp, BorderLayout.NORTH);
        game.startRound();
        pack();
        schedule.schedule(timer::start, 2, TimeUnit.SECONDS);
    }

    // EFFECTS: resets the game based on the previous winner and starts a new round
    private void playAgain() {
        int prevWinner = game.getPrevWinner();
        game = new Game(prevWinner);
        gp = new GamePanel(WIDTH, HEIGHT, game, this);
        scp = new ScorePanel(game);
        add(gp, BorderLayout.SOUTH);
        add(scp, BorderLayout.NORTH);
        remove(gop);
        pack();
        game.startRound();
        schedule.schedule(timer::start, 2, TimeUnit.SECONDS);
    }

    // MODIFIES: game
    // EFFECTS: if the game is in progress, updates the object locations
    public void updateGame() {
        if (game.getStatus() == GameStatus.IN_PROGRESS) {
            game.update();
        } else if (game.getStatus() == GameStatus.BETWEEN_ROUNDS) {
            scp.update();
            game.checkWinner(); // if game is over, changes status
            if (game.getStatus() == GameStatus.BETWEEN_ROUNDS) {
                game.reset();
                // wait three seconds then start next round
                schedule.schedule(game::startRound, 2, TimeUnit.SECONDS);

            } else if (game.getStatus() == GameStatus.GAME_OVER) {
                gameOver();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: displays an end-game panel with the winner and options to play again or quit
    private void gameOver() {
        timer.stop();
        remove(gp);
        remove(scp);
        gop = new GameOverPanel(WIDTH, HEIGHT, game, this);
        add(gop);
        pack();
    }

    // MODIFIES: this, game
    // EFFECTS: displays a popup panel with options for quitting the game and
    //          returning to the game, pausing the game
    private void quitGame() {
        game.setStatus(GameStatus.PAUSED);
        PopupFactory pf = new PopupFactory();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        popup = pf.getPopup(this, qp, (screen.width - 350) / 2,
                (screen.height - 200) / 2);
        popup.show();
    }

    // MODIFIES: this, game
    // EFFECTS: hides the popup window displaying quitting options and unpauses the game
    private void returnToGame() {
        popup.hide();
        game.setStatus(GameStatus.IN_PROGRESS);
    }
}
