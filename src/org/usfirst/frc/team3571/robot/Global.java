package org.usfirst.frc.team3571.robot;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Relay.Value;

public class Global {
public static XboxController driver = new XboxController(0);
public static XboxController operator = new XboxController(1);

public static DoubleSolenoid Shifter = new DoubleSolenoid(0,1);
public static DoubleSolenoid MiddleWheel = new DoubleSolenoid(2,3);
public static DoubleSolenoid LiftArm = new DoubleSolenoid(4,5);

public static Preferences Settings = Preferences.getInstance();
public static Compressor Comp = new Compressor();
//public static Encoder LiftEncoder = new Encoder(0,1,false,EncodingType.k4X);
public static Point point=new Point(0, 0);
//public static Vission vission=new Vission();

//public static Switch BinSwitchBottom = new Switch(2);


public static final double fifthWheelToMainRatio=0.32831;
public static int ControlMode = 0;
public static int Direction = 1;
static double toteSpeed=0.8;

public static boolean HighGear = false;
public static boolean LiftArmActive = false;

public static RobotDrive Drive = new RobotDrive(0,1,2,3);
public static Talon FifthWheel = new Talon(4);
public static Talon BinLift = new Talon(5);

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
	
	if (Math.abs(Center) > 0 || Math.abs(X) > 0) {
		Global.FifthWheel.set(-(Center+(Math.max(-1,Math.min(1,X*fifthWheelToMainRatio)))));
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
public static class Intake{
	public static enum IntakeDirection{
		Off(Value.kOff,Value.kOff),
		Left(Value.kForward,Value.kForward),
		Up(Value.kReverse,Value.kForward),
		Down(Value.kForward,Value.kReverse),
		Right(Value.kReverse,Value.kReverse),
		UpLeft(Value.kOff,Value.kForward),
		UpRight(Value.kReverse,Value.kOff),
		DownLeft(Value.kOff,Value.kReverse),
		DownRight(Value.kForward,Value.kOff);
		public final Value r,l;
		private IntakeDirection(Value L, Value R){
			l=L;
			r=R;
		}
	}
	public static Relay IntakeMotorsL = new Relay(0);
	public static Relay IntakeMotorsR = new Relay(1);
	public static void set(IntakeDirection d){
		IntakeMotorsL.set(d.l);
		IntakeMotorsR.set(d.r);
	}
	static int LastPOV=-1;
	public static void set(XboxController.POV pov){
		if(pov.degrees!=LastPOV){
			LastPOV=pov.degrees;
			IntakeDirection dir;
			if(pov.Up){
				if(pov.Left)dir=IntakeDirection.UpLeft;
				else if(pov.Right)dir=IntakeDirection.UpRight;
				else dir=IntakeDirection.Up;
			}
			else if(pov.Down){
				if(pov.Left)dir=IntakeDirection.DownLeft;
				else if(pov.Right)dir=IntakeDirection.DownRight;
				else dir=IntakeDirection.Down;
			}
			else dir=IntakeDirection.Off;
			IntakeMotorsL.set(dir.l);
			IntakeMotorsR.set(dir.r);
		}
	}
	public static void stop(){
		IntakeMotorsL.set(Value.kOff);
		IntakeMotorsR.set(Value.kOff);
	}
}
	public static class ToteLift{
		public static Switch ToteSwitchBottomLeft = new Switch(3);
		public static Switch ToteSwitchTopLeft = new Switch(4);
		public static Switch ToteSwitchBottomRight = new Switch(5);
		public static Switch ToteSwitchTopRight = new Switch(6);
		private static Talon ToteLift1 = new Talon(6);
		private static Talon ToteLift2 = new Talon(7);
		private static double speed1=0,speed2=0;
		public static boolean ToteLiftUp = false, manual=false;
		public static int isMoving=0;
		public static void stop(){
			ToteLift1.stopMotor();
			ToteLift2.stopMotor();
			isMoving=0;
			speed1=speed2=0;
		}
		public static void set(double speed,boolean Manual){
			ToteLift1.set(-speed);
			ToteLift2.set(speed);
			speed2=speed1=speed;
			ToteLiftUp=speed>0;
			isMoving=3;
			manual=Manual;
			
		}
		public static void Refresh(){
			if(!manual){
				ToteSwitchBottomLeft.refresh();
				ToteSwitchTopLeft.refresh();
				ToteSwitchBottomRight.refresh();
				ToteSwitchTopRight.refresh();
				isMoving=(speed2!=0?2:0)+(speed1!=0?1:0);
				if ((ToteLiftUp && ToteSwitchTopLeft.Current) || (!ToteLiftUp && ToteSwitchBottomLeft.Current)) {
					ToteLift1.stopMotor();
					speed1=0;
				}
				if ((ToteLiftUp && ToteSwitchTopRight.Current) || (!ToteLiftUp && ToteSwitchBottomRight.Current)) {
					ToteLift2.stopMotor();
					speed2=0;
				}
			}
		}
	}
}