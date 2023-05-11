package main.gui;

import main.model.Game;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class GameOverPanel extends JPanel {

    private static final int MARGIN = 50;
    private Game game;
    private PongApp gameApp;
    private JLabel gameOver;
    private JLabel text;
    private JButton playAgain;
    private JButton exitGame;

    // EFFECTS: constructs a panel with a size and background, and initializes components
    public GameOverPanel(int width, int height, Game game, PongApp p) {
        this.game = game;
        gameApp = p;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setLayout(new FlowLayout(FlowLayout.CENTER, 1000, MARGIN));

        JPanel box = new JPanel();
        box.setSize(width, height / 3);
        box.setBackground(Color.BLACK);
        add(box);

        ImageIcon image = new ImageIcon("Game Over.png");
        gameOver = new JLabel(image);
        add(gameOver);
        initializeText();
        initializeButtons();
    }


    // MODIFIES: this
    // EFFECTS: initializes the text that shows game stats and adds it to the panel
    private void initializeText() {
        text = new JLabel("PLAYER " + game.getPrevWinner() + " WINS!");
        text.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 16));
        text.setForeground(Color.WHITE);
        text.setBackground(Color.BLACK);
        add(text);
    }

    // MODIFIES: this
    // EFFECTS: initializes the buttons for the panel
    private void initializeButtons() {

        playAgain = new JButton();
        playAgain.setActionCommand("play again");

        exitGame = new JButton();
        exitGame.setActionCommand("quit");

        JPanel menuArea = new JPanel();
        menuArea.setLayout(new GridLayout(0,2));
        add(menuArea);

        customizeButton(playAgain, menuArea, "Play Again.png");
        customizeButton(exitGame, menuArea, "Quit.png");
    }

    // MODIFIES: this
    // EFFECTS: customizes a button and adds it to p
    private void customizeButton(JButton b, JPanel p, String file) {
        b.addActionListener(gameApp);
        b.setForeground(Color.WHITE);
        b.setBackground(Color.DARK_GRAY);
        b.setIcon(new ImageIcon(file));
        b.setBorder(new BevelBorder(BevelBorder.RAISED));
        b.setFocusable(false);
        p.add(b);
    }
}
