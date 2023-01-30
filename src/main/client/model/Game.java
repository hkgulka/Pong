package client.model;

// Represents a game of pong with a ball and two paddles
public class Game {

    public static final int CENTRE_X = 1; // the x centre of the screen
    public static final int CENTRE_Y = 1; // the y centre of the screen
    private static final int PADDLE_X = 1; // the x distance from each paddle to screen edge
    public static final int WIDTH = 1; // the width of the screen in pixels
    public static final int HEIGHT = 1; // the height of the screen in pixels

    public static final int MAX_SCORE = 3; // the max score a player can have

    private Ball ball; // the ball
    private Paddle paddleL; // the left paddle
    private Paddle paddleR; // the right paddle

    // EFFECTS: constructs a game of pong with a ball and two paddles in default position, unmoving
    public Game() {
        this.ball = new Ball();
        this.paddleL = new Paddle(PADDLE_X);
        this.paddleR = new Paddle(WIDTH - PADDLE_X - 1);
    }

}
