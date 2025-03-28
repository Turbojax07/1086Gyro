package frc.robot.subsystems.gyro;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.LinearAcceleration;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Gyro extends SubsystemBase {
    private GyroIO gyroIO;

    /**
     * Creates a new instance of the Gyro class.
     * 
     * @param gyroIO The IO interface to use for this class.  It must implement {@link GyroIO}.
     */
    public Gyro(GyroIO gyroIO) {
        this.gyroIO = gyroIO;
    }

    /** Runs once every tick that the subsystem is initialized. */
    @Override
    public void periodic() {
        gyroIO.updateInputs();
    }

    /** Gets the roll of the robot as an {@link Angle}. */
    public Angle getRoll() {
        return gyroIO.getRoll();
    }

    /** Gets the pitch of the robot as an {@link Angle}. */
    public Angle getPitch() {
        return gyroIO.getPitch();
    }

    /** Gets the yaw of the robot as an {@link Angle}. */
    public Angle getYaw() {
        return gyroIO.getYaw();
    }


    /** Gets the angular velocity on the x axis as an {@link AngularVelocity}. */
    public AngularVelocity getXVelocity() {
        return gyroIO.getXVelocity();
    }

    /** Gets the angular velocity on the y axis as an {@link AngularVelocity}. */
    public AngularVelocity getYVelocity() {
        return gyroIO.getYVelocity();
    }
    
    /** Gets the angular velocity on the z axis as an {@link AngularVelocity}. */
    public AngularVelocity getZVelocity() {
        return gyroIO.getZVelocity();
    }


    /** Gets the acceleration of the robot along the x axis as an {@link LinearAcceleration}. */
    public LinearAcceleration getXAcceleration() {
        return gyroIO.getXAcceleration();
    }

    /** Gets the acceleration of the robot along the y axis as an {@link LinearAcceleration}. */
    public LinearAcceleration getYAcceleration() {
        return gyroIO.getYAcceleration();
    }

    /** Gets the acceleration of the robot along the z axis as an {@link LinearAcceleration}. */
    public LinearAcceleration getZAcceleration() {
        return gyroIO.getZAcceleration();
    }

    public void reset(){
        gyroIO.resetGyro();
    }
}