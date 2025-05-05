package frc.robot.subsystems.gyro;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.wpilibj.SerialPort;

public class GyroIOBNO085 implements GyroIO {
    private SerialPort serial;

    private GyroIOInputs previousInputs;

    private double offset;

    /** Creates a new instance of GyroIOBNO085. */
    public GyroIOBNO085() {
        serial = new SerialPort(GyroConstants.BAUDRATE, SerialPort.Port.kOnboard);

        serial.setReadBufferSize(17);

        previousInputs = new GyroIOInputsAutoLogged();
    }

    @Override
    public void updateInputs(GyroIOInputs inputs) {
        // If the data is corrupted or the serial interface isn't recieving data, then the gyro
        // reports
        // itself as not being connected.

        previousInputs = inputs;

        // Not reading until I have all the data
        if (serial.getBytesReceived() < 19) {
            inputs.isConnected = false;
            return;
        }

        // Checking first header byte
        if (serial.read(1)[0] != 0xAA) {
            inputs.isConnected = false;
            System.out.println("Message did not start with 0xAAAA");
            return;
        }

        // Checking second header byte
        if (serial.read(1)[0] != 0xAA) {
            inputs.isConnected = false;
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
            inputs.isConnected = false;
            System.out.println("Invalid checksum!");
            return;
        }

        // De-endianing the data
        short[] buffer_16 = new short[6];
        for (int i = 0; i < 6; i++) {
            buffer_16[i] =
                    (short)
                            ((buffer_8[1 + (i * 2)] & 0xFF)
                                    + ((buffer_8[2 + (i * 2)] & 0xFF) << 8));
        }

        // Loading values into the inputs
        inputs.yaw = Degrees.of(buffer_16[0] * GyroConstants.DEGREE_SCALE - offset);
        inputs.pitch = Degrees.of(buffer_16[1] * GyroConstants.DEGREE_SCALE);
        inputs.roll = Degrees.of(buffer_16[2] * GyroConstants.DEGREE_SCALE);

        inputs.x_vel = inputs.pitch.minus(previousInputs.pitch).div(Seconds.of(0.02));
        inputs.y_vel = inputs.roll.minus(previousInputs.roll).div(Seconds.of(0.02));
        inputs.z_vel = inputs.yaw.minus(previousInputs.yaw).div(Seconds.of(0.02));

        inputs.x_accel = MetersPerSecondPerSecond.of(buffer_16[3] * GyroConstants.MILLI_G_TO_MS2);
        inputs.y_accel = MetersPerSecondPerSecond.of(buffer_16[4] * GyroConstants.MILLI_G_TO_MS2);
        inputs.z_accel = MetersPerSecondPerSecond.of(buffer_16[5] * GyroConstants.MILLI_G_TO_MS2);

        inputs.isConnected = true;
    }

    @Override
    public void resetGyro() {
        offset -= previousInputs.yaw.in(Degrees);
    }

    @Override
    public void resetGyro(Angle angle) {
        offset -= previousInputs.yaw.minus(angle).in(Degrees);
    }
}
