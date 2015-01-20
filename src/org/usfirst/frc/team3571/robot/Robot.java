
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
	//RobotDrive d = new RobotDrive(0,1,2,3); 
	double driveX=0,driveY=0;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	Global.Shifter.set(Value.kReverse);
    	if (!Global.Settings.containsKey("ControlMode")) {
    		Global.Settings.putInt("ControlMode", 0);
    	}
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousInit(){
    	Autonomous.AutoInit();
    }
    		
    public void autonomousPeriodic() {
    	Autonomous.AutoP();
    }

    /**
     * This function is called periodically during operator control
     */
    
    public void teleopInit() {
    	Teleop.TeleopInit();
    }
    
    public void teleopPeriodic() {
    	try {
        	Teleop.TeleopP();
			ArduinoCom.main();
			Global.driver.refresh();
		} catch (Exception e) {
			SmartDashboard.putString("error", e.getMessage());
		}
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    public void disableInit(){
    	Global.Settings.putInt("ControlMode", Global.ControlMode);
    	Global.Settings.save();
		SmartDashboard.putString("error","");
		Global.Comp.stop();
    }
    
}