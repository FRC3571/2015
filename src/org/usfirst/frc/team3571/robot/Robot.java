
package org.usfirst.frc.team3571.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.*;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	double driveX=0,driveY=0;
	Camera CameraThread;
	Timer TopLeftTimer = new Timer();
	Timer TopRightTimer = new Timer();
	int Reset = 0;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	try {
	    	Global.Shifter.set(Value.kReverse);
	    	if (!Global.Settings.containsKey("ControlMode")) {
	    		Global.Settings.putInt("ControlMode", 0);
	    	}
	    	if (!Global.Settings.containsKey("ToteHeight")) {
	    		Global.Settings.putInt("ToteHeight", 100);
	    	}
	    	CameraThread = new Camera();
    	} catch (Exception e) {
    		SmartDashboard.putString("error", e.getMessage());
    	}
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousInit(){
    	try {
    		Autonomous.AutoInit();
    	} catch (Exception e) {
    		SmartDashboard.putString("error", e.getMessage());
    	}
    }
    		
    public void autonomousPeriodic() {
    	try {
    		Autonomous.AutoP();
    	} catch (Exception e) {
    		SmartDashboard.putString("error", e.getMessage());
    	}
    }

    /**
     * This function is called periodically during operator control
     */
    
    public void teleopInit() {
    	try {
    		Teleop.TeleopInit();
    		CameraThread.teleOp();
    	} catch (Exception e) {
    		SmartDashboard.putString("error", e.getMessage());
    	}
    }
    
    public void teleopPeriodic() {
    	try {
			Global.driver.refresh();
			Global.operator.refresh();
			Global.BinSwitchBottom.refresh();
			Global.BinSwitchTop.refresh();
			Global.ToteSwitchBottom.refresh();
			Global.ToteSwitchTop.refresh();
			Teleop.TeleopP();
			ArduinoCom.main();
		} catch (Exception e) {
			SmartDashboard.putString("error", e.getMessage());
		}
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	try {
    		SmartDashboard.putNumber("RightTimer", TopRightTimer.get());
    		SmartDashboard.putNumber("LeftTimer", TopLeftTimer.get());
    		if(Global.ToteSwitchBottom.Current){
    			TopLeftTimer.start();
    			TopRightTimer.start();
    			Global.ToteLift1.set(1);
				Global.ToteLift2.set(1);
    		} 
    		
    		if(!Global.ToteSwitchTop.Current){
    			Global.ToteLift1.set(1);
    		}
    		if(Global.ToteSwitchTop.Pressed){
    			Global.ToteLift1.stopMotor();
    			TopLeftTimer.stop();
    			Reset+=1;
    		}
    		if(!Global.ToteSwitchTopRight.Current){
    			Global.ToteLift2.set(1);
    		}
    		if(Global.ToteSwitchTopRight.Pressed){
    			Global.ToteLift2.stopMotor();
    			TopRightTimer.stop();
    			Reset+=1;
    		}
    		
    		if(Reset==2){
    			if(!Global.ToteSwitchBottom.Current){
	    			Global.ToteLift1.set(-1);
    			}
    			if(!Global.ToteSwitchBottom.Current){
					Global.ToteLift2.set(-1);
    			}
    			if(Global.ToteSwitchBottom.Current&&Global.ToteSwitchBottomRight.Current){
    				TopLeftTimer.reset();
    				TopRightTimer.reset();
    				Reset = 0;
    				TopLeftTimer.start();
    				TopRightTimer.start();
    				Global.ToteLift1.set(1);
    				Global.ToteLift2.set(1);
    			}
    		} 
    		
    	} catch (Exception e) {
    		
    	}
    }
    public void disableInit(){
    	try {
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
