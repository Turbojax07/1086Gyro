package frc.robot.Subsystems.Gyro;

import com.ctre.phoenix6.hardware.Pigeon2;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.LinearAcceleration;
import frc.robot.Constants.RobotMap;
import org.littletonrobotics.junction.Logger;

public class GyroIOPigeon2 implements GyroIO {
    private Pigeon2 gyro;

    private GyroIOInputs inputs;

    /** Creates a new instance of GyroIOPigeon2. */
    public GyroIOPigeon2() {
        gyro = new Pigeon2(RobotMap.GYRO_Pigeon2Id);

        inputs = new GyroIOInputs();
    }

    @Override
    public void updateInputs() {
        inputs.roll = getRoll();
        inputs.pitch = getPitch();
        inputs.yaw = getYaw();

        inputs.x_accel = getXAcceleration();
        inputs.y_accel = getYAcceleration();
        inputs.z_accel = getZAcceleration();

        Logger.processInputs("Gyro_Pigeon2", inputs);
    }

    @Override
    public Rotation2d getHeading() {
        return gyro.getRotation2d();
    }

    @Override
    public Angle getRoll() {
        return gyro.getRoll().getValue();
    }

    @Override
    public Angle getPitch() {
        return gyro.getPitch().getValue();
    }

    @Override
    public Angle getYaw() {
        return gyro.getYaw().getValue();
    }

    @Override
    public LinearAcceleration getXAcceleration() {
        return gyro.getAccelerationX().getValue();
    }

    @Override
    public LinearAcceleration getYAcceleration() {
        return gyro.getAccelerationY().getValue();
    }

    @Override
    public LinearAcceleration getZAcceleration() {
        return gyro.getAccelerationZ().getValue();
    }
}
