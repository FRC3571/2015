package org.usfirst.frc.team3571.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;

/**
 *
 * @author TomasR
 */
public class XboxController {
    private final Joystick joy;
    public Axis LeftStick=new Axis(0,0), RightStick=new Axis(0,0);
    public triggers Triggers=new triggers(0,0);
    private Button[] button=new Button[10];
    public buttons Buttons;
    
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
    public class buttons{
        public Button A =button[0];
        public Button B =button[1];
        public Button X =button[2];
        public Button Y =button[3];
        public Button Start =button[7];
        public Button Back =button[6];
        public Button LeftStick =button[8];
        public Button RightStick =button[9];
        public Button RB =button[5];
        public Button LB =button[4];
    	
    }
    public static class Button{
        public boolean current=false , last=false,changedDown=false,changedUp=false;
        private void set(boolean c){
        	last=current;
        	current=c;
        	changedDown=!last && current;
        	changedUp=last && !current;
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
    	for(int i=1;i<11;i++){
    		button[i-1].set(joy.getRawButton(i));
    	}
        leftStick();
        rightStick();
        trigger();
    }
    public XboxController(int i) {
        joy=new Joystick(i);
        for(int ii=0;ii<10;ii++){
        	button[ii]=new Button();
        }
        refresh();
        Buttons=new buttons();
    }
    public void vibrate(RumbleType type,float value){
    	joy.setRumble(type, value);
    }
}

