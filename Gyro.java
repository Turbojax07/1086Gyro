package frc.robot.subsystems.gyro;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Gyro extends SubsystemBase {
    private GyroIO gyroIO;

    /**
     * Creates a new instance of the Gyro class.
     * 
     * @param gyroIO The IO interface to use for this class.  It must implement {@link GyroIO}.
     */
    public Gyro(GyroIO gyroIO) {
        if (instance != null) return;

        instance = this;

        this.gyroIO = gyroIO;
    }

    /** Runs once every tick that the subsystem is initialized. */
    @Override
    public void periodic() {
        gyroIO.updateInputs();
    }

    /** Gets the heading of the robot according to the gyro. */
    public Rotation2d getHeading() {
        return gyroIO.getHeading();
    }
}
