package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import frc.robot.RobotMap;
import frc.robot.commands.Shoot;

public class Shooter extends SubsystemBase {

// Any variables/fields used in the constructor must appear before the "INSTANCE" variable
// so that they are initialized before the constructor is called.

    /**
     * The Singleton instance of this Shooter. External classes should
     * use the {@link #getInstance()} method to get the instance.
     */
    private final static Shooter INSTANCE = new Shooter();

    private CANSparkMax top;
    private CANSparkMax bot;

    private Servo hood;

    /**
     * Creates a new instance of this Shooter.
     * This constructor is private since this class is a Singleton. External classes
     * should use the {@link #getInstance()} method to get the instance.
     */
    private Shooter() {
        // TODO: Set the default command, if any, for this subsystem by calling setDefaultCommand(command)
        //       in the constructor or in the robot coordination class, such as RobotContainer.
        //       Also, you can call addChild(name, sendableChild) to associate sendables with the subsystem
        //       such as SpeedControllers, Encoders, DigitalInputs, etc

        //this.top = new CANSparkMax(RobotMap.SHOOTER.TOP, CANSparkMaxLowLevel.MotorType.kBrushless);
        //this.bot = new CANSparkMax(RobotMap.SHOOTER.BOT, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.top = new CANSparkMax(12, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.bot = new CANSparkMax(13, CANSparkMaxLowLevel.MotorType.kBrushless);
        //this.bot.setInverted(false);
        //this.bot.follow(this.top);

        this.hood = new Servo(8);
    }

    public void initDefaultCommand(){
        setDefaultCommand(new Shoot());
    }

    public void shoot(double power){
        this.top.set(power);
        this.bot.set(power);
    }

    /**
     * Returns the Singleton instance of this Shooter. This static method
     * should be used -- {@code Shooter.getInstance();} -- by external
     * classes, rather than the constructor to get the instance of this class.
     */
    public static Shooter getInstance() {
        return INSTANCE;
    }

}
