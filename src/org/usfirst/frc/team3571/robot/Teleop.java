package org.usfirst.frc.team3571.robot;

import org.usfirst.frc.team3571.robot.XboxController.*;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Joystick.RumbleType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Teleop extends Global {
	static int Countdown = 0;
	static int Acceleration = 0;
	static int LimitedSpeed = 0;
	static Axis LeftStick=driver.LeftStick;
	static Axis RightStick=driver.RightStick;
	static triggers Triggers = driver.Triggers;
	static buttons DriverButtons = driver.Buttons;
	static buttons OperatorButtons = operator.Buttons;
	static POV OperatorDpad = operator.DPad;
	static double XSpeed;
	static double Y;
	static double X;
	static double Strafe;
	static double LiftY = 0;
	static double ToteStack = 0;
	static double LiftHeight = 0;
	static int run=0;
	static double driveMax=0.8;
	static boolean Manual = false;
	//public static LogExcel l;
	//static Command log;

	/**
 	* Teleop initialization code
	 * @throws Exception 
 	*/
	 public static void TeleopInit() throws Exception{
	    try{
	    	//log=new ExcelLog();
	    	//log.start();
		 Comp.start();
	    	ControlMode = Settings.getInt("ControlMode", 0);
			MiddleWheel.set(Value.kReverse);
			run=0;
	    }
	    catch(Exception e){
	    	throw new Exception("TelInit"+ " "+e.getMessage());
	    }
	 }
	 /**
	  * Teleop periodic code
	  */
	 public static void TeleopP() throws Exception{
		 int n=0;
		 run++;
			try{
				Manual=SmartDashboard.getBoolean("ToteManual", false);
				//SmartDashboard.putNumber("LiftEncoder", LiftEncoder.getDistance());
				SmartDashboard.putNumber("Totes", ToteStack);
				SmartDashboard.putNumber("Totes", ToteStack);
				SmartDashboard.putBoolean("LiftArm", LiftArmActive);
				toteSpeed=Settings.getDouble("ToteSpeed", 0.8);
				n = 1;
				if(DriverButtons.LeftStick.changedDown){
					if(MiddleWheel.get() ==Value.kReverse) MiddleWheel.set(Value.kForward);
					else MiddleWheel.set(Value.kReverse);
				}
				if (DriverButtons.X.changedDown)
				{
					Countdown = 5;
					
					if (HighGear)
					{
						Shifter.set(Value.kReverse);
					} else {
						Shifter.set(Value.kForward);
					}
					HighGear=!HighGear;
				}
				n = 2;
				SmartDashboard.putNumber("ControlMode", ControlMode);
				
				if (DriverButtons.Start.changedDown){
					ControlMode = (ControlMode+1)%2;
				}
				Y = LeftStick.Y;
				X = LeftStick.X;
				Strafe = RightStick.X;
				
				if(Math.abs(Strafe)<0.2)Strafe=0;
				if (Math.abs(X) <= 0.2){
					X = 0;
				}	
				
				if (ControlMode == 0) {
					if (Math.abs(Y) <= 0.2){
						Y = 0;
					}	
				} else if (ControlMode == 1) {
					Y = -Triggers.Combined;
				}
				n = 3;
				
				if (Countdown > 0) {
					Countdown-=1;
					if (HighGear) {
						Y/=2.27;
						X/=2.27;
					} else {
						Y = (Math.abs(Y*2.27)<1?Y*2.27:Math.abs(Y)/Y);
						X = (Math.abs(X*2.27)<1?X*2.27:Math.abs(X)/X);
					}
				}
				if (Strafe > XSpeed) {
					XSpeed+=0.02; // change this later
					if (XSpeed > Strafe) {
						XSpeed = Strafe;
					}
				} else if (XSpeed > Strafe) {
					XSpeed-=0.02;
					if (Strafe > XSpeed){
						XSpeed = Strafe;
					}
				}
				if(Strafe==0)XSpeed=0;
				else if(XSpeed/Strafe>1)XSpeed=Strafe;
				else if(XSpeed*Strafe<0)XSpeed=0;
				n = 4;
				if(DriverButtons.B.current){
					X=0;
					Y=0;
					XSpeed=0;
				}
				if(DriverButtons.RightStick.current)XSpeed=Strafe;
				//Intake.set(OperatorDpad);
				
				n=5;
				
				if(OperatorButtons.X.changedDown){
					if(!LiftArmActive){
						LiftArm.set(Value.kForward);
						LiftArmActive = true;
					} else {
						LiftArm.set(Value.kReverse);
						LiftArmActive = false;
					}
				}
				
				if(OperatorButtons.RB.changedDown) ToteLift.set(toteSpeed,Manual);
				if(OperatorButtons.LB.changedDown) ToteLift.set(-toteSpeed,Manual);
				if(OperatorButtons.LeftStick.changedDown) ToteLift.stop();
				operator.vibrate(RumbleType.kRightRumble, ToteLift.isMoving>0?1:0);
				operator.vibrate(RumbleType.kLeftRumble, ToteLift.isMoving>0?1:0);				
				LiftY = -operator.Triggers.Combined;
				if(Math.abs(LiftY) > 0){
					BinLift.set(LiftY);
					Motors.bl=LiftY;
				} else {
					BinLift.stopMotor();
					Motors.bl=0;
				}
				if(Math.abs(Strafe)<0.2)Strafe=0;
				ArcadeDrive(X,(DriverButtons.A.current?-1:1)*(DriverButtons.Y.current?1:SmartDashboard.getNumber("driveMax",0.8))*Y,XSpeed);
				n = 6;
			} catch(Exception e) {
				throw new Exception("Teleop "+n+" "+e.getMessage());
			}
	 }
}
