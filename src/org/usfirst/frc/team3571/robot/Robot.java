
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
	PowerDistributionPanel pdp=new PowerDistributionPanel();
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	try {
    		SmartDashboard.putNumber("TotalAmps", pdp.getTotalCurrent());
    		SmartDashboard.putBoolean("AutoOFF", false);
    		SmartDashboard.putBoolean("ToteManual", false);
    	   	SmartDashboard.putNumber("AutoSpeed", 1);
    	 	SmartDashboard.putNumber("AutoTime", 1.5);
    	 	
    	 	//Autonomous chooser code
    		AutoChooser = new SendableChooser();
    		AutoChooser.addDefault("MoveAuto", new AutoMove());
    		AutoChooser.addObject("Expermental Tote Pickup", new AutoTote());
    		SmartDashboard.putData("AutoChoices", AutoChooser);
    		
    		//The rest in this method is for checking if a variable is in the robots saved file and getting it from there
    		//else it will put a default value into the file
	    	if (!Global.Settings.containsKey("driveMax")) {
	    		Global.Settings.putDouble("driveMax", 0.8);
	    		SmartDashboard.putNumber("driveMax", 0.8);
	    	}
	    	else SmartDashboard.putNumber("driveMax", Global.Settings.getDouble("driveMax", 0.8));
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
    		if(!SmartDashboard.getBoolean("AutoOFF",false)){
	    		AutoCommand = (Command)AutoChooser.getSelected();
	    		AutoCommand.start();
    		}
    	} catch (Exception e) {
    		SmartDashboard.putString("error", e.getMessage());
    	}
    }
    public void autonomousPeriodic(){
    	Scheduler.getInstance().run();
    }
    
    public void teleopInit() {
    	try {
    		//log=new ExcelLog();
    		//log.start();
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
			Global.ToteLift.Refresh();
			Teleop.TeleopP();
			//ArduinoCom.main();
	    	//Scheduler.getInstance().run();
			
		} catch (Exception e) {
			SmartDashboard.putString("error", e.getMessage());
		}
    }
    
    public void testInit(){
    	Global.ToteLift.set(-1,false);
    }
    public void testPeriodic() {
    	//Shows the time it takes for each of the tote lift arms to reach the top sensor
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
    public void disabledInit(){
    	try {
    		//if(!log.isCanceled())log.cancel();
    		Global.Settings.putDouble("driveMax",SmartDashboard.getNumber("driveMax", 0.8) );
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
