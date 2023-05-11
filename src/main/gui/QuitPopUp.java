package main.gui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.StrokeBorder;
import java.awt.*;

public class QuitPopUp extends JPanel {

    private static final int WIDTH = 350;
    private static final int HEIGHT = 200;
    private static final int MARGIN = 30;
    private PongApp gameApp;
    private JLabel text;
    private JButton quitButton;
    private JButton cancelButton;

    // EFFECTS: constructs a panel with a size, background, border, and layout, and initializes components
    public QuitPopUp(PongApp p) {
        gameApp = p;
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setLayout(new FlowLayout(FlowLayout.CENTER, 1000, MARGIN));
        setBorder(new StrokeBorder(new BasicStroke(2), Color.DARK_GRAY));
        initializeText();
        initializeButtons();
    }

    // MODIFIES: this
    // EFFECTS: initializes the text and adds it to the panel
    private void initializeText() {
        text = new JLabel("ARE YOU SURE YOU WANT TO QUIT?");
        text.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 16));
        text.setForeground(Color.WHITE);
        text.setBackground(Color.BLACK);
        add(text);
    }

    // MODIFIES: this
    // EFFECTS: initializes the menu buttons for the panel
    private void initializeButtons() {
        quitButton = new JButton();
        quitButton.setActionCommand("quit");
        cancelButton = new JButton();
        cancelButton.setActionCommand("cancel");

        JPanel menuArea = new JPanel();
        menuArea.setLayout(new GridLayout(0,1));
        add(menuArea);

        customizeButton(quitButton, menuArea, "Quit.png");
        customizeButton(cancelButton, menuArea, "Continue.png");
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
