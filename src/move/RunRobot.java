package move;

import java.io.File;

import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.util.TextMenu;

public class RunRobot {
	
	public static void main(String args[]){
		String []modes={"new","old"};
		File file =new File("ListBackUp.txt");
		TextMenu modeMenu = new TextMenu(modes, 1, "File Mode");
		int num = modeMenu.select();
		if(num==0){
			LCD.drawString("Initialise File!", 0, 4);
			file.delete();
			ControlColorSensor mColorSensor = new ControlColorSensor();
			ColorSensor cs = mColorSensor.setColorSenor();
			FileHandler f = new FileHandler();
			mColorSensor.colorReader(cs);
			mColorSensor.colorChecker(cs);
			f.writeInFile();
		}else if(num==1){
			if(!file.exists()){
				LCD.drawString("Initialise File!", 0, 4);
				ControlColorSensor mColorSensor = new ControlColorSensor();
				ColorSensor cs = mColorSensor.setColorSenor();
				FileHandler f = new FileHandler();
				mColorSensor.colorReader(cs);
				mColorSensor.colorChecker(cs);
				f.writeInFile();
			}else{
				LCD.drawString("Load and use File!", 0, 4);
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

}
