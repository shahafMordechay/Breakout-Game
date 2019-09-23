package factories;

import other.Velocity;

import java.util.ArrayList;
import java.util.List;

/**
 * Create a list of velocities according to given the most left angle
 * and speed.
 * Also needs to know number of velocities to create and if to
 * include the 360 angle velocity.
 *
 * @author Shahaf Mordechay
 */
public class VelocitiesCreator {

    // members
    private double angle;
    private double speed;

    /**
     * Constructs and initializes the first velocity properties.
     *
     * @param maxAngle the angle of the most left velocity direction.
     * @param speed    the speed of all velocities.
     */
    public VelocitiesCreator(double maxAngle, double speed) {
        this.angle = maxAngle % 360;
        this.speed = speed;
    }

    /**
     * Creates number of velocities according to given amount.
     *
     * @param amount     the amount of velocities to create.
     * @param withMiddle tells if to create vertical velocity(angle 360).
     *
     * @return a list of velocities(from left to right direction).
     */
    public List<Velocity> createVelocities(int amount, boolean withMiddle) {

        int middleVelocity = amount / 2;
        List<Velocity> velocities = new ArrayList<>();

        // change in angle
        double change = ((360 - this.angle) * 2) / amount;
        if (withMiddle) {
            change = ((360 - this.angle) * 2) / (amount - 1);
        }



        for (int i = 0; i < amount; i++) {

            // fix angle 0
            if (this.angle == 0) {
                this.angle = 360;
            }

            // don't include vertical velocity
            if (!withMiddle && i == middleVelocity) {
                this.angle = (this.angle + change) % 360;
            }

            velocities.add(Velocity.fromAngleAndSpeed(this.angle, this.speed));

            // advance velocity by change
            this.angle = (this.angle + change) % 360;
        }

        return velocities;
    }
}
