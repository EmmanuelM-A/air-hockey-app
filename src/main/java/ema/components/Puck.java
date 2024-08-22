package ema.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JPanel;

import ema.audio.AudioPlayer;
import ema.mechanics.PhysicsEngine;

/**
 * Represents the puck
 */
public class Puck extends JPanel {
    /**
     * The default diameter of the puck.
     */
    public static final int DIAMETER = 50;

    /**
     * The location of the puck on the table.
     */
    private Point location;

    /**
     * The velocity of the puck in the x-axis.
     */
    private double xVelocity;

    /**
     * The velocity of the puck in the y-axis.
     */
    private double yVelocity;

    /**
     * The distance between the puck's (assigned) centre and the paddle's (assigned) centre.
     */
    private double distance;

    /**
     * The length of the overlap when the puck overlaps with a paddle.
     */
    private int overlap;

    /**
     * An instance of the puck class (as there will only be one instance of the puck class created).
     */
    public static Puck instance;
    
    /**
     * Creates an instance of the puck class at the provided starting location.
     * @param startingLocation The location of the puck when the game is loaded.
     */
    public Puck(Point startingLocation) {
        this.location = startingLocation;
        init();

        instance = this;
    }

    /**
     * Initialises and assigns the variables their inital variables. 
     */
    public void init() {
        this.xVelocity = 0.0;
        this.yVelocity = 0.0;
    }

    /**
     * Gets the location of the puck on the game panel.
     * @return The location 
     */
    public Point getLocation() {
        return this.location;
    }

    /**
     * Gets the overlap between the puck and any component on the game panel.
     * @return The overlap
     */
    public int getOverlap() {
        return this.overlap;
    }

    /**
     * Gets the x velocity of the puck.
     * @return The x velocity.
     */
    public double getXVelocity() {
        return this.xVelocity;
    }

    /**
     * Gets the y velocity of the puck.
     * @return The y velocity.
     */
    public double getYVelocity() {
        return this.yVelocity;
    }

    /**
     * Gets the distance between the puck's centre and paddle's centre.
     * @return The distance
     */
    public double getDistance() {
        return this.distance;
    }

    /**
     * Gets the left boundary of the puck.
     * @return The x coordinate corresponding to puck's left boudnary.
     */
    public int getLeftBoundary() {
        return this.location.x;
    }

    /**
     * Gets the right boundary of the puck.
     * @return The x coordinate corresponding to the puck's right boudnary.
     */
    public int getRightBoundary() {
        return this.location.x + DIAMETER;
    }

    /**
     * Gets the top boundary of the puck.
     * @return The y coordinate corresponding to the puck's top boundary.
     */
    public int getTopBoundary() {
        return this.location.y;
    }

    /**
     * Gets the bottom boundary of the puck.
     * @return The y coordinate corresponding to the puck's bottom boundary.
     */
    public int getBottomBoundary() {
        return this.location.y + DIAMETER;
    }

    /**
     * Sets the location of the puck to the inputted location.
     * @param The new location.
     */
    public void setLocation(Point newLocation) {
        this.location = newLocation;
    }

    /**
     * Sets the x velocity of the puck to a new x velocity.
     * @param newxVelocity The new x velocity.
     */
    public void setXVelocity(double newxVelocity) {
        this.xVelocity = newxVelocity;
    }

    /**
     * Sets the y velocity of the puck to a new y velocity.
     * @param newyVelocity The y velocity.
     */
    public void setYVelocity(double newyVelocity) {
        this.yVelocity = newyVelocity;
    }

    /**
     * Moves the puck by a given amount, determined by the puck's velocity.
     */
    public void move() {
        int x = (int)(this.getLocation().x + xVelocity);
        int y = (int)(this.getLocation().y + yVelocity);
        this.setLocation(new Point(x, y));
    }

    public boolean collides(Paddle paddle) {
        Point paddleLocation = paddle.getLocation();
        Point puckLocation = this.getLocation();
    
        // Calculate the distance between the centers of the puck and the paddle
        this.distance = Math.sqrt(Math.pow((double)(puckLocation.x + DIAMETER / 2 - (paddleLocation.x + Paddle.DIAMETER / 2)), 2.0) + 
                                    Math.pow((double)(puckLocation.y + DIAMETER / 2 - (paddleLocation.y + Paddle.DIAMETER / 2)), 2.0));
                                    
        int radiusSum = (DIAMETER / 2) + (Paddle.DIAMETER / 2);

        this.overlap = (int)(radiusSum - distance);
    
        return distance < radiusSum;
    }

    public void handleCollsions(Paddle paddle) {
        if(collides(paddle)) {
            double[] newPuckVelocity = PhysicsEngine.collisions(paddle);

            this.setXVelocity(newPuckVelocity[0]);
            this.setYVelocity(newPuckVelocity[1]);

            // Play the collision sound effect
            AudioPlayer.play("collision.wav"); 
        }
    }

    public void handleWallCollisions(int right, int top, int bottom, int left) {
        // Checks for collision with the top wall
        if (location.y <= top) {
            this.setYVelocity(this.getYVelocity() * -1);
            location.y = top;
            AudioPlayer.play("bounce.wav");
        }
        // Checks for collision with the bottom wall
        if (location.y + DIAMETER >= bottom) {
            this.setYVelocity(this.getYVelocity() * -1);
            location.y = bottom - DIAMETER;
            AudioPlayer.play("bounce.wav");
        }
        // Checks for collision with the right wall
        if (location.x + DIAMETER >= right) {
            this.setXVelocity(this.getXVelocity() * -1);
            location.x = right - DIAMETER;
            AudioPlayer.play("bounce.wav");
        }
        // Checks for collision with the left wall
        if (location.x <= left) {
            this.setXVelocity(this.getXVelocity() * -1);
            location.x = left;
            AudioPlayer.play("bounce.wav");
        }
    }

    public boolean goalDetected(Goal goal) {
        // Get the puck and goal boundaries
        Rectangle puckBounds = new Rectangle(location.x, location.y, DIAMETER, DIAMETER);
        Rectangle goalBounds = new Rectangle(goal.getLocation().x, goal.getLocation().y, goal.getSize().width, goal.getSize().height);

        // Check if the puck has crossed the goal boundaries
        return puckBounds.intersects(goalBounds);
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(location.x, location.y, DIAMETER, DIAMETER);
    }

    public boolean isStationary() {
        if(yVelocity == 0.0 && xVelocity == 0.0) {
            return true;
        } else {
            return false;
        }
    }
}
