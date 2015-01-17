package org.usfirst.frc.team3571.robot;
import edu.wpi.first.wpilibj.Talon;
public class marium {
	 static Talon motor1 = new Talon(1);
	
	public static void main()  throws Exception {
	int n = 0;
	try 
	{
		
		n = 1;
		if (Math.abs(Global.driver.leftStick().Y) >= 0.15){
			motor1.set(Global.driver.leftStick().Y* Global.Direction);
		}
		else {
		
			motor1.stopMotor();
		} 
	}
		catch (Exception e) {
		throw e;
		}
	}

}