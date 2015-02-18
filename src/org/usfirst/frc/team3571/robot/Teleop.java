package org.usfirst.frc.team3571.robot;

import org.usfirst.frc.team3571.robot.Global.Intake;
import org.usfirst.frc.team3571.robot.Global.Intake.IntakeDirection;
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
	static POV OperatorDpad = Global.operator.DPad;
	static double YSpeed;
	static double Y;
	static double X;
	static double Strafe;
	static double LiftY = 0;
	static double ToteStack = 0;
	static double LiftHeight = 0;

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
				for(int i=0;i<4;i++){
					SmartDashboard.putNumber("Amps"+i, pdp.getCurrent(i));
				}
				SmartDashboard.putNumber("TotalAmps", pdp.getTotalCurrent());
				SmartDashboard.putNumber("LiftEncoder", Global.LiftEncoder.getDistance());
				SmartDashboard.putNumber("Totes", ToteStack);
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
				
				if (Math.abs(X) <= 0.2){
					X = 0;
				}	
				
				if (Global.ControlMode == 0) {
					if (Math.abs(Y) <= 0.2){
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
				
				Intake.set(OperatorDpad);
				
				n=5;
				
				if(OperatorButtons.X.changedDown){
					if(!Global.LiftArmActive){
						Global.LiftArm.set(Value.kForward);
						Global.LiftArmActive = true;
					} else {
						Global.LiftArm.set(Value.kReverse);
						Global.LiftArmActive = false;
					}
				}
				
				if(OperatorButtons.RB.changedDown) Global.ToteLift.set(1);
				if(OperatorButtons.LB.changedDown) Global.ToteLift.set(-1);
				if(OperatorButtons.LeftStick.changedDown) Global.ToteLift.stop();
				
				LiftY = -Global.operator.Triggers.Combined;
				if(Math.abs(LiftY) > 0){
					Global.BinLift.set(LiftY);
				} else {
					Global.BinLift.stopMotor();
				}
				if(Math.abs(Strafe)<0.2)Strafe=0;
				Global.ArcadeDrive(X,(Global.AccelerationLimit? YSpeed:Y),Strafe);
				n = 6;
			} catch(Exception e) {
				throw new Exception("Teleop "+n+" "+e.getMessage());
			}
	 }
}
