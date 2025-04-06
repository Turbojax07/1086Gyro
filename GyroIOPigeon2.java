package frc.robot.subsystems.gyro;

import static edu.wpi.first.units.Units.Hertz;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.hardware.Pigeon2;
import edu.wpi.first.units.measure.Angle;

public class GyroIOPigeon2 implements GyroIO {
    private Pigeon2 gyro;

    /** Creates a new instance of GyroIOPigeon2. */
    public GyroIOPigeon2(int id) {
        gyro = new Pigeon2(id, "rio");

        gyro.getConfigurator().apply(new Pigeon2Configuration());

        BaseStatusSignal.setUpdateFrequencyForAll(Hertz.of(100),
                gyro.getRoll(),
                gyro.getPitch(),
                gyro.getYaw(),
                gyro.getAngularVelocityXDevice(),
                gyro.getAngularVelocityYDevice(),
                gyro.getAngularVelocityZDevice(),
                gyro.getAccelerationX(),
                gyro.getAccelerationY(),
                gyro.getAccelerationZ());

        gyro.optimizeBusUtilization();
    }

    @Override
    public void updateInputs(GyroIOInputs inputs) {
        inputs.roll = gyro.getRoll().getValue();
        inputs.pitch = gyro.getPitch().getValue();
        inputs.yaw = gyro.getYaw().getValue();

        inputs.x_vel = gyro.getAngularVelocityXDevice().getValue();
        inputs.y_vel = gyro.getAngularVelocityYDevice().getValue();
        inputs.z_vel = gyro.getAngularVelocityZDevice().getValue();

        inputs.x_accel = gyro.getAccelerationX().getValue();
        inputs.y_accel = gyro.getAccelerationY().getValue();
        inputs.z_accel = gyro.getAccelerationZ().getValue();
    }

    @Override
    public void resetGyro() {
        gyro.reset();
    }

    @Override
    public void resetGyro(Angle angle) {
        gyro.setYaw(angle);
    }
}