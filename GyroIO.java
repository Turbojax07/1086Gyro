package frc.robot.subsystems.gyro;

import org.littletonrobotics.junction.AutoLog;

public interface GyroIO {
    @AutoLog
    public class GyroIOInputs {
        double roll = 0; // Radians
        double pitch = 0; // Radians
        double yaw = 0; // Radians

        double x_vel = 0; // Radians / Second
        double y_vel = 0; // Radians / Second
        double z_vel = 0; // Radians / Second

        double x_acc = 0; // Meters / Second^2
        double y_acc = 0; // Meters / Second^2
        double z_acc = 0; // Meters / Second^2

        boolean isConnected = false;
    }

    /** Updates the values of the inputs defined in {@link GyroIOInputs}. */
    public void updateInputs(GyroIOInputs inputs);

    /** Resets the heading of the gyro to zero */
    public void resetGyro();

    /** Resets the heading of the gyro to the provided angle in radians. */
    public void resetGyro(double angle);
}