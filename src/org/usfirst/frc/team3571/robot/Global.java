package org.usfirst.frc.team3571.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

public class Global {
public static XboxController driver = new XboxController(0);
public static XboxController operator = new XboxController(1);
public static int Direction = 1;
public static boolean HighGear = false;
public static boolean AccelerationLimit = false;
public static int ControlMode = 0;
public static Preferences Settings = Preferences.getInstance();
public static RobotDrive Drive = new RobotDrive(0,1,2,3);
public static DoubleSolenoid Shifter = new DoubleSolenoid(0,1);
public static Compressor Comp = new Compressor();
public static Talon FifthWheel = new Talon(4);
public static Talon LiftMotor = new Talon(5);
public static Encoder LiftEncoder = new Encoder(0,1,false,EncodingType.k4X);

public static void ArcadeDrive(double X, double Y, double Center){
	
	if (Math.abs(Center) > 0.2 || Math.abs(X) > 0) {
		//if (Math.abs(Center+(X*0.32831)) > 1 ){ 
		//	Center-=(Center+(X*0.32831))%1;
		//}
		Global.FifthWheel.set(Center+(X*0.32831));
	} else {
		Global.FifthWheel.stopMotor();
	}
	
	if (Math.abs(X) > 0 || Math.abs(Y) > 0){
		Drive.arcadeDrive(Y,X);
	}
	else {
		Drive.stopMotor();
	}
}
}
