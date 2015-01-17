package org.usfirst.frc.team3571.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Joystick.RumbleType;

/**
 *
 * @author TomasR
 */
public class XboxController {
    private final boolean[] previousButton=new boolean[10],currentButton=new boolean[10];
    private final Joystick joy;
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
    public Axis leftStick(){
        return new Axis(joy.getRawAxis(0),joy.getRawAxis(1));
    }
    public Axis rightStick(){
        return new Axis(joy.getRawAxis(4),joy.getRawAxis(5));
    }
    public double triggerLeft(){
        return joy.getRawAxis(2);
    }
    public double triggerRight(){
        return joy.getRawAxis(3);
    }
    public void refresh(){
        for (int j = 0; j < 10; j++) {
            previousButton[j]=currentButton[j];
            currentButton[j]=joy.getRawButton(j+1);
        }
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

