package main.model;

import java.awt.Rectangle;

// Represents a game of pong with a ball and two paddles
public class Game {

    public static final int WIDTH = 800; // the width of the screen in pixels
    public static final int HEIGHT = 500; // the height of the screen in pixels
    private static final int PADDLE_X = 30; // the x distance from each paddle to screen edge
    public static final int MAX_SCORE = 3; // the max score a player can have

    private Ball ball; // the ball
    private Paddle paddle1; // the left paddle
    private Paddle paddle2; // the right paddle
    private int score1; // the current score of player 1
    private int score2; // the current score of player 2
    private int prevWinner; // the previous round winner, player 1 or 2
    private GameStatus status; // the current status of the game

    // EFFECTS: constructs a game of pong with a ball and two paddles in default position, unmoving
    public Game() {
        ball = new Ball();
        paddle1 = new Paddle(PADDLE_X);
        paddle2 = new Paddle(WIDTH - PADDLE_X - 1);
        score1 = 0;
        score2 = 0;
        prevWinner = 2;
        status = GameStatus.NEW;
    }

    // EFFECTS: constructs a new game of pong, keeping the previous winner
    public Game(int prevWinner) {
        ball = new Ball();
        paddle1 = new Paddle(PADDLE_X);
        paddle2 = new Paddle(WIDTH - PADDLE_X - 1);
        this.prevWinner = prevWinner;
        status = GameStatus.NEW;
    }

    // MODIFIES: this
    // EFFECTS: starts the round by moving the ball in the correct direction
    public void startRound() {
        status = GameStatus.IN_PROGRESS;
        if (prevWinner == 2) {
            ball.redirectBall(-Ball.SPEED, 0);
        } else {
            ball.redirectBall(Ball.SPEED, 0);
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the current state of the game
    public void update() {
        movePaddles();
        moveBall();
    }

    // MODIFIES: this
    // EFFECTS: resets the position of the game's objects, keeping scores and previous winner
    public void reset() {
        ball = new Ball();
        paddle1 = new Paddle(PADDLE_X);
        paddle2 = new Paddle(WIDTH - PADDLE_X - 1);
    }


    // MODIFIES: this
    // EFFECTS: increases the goal count for player who scored and updates previous winner
    public void goalScored(int player) {
        if (player == 1) {
            prevWinner = 1;
            score1 += 1;
        } else {
            prevWinner = 2;
            score2 += 1;
        }
        status = GameStatus.BETWEEN_ROUNDS;
    }

    // MODIFIES: this, status
    // EFFECTS: returns true if there is a game winner
    public void checkWinner() {
        if (score1 >= MAX_SCORE || score2 >= MAX_SCORE) {
            status = GameStatus.GAME_OVER;
        }
    }


    // REQUIRES: player is 1 or 2, direction is -1 or 0 or 1
    // MODIFIES: this, paddle1, paddle2
    // EFFECTS: changes the corresponding paddle to the given direction
    public void redirectPaddle(int player, int direction) {
        if (player == 1) {
            if (direction == 1) {
                paddle1.redirectPaddle(Paddle.SPEED);
            } else if (direction == -1) {
                paddle1.redirectPaddle(-Paddle.SPEED);
            } else {
                paddle1.redirectPaddle(0);
            }
        } else if (player == 2) {
            if (direction == 1) {
                paddle2.redirectPaddle(Paddle.SPEED);
            } else if (direction == -1) {
                paddle2.redirectPaddle(-Paddle.SPEED);
            } else {
                paddle2.redirectPaddle(0);
            }
        }
    }

    // MODIFIES: this, paddle1, paddle2
    // EFFECTS: moves the paddles one frame in the given direction/speed
    public void movePaddles() {
        if (paddle1.getY() + paddle1.getDY() >= 0 && paddle1.getY() + paddle1.getDY() + Paddle.HEIGHT < HEIGHT) {
            paddle1.movePaddle();
        }
        if (paddle2.getY() + paddle2.getDY() >= 0 && paddle2.getY() + paddle2.getDY() + Paddle.HEIGHT < HEIGHT) {
            paddle2.movePaddle();
        }
    }

    // MODIFIES: this, ball
    // EFFECTS: if ball is going to collide, calls collision method; else moves the ball
    public void moveBall() {
        Rectangle b = new Rectangle(ball.getX() + ball.getDX(), ball.getY() + ball.getDY(), Ball.DIAMETER, Ball.DIAMETER);
        Rectangle p1 = new Rectangle(paddle1.getX(), paddle1.getY(), Paddle.WIDTH, Paddle.HEIGHT);
        Rectangle p2 = new Rectangle(paddle2.getX(), paddle2.getY(), Paddle.WIDTH, Paddle.HEIGHT);
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
    // MODIFIES: this, ball
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
        } else if (surface.equals("paddleL")) {
            // ball bounces off of left paddle
            if (paddle1.getDY() == 0) {
                ball.redirectBall(-ball.getDX() - 1, ball.getDY());
            } else if (paddle1.getDY() > 0) {
                ball.redirectBall(Ball.SPEED + 1, Ball.SPEED + 1);
            } else {
                ball.redirectBall(Ball.SPEED + 1, -Ball.SPEED - 1);
            }
            ball.moveBall();
        } else if (surface.equals("paddleR")) {
            if (paddle2.getDY() == 0) {
                ball.redirectBall(-ball.getDX() - 1, ball.getDY());
            } else if (paddle2.getDY() > 0) {
                ball.redirectBall(-Ball.SPEED - 1, Ball.SPEED + 1);
            } else {
                ball.redirectBall(-Ball.SPEED - 1, -Ball.SPEED - 1);
            }
            ball.moveBall();
        }
    }

    public void setStatus(GameStatus s) {
        status = s;
    }

    public GameStatus getStatus() {
        return status;
    }

    public int getPrevWinner() {
        return prevWinner;
    }
    
    public int getScoreOne() {
        return score1;
    }

    public int getScoreTwo() {
        return score2;
    }

    public int getBallX() {
        return ball.getX();
    }

    public int getBallY() {
        return ball.getY();
    }

    // EFFECTS: returns the x coordinate of the given paddle
    public int getPaddleX(int num) {
        if (num == 1) {
            return paddle1.getX();
        } else {
            return paddle2.getX();
        }

    }

    // EFFECTS: returns the y coordinate of the given paddle
    public int getPaddleY(int num) {
        if (num == 1) {
            return paddle1.getY();
        } else {
            return paddle2.getY();
        }

    }
}
