package org.usfirst.frc.team3571.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;

/**
 *
 * @author TomasR
 */
public class XboxController {
    private final Joystick joy;
    private final boolean[] previousButton=new boolean[10],currentButton=new boolean[10];
    public Axis LeftStick=new Axis(0,0), RightStick=new Axis(0,0);
    public triggers Triggers=new triggers(0,0);
    
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
    public static class Button{
        public int i;
        public Button(int a){
            i=a;
        }
        public static final Button A =new Button(1);
        public static final Button B =new Button(2);
        public static final Button X =new Button(3);
        public static final Button Y =new Button(4);
        public static final Button Start =new Button(8);
        public static final Button Back =new Button(7);
        public static final Button LeftStick =new Button(9);
        public static final Button RightStick =new Button(10);
        public static final Button RB =new Button(6);
        public static final Button LB =new Button(5);
    }
    public boolean getButton(Button button){
        return currentButton[button.i-1];
    }
    public boolean getLastButton(Button button){
        return previousButton[button.i-1];
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
        for (int j = 0; j < 10; j++) {
            previousButton[j]=currentButton[j];
            currentButton[j]=joy.getRawButton(j+1);
        }
        leftStick();
        rightStick();
    }
    public XboxController(int i) {
        joy=new Joystick(i);
        for (int j = 0; j < 10; j++) {
            currentButton[j]=joy.getRawButton(j);
        }
    }
    public void vibrate(RumbleType type,float value){
    	joy.setRumble(type, value);
    }
}

