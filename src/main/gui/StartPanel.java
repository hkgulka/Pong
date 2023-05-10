package main.gui;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {

    private int width;
    private int height;
    private JLabel icon;
    private JTextArea text;
    private JButton playButton;
    private PongApp gameApp;

    // EFFECTS: constructs a start panel with a corresponding game app, a size and background colour of panel, an
    //          icon image, a text label, and menu button
    public StartPanel(int width, int height, PongApp p) {
        this.width = width;
        this.height = height;
        gameApp = p;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setLayout(new FlowLayout(FlowLayout.CENTER, 1000, 40));
        JPanel box = new JPanel();
        box.setSize(width, height / 2);
        box.setBackground(Color.BLACK);
        add(box);
        ImageIcon image = new ImageIcon("PONG.png");
        this.icon = new JLabel(image);
        add(icon);
        initializeText();
        initializeButton();
    }

    // MODIFIES: this
    // EFFECTS: initializes the menu button for the panel
    private void initializeButton() {
        this.playButton = new JButton("PLAY");
        playButton.setActionCommand("play");

        JPanel menuArea = new JPanel();
        menuArea.setLayout(new GridLayout(0,1));
        add(menuArea);

        customizeButton(playButton, menuArea);
    }

    // MODIFIES: this
    // EFFECTS: customizes a button and adds it to p
    private void customizeButton(JButton b, JPanel p) {
        b.addActionListener(gameApp);
        b.setForeground(Color.BLACK);
        b.setBackground(Color.WHITE);
        b.setFont(new Font("Arial", Font.BOLD, 20));
        b.setFocusable(false);
        p.add(b);
    }

    // MODIFIES: this
    // EFFECTS: initializes and customizes a text component
    public void initializeText() {
        text = new JTextArea(
                "Player 1: Use 'W' and 'S' keys to move your paddle up and down."
                        + "Player 2: Use the UP and DOWN arrows to move your paddle up and down."
        );
        text.setSize(width / 2, height / 4);
        text.setFont(new Font("Arial", Font.PLAIN, 20));
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.setEditable(false);
        text.setForeground(Color.WHITE);
        text.setBackground(Color.BLACK);
        add(text);
    }
}
