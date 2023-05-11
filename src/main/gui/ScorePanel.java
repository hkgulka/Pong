package main.gui;

import main.model.Game;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {

    private static final int LABEL_WIDTH = 80;
    private static final int LABEL_HEIGHT = 40;
    private Game game;
    private JLabel p1Label;
    private JLabel p2Label;

    // EFFECTS: constructs a score panel with background colour and draws the initial labels,
    //          and also updates this with the scores to be displayed
    public ScorePanel(Game game) {
        this.game = game;
        setBackground(Color.BLACK);
        p1Label = new JLabel("PLAYER ONE: " + game.getScoreOne());
        p1Label.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
        p2Label = new JLabel("PLAYER TWO: " + game.getScoreTwo());
        p2Label.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
        setLayout(new GridLayout(1, 2));
        formatText(p1Label, Color.BLUE);
        formatText(p2Label, Color.RED);
    }

    // MODIFIES: this
    // EFFECTS: formats the text for the given JLabel and adds it to the panel
    private void formatText(JLabel l, Color c) {
        l.setHorizontalAlignment(SwingConstants.CENTER);
        l.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 16));
        l.setForeground(c);
        add(l);
    }

    // MODIFIES: this
    // EFFECTS: updates the scores to match those of current game state
    public void update() {
        p1Label.setText("PLAYER ONE: " + game.getScoreOne());
        p2Label.setText("PLAYER TWO: " + game.getScoreTwo());
    }

}
