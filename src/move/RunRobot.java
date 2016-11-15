package move;

import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;

public class RunRobot {
	
	public static void main(String args[]){
		ControlColorSensor mColorSensor = new ControlColorSensor();
		Moteur mMoteur = new Moteur();
		ColorSensor cs = mColorSensor.setColorSenor();
		int setcolor = -1;
		
		while(!Button.ESCAPE.isDown()){
			if(Button.LEFT.isDown()){
				String color = mColorSensor.returnColor(cs);
				setcolor = mColorSensor.returnColorId(cs);
				LCD.drawString("Set color: "+color, 0, 1);
			}
			int idCurrent = mColorSensor.returnColorId(cs);
			if(idCurrent == setcolor){
				mMoteur.smallmove();
			}else{
				mMoteur.stop();
			}
		}
		
		//mColorSensor.colorSensorReader();
		
		//mColorSensor.testFlootLight(cs);
	}

}
