package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.sensors.IMU;
import frc.robot.subsystems.DriveTrain;


public class Drive extends CommandBase {

    public Drive() {
        addRequirements(DriveTrain.getInstance());
    }

    @Override
    public void initialize() {
        //SmartDashboard.putBoolean("DriveStraight?", false);
        //SmartDashboard.putBoolean("DriveDistance?", false);
        SmartDashboard.putBoolean("Drive?", true);
    }

    @Override
    public void execute() {
        double leftPower = OI.getINSTANCE().getLeftY();
        SmartDashboard.putNumber("Left Y", leftPower);
        double rightPower = OI.getINSTANCE().getRightY();
        DriveTrain.getInstance().tankDrive(leftPower, rightPower);

        /**if(DriveTrain.getInstance().aboveMaxTemp()){
            //release solenoid to cool motors down
            DriveTrain.getInstance().openTempSolenoid();
        }*/
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        SmartDashboard.putBoolean("Drive?", false);
    }
}