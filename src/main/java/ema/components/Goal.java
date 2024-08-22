package ema.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

/**
 * Represents the Goal on the game panel.
 */
public class Goal extends JPanel {
    /**
     * The location of the goal on the game panel.
     */
    private Point location;

    /**
     * The size of the goal.
     */
    private Dimension size;

    /**
     * Determines wether a goal has occured.
     */
    private boolean isGoal;

    /**
     * Constructs a goal and given location and sets a name.
     * @param location The location of the goal.
     * @param name The assigned name for the goal.
     */
    public Goal(Point location, String name) {
        super.setName(name);
        this.location = location;
        this.isGoal = false;
        this.size = new Dimension(15, 150);
        super.setBounds(location.x, location.y, size.width, size.height);
        this.setBackground(Color.GRAY);
    }

    /**
     * Gets the location of the goal on the game panel.
     * @return The loaction
     */
    public Point getLocation() {
        return this.location;
    }

    /**
     * Gets the size of the goal on the game panel.
     * @return The size of the goal.
     */
    public Dimension getSize() {
        return this.size;
    }

    /**
     * Gets the left boundary of the goal.
     * @return The x coordinate corresponding to the left boundary of the goal.
     */
    public int getLeftBoundary() {
        return this.location.x;
    }

    /**
     * Gets the right boundary of the goal.
     * @return The x coordinate corresponding to the right boundary of the goal.
     */
    public int getRightBoundary() {
        return this.location.x + this.size.width;
    }

    /**
     * Gets the top boundary of the goal.
     * @return The y coordinate corresponding to the top of the goal.
     */
    public int getTopBoundary() {
        return this.location.y;
    }

    /**
     * Gets the bottom boundary of the goal.
     * @return The y coordinate corresponding to the bottom of the goal. 
     */
    public int getBottomBoundary() {
        return this.location.y + this.size.height;
    }

    /**
     * Gets the isGoal result.
     * @return A boolean determining wether a goal was scored.
     */
    public boolean getIsGoal() {
        return this.isGoal;
    }

    /**
     * Sets the isGoal value.
     * @param truthy Assign a new truthy to the isGoal vlaue.
     */
    public void setIsGoal(boolean truthy) {
        this.isGoal = truthy;
    }

    /**
     * Draws the goal at the given location.
     * @param g The passed in graphics object of the container class.
     */
    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(location.x, location.y, size.width, size.height);
    }
}
