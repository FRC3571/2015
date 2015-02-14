package org.usfirst.frc.team3571.robot;

import edu.wpi.first.wpilibj.DigitalInput;

/**
 *
 * @author NikB
 */
public class Switch{
    public boolean Current = false;
    public boolean Last = false;
    public boolean Pressed = false;
    public boolean Released = false;
    public DigitalInput SwitchInput;
    public void refresh(){
    	Last = Current;
    	Current = SwitchInput.get();
    	Pressed = (Current && !Last);
    	Released = (Last && !Current);
    }
	public Switch(int input) {
    	SwitchInput = new DigitalInput(input);
    	refresh();
	}
}

