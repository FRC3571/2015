package org.usfirst.frc.team3571.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class AutoMove extends Command {

	Timer t1=new Timer();
	double mTime=1.5,mspeed=1;
    public AutoMove() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
   	 t1.reset();
   	 t1.start();
   	 mTime=SmartDashboard.getNumber("AutoSpeed", 1);
	mspeed=	SmartDashboard.getNumber("AutoTime", 1.5);
   	 Global.MiddleWheel.set(Value.kReverse);
    }

    // Called repeatedly when this Command is scheduled to run
    double time=0;
    protected void execute() {
   	 time=t1.get();
   	 if(time<mTime)Global.ArcadeDrive(0, mspeed, 0);//Move forward for a set amount o time
   	 else Global.ArcadeDrive(0, 0, 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timeSinceInitialized()>mTime+0.1;//Finish 0.1s after the set time to allow the bot to stop the motors
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
