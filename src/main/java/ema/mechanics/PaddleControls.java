package ema.mechanics;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Set;

import javax.swing.JPanel;


import ema.components.Paddle;

public class PaddleControls {
    public static void movePaddle(Paddle paddle, Set<Integer> pressedKeys, JPanel playableArea) {
        if(paddle.getIsPaddleDisabled() == false) {
            // Get the x and y values of the paddle        
            int x = paddle.getLocation().x;
            int y = paddle.getLocation().y;

            if (pressedKeys.contains(paddle.getKeyCodes()[0])) { // Left
                x -= paddle.getVelocity();
            }
            if (pressedKeys.contains(paddle.getKeyCodes()[1])) { // Up
                y -= paddle.getVelocity();
            }
            if (pressedKeys.contains(paddle.getKeyCodes()[2])) { // Right
                x += paddle.getVelocity();
            }
            if (pressedKeys.contains(paddle.getKeyCodes()[3])) { // Down
                y += paddle.getVelocity();
            }

            // Makes sure the paddle hasn't passed its designated boundaries
            int[] bounds = paddle.getRegionBounds();
            x = Math.max(bounds[0], Math.min(x, bounds[1] - Paddle.DIAMETER));
            y = Math.max(bounds[2], Math.min(y, bounds[3] - Paddle.DIAMETER));

            paddle.setLocation(new Point(x, y));
            paddle.repaint();
            playableArea.repaint();
        }
    }

    public static void movePaddleWithMouse(MouseEvent e, Paddle player, JPanel playableArea) {
        if(!player.getIsPaddleDisabled()) {
            // Get the location of the mouse when on the playable area
            int mouseX = e.getX();
            int mouseY = e.getY();
            
            // Ensure the paddle stays within the allowed vertical bounds
            int paddleX = Math.max(player.getRegionBounds()[0], Math.min(mouseX, player.getRegionBounds()[1] - Paddle.DIAMETER));
            int paddleY = Math.max(player.getRegionBounds()[2], Math.min(mouseY, player.getRegionBounds()[3] - Paddle.DIAMETER));
            
            player.setLocation(new Point(paddleX, paddleY));
            player.repaint();
            playableArea.repaint();
        }
    }
}
