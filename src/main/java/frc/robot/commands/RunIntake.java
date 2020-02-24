/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.subsystems.Intake;

public class RunIntake extends CommandBase {
	/**
	 * Creates a new RunIntake.
	 */
	public RunIntake() {
	  // Use addRequirements() here to declare subsystem dependencies.
	  	addRequirements(Intake.getInstance());
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		Intake.getInstance().runIntake(0);
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
	public void execute() {
		Intake.getInstance().runIntake(OI.getINSTANCE().getXboxRightTrigger());
		Intake.getInstance().setDeployed(OI.getINSTANCE().getXboxRightBumper());
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
	  	return false;
	}
}
