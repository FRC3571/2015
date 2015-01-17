
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
    public void autonomousPeriodic() {

    }

    /**
     * This function is called periodically during operator control
     */
    
    public void teleopInit() {
    	Global.Comp.start();
    	Global.ControlMode = Global.Settings.getInt("ControlMode", 0);
    }
    
    public void teleopPeriodic() {/*
    	driveX=driver.getRawAxis(0);
    	driveY=driver.getRawAxis(1);
    	if(Math.sqrt(Math.pow(driveX, 2)+Math.pow(driveY, 2))>0.1) d.arcadeDrive(driveY, driveX);
    	else d.stopMotor();
    	driver.setRumble(RumbleType.kLeftRumble, (driver.getRawButton(3)?1f:0));
    	driver.setRumble(RumbleType.kRightRumble, (driver.getRawButton(4)?1f:0));
    	*/
    	try {
        	nik.main();
			marium.main();
			tom.main();
			Global.driver.refresh();
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
