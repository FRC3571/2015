package org.usfirst.frc.team3571.robot;
import edu.wpi.first.wpilibj.Timer;

public class Autonomous {

	/**
	 * Autonomous initialization code
	 */
	public static Timer t1=new Timer();
 public static void AutoInit(){
	 t1.reset();
	 t1.start();
 }
 	
	/**
	 * Autonomous periodic code
	 */
 public static void AutoP(){
	 double time=t1.get();
	 if(time<2)Global.ArcadeDrive(0, 0.5, 0);
 }
}
