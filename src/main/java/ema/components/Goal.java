package ema.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

public class Goal extends JPanel {
    private Point location;
    private Dimension size;
    private boolean isGoal;

    public Goal(Point location, String name) {
        super.setName(name);
        this.location = location;
        this.isGoal = false;
        this.size = new Dimension(15, 150);
        super.setBounds(location.x, location.y, size.width, size.height);
        this.setBackground(Color.GRAY);
    }

    public Point getLocation() {
        return this.location;
    }

    public Dimension getSize() {
        return this.size;
    }

    public int getLeftBoundary() {
        return this.location.x;
    }

    public int getRightBoundary() {
        return this.location.x + this.size.width;
    }

    public int getTopBoundary() {
        return this.location.y;
    }

    public int getBottomBoundary() {
        return this.location.y + this.size.height;
    }

    public boolean getIsGoal() {
        return this.isGoal;
    }

    public void setIsGoal(boolean truthy) {
        this.isGoal = truthy;
    }

    public void draw(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(location.x, location.y, size.width, size.height);
    }
}
