package main.client.model;

// Represents a paddle with a location and a y speed
public class Paddle {

    public static final int RADIUS = 2; // the vertical radius of the paddle (pixels)
    public static final int TOTAL_SPEED = 1; // the total speed of the paddle while moving

    private int x; // the x coordinate of the paddle
    private int y; // the y coordinate of the paddle
    private int dY; // the y speed of the paddle

    // REQUIRES: x_pos is an x coordinate within the screen
    // EFFECTS: creates a paddle in the default position, unmoving
    public Paddle(int x_pos) {
        this.x = x_pos;
        this.y = Game.CENTRE_Y;
        this.dY = 0;
    }

    // REQUIRES: |dir_y| = TOTAL_SPEED
    // MODIFIES: this
    // EFFECTS: changes the paddle's y speed to the given value
    public void redirectPaddle(int dir_y) {
        this.dY = dir_y;
    }

    // REQUIRES: the new y coordinate is within the bounds of the screen
    // MODIFIES: this
    // EFFECTS: moves the paddle in the direction of dY
    public void movePaddle() {
        this.y += this.dY;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getDY() {
        return this.dY;
    }
}
