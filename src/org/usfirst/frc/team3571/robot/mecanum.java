package org.usfirst.frc.team3571.robot;
import edu.wpi.first.wpilibj.*;
public class mecanum {
	static Talon m1=new Talon(1),m2=new Talon(2),m3=new Talon(3),m4=new Talon(4);
	public static void v1(double x,double y,double r){
		double v=y-0.5d,ro=x-0.5d,s=r-0.5d;
		m1.set(v-ro-s+0.5d);
		m2.set(0.5d-v-r-s);
		m3.set(v-ro+s+0.5d);
		m4.set(0.5d-v-r+s);
	}
	public static void stop(){
		m1.stopMotor();
		m2.stopMotor();
		m3.stopMotor();
		m4.stopMotor();
	}
	public static void v2(double x,double y,double r){
		double speed=Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2));
		double angle=0;
		double[] ms=new double[4];
	}
}
