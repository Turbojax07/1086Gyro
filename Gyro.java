package frc.robot.subsystems.gyro;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;

public class Gyro extends SubsystemBase {
    private GyroIO gyroIO;
    private GyroIOInputsAutoLogged gyroInputs;

    /**
     * Creates a new instance of the Gyro class.
     * 
     * @param gyroIO The IO interface to use for this class. It must implement {@link GyroIO}.
     */
    public Gyro(GyroIO gyroIO) {
        this.gyroIO = gyroIO;
        gyroInputs = new GyroIOInputsAutoLogged();
    }

    /** Runs once every tick that the subsystem is initialized. */
    @Override
    public void periodic() {
        gyroIO.updateInputs(gyroInputs);
        Logger.processInputs("/RealOutputs/Gyro", gyroInputs);

    }

    /** Gets the heading (yaw) of the robot as a {@link Rotation2d}. */
    public Rotation2d getHeading() {
        return new Rotation2d(gyroInputs.yaw);
    }

    /** Gets the roll of the robot in radians. */
    public double getRoll() {
        return gyroInputs.roll;
    }

    /** Gets the pitch of the robot in radians. */
    public double getPitch() {
        return gyroInputs.pitch;
    }

    /** Gets the yaw of the robot in radians. */
    public double getYaw() {
        return gyroInputs.yaw;
    }

    /** Gets the angular velocity on the x axis in radians / second. */
    public double getXVelocity() {
        return gyroInputs.x_vel;
    }

    /** Gets the angular velocity on the y axis in radians / second. */
    public double getYVelocity() {
        return gyroInputs.y_vel;
    }

    /** Gets the angular velocity on the z axis in radians / second. */
    public double getZVelocity() {
        return gyroInputs.z_vel;
    }

    /** Gets the acceleration of the robot along the x axis in meters / second^2. */
    public double getXAcceleration() {
        return gyroInputs.x_acc;
    }

    /** Gets the acceleration of the robot along the y axis in meters / second^2. */
    public double getYAcceleration() {
        return gyroInputs.y_acc;
    }

    /** Gets the acceleration of the robot along the z axis in meters / second^2. */
    public double getZAcceleration() {
        return gyroInputs.z_acc;
    }

    /** Gets whether or not the gyro is connected. */
    public boolean isConnected() {
        return gyroInputs.isConnected;
    }

    /** Resets the heading of the gyro to 0. */
    public void reset() {
        gyroIO.resetGyro();
    }

    /** Resets the heading of the gyro to the provided angle in radians. */
    public void reset(double angle) {
        gyroIO.resetGyro(angle);
    }
}