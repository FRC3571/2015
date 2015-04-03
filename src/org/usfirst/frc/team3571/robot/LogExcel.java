/*package org.usfirst.frc.team3571.robot;
import java.io.File;
import java.io.IOException;

import jxl.*;
import jxl.read.biff.BiffException;
import jxl.write.*;
import jxl.write.Number;
import jxl.write.biff.RowsExceededException;
public class LogExcel {
	private WritableWorkbook wwb;
	private WritableSheet ws;
	public int Lrow=0;
	private int my=0;
	public LogExcel() throws BiffException, IOException{
		File fil=new File("/c/log.xls");
		if(fil.exists()){
			wwb=Workbook.createWorkbook(fil, Workbook.getWorkbook(fil));
			ws = wwb.getSheet(0);
			Lrow=(int)((Number)ws.getCell(0, 0)).getValue()+1;
		}
		else{
			fil.createNewFile();
			wwb=Workbook.createWorkbook(fil);
			ws=wwb.createSheet("Log", 0);
		}
		
	}
	public void writeNumber(int X,int Y, double val) throws RowsExceededException, WriteException{
		ws.addCell(new Number(X,Y,val));
		if(my<Y)my=Y;
	}
	public void writeText(int X,int Y,String val) throws RowsExceededException, WriteException{
		ws.addCell(new Label(X,Y,val));
		if(my<Y)my=Y;
	}
	public void close() throws WriteException, IOException{
		if(ws.getCell(0,0).getType() ==CellType.EMPTY)ws.addCell(new Number(0,0,my));
		else ((Number)(ws.getCell(0,0))).setValue(my);
		wwb.write();
		wwb.close();
	}

}*/
