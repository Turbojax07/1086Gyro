package frc.robot.Subsystems.Gyro;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.LinearAcceleration;
import edu.wpi.first.wpilibj.SerialPort;

import org.littletonrobotics.junction.Logger;

public class GyroIOBNO085 implements GyroIO {
    private SerialPort serial;

    public static final double MILLI_G_TO_MS2 = 0.0098067; // < Scalar to convert milli-gs to m/s^2
    public static final double DEGREE_SCALE = 0.01;        // < To convert the degree values
    public static final int BAUDRATE = 115200;             // < Baud rate of the serial connection

    private GyroIOInputsAutoLogged inputs;

    /** Creates a new instance of GyroIOBNO085. */
    public GyroIOBNO085() {
        serial = new SerialPort(BAUDRATE, SerialPort.Port.kOnboard);

        serial.setReadBufferSize(17);

        inputs = new GyroIOInputsAutoLogged();
    }


    @Override
    public void updateInputs() {
        // Not reading until I have all the data
        if (serial.getBytesReceived() < 19) {
            return;
        }

        // Checking first header byte
        if (serial.read(1)[0] != 0xAA) {
            System.out.println("Message did not start with 0xAAAA");
            return;
        }

        // Checking second header byte
        if (serial.read(1)[0] != 0xAA) {
            System.out.println("Message did not start with 0xAAAA");
            return;
        }

        // Reading data into a buffer
        byte[] buffer_8 = serial.read(17);

        // Emptying the serial cache to prevent overflow
        serial.readString();

        // Getting checksum
        int checksum = 0;
        for (int i = 0; i < 16; i++) {
            checksum += buffer_8[i];
        }

        // Comparing checksum
        if (checksum != buffer_8[16]) {
            System.out.println("Invalid checksum!");
            return;
        }

        // De-endianing the data
        short[] buffer_16 = new short[6];
        for (int i = 0; i < 6; i++) {
            buffer_16[i] = (short) ((buffer_8[1 + (i * 2)] & 0xFF) + ((buffer_8[2 + (i * 2)] & 0xFF) << 8));
        }

        // Loading values into the inputs
        inputs.yaw   = Degrees.of(buffer_16[0] * DEGREE_SCALE);
        inputs.pitch = Degrees.of(buffer_16[1] * DEGREE_SCALE);
        inputs.roll  = Degrees.of(buffer_16[2] * DEGREE_SCALE);

        inputs.x_accel = MetersPerSecondPerSecond.of(buffer_16[3] * MILLI_G_TO_MS2);
        inputs.y_accel = MetersPerSecondPerSecond.of(buffer_16[4] * MILLI_G_TO_MS2);
        inputs.z_accel = MetersPerSecondPerSecond.of(buffer_16[5] * MILLI_G_TO_MS2);

        // Logging the inputs
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