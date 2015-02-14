package org.usfirst.frc.team3571.robot;

import org.usfirst.frc.team3571.robot.XboxController.*;

import edu.wpi.first.wpilibj.PowerDistributionPanel;


import edu.wpi.first.wpilibj.Relay;
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
	static buttons DriverButtons = Global.driver.Buttons;
	static buttons OperatorButtons = Global.operator.Buttons;
	static int Dpad = Global.operator.getDpad();
	static double YSpeed;
	static double Y;
	static double X;
	static double Strafe;
	static double LiftY = 0;
	static double ToteStack = 0;
	static double LiftHeight = 0;
	static boolean GoingDown = false;
	static boolean GoingUp = false;

	/**
 	* Teleop initialization code
 	*/
	 public static void TeleopInit(){
	    	Global.Comp.start();
	    	Global.ControlMode = Global.Settings.getInt("ControlMode", 0);
	 }
	 /**
	  * Teleop periodic code
	  */
	 public static void TeleopP() throws Exception{
		 int n=0;
			try{
				Dpad = Global.operator.getDpad();
				for(int i=0;i<4;i++){
					SmartDashboard.putNumber("Amps"+i, pdp.getCurrent(i));
				}
				SmartDashboard.putNumber("TotalAmps", pdp.getTotalCurrent());
				SmartDashboard.putNumber("LiftEncoder", Global.LiftEncoder.getDistance());
				SmartDashboard.putNumber("Totes", ToteStack);
				SmartDashboard.putBoolean("LiftSwitchBottom", Global.LiftSwitchBottom.Current);
				SmartDashboard.putBoolean("LiftArm", Global.LiftArmActive);
				n = 1;
				if (DriverButtons.X.changedDown)
				{
					Countdown = 5;
					
					if (Global.HighGear)
					{
						Global.Shifter.set(Value.kReverse);
					} else {
						Global.Shifter.set(Value.kForward);
					}
					Global.HighGear=!Global.HighGear;
				}
				n = 2;
				SmartDashboard.putNumber("ControlMode", Global.ControlMode);
				
				if (DriverButtons.Start.changedDown){
					Global.ControlMode = (Global.ControlMode+1)%2;
				}
				Y = LeftStick.Y;
				X = LeftStick.X;
				Strafe = RightStick.X;
				
				if (Math.abs(X) <= 0.15){
					X = 0;
				}	
				
				if (Global.ControlMode == 0) {
					if (Math.abs(Y) <= 0.15){
						Y = 0;
					}	
				} else if (Global.ControlMode == 1) {
					Y = -Triggers.Combined;
				}
				n = 3;
				
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
				n = 4;
				if(DriverButtons.B.current){
					X=0;
					Y=0;
					YSpeed=0;
				}
				if(Dpad == 180){
					Global.IntakeMotors.set(Relay.Value.kReverse);
				} else if(Dpad == 0) {
					Global.IntakeMotors.set(Relay.Value.kForward);
				} else {
					Global.IntakeMotors.set(Relay.Value.kOff);
				}
				
				if(DriverButtons.RB.changedDown){
					if(Global.CameraLights.get() == Relay.Value.kOff){
						Global.CameraLights.set(Relay.Value.kOn);
					} else {
						Global.CameraLights.set(Relay.Value.kOff);
					}
				}
				
				if(OperatorButtons.X.changedDown){
					if(!Global.LiftArmActive){
						Global.LiftArm.set(Value.kForward);
						Global.LiftArmActive = true;
					} else {
						Global.LiftArm.set(Value.kReverse);
						Global.LiftArmActive = false;
					}
				}
				
				LiftY = -Global.operator.Triggers.Combined;
				if(Math.abs(LiftY) > 0){
					GoingUp = false;
					GoingDown = false;
					LiftHeight = 0;
				}
				
				if(OperatorButtons.Y.changedDown){
					LiftHeight = Global.LiftEncoder.get()+Global.Settings.getInt("ToteHeight", 100);
					GoingUp = true;
				}
				
				if(OperatorButtons.A.changedDown){
					LiftHeight = 0;
					GoingDown = true;
				}
				
				if(Global.LiftEncoder.get() < LiftHeight && GoingUp){
					LiftY = 0.7;
				} else {
					GoingUp = false;
				}
				
				if(GoingDown && !Global.LiftSwitchBottom.Current){
					LiftY = -0.7;
				}
				
				if(LiftY > 0 || (LiftY < 0 && !Global.LiftSwitchBottom.Current)){
					Global.LiftMotor.set(LiftY);
				} else {
					Global.LiftMotor.stopMotor();
				}
				
				if(Global.LiftSwitchBottom.Pressed){
					GoingDown = false;
					Global.LiftEncoder.reset();
				}
				
				Global.ArcadeDrive(X,(Global.AccelerationLimit? YSpeed:Y),Strafe);
				n = 5;
			} catch(Exception e) {
				throw new Exception("Teleop "+n);
			}
	 }
}
