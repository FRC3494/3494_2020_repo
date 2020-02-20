package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.drive.DistanceDrive;
import frc.robot.commands.drive.Drive;
import frc.robot.commands.drive.DriveStraight;
import frc.robot.commands.drive.TurnDegrees;
import frc.robot.commands.turret.AimBot;
import frc.robot.commands.turret.QuickTurretLimit;

public class OI {
    private static OI INSTANCE = new OI();
    private Joystick leftFlight;
    private Joystick rightFlight;
    private XboxController xbox;
    private JoystickButton driveStraight;
    private JoystickButton driveTurn;
    private JoystickButton driveDistance;

    private JoystickButton turretLimits;
    private JoystickButton turretToSetpoint;

    private OI(){
        leftFlight = new Joystick(RobotMap.OI.LEFT_FLIGHT);
        rightFlight = new Joystick(RobotMap.OI.RIGHT_FLIGHT);
        xbox = new XboxController(RobotMap.OI.XBOX);

        driveStraight = new JoystickButton(xbox, RobotMap.OI.DRIVE_STRAIGHT);
        driveStraight.whenPressed(new DriveStraight());
        driveStraight.whenReleased(new Drive());

        driveTurn = new JoystickButton(xbox, RobotMap.OI.DRIVE_TURN);
        driveTurn.whenPressed(new TurnDegrees(SmartDashboard.getNumber("Rotation(degrees)", 0)));

        driveDistance = new JoystickButton(xbox, RobotMap.OI.DRIVE_DISTANCE);
        //driveDistance.whenPressed(() -> SmartDashboard.putBoolean())
        driveDistance.whenPressed(new DistanceDrive(SmartDashboard.getNumber("Inches to Drive", 0)), false);

        this.turretLimits = new JoystickButton(xbox, 3);
        this.turretLimits.whenPressed(new QuickTurretLimit());

        this.turretToSetpoint = new JoystickButton(xbox, 4);
        this.turretToSetpoint.toggleWhenPressed(new AimBot());
    }

    public double getLeftY(){
        return removeDeadband(leftFlight.getY(GenericHID.Hand.kLeft));
    }

    public double getRightY(){
        return removeDeadband(rightFlight.getY(GenericHID.Hand.kRight));
    }

    public double getXboxRightY(){
        return this.xbox.getY(GenericHID.Hand.kRight);
    }

    public double getXboxRightX() {
        return this.xbox.getX(GenericHID.Hand.kRight);
    }

    public double getXboxRightTrigger(){
        return this.xbox.getTriggerAxis(GenericHID.Hand.kRight);
    }

    public double getXboxLeftTrigger(){
        return this.xbox.getTriggerAxis(GenericHID.Hand.kLeft);
    }

    public boolean getXboxRightBumper(){
        return this.xbox.getBumper(GenericHID.Hand.kRight);
    }

    /**
     * Returns 0.0 if the given value is within the specified range around zero. The remaining range
     * between the deadband and 1.0 is scaled from 0.0 to 1.0.
     *
     * @param value    value to clip
     * @param deadband range around zero
     * @author WPI
     */
    public static double removeDeadband(double value, double deadband) {
        if (Math.abs(value) > deadband) {
            if (value > 0.0) {
                return (value - deadband) / (1.0 - deadband);
            } else {
                return (value + deadband) / (1.0 - deadband);
            }
        } else {
            return 0.0;
        }
    }
    public static double removeDeadband(double value) {
        if (Math.abs(value) > .05) {
            if (value > 0.0) {
                return (value - .05) / (1.0 - .05);
            } else {
                return (value + .05) / (1.0 - .05);
            }
        } else {
            return 0.0;
        }
    }

    public static OI getINSTANCE(){
        return INSTANCE;
    }
}
