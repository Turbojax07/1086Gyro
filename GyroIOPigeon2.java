package frc.robot.subsystems.gyro;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.configs.Pigeon2Configuration;
import com.ctre.phoenix6.hardware.Pigeon2;

public class GyroIOPigeon2 implements GyroIO {
    private Pigeon2 gyro;

    /** Creates a new instance of GyroIOPigeon2. */
    public GyroIOPigeon2(int id) {
        gyro = new Pigeon2(id, "rio");

        gyro.getConfigurator().apply(new Pigeon2Configuration());

        BaseStatusSignal.setUpdateFrequencyForAll(100,
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
        inputs.roll  = gyro.getRoll().getValueAsDouble()  * 2 * Math.PI;
        inputs.pitch = gyro.getPitch().getValueAsDouble() * 2 * Math.PI;
        inputs.yaw   = gyro.getYaw().getValueAsDouble()   * 2 * Math.PI;

        inputs.x_vel = gyro.getAngularVelocityXDevice().getValueAsDouble() * 2 * Math.PI;
        inputs.y_vel = gyro.getAngularVelocityYDevice().getValueAsDouble() * 2 * Math.PI;
        inputs.z_vel = gyro.getAngularVelocityZDevice().getValueAsDouble() * 2 * Math.PI;

        inputs.x_acc = gyro.getAccelerationX().getValueAsDouble() / 9.80665; // Converting Gs to mps^2
        inputs.y_acc = gyro.getAccelerationY().getValueAsDouble() / 9.80665; // Converting Gs to mps^2
        inputs.z_acc = gyro.getAccelerationZ().getValueAsDouble() / 9.80665; // Converting Gs to mps^2

        inputs.isConnected = gyro.isConnected();
    }

    @Override
    public void resetGyro() {
        gyro.reset();
    }

    @Override
    public void resetGyro(double angle) {
        gyro.setYaw(angle * Math.PI / 180);
    }
}