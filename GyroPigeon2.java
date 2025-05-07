package frc.robot.subsystems.gyro;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.LinearAcceleration;
import frc.robot.util.TurboLogger;

public class GyroPigeon2 extends Gyro {
    private Pigeon2 gyro;

    /** Creates a new instance of GyroPigeon2. */
    public GyroPigeon2(int id) {
        gyro = new Pigeon2(id, "rio");

        gyro.getConfigurator().apply(new Pigeon2Configuration());

        BaseStatusSignal.setUpdateFrequencyForAll(
                Hertz.of(100),
                gyro.getRoll(),
                gyro.getPitch(),
                gyro.getYaw(),
                gyro.getAngularVelocityXDevice(),
                gyro.getAngularVelocityYDevice(),
                gyro.getAngularVelocityZDevice(),
                gyro.getAccelerationX(),
                gyro.getAccelerationY(),
                gyro.getAccelerationZ());

        gyro.optimizeBusUtilization();
    }

    @Override
    public void periodic() {
        TurboLogger.log("roll", gyro.getRoll().getValue().in(Degrees));
        TurboLogger.log("pitch", gyro.getPitch().getValue().in(Degrees));
        TurboLogger.log("yaw", gyro.getYaw().getValue().in(Degrees));

        TurboLogger.log("x_vel", gyro.getAngularVelocityXDevice().getValue().in(DegreesPerSecond));
        TurboLogger.log("y_vel", gyro.getAngularVelocityYDevice().getValue().in(DegreesPerSecond));
        TurboLogger.log("z_vel", gyro.getAngularVelocityZDevice().getValue().in(DegreesPerSecond));

        TurboLogger.log("x_accel", gyro.getAccelerationX().getValue().in(MetersPerSecondPerSecond));
        TurboLogger.log("y_accel", gyro.getAccelerationY().getValue().in(MetersPerSecondPerSecond));
        TurboLogger.log("z_accel", gyro.getAccelerationZ().getValue().in(MetersPerSecondPerSecond));

        TurboLogger.log("isConnected", gyro.isConnected());
    }

    /** Gets the heading (yaw) of the robot as a {@link Rotation2d}. */
    public Rotation2d getHeading() {
        return gyro.getRotation2d();
    }

    /** Gets the roll of the robot as an {@link Angle}. */
    public Angle getRoll() {
        return gyro.getRoll().getValue();
    }

    /** Gets the pitch of the robot as an {@link Angle}. */
    public Angle getPitch() {
        return gyro.getPitch().getValue();
    }

    /** Gets the yaw of the robot as an {@link Angle}. */
    public Angle getYaw() {
        return gyro.getYaw().getValue();
    }

    /** Gets the angular velocity on the x axis as an {@link AngularVelocity}. */
    public AngularVelocity getXVelocity() {
        return gyro.getAngularVelocityXDevice().getValue();
    }

    /** Gets the angular velocity on the y axis as an {@link AngularVelocity}. */
    public AngularVelocity getYVelocity() {
        return gyro.getAngularVelocityYDevice().getValue();
    }

    /** Gets the angular velocity on the z axis as an {@link AngularVelocity}. */
    public AngularVelocity getZVelocity() {
        return gyro.getAngularVelocityZDevice().getValue();
    }

    /** Gets the acceleration of the robot along the x axis as an {@link LinearAcceleration}. */
    public LinearAcceleration getXAcceleration() {
        return gyro.getAccelerationX().getValue();
    }

    /** Gets the acceleration of the robot along the y axis as an {@link LinearAcceleration}. */
    public LinearAcceleration getYAcceleration() {
        return gyro.getAccelerationY().getValue();
    }

    /** Gets the acceleration of the robot along the z axis as an {@link LinearAcceleration}. */
    public LinearAcceleration getZAcceleration() {
        return gyro.getAccelerationZ().getValue();
    }

    /** Gets whether or not the gyro is connected. */
    public boolean isConnected() {
        return gyro.isConnected();
    }

    /** Resets the heading of the gyro to 0. */
    public void reset() {
        gyro.reset();
    }

    /** Resets the heading of the gyro to the provided {@link Angle}. */
    public void reset(Angle angle) {
        gyro.setYaw(angle);
    }
}
