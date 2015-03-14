package org.usfirst.frc.team3571.robot;

import java.util.ArrayList;
import java.util.List;
import java.math.BigInteger;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

public class ArduinoCom {
	static I2C Wire = new I2C(Port.kOnboard, 4);
	private static LED pastLEDs[]=new LED[300];
	public class  LED {
		public byte R=0,G=0,B=0, movement=0;
		public int timein=500,timeout=500, location=0, min=0, max=29;
		public double mspeed=10;
		public boolean bounce=false,changingLight=false,fadeIn=false,fadeOut=false;
		public LED(byte Red, byte Green, byte Blue, byte Location){
			location=Location;
			min=location;
			max =location;
			R=Red;
			G=Green;
			B=Blue;
		}
		public boolean compare(LED leds){
			boolean tot= this.R==leds.R && this.G==leds.G && this.B==leds.B
					&& this.movement==leds.movement && this.min==leds.min && this.max==leds.max
					&& this.timein==leds.timein && this.timeout==leds.timeout && this.mspeed==leds.mspeed
					&& this.bounce==leds.bounce && this.changingLight==leds.changingLight && this.fadeIn==leds.fadeIn
					&& this.fadeOut==leds.fadeOut;
			return tot;
		}
	}
	public static void send(LED led[]) throws Exception {
		int n = 0;
		try {
			List<Integer> tosend=new ArrayList<Integer>();
			for(int i=0;i<led.length;i++){
				if(!pastLEDs[led[i].location].compare(led[i])) tosend.add(i);
			}
			n = 1;
			for(int i=0;i<tosend.size();i++){
				LED now=led[tosend.get(i)];
				int move=0;
				byte[] timeIn=BigInteger.valueOf(now.timein).toByteArray(), timeOut=BigInteger.valueOf(now.timeout).toByteArray(),mspeed=BigInteger.valueOf((int)now.mspeed*1000).toByteArray();
				byte[] bytesToSend=new byte[10]; //Initialize later
				bytesToSend[0]=(byte)(now.location>255?'l':'L');
				bytesToSend[1]=(byte)(now.location%256);
				bytesToSend[2]=now.R;
				bytesToSend[3]=now.G;
				bytesToSend[4]=now.B;
				bytesToSend[5]=(byte)((now.movement << 4) + ((now.bounce ? 1 : 0) << 3) + ((now.changingLight ? 1 : 0) << 2) + ((now.fadeOut ? 1 : 0) << 1) + (now.fadeIn ? 1 : 0));
				bytesToSend[6]=(byte)((timeIn.length << 6)+(timeOut.length<<4)+(now.max>255?8:0)+(now.min>255?4:0)+(mspeed.length));
				for(int ii=0;ii<timeIn.length;ii++)bytesToSend[7+ii]=timeIn[ii];
				move=timeIn.length-1;
				for(int ii=0;ii<timeOut.length;ii++)bytesToSend[8+move+ii]=timeOut[ii];
				move+=timeOut.length-1;
				bytesToSend[9+move]=(byte)(now.max%256);
				bytesToSend[10+move]=(byte)(now.min%256);
				for(int ii=0;ii<mspeed.length;ii++)bytesToSend[11+move+ii]=mspeed[ii];
				Wire.transaction(bytesToSend, bytesToSend.length, null, 0);
				
				
			}

		} catch (Exception e) {
			throw new Exception("ArduinoCom "+n);
		}
	}
	public static byte[] base10tobyte(int base10){
		if(base10<=0) return new byte[]{0};
		int length=(int)Math.floor(Math.log10(base10)/Math.log10(255));
		byte fin[]=new byte[length+1];
		for(int i=length;i>0;i--){
			fin[i]=(byte)Math.floor(base10/Math.pow(255, i));
			base10%=Math.pow(255, i);
		}
		return fin;
	}
}
