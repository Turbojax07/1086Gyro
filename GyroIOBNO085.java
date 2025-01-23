package frc.robot.Subsystems.Gyro;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.LinearAcceleration;
import edu.wpi.first.wpilibj.SerialPort;
import frc.robot.Constants.GyroConstants;
import org.littletonrobotics.junction.Logger;

public class GyroIOBNO085 implements GyroIO {
    private SerialPort serial;

    private GyroIOInputs inputs;

    /** Creates a new instance of GyroIOBNO085. */
    public GyroIOBNO085() {
        serial = new SerialPort(GyroConstants.baudrate, SerialPort.Port.kOnboard);

        serial.setReadBufferSize(17);

        inputs = new GyroIOInputs();
    }

    @Override
    public void updateInputs() {
        byte[] buffer_8 = serial.read(17);

        byte checksum = 0;
        for (int i = 0; i < 16; i++) {
            checksum += buffer_8[i];
        }

        if (checksum == buffer_8[16]) {
            int[] buffer_16 = new int[6];
            for (int i = 0; i < 6; i++) {
                buffer_16[i] = buffer_8[1 + (i * 2)] + (buffer_8[1 + (i * 2) + i] << 8);
            }

            inputs.yaw = Degrees.of(buffer_16[0] * GyroConstants.DEGREE_SCALE);
            inputs.pitch = Degrees.of(buffer_16[1] * GyroConstants.DEGREE_SCALE);
            inputs.roll = Degrees.of(buffer_16[2] * GyroConstants.DEGREE_SCALE);

            inputs.x_accel = MetersPerSecondPerSecond.of(buffer_16[3] * GyroConstants.MILLI_G_TO_MS2);
            inputs.y_accel = MetersPerSecondPerSecond.of(buffer_16[4] * GyroConstants.MILLI_G_TO_MS2);
            inputs.z_accel = MetersPerSecondPerSecond.of(buffer_16[5] * GyroConstants.MILLI_G_TO_MS2);
        } else {
            System.out.println("Error reading data");
        }

        Logger.processInputs("Gyro_BNO085", inputs);
    }

    @Override
    public Rotation2d getHeading() {
        return new Rotation2d(inputs.yaw);
    }

    @Override
    public Angle getRoll() {
        return inputs.roll;
    }

    @Override
    public Angle getPitch() {
        return inputs.pitch;
    }

    @Override
    public Angle getYaw() {
        return inputs.yaw;
    }

    @Override
    public LinearAcceleration getXAcceleration() {
        return inputs.x_accel;
    }

    @Override
    public LinearAcceleration getYAcceleration() {
        return inputs.y_accel;
    }

    @Override
    public LinearAcceleration getZAcceleration() {
        return inputs.z_accel;
    }
}
