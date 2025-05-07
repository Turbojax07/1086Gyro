package frc.robot.subsystems.gyro;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.LinearAcceleration;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Gyro extends SubsystemBase {
    /** Gets the heading (yaw) of the robot as a {@link Rotation2d}. */
    public Rotation2d getHeading() {
        return new Rotation2d();
    }

    /** Gets the roll of the robot as an {@link Angle}. */
    public Angle getRoll() {
        return Degrees.zero();
    }

    /** Gets the pitch of the robot as an {@link Angle}. */
    public Angle getPitch() {
        return Degrees.zero();
    }

    /** Gets the yaw of the robot as an {@link Angle}. */
    public Angle getYaw() {
        return Degrees.zero();
    }

    /** Gets the angular velocity on the x axis as an {@link AngularVelocity}. */
    public AngularVelocity getXVelocity() {
        return DegreesPerSecond.zero();
    }

    /** Gets the angular velocity on the y axis as an {@link AngularVelocity}. */
    public AngularVelocity getYVelocity() {
        return DegreesPerSecond.zero();
    }

    /** Gets the angular velocity on the z axis as an {@link AngularVelocity}. */
    public AngularVelocity getZVelocity() {
        return DegreesPerSecond.zero();
    }

    /** Gets the acceleration of the robot along the x axis as an {@link LinearAcceleration}. */
    public LinearAcceleration getXAcceleration() {
        return MetersPerSecondPerSecond.zero();
    }

    /** Gets the acceleration of the robot along the y axis as an {@link LinearAcceleration}. */
    public LinearAcceleration getYAcceleration() {
        return MetersPerSecondPerSecond.zero();
    }

    /** Gets the acceleration of the robot along the z axis as an {@link LinearAcceleration}. */
    public LinearAcceleration getZAcceleration() {
        return MetersPerSecondPerSecond.zero();
    }

    /** Gets whether or not the gyro is connected. */
    public boolean isConnected() {
        return false;
    }

    /** Resets the heading of the gyro to 0. */
    public void reset() {}

    /** Resets the heading of the gyro to the provided {@link Angle}. */
    public void reset(Angle angle) {}
}