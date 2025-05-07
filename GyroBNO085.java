package frc.robot.subsystems.gyro;

import static edu.wpi.first.units.Units.*;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.LinearAcceleration;
import edu.wpi.first.wpilibj.SerialPort;

public class GyroBNO085 extends Gyro {
    private SerialPort serial;

    private Angle roll;
    private Angle pitch;
    private Angle yaw;

    private AngularVelocity xVelocity;
    private AngularVelocity yVelocity;
    private AngularVelocity zVelocity;

    private LinearAcceleration xAcceleration;
    private LinearAcceleration yAcceleration;
    private LinearAcceleration zAcceleration;

    private boolean isConnected = false;

    private double offset;

    /** Creates a new instance of GyroIOBNO085. */
    public GyroBNO085() {
        serial = new SerialPort(GyroConstants.BAUDRATE, SerialPort.Port.kOnboard);

        serial.setReadBufferSize(17);
    }

    @Override
    public void periodic() {
        // If the data is corrupted or the serial interface isn't recieving data, then the gyro
        // reports
        // itself as not being connected.
        // Not reading until I have all the data
        if (serial.getBytesReceived() < 19) {
            isConnected = false;
            return;
        }

        // Checking first header byte
        if (serial.read(1)[0] != 0xAA) {
            isConnected = false;
            System.out.println("Message did not start with 0xAAAA");
            return;
        }

        // Checking second header byte
        if (serial.read(1)[0] != 0xAA) {
            isConnected = false;
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
            isConnected = false;
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

        // Reading angle measurements into local variables temporarily.
        Angle roll = Degrees.of(buffer_16[2] * GyroConstants.DEGREE_SCALE);
        Angle pitch = Degrees.of(buffer_16[1] * GyroConstants.DEGREE_SCALE);
        Angle yaw = Degrees.of(buffer_16[0] * GyroConstants.DEGREE_SCALE - offset);
        
        // Calculating the angular velocities.
        xVelocity = pitch.minus(this.pitch).div(Seconds.of(0.02));
        yVelocity = roll.minus(this.roll).div(Seconds.of(0.02));
        zVelocity = yaw.minus(this.yaw).div(Seconds.of(0.02));

        // Updating the global angle values.
        this.roll = roll;
        this.pitch = pitch;
        this.yaw = yaw;

        // Measuring the linear accelerations.
        xAcceleration = MetersPerSecondPerSecond.of(buffer_16[3] * GyroConstants.MILLI_G_TO_MS2);
        yAcceleration = MetersPerSecondPerSecond.of(buffer_16[4] * GyroConstants.MILLI_G_TO_MS2);
        zAcceleration = MetersPerSecondPerSecond.of(buffer_16[5] * GyroConstants.MILLI_G_TO_MS2);

        isConnected = true;
    }

    /** Gets the heading (yaw) of the robot as a {@link Rotation2d}. */
    public Rotation2d getHeading() {
        return new Rotation2d(yaw);
    }

    /** Gets the roll of the robot as an {@link Angle}. */
    public Angle getRoll() {
        return roll;
    }

    /** Gets the pitch of the robot as an {@link Angle}. */
    public Angle getPitch() {
        return pitch;
    }

    /** Gets the yaw of the robot as an {@link Angle}. */
    public Angle getYaw() {
        return yaw;
    }

    /** Gets the angular velocity on the x axis as an {@link AngularVelocity}. */
    public AngularVelocity getXVelocity() {
        return xVelocity;
    }

    /** Gets the angular velocity on the y axis as an {@link AngularVelocity}. */
    public AngularVelocity getYVelocity() {
        return yVelocity;
    }

    /** Gets the angular velocity on the z axis as an {@link AngularVelocity}. */
    public AngularVelocity getZVelocity() {
        return zVelocity;
    }

    /** Gets the acceleration of the robot along the x axis as an {@link LinearAcceleration}. */
    public LinearAcceleration getXAcceleration() {
        return xAcceleration;
    }

    /** Gets the acceleration of the robot along the y axis as an {@link LinearAcceleration}. */
    public LinearAcceleration getYAcceleration() {
        return yAcceleration;
    }

    /** Gets the acceleration of the robot along the z axis as an {@link LinearAcceleration}. */
    public LinearAcceleration getZAcceleration() {
        return zAcceleration;
    }

    /** Gets whether or not the gyro is connected. */
    public boolean isConnected() {
        return isConnected;
    }

    @Override
    public void reset() {
        offset -= yaw.in(Degrees);
    }

    @Override
    public void reset(Angle angle) {
        offset -= yaw.minus(angle).in(Degrees);
    }
}
