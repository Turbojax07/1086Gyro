package frc.robot.subsystems.gyro;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.LinearAcceleration;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

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

    /** Gets the roll of the robot as an {@link Angle}. */
    public Angle getRoll() {
        return gyroInputs.roll;
    }

    /** Gets the pitch of the robot as an {@link Angle}. */
    public Angle getPitch() {
        return gyroInputs.pitch;
    }

    /** Gets the yaw of the robot as an {@link Angle}. */
    public Angle getYaw() {
        return gyroInputs.yaw;
    }

    /** Gets the angular velocity on the x axis as an {@link AngularVelocity}. */
    public AngularVelocity getXVelocity() {
        return gyroInputs.x_vel;
    }

    /** Gets the angular velocity on the y axis as an {@link AngularVelocity}. */
    public AngularVelocity getYVelocity() {
        return gyroInputs.y_vel;
    }

    /** Gets the angular velocity on the z axis as an {@link AngularVelocity}. */
    public AngularVelocity getZVelocity() {
        return gyroInputs.z_vel;
    }

    /** Gets the acceleration of the robot along the x axis as an {@link LinearAcceleration}. */
    public LinearAcceleration getXAcceleration() {
        return gyroInputs.x_accel;
    }

    /** Gets the acceleration of the robot along the y axis as an {@link LinearAcceleration}. */
    public LinearAcceleration getYAcceleration() {
        return gyroInputs.y_accel;
    }

    /** Gets the acceleration of the robot along the z axis as an {@link LinearAcceleration}. */
    public LinearAcceleration getZAcceleration() {
        return gyroInputs.z_accel;
    }

    /** Gets whether or not the gyro is connected. */
    public boolean isConnected() {
        return gyroInputs.isConnected;
    }

    /** Resets the heading of the gyro to 0. */
    public void reset() {
        gyroIO.resetGyro();
    }

    /** Resets the heading of the gyro to the provided {@link Angle}. */
    public void reset(Angle angle) {
        gyroIO.resetGyro(angle);
    }
}