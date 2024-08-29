package ema.mechanics;

import java.awt.Point;

import ema.components.Paddle;
import ema.components.Puck;

/**
 * This class handles the physics and collisions within the game.
 */
public class PhysicsEngine {
    /**
     * Calculates the velocity and the direction of the puck after the puck collides with a paddle.
     * @param paddle The paddle on the board
     * @return An array containing the new velocities of puck and paddle after collision
     */
    public static double[] collisions(Paddle paddle) {
        // Get the velocites and positions of the puck and the paddle
        Point paddlePosition = paddle.getLocation();
        int paddleVelocity = paddle.getVelocity();
        Point puckPosition = Puck.instance.getLocation();
        double puckXVelocity = Puck.instance.getXVelocity();
        double puckYVelocity = Puck.instance.getYVelocity();

        // Calculate the initial momentum of the puck and the paddle just before collision
        double initPuckMomentum = Math.sqrt(Math.pow(puckXVelocity, 2.0) + Math.pow(puckYVelocity, 2.0));
        double initPaddleMomentum = Math.sqrt(Math.pow(paddleVelocity, 2.0) + Math.pow(paddleVelocity, 2.0));

        // calculate motion vectors
        double[] puckTrajectory = {puckXVelocity, puckYVelocity};
        double[] paddleTrajectory = {paddleVelocity, paddleVelocity};

        // Calculate the impact Vector
        double[] impactVector = {puckPosition.x - paddlePosition.x, puckPosition.y - paddlePosition.y};
        double[] impactVectorNorm = normalizeVector(impactVector);

        // Calculate the scalar product of each trajectory and the impact vector
        double puckdotImpact = Math.abs(puckTrajectory[0] * impactVectorNorm[0] + puckTrajectory[1] * impactVectorNorm[1]);
        double paddledotImpact = Math.abs(paddleTrajectory[0] * impactVectorNorm[0] + paddleTrajectory[1] * impactVectorNorm[1]);

        // Calculate the deflection vectors
        double[] puckDeflect = { -impactVectorNorm[0] * paddledotImpact, -impactVectorNorm[1] * paddledotImpact };
        double[] paddleDeflect = { impactVectorNorm[0] * puckdotImpact, impactVectorNorm[1] * puckdotImpact };

        // Calculate the final trajectories
        double[] puckFinalTrajectory = {puckTrajectory[0] + puckDeflect[0] - paddleDeflect[0], puckTrajectory[1] + puckDeflect[1] - paddleDeflect[1]};
        double[] paddleFinalTrajectory = {paddleTrajectory[0] + paddleDeflect[0] - puckDeflect[0], paddleTrajectory[1] + paddleDeflect[1] - puckDeflect[1]};
        
        // Calculate the final energy in the system
        double puckFinalMomentum = Math.sqrt(puckFinalTrajectory[0] * puckFinalTrajectory[0] + puckFinalTrajectory[1] * puckFinalTrajectory[1]);
        double paddleFinalMomentum = Math.sqrt(paddleFinalTrajectory[0] * paddleFinalTrajectory[0] + paddleFinalTrajectory[1] * paddleFinalTrajectory[1]);

        // Scale the resultant trajectories
        double mag = (initPuckMomentum + initPaddleMomentum) / (puckFinalMomentum + paddleFinalMomentum);

        // Calculate the final x and y velocities settings for the puck and the paddle after collision.
        puckXVelocity = (int) puckFinalTrajectory[0] * mag * -1;
        puckYVelocity = (int) puckFinalTrajectory[1] * mag * -1;
        paddleVelocity = (int) ((int) paddleFinalTrajectory[0] * mag);
        paddleVelocity = (int) ((int) paddleFinalTrajectory[1] * mag);

        double[] velocities = {puckXVelocity, puckYVelocity, paddleVelocity, paddleVelocity};

        return(velocities);
    }
    
    /**
     * Converts a vector into a unit vector.
     * @param vec The vector
     * @return The unit vector
     */
    static double[] normalizeVector(double[] vec) {
        double mag = 0.0;
        int dimensions = vec.length;
        double[] result = new double[dimensions];

        for (int i=0; i < dimensions; i++) {
            mag += vec[i] * vec[i];   
        }

        mag = Math.sqrt(mag);
        if (mag == 0.0) {
            result[0] = 1.0;
            for (int i=1; i < dimensions; i++) {
                result[i] = 0.0;
            }
        } else {
            for (int i=0; i < dimensions; i++) {
                result[i] = vec[i] / mag;
            }
        }
        return result;
    }
}
