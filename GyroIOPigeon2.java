package frc.robot.subsystems.gyro;

import com.ctre.phoenix6.hardware.Pigeon2;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.LinearAcceleration;
import org.littletonrobotics.junction.Logger;

public class GyroIOPigeon2 implements GyroIO {
    private Pigeon2 gyro;

    private GyroIOInputsAutoLogged inputs;

    /** Creates a new instance of GyroIOPigeon2. */
    public GyroIOPigeon2(int id) {
        gyro = new Pigeon2(id);

        inputs = new GyroIOInputsAutoLogged();
    }

    @Override
    public void updateInputs() {
        inputs.roll = getRoll();
        inputs.pitch = getPitch();
        inputs.yaw = getYaw();

        inputs.x_accel = getXAcceleration();
        inputs.y_accel = getYAcceleration();
        inputs.z_accel = getZAcceleration();

        Logger.processInputs("/RealOutputs/Subsystems/Gyro_Pigeon2", inputs);
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