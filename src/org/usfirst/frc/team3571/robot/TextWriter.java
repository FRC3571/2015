package org.usfirst.frc.team3571.robot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TextWriter {
	private File textFile;
	private FileWriter fw;
	private BufferedWriter bw;
	/**
	 * Creates a TextWriter
	 * @param location of the text file
	 * @param overwrite overwrites text file if true else adds a number
	 * @throws IOException
	 */
	public TextWriter(String location, boolean overwrite) throws IOException{
		textFile = new File(location);
		if(textFile.exists() && !overwrite){
			for(int i=0;i<20;i++){ 
				if(!(new File(location + i)).exists()){
					textFile=new File(location+i);
					break;
				}
			}
		}
		if(!textFile.exists()){
			textFile.createNewFile();
		}
		fw=new FileWriter(textFile);
		bw=new BufferedWriter(fw);
	}
	/**
	 * Writes to the Text File
	 * @param text
	 * @throws IOException
	 */
	public void write(String text) throws IOException{
		bw.write(text);
	}
	/**
	 * closes the BufferedWriter and FileWriter
	 * @throws IOException
	 */
	public void close() throws IOException{
		bw.close();
		fw.close();
	}
}
