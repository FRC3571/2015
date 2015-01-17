package org.usfirst.frc.team3571.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Talon;

public class Global {
public static XboxController driver = new XboxController(0);
public static int Direction = 1;
public static boolean HighGear = false;
public static int ControlMode = 0;
public static Preferences Settings = Preferences.getInstance();
public static RobotDrive Drive = new RobotDrive(0,1,2,3);
public static DoubleSolenoid Shifter = new DoubleSolenoid(0,1);
public static Compressor Comp = new Compressor();
public static Talon FifthWheel = new Talon(4);
}
