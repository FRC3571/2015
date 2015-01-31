package org.usfirst.frc.team3571.robot;

import org.usfirst.frc.team3571.robot.XboxController.*;

import edu.wpi.first.wpilibj.PowerDistributionPanel;


import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Teleop {
	
	static PowerDistributionPanel pdp=new PowerDistributionPanel();
	static int Countdown = 0;
	static int Acceleration = 0;
	static int LimitedSpeed = 0;
	static Axis LeftStick=Global.driver.LeftStick;
	static Axis RightStick=Global.driver.RightStick;
	static triggers Triggers = Global.driver.Triggers;
	static double RightTrigger;
	static double LeftTrigger;
	static double YSpeed;
	static double Y;
	static double X;
	static double Strafe;
	
	 public static void TeleopInit(){
	    	Global.Comp.start();
	    	Global.ControlMode = Global.Settings.getInt("ControlMode", 0);
	 }
	 public static void TeleopP() throws Exception{
		 int n=0;
			try{
				
				for(int i=0;i<4;i++){
					SmartDashboard.putNumber("Amps"+i, pdp.getCurrent(i));
				}
				buttons DriverButtons=Global.driver.Buttons;
				SmartDashboard.putNumber("TotalAmps", pdp.getTotalCurrent());
				if (DriverButtons.X.changedHigh)
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
				
				if (DriverButtons.Start.changedHigh){
					Global.ControlMode = (Global.ControlMode+1)%2;
				}
				Y = LeftStick.Y;
				X = LeftStick.X;
				Strafe = RightStick.X;
				
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
					if (Math.abs(Triggers.Combined) > 0.05) {
						Y = Triggers.Combined;
					} else {
						Y = 0;
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
				
				if (Y > YSpeed) {
					YSpeed+=0.01; // change this later
					if (YSpeed > Y) {
						YSpeed = Y;
					}
				} else if (YSpeed > Y) {
					YSpeed-=0.01;
					if (Y > YSpeed){
						YSpeed = Y;
					}
				}
				
				if(DriverButtons.B.current){
					X=0;
					Y=0;
					YSpeed=0;
				}
				
				if (Global.AccelerationLimit) {
					Global.ArcadeDrive(X, YSpeed);
				} else {
					Global.ArcadeDrive(X,Y);
				}
				
				if (Strafe > 0 || Strafe < 0) {
					Global.FifthWheel.set(Strafe);
				} else {
					Global.FifthWheel.stopMotor();
				}
				
				
			} catch(Exception e) {
				throw e;
			}
	 }
}
