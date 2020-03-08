package frc.robot.commands.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.util.QuadTimer;


public class CountBallsShot extends CommandBase {
    private int ballsShotSoFar;
    private int ballsShotGoal;
    private boolean topLinebreakTripped;
    private QuadTimer timer;

    public CountBallsShot(int goal) {
        addRequirements();
        this.ballsShotSoFar = 0;
        this.ballsShotGoal = goal;
        this.topLinebreakTripped = false;
        this.timer = new QuadTimer();
        this.timer.reset();
        System.out.println("Timer: " + this.timer.get());
        this.timer.stop();
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        if(!this.topLinebreakTripped && Robot.getLinebreakTop().lineBroken()){
            this.ballsShotSoFar++;
            this.topLinebreakTripped = true;
            System.out.println("Balls shot: " + this.ballsShotSoFar);
        }
        if(this.topLinebreakTripped && !Robot.getLinebreakTop().lineBroken()){
            this.topLinebreakTripped = false;
        }
        if (this.ballsShotSoFar == this.ballsShotGoal && this.timer.get() == 0.0){
            this.timer.start();
            System.out.println("Starting Timer");
        }
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        System.out.println("Timer: " + this.timer.get());
        return (this.timer.get() > 0.5) && !Robot.getLinebreakTop().lineBroken();
    }

    @Override
    public void end(boolean interrupted) {

    }
}
