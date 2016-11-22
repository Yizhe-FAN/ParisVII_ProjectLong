package move;

import lejos.nxt.ColorSensor;

public class RunRobot {
	
	public static void main(String args[]){
		ControlColorSensor mColorSensor = new ControlColorSensor();
		ColorSensor cs = mColorSensor.setColorSenor();
		mColorSensor.colorReader(cs);
		mColorSensor.colorChecker(cs);
	}

}
