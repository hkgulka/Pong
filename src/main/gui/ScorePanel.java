package main.gui;

import main.model.*;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {

    private static final int LABEL_WIDTH = 200;
    private static final int LABEL_HEIGHT = 40;
    private Game game;
    private JLabel p1Label;
    private JLabel p2Label;

    // EFFECTS: constructs a score panel with background colour and draws the initial labels,
    //          and also updates this with the game guesses and matches to be displayed
    public ScorePanel(Game game) {
        this.game = game;
        setBackground(Color.BLACK);
        p1Label = new JLabel("PLAYER ONE: " + game.getScoreOne());
        p1Label.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
        p2Label = new JLabel("PLAYER TWO: " + game.getScoreTwo());
        p2Label.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
        add(p1Label);
        add(Box.createHorizontalStrut(10));
        add(p2Label);
        formatText(p1Label, Color.BLUE);
        formatText(p2Label, Color.RED);
    }

    // MODIFIES: this
    // EFFECTS: formats the text for the given JLabel and adds it to the panel
    private void formatText(JLabel l, Color c) {
        l.setFont(new Font("Arial", Font.PLAIN, 14));
        l.setForeground(c);
        add(l);
    }

    // MODIFIES: this
    // EFFECTS: updates the number of guesses and matches to match those of current game state
    public void update() {
        p1Label.setText("PLAYER ONE: " + game.getScoreOne());
        p2Label.setText("PLAYER TWO: " + game.getScoreTwo());
    }

}
