package main.client.model;

// Represents a game of pong with a ball and two paddles
public class Game {

    public static final int CENTRE_X = 1; // the x centre of the screen
    public static final int CENTRE_Y = 1; // the y centre of the screen
    private static final int PADDLE_X = 1; // the x distance from each paddle to screen edge
    public static final int WIDTH = 1; // the width of the screen in pixels
    public static final int HEIGHT = 1; // the height of the screen in pixels

    public static final int MAX_SCORE = 3; // the max score a player can have

    private client.model.Ball ball; // the ball
    private client.model.Paddle paddleL; // the left paddle
    private client.model.Paddle paddleR; // the right paddle

    private int oneScore; // the score of player 1
    private int twoScore; // the score of player 2
    private int prevWinner; // the previous winner, player 1 or 2
    private boolean gameOver; // true if the game is over

    // EFFECTS: constructs a game of pong with a ball and two paddles in default position, unmoving
    public Game() {
        ball = new client.model.Ball();
        paddleL = new client.model.Paddle(PADDLE_X);
        paddleR = new client.model.Paddle(WIDTH - PADDLE_X - 1);
        prevWinner = Random().nextInt(2) + 1;
        oneScore = 0;
        twoScore = 0;
        gameOver = false;
    }

    // MODIFIES: this
    // EFFECTS: starts the round by moving the ball in the correct direction
    public void startRound() {
        if (prevWinner == 1) {
            ball.redirectBall((-1 * client.model.Ball.TOTAL_SPEED), 0);
        } else {
            ball.redirectBall(client.model.Ball.TOTAL_SPEED, 0);
        }
    }

    // MODIFIES: this
    // EFFECTS: increases the goal count for player who scored and updates previous winner
    public void goalScored(int player) {
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
        // check if going to hit wall
        // check if going to hit paddle
        // else moves ball
    }
    
    // REQUIRES: surface is wall or paddle
    // MODIFIES: this
    // EFFECTS: determines how the ball reacts to its collision; bounce of wall, goal scored, or bounce off paddle
    public void ballCollision(String surface) {
        if (surface.equals("wall")) {
            if (ball.getX() + ball.getDX() <= 0) {
                // goal is scored, new round/update game
            } else {
                // goal is not scored, ball must bounce
                moveBall();
            }
        } else { // surface == "paddle"
            // ball bounces off of paddle
            moveBall();
        }
    }
    
    public boolean isGameOver() {
        return gameOver;
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
