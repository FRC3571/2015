package org.usfirst.frc.team3571.robot;

import org.usfirst.frc.team3571.robot.XboxController.Axis;
import org.usfirst.frc.team3571.robot.XboxController.Button;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Teleop {

	static int Countdown = 0;
	 public static void TeleopInit(){
	    	Global.Comp.start();
	    	Global.ControlMode = Global.Settings.getInt("ControlMode", 0);
	 }
	 public static void TeleopP() throws Exception{
		 int n=0;
			try{
				
				// code block
				RobotDrive Drive = Global.Drive;
				boolean bButton = Global.driver.getButton(Button.B);
				boolean xButton = Global.driver.getButton(Button.X);
				boolean xButtonLast = Global.driver.getLastButton(Button.X);
				boolean StartButton = Global.driver.getButton(Button.Start);
				boolean StartButtonLast = Global.driver.getLastButton(Button.Start);
				if (xButton && !xButtonLast)
				{
					Countdown = 5;
					
					if (Global.HighGear)
					{
						Global.Shifter.set(Value.kReverse);
						Global.HighGear = false;
					} else {
						Global.Shifter.set(Value.kForward);
						Global.HighGear = true;
					}
				}
				SmartDashboard.putNumber("ControlMode", Global.ControlMode);
				
				if (StartButton && !StartButtonLast){
					Global.ControlMode = (Global.ControlMode+1)%2;
				}
				
				Axis LeftStick = Global.driver.leftStick();
				Axis RightStick = Global.driver.rightStick();
				double RightTrigger = Global.driver.triggerRight();
				double LeftTrigger = Global.driver.triggerLeft();
				double Y = LeftStick.Y;
				double X = LeftStick.X;
				double Strafe = RightStick.X;
				
				if (Math.abs(X) <= 0.15){
					X = 0;
				}	
				
				if (Math.abs(Strafe) <= 0.15) {
					Strafe = 0;
				}
				
				if (Global.ControlMode == 0) {
					if (Math.abs(Y) <= 0.15){
						Y = 0;
					}	
				} else if (Global.ControlMode == 1) {
					if (RightTrigger > 0.05 || LeftTrigger > 0.05) {
						Y = RightTrigger - LeftTrigger;
					}
				}
				
				if (Countdown > 0) {
					Countdown-=1;
					if (Global.HighGear) {
						Y/=2.27;
						X/=2.27;
					} else {
						Y = (Math.abs(Y*2.27)<1?Y*2.27:Math.abs(Y)/Y);
						X = (Math.abs(X*2.27)<1?X*2.27:Math.abs(X)/X);
					}
				}

				if (!bButton && (Math.abs(X) > 0 || Math.abs(Y) > 0)){
					Drive.arcadeDrive(Y,X);
				}
				else {
					Drive.stopMotor();
				}
				
				if (Strafe > 0 || Strafe < 0) {
					Global.FifthWheel.set(Strafe);
				} else {
					Global.FifthWheel.stopMotor();
				}
				
				n=1;
				
				// code block
				
			} catch(Exception e) {
				throw e;
			}
	 }
}