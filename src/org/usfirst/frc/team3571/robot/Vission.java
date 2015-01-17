package org.usfirst.frc.team3571.robot;
import edu.wpi.first.wpilibj.image.*;
import edu.wpi.first.wpilibj.vision.AxisCamera;
import edu.wpi.first.wpilibj.vision.AxisCamera.Resolution;

import java.util.*;

import com.ni.vision.NIVision.ParticleFilterCriteria2;

public class Vission {/*
	public class Point{
		public double X,Y;
		public Point(double x,double y){
			X=x;
			Y=y;
		}
	}
	static AxisCamera cam;
	static ParticleFilterCriteria2[] criteria={
		{IMAQ_MT_AVERAGE_VERT_SEGMENT_LENGTH,4,5,false,false}
	}
	public static void begin(){
		cam=new AxisCamera("10.35.71.11");
		
	}
	public static List<Point> Isee(){
		List<Point> points=new ArrayList<Point>();
		try {
			HSLImage image = cam.getImage();
			BinaryImage bImage = image.thresholdRGB(0, 255, 227, 255, 110, 225);
			bImage.particleFilter(criteria);
			bImage=bImage.convexHull(false);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return points;
	}*/
}
