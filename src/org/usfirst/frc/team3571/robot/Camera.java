package org.usfirst.frc.team3571.robot;


/**
 * 
 * @author NikB
 */
public class Camera extends Thread {
	private boolean isRunning = false;
	private boolean teleOp = false;
	
	public void teleOp(){
		teleOp = true;
	}
	
	public void stopThread(){
		isRunning = false;
	}
   
	public Camera(){
		this.start();
	}
	
	public void run(){
		teleOp = true;
		isRunning = true;
		while(isRunning){
			
		}
	}
	
}

