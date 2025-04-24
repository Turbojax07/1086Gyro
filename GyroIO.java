
package frc.robot.subsystems.gyro;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.LinearAcceleration;
import org.littletonrobotics.junction.AutoLog;

public interface GyroIO {
    @AutoLog
    public class GyroIOInputs {
        Angle roll = Radians.zero();
        Angle pitch = Radians.zero();
        Angle yaw = Radians.zero();

        AngularVelocity x_vel = RadiansPerSecond.zero();
        AngularVelocity y_vel = RadiansPerSecond.zero();
        AngularVelocity z_vel = RadiansPerSecond.zero();

        LinearAcceleration x_accel = MetersPerSecondPerSecond.zero();
        LinearAcceleration y_accel = MetersPerSecondPerSecond.zero();
        LinearAcceleration z_accel = MetersPerSecondPerSecond.zero();

        boolean isConnected = false;
    }

    /** Updates the values of the inputs defined in {@link GyroIOInputs}. */
    public void updateInputs(GyroIOInputs inputs);

    /** Resets the heading of the gyro to zero */
    public void resetGyro();

    /** Resets the heading of the gyro to the provided {@link Angle} */
    public void resetGyro(Angle angle);
}
