package main.model;

import java.awt.Rectangle;

// Represents a game of pong with a ball and two paddles
public class Game {

    public static final int CENTRE_X = 1; // the x centre of the screen
    public static final int CENTRE_Y = 1; // the y centre of the screen
    private static final int PADDLE_X = 1; // the x distance from each paddle to screen edge
    public static final int WIDTH = 1; // the width of the screen in pixels
    public static final int HEIGHT = 1; // the height of the screen in pixels

    public static final int MAX_SCORE = 3; // the max score a player can have

    private Ball ball; // the ball
    public Paddle paddleL; // the left paddle
    public Paddle paddleR; // the right paddle

    private int oneScore; // the score of player 1
    private int twoScore; // the score of player 2
    private int prevWinner; // the previous round winner, player 1 or 2
    private boolean gameOver; // true if the game is over
    private boolean gameOn; // true if the game is in play

    // EFFECTS: constructs a game of pong with a ball and two paddles in default position, unmoving
    public Game() {
        ball = new Ball();
        paddleL = new Paddle(PADDLE_X);
        paddleR = new Paddle(WIDTH - PADDLE_X - 1);
        prevWinner = 2;
        oneScore = 0;
        twoScore = 0;
        gameOver = false;
        gameOn = false;
    }

    // EFFECTS: constructs a new game of pong, keeping the previous winner
    public Game(int prevWinner) {
        ball = new Ball();
        paddleL = new Paddle(PADDLE_X);
        paddleR = new Paddle(WIDTH - PADDLE_X - 1);
        this.prevWinner = prevWinner;
        oneScore = 0;
        twoScore = 0;
        gameOver = false;
        gameOn = false;
    }

    // MODIFIES: this
    // EFFECTS: resets the position of the game's objects, keeping scores and previous winner
    public void reset() {
        ball = new Ball();
        paddleL = new Paddle(PADDLE_X);
        paddleR = new Paddle(WIDTH - PADDLE_X - 1);
    }

    // MODIFIES: this
    // EFFECTS: starts the round by moving the ball in the correct direction
    public void startRound() {
        gameOn = true;
        if (prevWinner == 1) {
            ball.redirectBall(-Ball.SPEED, 0);
        } else {
            ball.redirectBall(Ball.SPEED, 0);
        }
    }

    // MODIFIES: this
    // EFFECTS: increases the goal count for player who scored and updates previous winner
    public void goalScored(int player) {
        gameOn = false;
        if (player == 1) {
            prevWinner = 1;
            oneScore += 1;
        } else {
            prevWinner = 2;
            twoScore += 1;
        }
        if (oneScore >= MAX_SCORE || twoScore >= MAX_SCORE) {
            gameOver = true;
        }
    }
    
    // MODIFIES: this
    // EFFECTS: if ball is going to collide, calls collision method; else moves the ball
    public void moveBall() {
        Rectangle b = new Rectangle(ball.getX() + ball.getDX(), ball.getY() + ball.getDY(), Ball.DIAMETER, Ball.DIAMETER);
        Rectangle p1 = new Rectangle(paddleL.getX(), paddleL.getY(), Paddle.WIDTH, Paddle.HEIGHT);
        Rectangle p2 = new Rectangle(paddleR.getX(), paddleR.getY(), Paddle.WIDTH, Paddle.HEIGHT);
        // check if going to hit wall or paddle
        if (ball.getX() + ball.getDX() < 0 || ball.getX() + ball.getDX() >= WIDTH || ball.getY() + ball.getDY() < 0 || ball.getY() + ball.getDY() >= HEIGHT) {
            ballCollision("wall");
        } else if (b.intersects(p1)) {
            ballCollision("paddleL");
        } else if (b.intersects(p2)) {
            ballCollision("paddleR");
        } else {
            ball.moveBall();
        }
    }
    
    // REQUIRES: surface is "wall" or "paddleL" or "paddleR"
    // MODIFIES: this
    // EFFECTS: determines how the ball reacts to its collision; bounce of wall, goal scored, or bounce off paddle
    public void ballCollision(String surface) {
        if (surface.equals("wall")) {
            if (ball.getX() + ball.getDX() < 0) {
                // goal is scored, new round/update game
                goalScored(2);
            } else if (ball.getX() + ball.getDX() >= WIDTH) {
                // goal is scored
                goalScored(1);
            } else { // goal not scored, ball bounces off top or bottom wall
                ball.redirectBall(ball.getDX(), -ball.getDY());
                ball.moveBall();
            }
        } else if (surface.equals("paddleL")){
            // ball bounces off of left paddle
            if (paddleL.getDY() == 0) {
                ball.redirectBall(-ball.getDX(), ball.getDY());
            } else if (paddleL.getDY() > 0) {
                ball.redirectBall(Ball.SPEED, Ball.SPEED);
            } else {
                ball.redirectBall(Ball.SPEED, -Ball.SPEED);
            }
            ball.moveBall();
        } else { // ball bounces off of right paddle
            if (paddleR.getDY() == 0) {
                ball.redirectBall(-ball.getDX(), ball.getDY());
            } else if (paddleR.getDY() > 0) {
                ball.redirectBall(-Ball.SPEED, Ball.SPEED);
            } else {
                ball.redirectBall(-Ball.SPEED, -Ball.SPEED);
            }
            ball.moveBall();
        }
    }
    
    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isGameOn() {
        return gameOn;
    }
    
    public int getPrevWinner() {
        return prevWinner;
    }
    
    public int getOneScore() {
        return oneScore;
    }
    
    public int getTwoScore() {
        return twoScore;
    }




}
