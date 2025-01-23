package frc.robot.Subsystems.Gyro;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.LinearAcceleration;
import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;
import java.lang.Cloneable;
import java.lang.Override;

public class GyroIOInputs implements LoggableInputs, Cloneable {
    Angle roll;
    Angle pitch;
    Angle yaw;
    LinearAcceleration x_accel;
    LinearAcceleration y_accel;
    LinearAcceleration z_accel;

    @Override
    public void toLog(LogTable table) {
        table.put("Roll", roll);
        table.put("Pitch", pitch);
        table.put("Yaw", yaw);
        table.put("X_accel", x_accel);
        table.put("Y_accel", y_accel);
        table.put("Z_accel", z_accel);
    }

    @Override
    public void fromLog(LogTable table) {
        roll = table.get("Roll", roll);
        pitch = table.get("Pitch", pitch);
        yaw = table.get("Yaw", yaw);
        x_accel = table.get("X_accel", x_accel);
        y_accel = table.get("Y_accel", y_accel);
        z_accel = table.get("Z_accel", z_accel);
    }

    public GyroIOInputs clone() {
        GyroIOInputs copy = new GyroIOInputs();
        copy.roll = this.roll;
        copy.pitch = this.pitch;
        copy.yaw = this.yaw;
        copy.x_accel = this.x_accel;
        copy.y_accel = this.y_accel;
        copy.z_accel = this.z_accel;
        return copy;
    }
}
