package org.usfirst.frc.team3571.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

public class ArduinoCom {
	static I2C Wire = new I2C(Port.kOnboard, 4);

	public static void main() throws Exception {
		int n = 0;
		try {
			n = 1;
			if (Global.driver.Buttons.Back.changedDown) {
				String WriteString = "go";
				char[] CharArray = WriteString.toCharArray();
				n = 2;
				byte[] WriteData = new byte[CharArray.length];
				for (int i = 0; i < CharArray.length; i++) {
					WriteData[i] = (byte) CharArray[i];
				}
				n = 3;
				Wire.transaction(WriteData, WriteData.length, null, 0);
				n = 4;
			}

		} catch (Exception e) {
			throw new Exception("ArduinoCom "+n);
		}
	}
}
