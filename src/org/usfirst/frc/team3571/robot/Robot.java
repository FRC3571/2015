
package org.usfirst.frc.team3571.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.*;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	Command AutoCommand;
	SendableChooser AutoChooser;
	double driveX=0,driveY=0;
	//Camera CameraThread;
	Timer TopLeftTimer = new Timer();
	Timer TopRightTimer = new Timer();
	int Reset = 0;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	try {
    		AutoChooser = new SendableChooser();
    		AutoChooser.addDefault("MoveAuto", new AutoMove());
    		AutoChooser.addObject("Expermental Tote Pickup", new AutoTote());
    		SmartDashboard.putData("AutoChoices", AutoChooser);
	    	Global.Shifter.set(Value.kReverse);
	    	if (!Global.Settings.containsKey("ControlMode")) {
	    		Global.Settings.putInt("ControlMode", 0);
	    	}
	    	if (!Global.Settings.containsKey("ToteHeight")) {
	    		Global.Settings.putInt("ToteHeight", 100);
	    	}
	    	if (!Global.Settings.containsKey("ToteSpeed")) {
	    		Global.Settings.putDouble("ToteSpeed", Global.toteSpeed);
	    	}
	    	//CameraThread = new Camera();
    	} catch (Exception e) {
    		SmartDashboard.putString("error", e.getMessage());
    	}
    }
    		
    public void autonomousInit() {
    	try {
    		AutoCommand = (Command)AutoChooser.getSelected();
    		AutoCommand.start();
    	} catch (Exception e) {
    		SmartDashboard.putString("error", e.getMessage());
    	}
    }
    public void autonomousPeriodic(){
    	Scheduler.getInstance().run();
    }
    
    public void teleopInit() {
    	try {
    		Teleop.TeleopInit();
    		//CameraThread.teleOp();
    	} catch (Exception e) {
    		SmartDashboard.putString("error", e.getMessage());
    	}
    }
    
    public void teleopPeriodic() {
    	try {
			Global.driver.refresh();
			Global.operator.refresh();
			//Global.BinSwitchBottom.refresh();
			//Global.ToteLift.Refresh();
			Teleop.TeleopP();
			//ArduinoCom.main();
		} catch (Exception e) {
			SmartDashboard.putString("error", e.getMessage());
		}
    }
    
    public void testInit(){
    	Global.ToteLift.set(-1,false);
    }
    public void testPeriodic() {
    	try {
    		Global.ToteLift.Refresh();
    		SmartDashboard.putNumber("RightTimer", TopRightTimer.get());
    		SmartDashboard.putNumber("LeftTimer", TopLeftTimer.get());
    		SmartDashboard.putNumber("difTimer",TopLeftTimer.get()-TopRightTimer.get());
    		if(Global.ToteLift.isMoving==0 && Global.ToteLift.ToteSwitchBottomLeft.Current){
    			TopLeftTimer.start();
    			TopRightTimer.start();
    			Global.ToteLift.set(1,false);
    		} 
    		if(Global.ToteLift.isMoving%2!=1){
    			TopLeftTimer.stop();
    		}
    		if(Global.ToteLift.isMoving>=2){
    			TopRightTimer.stop();
    		}
    		
    	} catch (Exception e) {
    		
    	}
    }
    public void disableInit(){
    	try {
			Global.Settings.putDouble("ToteSpeed", Global.toteSpeed);
	    	Global.Settings.putInt("ControlMode", Global.ControlMode);
	    	Global.Settings.save();
			SmartDashboard.putString("error","");
			Global.Comp.stop();
			//Global.vission.isRunning=false;
    	} catch (Exception e) {
    		SmartDashboard.putString("error", e.getMessage());
    	}
    }
    
}
