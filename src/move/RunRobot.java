package move;

import lejos.nxt.Button;
import lejos.nxt.ColorSensor;

public class RunRobot {
	
	public static void main(String args[]){
		ControlColorSensor test = new ControlColorSensor();
		Moteur mTest = new Moteur();
		ColorSensor cs = test.setColorSenor();
		
		while(!Button.ESCAPE.isDown()){
			int id = test.returnColorId(cs);
			if(id == 7){
				mTest.smallmove();
			}else{
				mTest.stop();
			}//
		}
	}

}
