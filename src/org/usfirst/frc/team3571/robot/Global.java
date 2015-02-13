package org.usfirst.frc.team3571.robot;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

public class Global {
public static final double fifthWheelToMainRatio=0.32831;
public static XboxController driver = new XboxController(0);
public static XboxController operator = new XboxController(1);
public static DoubleSolenoid Shifter = new DoubleSolenoid(0,1);
public static Preferences Settings = Preferences.getInstance();
public static RobotDrive Drive = new RobotDrive(0,1,2,3);
public static Compressor Comp = new Compressor();
public static Talon FifthWheel = new Talon(4);
public static Talon LiftMotor = new Talon(5);
public static boolean AccelerationLimit = false;
public static boolean HighGear = false;
public static int ControlMode = 0;
public static int Direction = 1;
public static Encoder LiftEncoder = new Encoder(0,1,false,EncodingType.k4X);
public static Point point=new Point(0, 0);
public static Vission vission=new Vission();
public static Relay IntakeMotors = new Relay(0);
public static Relay CameraLights = new Relay(1);



static class Point{
	public double X,Y;
	public Point(double x,double y){
		X=x;
		Y=y;
	}
}
/**
 * Controlling main 5 wheel drive
 * @param X Rotation Value
 * @param Y Forward/Back motion value
 * @param Center Sideways motion value
 */
public static void ArcadeDrive(double X, double Y, double Center){
	
	if (Math.abs(Center) > 0.15 || Math.abs(X) > 0) {
		Global.FifthWheel.set(Center+(Math.max(-1,Math.min(1,X*fifthWheelToMainRatio))));
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
