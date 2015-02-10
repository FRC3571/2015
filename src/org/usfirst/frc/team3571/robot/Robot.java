
package org.usfirst.frc.team3571.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.smartdashboard.*;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	//RobotDrive d = new RobotDrive(0,1,2,3);
	Relay re=new Relay(0);
	double driveX=0,driveY=0;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	try {
	    	Global.Shifter.set(DoubleSolenoid.Value.kReverse);
	    	if (!Global.Settings.containsKey("ControlMode")) {
	    		Global.Settings.putInt("ControlMode", 0);
	    	}
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
    	} catch (Exception e) {
    		SmartDashboard.putString("error", e.getMessage());
    	}
    }
    
    public void teleopPeriodic() {
    	try {
			Global.driver.refresh();
			Global.operator.refresh();
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
    	int d=Global.driver.getDpad();
    	if(d==0)re.set(Value.kForward);
    	else if(d==0)re.set(Value.kForward);
    	else re.set(Value.kOff);
    }
    public void disableInit(){
    	try {
	    	Global.Settings.putInt("ControlMode", Global.ControlMode);
	    	Global.Settings.save();
			SmartDashboard.putString("error","");
			Global.Comp.stop();
    	} catch (Exception e) {
    		SmartDashboard.putString("error", e.getMessage());
    	}
    }
    
}
