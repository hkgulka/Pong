package main.gui;

import main.model.Game;
import main.model.GameStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PongApp extends JFrame implements ActionListener, KeyListener {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 840;
    private Game game;
    private StartPanel stp;
    private GamePanel gp;
    private ScorePanel scp;
    private QuitPopUp qp;
    private Popup popup;
    private GameOverPanel gop;

    // EFFECTS: constructs a pong app with a starting window
    public PongApp() {
        super("Pong");
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
        } else if (e.getActionCommand().equals("leave game")) {
            quitGame();
        } else if (e.getActionCommand().equals("quit")) {
            System.exit(0);
        } else if (e.getActionCommand().equals("cancel")) {
            returnToGame();
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        // nothing
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        int key = ke.getKeyCode();

        if (key == KeyEvent.VK_W) {
            // player 1 move up
            game.redirectPaddle(1, 1);
        } else if (key == KeyEvent.VK_S) {
            // player 1 move down
            game.redirectPaddle(1, -1);
        } else if (key == KeyEvent.VK_UP) {
            // player 2 move up
            game.redirectPaddle(2, 1);
        } else if (key == KeyEvent.VK_DOWN) {
            // player 2 move down
            game.redirectPaddle(2, -1);
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
        } else if (key == KeyEvent.VK_UP) {
            // player 2 move up
            game.redirectPaddle(2, 0);
        } else if (key == KeyEvent.VK_DOWN) {
            // player 2 move down
            game.redirectPaddle(2, 0);
        }
    }

    // MODIFIES: this
    // EFFECTS: plays the game and advances beyond the starting screen
    private void playGame() {
        remove(stp);
        gp = new GamePanel(WIDTH, HEIGHT, game, this);
        scp = new ScorePanel(game);
        add(gp);
        add(scp, BorderLayout.NORTH);
        pack();

        game.startRound();
        while (game.getStatus() != GameStatus.GAME_OVER) {

            // repaint every 500 ms or something

            if (game.getStatus() == GameStatus.IN_PROGRESS) {
                // update the game
            }
            if (game.getStatus() == GameStatus.BETWEEN_ROUNDS) {
                // pause the game -- no updating
                scp.update();
                if (game.checkWinner()) {
                    game.setStatus(GameStatus.GAME_OVER);
                } else {
                    game.reset();
                    // wait three seconds then start next round
                }
            }
        }
        gameOver();
    }

    // MODIFIES: this
    // EFFECTS: displays an end-game panel with game stats
    private void gameOver() {
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
    }
}
