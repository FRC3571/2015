package org.usfirst.frc.team3571.robot;

import org.usfirst.frc.team3571.robot.XboxController.*;



import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Teleop {
	static int Countdown = 0;
	static int Acceleration = 0;
	static int LimitedSpeed = 0;
	static Axis LeftStick=Global.driver.LeftStick;
	static Axis RightStick=Global.driver.RightStick;
	static triggers Triggers = Global.driver.Triggers;
	static buttons DriverButtons = Global.driver.Buttons;
	static buttons OperatorButtons = Global.operator.Buttons;
	static POV OperatorDpad = Global.operator.DPad;
	static double XSpeed;
	static double Y;
	static double X;
	static double Strafe;
	static double LiftY = 0;
	static double ToteStack = 0;
	static double LiftHeight = 0;
	static int run=0;
	static double driveMax=0.8;
	final static boolean Manual = false;
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
		 Global.Comp.start();
	    	Global.ControlMode = Global.Settings.getInt("ControlMode", 0);
			Global.MiddleWheel.set(Value.kReverse);
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
				
				//SmartDashboard.putNumber("LiftEncoder", Global.LiftEncoder.getDistance());
				SmartDashboard.putNumber("Totes", ToteStack);
				SmartDashboard.putBoolean("LiftArm", Global.LiftArmActive);
				Global.toteSpeed=Global.Settings.getDouble("ToteSpeed", 0.8);
				n = 1;
				if(DriverButtons.LeftStick.changedDown){
					if(Global.MiddleWheel.get() ==Value.kReverse) Global.MiddleWheel.set(Value.kForward);
					else Global.MiddleWheel.set(Value.kReverse);
				}
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
				
				if(Math.abs(Strafe)<0.2)Strafe=0;
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
				
				//Intake.set(OperatorDpad);
				
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
				
				if(OperatorButtons.RB.changedDown) Global.ToteLift.set(Global.toteSpeed,Manual);
				if(OperatorButtons.LB.changedDown) Global.ToteLift.set(-Global.toteSpeed,Manual);
				if(OperatorButtons.LeftStick.changedDown) Global.ToteLift.stop();
				
				LiftY = -Global.operator.Triggers.Combined;
				if(Math.abs(LiftY) > 0){
					Global.BinLift.set(LiftY);
					Global.Motors.bl=LiftY;
				} else {
					Global.BinLift.stopMotor();
					Global.Motors.bl=0;
				}
				if(Math.abs(Strafe)<0.2)Strafe=0;
				Global.ArcadeDrive(X,(DriverButtons.A.current?-1:1)*(DriverButtons.RightStick.current?1:SmartDashboard.getNumber("driveMax",0.8))*Y,XSpeed);
				n = 6;
			} catch(Exception e) {
				throw new Exception("Teleop "+n+" "+e.getMessage());
			}
	 }
}
