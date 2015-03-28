package org.usfirst.frc.team3571.robot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Autonomous {

	
	static Timer t1=new Timer();
 public static void AutoInit(){
	 t1.reset();
	 t1.start();
	 Global.MiddleWheel.set(Value.kReverse);
 }
 	
	static double time=0;
 public static void AutoP(){
	 time=t1.get();
	 if(time<1.5)Global.ArcadeDrive(0, 1, 0);
 }
}
