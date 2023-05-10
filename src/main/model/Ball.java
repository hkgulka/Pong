package main.model;

// Represents a ball having x and y coordinates and x and y speed components
public class Ball {

    public static final int SPEED = 8; // the max speed of each direction in the velocity
    public static final int DIAMETER = 4; // the width/height of the ball

    private int x; // the x coordinate of the ball
    private int y; // the y coordinate of the ball
    private int dX; // the x component of speed
    private int dY; // the y component of speed

    // EFFECTS: constructs an unmoving ball in the centre of the screen
    public Ball() {
        this.x = Game.CENTRE_X;
        this.y = Game.CENTRE_Y;

        this.dX = 0;
        this.dY = 0;
    }

    // REQUIRES: the length of the vector <dir_x, dir_y> = TOTAL_SPEED
    // MODIFIES: this
    // EFFECTS: changes the ball's speed to the given components
    public void redirectBall(int dir_x, int dir_y) {
        this.dX = dir_x;
        this.dY = dir_y;
    }

    // REQUIRES: the new x and y coordinates are within the bounds of the screen
    // MODIFIES: this
    // EFFECTS: moves the ball in the direction of dX and dY
    public void moveBall() {
        this.x += this.dX;
        this.y += this.dY;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getDX() {
        return this.dX;
    }

    public int getDY() {
        return this.dY;
    }
}
