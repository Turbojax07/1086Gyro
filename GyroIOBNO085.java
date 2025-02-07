package frc.robot.Subsystems.Gyro;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.LinearAcceleration;
import edu.wpi.first.wpilibj.SerialPort;

import org.littletonrobotics.junction.Logger;

public class GyroIOBNO085 implements GyroIO {
    private SerialPort serial;

    private GyroIOInputsAutoLogged inputs;

    /** Creates a new instance of GyroIOBNO085. */
    public GyroIOBNO085() {
        serial = new SerialPort(115200, SerialPort.Port.kOnboard);

        serial.setReadBufferSize(17);

        inputs = new GyroIOInputsAutoLogged();
    }


    @Override
    public void updateInputs() {
        // Not reading until I have all the data
        if (serial.getBytesReceived() < 19) {
            return;
        }

        if (serial.read(1)[0] != 0xAA) {
            System.out.println("Message did not start with 0xAAAA");
            return;
        }

        if (serial.read(1)[0] != 0xAA) {
            System.out.println("Message did not start with 0xAAAA");
            return;
        }

        byte[] buffer_8 = serial.read(17);

        int checksum = 0;
        for (int i = 0; i < 16; i++) {
            checksum += buffer_8[i];
        }

        if (checksum != buffer_8[16]) {
            System.out.println("Invalid checksum!");
            return;
        }

        short[] buffer_16 = new short[6];
        for (int i = 0; i < 6; i++) {
            buffer_16[i] = (short) ((buffer_8[1 + (i * 2)] & 0xFF) + ((buffer_8[2 + (i * 2)] & 0xFF) << 8));
        }

        inputs.yaw   = Degrees.of(buffer_16[0] * 0.01);
        inputs.pitch = Degrees.of(buffer_16[1] * 0.01);
        inputs.roll  = Degrees.of(buffer_16[2] * 0.01);

        inputs.x_accel = MetersPerSecondPerSecond.of(buffer_16[3] * 0.0098067);
        inputs.y_accel = MetersPerSecondPerSecond.of(buffer_16[4] * 0.0098067);
        inputs.z_accel = MetersPerSecondPerSecond.of(buffer_16[5] * 0.0098067);

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
