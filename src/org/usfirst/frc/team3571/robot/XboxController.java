package org.usfirst.frc.team3571.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;

/**
 *
 * @author TomasR
 */
public class XboxController{
    private final Joystick joy;
    private Button[] button=new Button[10];
    
    /**
     * Axis state as of the last refresh
     */
    public Axis LeftStick=new Axis(0,0), RightStick=new Axis(0,0);
    /**
     * trigger states as of the last refresh
     */
    public triggers Triggers=new triggers(0,0);
    /**
     * Button states as of the last refresh
     */
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
    class Axis{
        public double X,Y;
        public Axis(double x,double y){
            X=x;
            Y=y;
        }
    }
    class buttons{
        public Button A =button[0];
        public Button B =button[1];
        public Button X =button[2];
        public Button Y =button[3];
        public Button LB =button[4];
        public Button RB =button[5];
        public Button Back =button[6];
        public Button Start =button[7];
        public Button LeftStick =button[8];
        public Button RightStick =button[9];
    	
    }
    class Button{
        public boolean current=false , last=false,changedDown=false,changedUp=false;
        private void set(boolean c){
        	last=current;
        	current=c;
        	changedDown=!last && current;
        	changedUp=last && !current;
        }
    }
    
    /** 
     * @return the angle in degrees or -1 if not pressed
     */
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
    private void trigger(){
        Triggers.Left = joy.getRawAxis(2);
        Triggers.Right = joy.getRawAxis(3);
        Triggers.combine();
    }
    
    /**
     * Refreshes all button and axis values. 
     * Should be called only once per run
     */
    public void refresh(){
    	for(int i=0;i<10;i++){
    		button[i].set(joy.getRawButton(i));
    	}
        leftStick();
        rightStick();
        trigger();
    }
    
    /**
     * @param i The controller number
     */
    public XboxController(int i) {
        joy=new Joystick(i);
        refresh();
        Buttons=new buttons();
    }
    
    /**
     * 
     * @param type Left or Right rumble
     * @param value A value from 0 to 1 for the intensity
     */
    public void vibrate(RumbleType type,float value){
    	joy.setRumble(type, value);
    }
}

