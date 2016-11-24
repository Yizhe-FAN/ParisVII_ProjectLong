package move;

import java.io.File;

import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;

public class RunRobot {
	
	public static void main(String args[]){
		File file =new File("ListBackUp.txt");
		if(!file.exists()){
			LCD.drawString("Initialise File!", 0, 6);
			ControlColorSensor mColorSensor = new ControlColorSensor();
			ColorSensor cs = mColorSensor.setColorSenor();
			FileHandler f = new FileHandler();
			mColorSensor.colorReader(cs);
			mColorSensor.colorChecker(cs);
			f.writeInFile();
		}else{
			LCD.drawString("Load and use File!", 0, 6);
			FileHandler f = new FileHandler();
			f.readInList("ListBackUp.txt");
			ControlColorSensor mColorSensor = new ControlColorSensor();
			ColorSensor cs = mColorSensor.setColorSenor();
			mColorSensor.colorReader(cs);
			mColorSensor.colorChecker(cs);
			f.writeInFile();
			
		}
		
	}

}
