package main.gui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class StartPanel extends JPanel {

    private int width;
    private int height;
    private JLabel icon;
    private JTextArea text1;
    private JTextArea text2;
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

        ImageIcon image = new ImageIcon("Pong.png");
        this.icon = new JLabel(image);
        add(icon);

        initializeText();
        initializeButton();
    }

    // MODIFIES: this
    // EFFECTS: initializes the menu button for the panel
    private void initializeButton() {
        this.playButton = new JButton();
        playButton.setActionCommand("play");

        JPanel menuArea = new JPanel();
        menuArea.setBackground(Color.BLACK);
        menuArea.setFocusable(false);
        add(menuArea);

        customizeButton(playButton, menuArea);
    }

    // MODIFIES: this
    // EFFECTS: customizes a button and adds it to panel p
    private void customizeButton(JButton b, JPanel p) {
        b.addActionListener(gameApp);
        b.setForeground(Color.WHITE);
        b.setBackground(Color.DARK_GRAY);
        b.setIcon(new ImageIcon("Play.png"));
        b.setBorder(new BevelBorder(BevelBorder.RAISED));
        b.setFocusable(false);
        p.add(b);
    }

    // MODIFIES: this
    // EFFECTS: initializes and customizes a text component
    public void initializeText() {
        text1 = new JTextArea("Player 1: Use 'W' and 'S' keys to move your paddle up and down.");
        text1.setSize(width / 2, height / 5);
        text1.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 14));
        text1.setLineWrap(true);
        text1.setWrapStyleWord(true);
        text1.setEditable(false);
        text1.setForeground(Color.WHITE);
        text1.setBackground(Color.BLACK);
        text1.setFocusable(false);
        add(text1);

        text2 = new JTextArea("Player 2: Use the UP and DOWN arrows to move your paddle up and down.");
        text2.setSize(width / 2, height / 5);
        text2.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 14));
        text2.setLineWrap(true);
        text2.setWrapStyleWord(true);
        text2.setEditable(false);
        text2.setForeground(Color.WHITE);
        text2.setBackground(Color.BLACK);
        text2.setFocusable(false);
        add(text2);
    }
}
