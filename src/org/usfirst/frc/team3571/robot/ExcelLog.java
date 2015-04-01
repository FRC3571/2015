package org.usfirst.frc.team3571.robot;

import java.io.IOException;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ExcelLog extends Command {
	LogExcel log;
	PowerDistributionPanel pdp=new PowerDistributionPanel();
	int runs=0;
    public ExcelLog() throws BiffException, IOException {
    	log=new LogExcel();
    }
String label[]={"Volts","TotalAmps"};
    // Called just before this Command runs the first time
    protected void initialize() {
    	try{
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
	    	}
    	}
    	catch(Exception e){
    		
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	runs++;
    	try{
        	log.writeNumber(0, runs+log.Lrow, timeSinceInitialized());
        	log.writeNumber(1, runs+log.Lrow, pdp.getVoltage());
        	log.writeNumber(2, runs+log.Lrow, pdp.getTotalCurrent());
    		for(int i=0;i<16;i++){
            	log.writeNumber(3+i, runs+log.Lrow, pdp.getCurrent(i));
    		}
        	log.writeNumber(19, runs+log.Lrow, Global.Motors.x);
        	log.writeNumber(20, runs+log.Lrow, Global.Motors.y);
        	log.writeNumber(21, runs+log.Lrow, Global.Motors.m);
        	log.writeNumber(22, runs+log.Lrow, Global.Motors.tl);
        	log.writeNumber(23, runs+log.Lrow, Global.Motors.bl);
    	}catch(Exception e){
    		
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	try {
			log.close();
		} catch (Exception e) {
		}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	try {
			log.close();
		} catch (Exception e) {
		}
    }
}
