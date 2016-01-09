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
		 run++;//Counts the number of times that this code runs
			try{
				Manual=SmartDashboard.getBoolean("ToteManual", false);
				//SmartDashboard.putNumber("LiftEncoder", LiftEncoder.getDistance());
				SmartDashboard.putNumber("Totes", ToteStack);
				SmartDashboard.putNumber("Totes", ToteStack);
				SmartDashboard.putBoolean("LiftArm", LiftArmActive);
				toteSpeed=Settings.getDouble("ToteSpeed", 0.8);//Gets the front totelift speed from the saved settings
				n = 1;
				
				//Toggling middle wheel lift
				if(DriverButtons.LeftStick.changedDown){
					if(MiddleWheel.get() == Value.kReverse) MiddleWheel.set(Value.kForward);
					else MiddleWheel.set(Value.kReverse);
				}
				
				//Drive Shifter
				if (DriverButtons.X.changedDown)
				{
					Countdown = 5;//Delays the effect of the shift for 5 runs
					
					//Toggle code for the shifter
					if (HighGear)
					{
						Shifter.set(Value.kReverse);
					} else {
						Shifter.set(Value.kForward);
					}
					HighGear = !HighGear;
					
				}
				n = 2;
				SmartDashboard.putNumber("ControlMode", ControlMode);
				
				//Switches between the two control modes
				if (DriverButtons.Start.changedDown){
					ControlMode = (ControlMode+1) % 2;
				}
				
				//Gets the current drive values so that they can be easily manipulated after
				Y = ControlMode == 1 ? -Triggers.Combined :  LeftStick.Y; //In control mode 1 the triggers are used for forward motion else the left stick is used
				X = LeftStick.X;
				Strafe = RightStick.X;
				
				//Dead Zone elimination
				if(Math.abs(Strafe)<0.2)Strafe=0;
				if (Math.abs(X) <= 0.2){
					X = 0;
				}
				if (Math.abs(Y) <= 0.2){
					Y = 0;
				}
				n = 3;
				
				//During a shift the bot should still be moving at the same speed to lower the wear on the gears
				if (Countdown > 0) {
					Countdown -= 1;
					if (HighGear) {
						Y /= 2.27;
						X /= 2.27;
					} else {
						Y = (Math.abs(Y * 2.27) < 1 ? Y * 2.27 : Math.abs(Y) / Y);
						X = (Math.abs(X * 2.27) < 1 ? X * 2.27 : Math.abs(X) / X);
					}
				}
				
				//Experimental code for decreasing sensitivity of the middle wheel
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
				
				//Stop button code
				if(DriverButtons.B.current){
					X=0;
					Y=0;
					XSpeed=0;
				}
				if(DriverButtons.RightStick.current)XSpeed=Strafe;
				//Intake.set(OperatorDpad);
				
				n = 5;
				
				//Claw opening and closing toggle
				if(OperatorButtons.X.changedDown){
					if(!LiftArmActive){
						LiftArm.set(Value.kForward);
					} else {
						LiftArm.set(Value.kReverse);
					}
					LiftArmActive = !LiftArmActive;
				}
				
				//Back ToteLift system for setting and stopping the lift
				if(OperatorButtons.RB.changedDown) ToteLift.set(toteSpeed,Manual);
				if(OperatorButtons.LB.changedDown) ToteLift.set(-toteSpeed,Manual);
				if(OperatorButtons.LeftStick.changedDown) ToteLift.stop();
				
				//Vibrates the operator controller when the lift motors are on
				operator.vibrate(RumbleType.kRightRumble, ToteLift.isMoving>0?1:0);
				operator.vibrate(RumbleType.kLeftRumble, ToteLift.isMoving>0?1:0);	
				
				//Operates the front bin lift
				LiftY = -operator.Triggers.Combined;
				if(Math.abs(LiftY) > 0){
					BinLift.set(LiftY);
					Motors.bl=LiftY;
				} else {
					BinLift.stopMotor();
					Motors.bl=0;
				}
				
				//Custom drive code for 5 motors
				ArcadeDrive(X,(DriverButtons.A.current?-1:1)*(DriverButtons.Y.current?1:SmartDashboard.getNumber("driveMax",0.8))*Y,XSpeed);
				
				n = 6;
			} catch(Exception e) {
				throw new Exception("Teleop "+n+" "+e.getMessage());
			}
	 }
}
