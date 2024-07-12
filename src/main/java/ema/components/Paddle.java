package ema.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

/**
 * This class represent the Paddle the player controls.
 */
public class Paddle extends JPanel {
    /**
     * The default diameter of the paddle
     */
    public static final int DIAMETER = 100;

    /**
     * The location of the paddle on the board. The top left of the paddle is drawing point.
     */
    private Point location;
    
    /**
     * The velocity of the paddle as it moves
     */
    private int velocity;

    /**
     * Represents the keys to move the paddle by each player. The keyCodes array will contain the key codes for
     * the Left btn, Up btn, Right btn and Down btn in that order
     */
    private int[] keyCodes;

    /**
     * Represents the region of space the paddle can move in. Consists of a left boundary, right boundary, top boundary
     * and a bottom boundary in that order.
     */
    private int[] regionBounds;

    /**
     * Determines whether the paddles are moveable or not.
     */
    private boolean isPaddleDisabled;

    /**
     * Constructs a Paddle object.
     * @param startinglocation The starting location of the paddle when the game loads.
     * @param keyCodes The button key codes for the paddles to move them.
     * @param regionBounds The boundaries that define the region in which the paddle can be moved in. The array contains the left boundary,
     * right boundary, top boundary and bottom boundary in that order.
     */
    public Paddle(Point startinglocation, int[] keyCodes, int[] regionBounds, String name) {
        this.location = startinglocation;
        this.keyCodes = keyCodes;
        this.regionBounds = regionBounds;
        super.setName(name);
        init();
    }

    /**
     * Initilizes the paddle with its default values.
     */
    public void init() {
        this.velocity = 13;
        this.isPaddleDisabled = false;
    }

    /**
     * Gets the location of the paddle on the board.
     */
    public Point getLocation() {
        return this.location;
    }

    /**
     * Gets the velocity of the paddle.
     * @return The velocity of the paddle.
     */
    public int getVelocity() {
        return this.velocity;
    }

    /**
     * Gets the buttons that are used to move the paddle.
     * @return An int array containing the button key codes
     */
    public int[] getKeyCodes() {
        return this.keyCodes;
    }

    /**
     * Gets the left boundary of the paddle.
     * @return The boundary line in the x-aixs.
     */
    public int getLeftBoundary() {
        return this.location.x;
    }

    /**
     * Gets the right boundary of the paddle.
     * @return The boundary line in the x-axis plus the paddle's diameter.
     */
    public int getRightBoundary() {
        return this.location.x + DIAMETER;
    }

    /**
     * Gets the top boundary of the paddle.
     * @return The boundary line in the y-axis.
     */
    public int getTopBoundary() {
        return this.location.y;
    }

    /**
     * Gets the bottom boundary of the paddle.
     * @return The boundary line in the y-axis plus the paddle's diameter.
     */
    public int getBottomBoundary() {
        return this.location.y + DIAMETER;
    }

    /**
     * Gets the region of space the paddle occupies.
     * @return An array containing the bounds of the region. The array contains the left boundary,
     * right boundary, top boundary and bottom boundary in that order.
     */
    public int[] getRegionBounds() {
        return this.regionBounds;
    }

    /**
     * Gets the is paddle desiabled value.
     * @return True if the paddle movement is disabled and false if the paddle movement is enabled.
     */
    public boolean getIsPaddleDisabled() {
        return this.isPaddleDisabled;
    }

    /**
     * Sets the location of the paddle to the new location provided.
     * @param newLocation The new location of the paddle.
     */
    public void setLocation(Point newLocation) {
        this.location = newLocation;
    }

    /**
     * Sets the velocity of the paddle to the new velocity.
     * @param newVelocity The new velocity of the paddle.
     */
    public void setVelocity(int newVelocity) {
        this.velocity = newVelocity;
    }

    /**
     * Sets the is paddle disbaled boolean.
     * @param truthy True to enable and false to disable.
     */
    public void setIsPaddleDisabled(boolean truthy) {
        this.isPaddleDisabled = truthy;
    }

    /**
     * Draws the paddle onto the main panel.
     * @param g The graphics of the main panel.
     */
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(location.x, location.y, DIAMETER, DIAMETER);
    }
}
