package org.usfirst.frc.team3571.robot;

import java.io.IOException;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ExcelLog{
	public static LogExcel log;
	public static PowerDistributionPanel pdp=new PowerDistributionPanel();
	public static int runs=0;
	public static boolean fin=false;
static String label[]={"Volts","TotalAmps"};
    // Called just before this Command runs the first time
    public static void initialize() {
    	try{
        	log=new LogExcel();
	    	if(log.Lrow==0){
	    		for(int i=0;i<2;i++){
	    			log.writeText(i+1, 0, label[i]);
	    		}
	    		for(int i=0;i<16;i++){
	    			log.writeText(i+3, 0, "current"+i);
	    		}
	    		log.writeText(19, 0, "driveX");
	    		log.writeText(20, 0, "driveY");
	    		log.writeText(21, 0, "SideDrive");
	    		log.writeText(22, 0, "toteLift");
	    		log.writeText(23, 0, "binLift");
				SmartDashboard.putNumber("save", 0);
				fin=false;
				runs=0;
	    	}
    	}
    	catch(Exception e){
    		SmartDashboard.putString("error", e.getMessage());	
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    public static void execute() {
    	runs++;
    	try{
        	log.writeNumber(0, runs+log.Lrow, Robot.time);
        	log.writeNumber(1, runs+log.Lrow, pdp.getVoltage());
        	log.writeNumber(2, runs+log.Lrow, pdp.getTotalCurrent());
    		for(int i=0;i<16;i++){
            	log.writeNumber(3+i, runs+log.Lrow, pdp.getCurrent(i));
    		}
        	log.writeNumber(19, runs+log.Lrow, 1);
    		SmartDashboard.putNumber("xL", Robot.x);
        	log.writeNumber(20, runs+log.Lrow, Robot.y);
        	log.writeNumber(21, runs+log.Lrow, Robot.m);
        	log.writeNumber(22, runs+log.Lrow, Robot.tl);
        	log.writeNumber(23, runs+log.Lrow, Robot.bl);
        	log.writeNumber(24, runs+log.Lrow, 12345);
    	}catch(Exception e){
			SmartDashboard.putString("error", e.getMessage());
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    

    // Called once after isFinished returns true
    public static void save() {
    	try {
			SmartDashboard.putNumber("save", 0.5);
			log.close();
			SmartDashboard.putNumber("save", 1);
		} catch (Exception e) {
			SmartDashboard.putString("error", e.getMessage());
		}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    
}
