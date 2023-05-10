package main.gui;

import main.model.Game;

import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JPanel {

    private static final int MARGIN = 40;
    private Game game;
    private JLabel gameOver;
    private JLabel statsText;

    // EFFECTS: constructs a panel with a size and background, and initializes components
    public GameOverPanel(int width, int height, Game game) {
        this.game = game;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setLayout(new FlowLayout(FlowLayout.CENTER, 1000, MARGIN));
        JPanel box = new JPanel();
        box.setSize(width, height / 3);
        box.setBackground(Color.BLACK);
        add(box);
        ImageIcon image = new ImageIcon("GameOver.png");
        this.gameOver = new JLabel(image);
        add(gameOver);
        initializeStatsText();
    }


    // MODIFIES: this
    // EFFECTS: initializes the text that shows game stats and adds it to the panel
    private void initializeStatsText() {
        statsText = new JLabel("Player" + game.getPrevWinner() + "wins!");
        statsText.setFont(new Font("Arial", Font.PLAIN, 20));
        statsText.setForeground(Color.WHITE);
        statsText.setBackground(Color.BLACK);
        add(statsText);
    }
}
