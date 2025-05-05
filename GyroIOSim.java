package frc.robot.subsystems.gyro;

import edu.wpi.first.units.measure.Angle;

public class GyroIOSim implements GyroIO {
    /**
     * Creates a new instance of GyroIOSim. This class does absolutely nothing and never reports any
     * data. It is just used to allow the drivetrain to not error out when it checks if the gyro is
     * connected. Technically, this class isn't even needed for that, but I wanted to make something
     * for it.
     */
    public GyroIOSim() {}

    @Override
    public void updateInputs(GyroIOInputs inputs) {}

    @Override
    public void resetGyro() {}

    @Override
    public void resetGyro(Angle angle) {}
}
