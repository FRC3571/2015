package org.usfirst.frc.team3571.robot;

import org.usfirst.frc.team3571.robot.XboxController.*;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

import org.usfirst.frc.team3571.robot.XboxController.Button;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Teleop {
	
	static PowerDistributionPanel pdp=new PowerDistributionPanel();
	static int Countdown = 0;
	static boolean bButton;
	static boolean xButton;
	static boolean xButtonLast;
	static boolean StartButton;
	static boolean StartButtonLast;
	static Axis LeftStick=Global.driver.LeftStick;
	static Axis RightStick=Global.driver.RightStick;
	static triggers Triggers = Global.driver.Triggers;
	static double RightTrigger;
	static double LeftTrigger;
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
				SmartDashboard.putNumber("TotalAmps", pdp.getTotalCurrent());
				bButton = Global.driver.getButton(Button.B);
				xButton = Global.driver.getButton(Button.X);
				xButtonLast = Global.driver.getLastButton(Button.X);
				StartButton = Global.driver.getButton(Button.Start);
				StartButtonLast = Global.driver.getLastButton(Button.Start);
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
				//LeftStick = Global.driver.LeftStick;
				//RightStick = Global.driver.RightStick;
				Y = LeftStick.Y;
				X = LeftStick.X;
				Strafe = Triggers.Combined;
				
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
				if(bButton){
					X=0;
					Y=0;
				}
				Global.ArcadeDrive(X, Y);
				
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
