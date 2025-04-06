package frc.robot.subsystems.gyro;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Time;
import edu.wpi.first.wpilibj.SerialPort;

public class GyroIOBNO085 implements GyroIO {
    private SerialPort serial;

    // These would normally be put into a constants file, but since these will
    // literally never change, they are just stored in here.
    private static final double MILLI_G_TO_MS2 = 0.0098067; // Scalar to convert milli-gs to m/s^2
    private static final double DEGREE_SCALE = 0.01; // To convert the degree values
    private static final int BAUDRATE = 115200; // Baud rate of the serial connection

    private GyroIOInputs previousInputs;

    private Time previousTime;

    private double offset;

    /** Creates a new instance of GyroIOBNO085. */
    public GyroIOBNO085() {
        serial = new SerialPort(BAUDRATE, SerialPort.Port.kOnboard);

        serial.setReadBufferSize(17);

        previousInputs = new GyroIOInputsAutoLogged();
    }

    @Override
    public void updateInputs(GyroIOInputs inputs) {
        previousInputs = inputs;

        previousTime = Milliseconds.of(System.currentTimeMillis());

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
        inputs.yaw = Degrees.of(buffer_16[0] * DEGREE_SCALE - offset);
        inputs.pitch = Degrees.of(buffer_16[1] * DEGREE_SCALE);
        inputs.roll = Degrees.of(buffer_16[2] * DEGREE_SCALE);

        inputs.x_vel = inputs.pitch.minus(previousInputs.pitch).div(Milliseconds.of(System.currentTimeMillis()).minus(previousTime));
        inputs.y_vel = inputs.roll.minus(previousInputs.roll).div(Milliseconds.of(System.currentTimeMillis()).minus(previousTime));
        inputs.z_vel = inputs.yaw.minus(previousInputs.yaw).div(Milliseconds.of(System.currentTimeMillis()).minus(previousTime));

        inputs.x_accel = MetersPerSecondPerSecond.of(buffer_16[3] * MILLI_G_TO_MS2);
        inputs.y_accel = MetersPerSecondPerSecond.of(buffer_16[4] * MILLI_G_TO_MS2);
        inputs.z_accel = MetersPerSecondPerSecond.of(buffer_16[5] * MILLI_G_TO_MS2);
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