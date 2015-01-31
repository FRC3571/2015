package org.usfirst.frc.team3571.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;

/**
 *
 * @author TomasR
 */
public class NiksController {
    private final Joystick joy;
    public Axis LeftStick=new Axis(0,0), RightStick=new Axis(0,0);
    public triggers Triggers=new triggers(0,0);
    
    public boolean[] Buttons = new boolean[10];
    public boolean[] ButtonsLast = new boolean[10];
    
    public class triggers{
    	public double Right;
    	public double Left;
    	public double Combined;
    	public triggers(double r, double l){
    		Right=r;
    		Left=l;
    		combine();
    	}
    	private void combine(){
    		Combined=Right-Left;
    	}
    }
    public class Axis{
        public double X,Y;
        public Axis(double x,double y){
            X=x;
            Y=y;
        }
    }
    public int getDpad(){
    	return joy.getPOV(0);
    }
    private void leftStick(){
    	LeftStick.X=joy.getRawAxis(0);
    	LeftStick.Y=joy.getRawAxis(1);
    }
    private void rightStick(){
    	RightStick.X=joy.getRawAxis(4);
    	RightStick.Y=joy.getRawAxis(5);
    }
    public void trigger(){
        Triggers.Left = joy.getRawAxis(2);
        Triggers.Right = joy.getRawAxis(3);
        Triggers.combine();
    }
    
    public void refresh(){
        for (int j = 1; j < 11; j++) {
            ButtonsLast[j]=Buttons[j];
            Buttons[j] = joy.getRawButton(j);
        }
        leftStick();
        rightStick();
        trigger();
    }
    public NiksController(int i) {
        joy=new Joystick(i);
        for (int j = 1; j < 11; j++) {
            Buttons[j]=joy.getRawButton(j);
        }
    }
    public void vibrate(RumbleType type,float value){
    	joy.setRumble(type, value);
    }
}

class Button{
   public static int A = 1;
   public static int B = 2;
   public static int X = 3;
   public static int Y = 4;
   public static int LB = 5;
   public static int RB = 6;
   public static int Back = 7;
   public static int Start = 8;
   public static int LeftStickButton = 9;
   public static int RightStickButton = 10;
}