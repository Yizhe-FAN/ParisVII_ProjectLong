package move;

import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;

public class RunRobot {
	
	public static void main(String args[]){
		ControlColorSensor mColorSensor = new ControlColorSensor();
		ColorSensor cs = mColorSensor.setColorSenor();
		mColorSensor.colorReader(cs);
		mColorSensor.colorChecker(cs);
	}

}
