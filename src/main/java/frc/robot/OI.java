package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.*;
import frc.robot.commands.drive.DistanceDrive;
import frc.robot.commands.drive.Drive;
import frc.robot.commands.drive.DriveStraight;
import frc.robot.commands.drive.TurnDegrees;
import frc.robot.commands.teleop.IntakingRoutine;
import frc.robot.commands.turret.QuickTurretLimit;

;

public class OI {
    private static OI INSTANCE = new OI();
    private XboxController primaryXbox;
    private XboxController secondaryXbox;
    private JoystickButton driveStraight;
    private JoystickButton driveTurn;
    private JoystickButton driveDistance;
    private JoystickButton runMagazine;

    private JoystickButton runShooter;
    private JoystickButton shooterPositionForward, shooterPositionBackward;
    private JoystickButton runHopper;
    private Trigger releaseClimber;
    private Trigger retractClimber;
    private Trigger extendClimber;
    private JoystickButton safetyClimber;
    private JoystickButton intakingRoutine;
    private JoystickButton spinHopperMagazine;
    private JoystickButton shooterHood;
    private JoystickButton shooterLimit;
    private JoystickButton quickTurretLimits;

    private ButtonBoard bb;
    private JoystickButton[] boardButtons;

    /**
     * Adam
     * Intake right trigger
     * Intake pneumatics: right bumper
     * shooting power left trigger
     * Magazine/hopper feed to shooter button b
     * Climber:
     * release = 12
     * climb = 14
     * retract = 13
     * shooter speed limit
     * short = 1
     * medium = 3
     * long = 6
     */

    /**
     * shooter right trigger analog power
     * pneumatics
     * magazine/hopper: manual left bumper
     * intake: left trigger analog power
     * climber: button board
     */

    private OI(){
        primaryXbox = new XboxController(RobotMap.OI.PRIMARY_XBOX);
        secondaryXbox = new XboxController(RobotMap.OI.SECONDARY_XBOX);

        bb = new ButtonBoard(RobotMap.OI.BUTTON_BOARD);
        boardButtons = new JoystickButton[15];

        driveStraight = new JoystickButton(bb, RobotMap.OI.DRIVE_STRAIGHT);
        driveStraight.whenPressed(new DriveStraight());
        driveStraight.whenReleased(new Drive());
        driveTurn = new JoystickButton(bb, RobotMap.OI.DRIVE_TURN);
        driveTurn.whenPressed(new TurnDegrees(SmartDashboard.getNumber("Rotation(degrees)", 0)));
        driveDistance = new JoystickButton(bb, RobotMap.OI.DRIVE_DISTANCE);
        //driveDistance.whenPressed(() -> SmartDashboard.putBoolean())
        driveDistance.whenPressed(new DistanceDrive(SmartDashboard.getNumber("Inches to Drive", 0)), false);

        spinHopperMagazine = new JoystickButton(bb, RobotMap.OI.SPIN_HOPPER_MAGAZINE);
        spinHopperMagazine.whenPressed(new ParallelCommandGroup(new RunHopper(), new RunMagazine(true, true, false)));

        runMagazine = new JoystickButton(bb, RobotMap.OI.RUN_MAGAZINE);
        runMagazine.whenPressed(new RunMagazine(true, true, true));
        runMagazine.whenReleased(new RunMagazine(false, false, false));

        runShooter = new JoystickButton(secondaryXbox, RobotMap.OI.RUN_SHOOTER);
        shooterPositionBackward = new JoystickButton(secondaryXbox, RobotMap.OI.SHOOTER_BACKWARD);
        shooterPositionForward = new JoystickButton(secondaryXbox, RobotMap.OI.SHOOTER_FORWARD);
        runShooter.whileHeld(new Shoot());
        //shooterPositionForward.whenPressed(new RollShooterPosition(true));
        //shooterPositionBackward.whenPressed(new RollShooterPosition(false));
        //runMagazine.whenPressed(new RunHopper());
        //runMagazine.whenReleased(new InstantCommand(() -> Hopper.getInstance().stop()));


        runHopper = new JoystickButton(bb, RobotMap.OI.RUN_HOPPER);
        runHopper.whenPressed(new RunHopper());

        quickTurretLimits = new JoystickButton(bb, RobotMap.OI.QUICK_TURRET_LIMITS);
        quickTurretLimits.whenPressed(new QuickTurretLimit());
        
        /**releaseClimber = new JoystickButton(bb, RobotMap.OI.RELEASE_CLIMBER);
        retractClimber = new JoystickButton(bb, RobotMap.OI.REVERSE_CLIMBER);
        extendClimber = new JoystickButton(bb, RobotMap.OI.DRIVE_CLIMBER);*/
        safetyClimber = new JoystickButton(bb, RobotMap.OI.SAFETY_CLIMBER);
        retractClimber = new JoystickButton(bb, RobotMap.OI.REVERSE_CLIMBER).and(safetyClimber);
        extendClimber = new JoystickButton(bb, RobotMap.OI.DRIVE_CLIMBER).and(safetyClimber);
        releaseClimber = new JoystickButton(bb, RobotMap.OI.RELEASE_CLIMBER).and(safetyClimber);
        //- = down
        //+ = up
        releaseClimber.whenActive(new Climb());
        retractClimber.whenActive(new DriveClimb(-RobotMap.CLIMBER.CLIMB_UP_POWER));
        extendClimber.whenActive(new DriveClimb(RobotMap.CLIMBER.CLIMB_UP_POWER));

        intakingRoutine = new JoystickButton(secondaryXbox, RobotMap.OI.INTAKING_ROUTINE);
        intakingRoutine.whenPressed(new IntakingRoutine());

        //climber safety button
    }

    /**public double getLeftY(){
        return removeDeadband(leftFlight.getY(GenericHID.Hand.kLeft));
    }

    public double getRightY(){
        return removeDeadband(rightFlight.getY(GenericHID.Hand.kRight));
    }*/

    public double getPrimaryXboxLeftTrigger(){
        return this.primaryXbox.getTriggerAxis(GenericHID.Hand.kLeft);
    }

    public double getPrimaryXboxRightTrigger(){
        return this.primaryXbox.getTriggerAxis(GenericHID.Hand.kRight);
    }

    public double getPrimaryXboxLeftX(){
        return this.primaryXbox.getX(GenericHID.Hand.kLeft);
    }

    public double getXboxRightY(){
        return this.secondaryXbox.getY(GenericHID.Hand.kRight);
    }

    public double getXboxRightX() {
        return this.secondaryXbox.getX(GenericHID.Hand.kRight);
    }

    public double getXboxRightTrigger(){
        return this.secondaryXbox.getTriggerAxis(GenericHID.Hand.kRight);
    }

    public double getXboxLeftTrigger(){
        return this.secondaryXbox.getTriggerAxis(GenericHID.Hand.kLeft);
    }

    public boolean getXboxRightBumper(){
        return this.secondaryXbox.getBumper(GenericHID.Hand.kRight);
    }

    public boolean getXboxLeftBumper(){
        return this.secondaryXbox.getBumper(GenericHID.Hand.kLeft);
    }

    public boolean getXboxDpadUp(){
        return this.secondaryXbox.getPOV() == 0;
    }

    public boolean getXboxDpadDown(){
        return this.secondaryXbox.getPOV() == 180;
    }

    public boolean getXboxDpadLeft(){
        return this.secondaryXbox.getPOV() == 270;
    }

    public boolean getXboxDpadRight(){
        return this.secondaryXbox.getPOV() == 90;
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
