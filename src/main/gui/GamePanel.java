package main.gui;

import main.model.Ball;
import main.model.Game;
import main.model.Paddle;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private static final int MARGIN = 30;
    private Game game;
    private PongApp gameApp;

    // EFFECTS: constructs a game panel with a size and background colour of panel,
    //          updates this with the game to be displayed and initializes all components
    public GamePanel(int width, int height, Game game, PongApp m) {
        this.game = game;
        gameApp = m;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setLayout(new FlowLayout(FlowLayout.CENTER, width, MARGIN));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(game.getBallX(), game.getBallY(), Ball.DIAMETER, Ball.DIAMETER);

        g.setColor(Color.BLUE);
        g.fillRect(game.getPaddleX(1), game.getPaddleY(1), Paddle.WIDTH, Paddle.HEIGHT);

        g.setColor(Color.RED);
        g.fillRect(game.getPaddleX(2), game.getPaddleY(2), Paddle.WIDTH, Paddle.HEIGHT);
    }


}
