package ema.mechanics;

import java.awt.Point;

import javax.swing.JPanel;

import ema.components.AIPaddle;
import ema.components.Paddle;
import ema.components.Puck;
import ema.ui.game.singlePlayer.SinglePlayerGame;

public class AIPaddleControls {
    public static void movePaddle(AIPaddle paddle, JPanel playableArea, double speedMultiplier) {
        if (!paddle.getIsPaddleDisabled()) {
            // Get the current location of the paddle
            Point paddleLocation = paddle.getLocation();
            int x = paddleLocation.x;
            int y = paddleLocation.y;

            // Get the puck's current position
            Point puckLocation = Puck.instance.getLocation();
            int puckX = puckLocation.x;
            int puckY = puckLocation.y;

            // Determine if the puck is on the AI's side of the table
            boolean isPuckOnAISide = puckX < (SinglePlayerGame.PANEL_WIDTH / 2);

            // Adjust the paddle's velocity based on the speed muliplier
            int adjustedVelocity = (int) (paddle.getVelocity() * speedMultiplier);

            if (isPuckOnAISide) {
                if (Puck.instance.isStationary()) {
                    // Move towards the puck
                    x = approach(x, puckX, adjustedVelocity);
                    y = approach(y, puckY, adjustedVelocity);

                    // Adjust paddle's position to hit the puck towards the player's goal
                    if (paddle.getBounds().intersects(Puck.instance.getBounds())) {
                        int hitDirectionX = 1; // Assuming the player's goal is on the right side
                        int hitDirectionY = (puckY < y) ? -1 : 1; // Adjust y direction based on puck's position
                        Puck.instance.setXVelocity(hitDirectionX * Math.abs(Puck.instance.getXVelocity()));
                        Puck.instance.setYVelocity(hitDirectionY * Math.abs(Puck.instance.getYVelocity()));
                    }
                } else {
                    // Move the paddle directly towards the puck's y position gradually
                    y = approach(y, puckY, adjustedVelocity);
                }
            } else {
                // Predict the puck's future position
                Point predictedPosition = predictPuckPosition(paddle);

                // Move the paddle towards the predicted position gradually
                y = approach(y, predictedPosition.y, adjustedVelocity);
            }

            // Constrain the paddle to its half of the table
            int[] bounds = paddle.getRegionBounds();
            y = Math.max(bounds[2], Math.min(y, bounds[3] - Paddle.DIAMETER));

            paddle.setLocation(new Point(x, y));
            paddle.repaint();
            playableArea.repaint();
        }
    }

    private static Point predictPuckPosition(AIPaddle paddle) {
        Point puckLocation = Puck.instance.getLocation();

        // Calculate the time it will take for the puck to reach the AI's side
        double timeToReachAI = (paddle.getLocation().x - puckLocation.x) / Puck.instance.getXVelocity();

        // Predict the puck's future position
        int predictedY = (int) (puckLocation.y + Puck.instance.getYVelocity() * timeToReachAI);

        // Adjust for puck bouncing off the top and bottom edges
        if (predictedY < 0) {
            predictedY = -predictedY;
        } else if (predictedY > SinglePlayerGame.PANEL_HEIGHT) {
            predictedY = SinglePlayerGame.PANEL_HEIGHT - (predictedY - SinglePlayerGame.PANEL_HEIGHT);
        }

        return new Point(paddle.getLocation().x, predictedY);
    }

    // Method to approach a target value gradually
    private static int approach(int current, int target, int velocity) {
        if (current < target) {
            return Math.min(current + velocity, target);
        } else if (current > target) {
            return Math.max(current - velocity, target);
        } else {
            return current;
        }
    }
}
