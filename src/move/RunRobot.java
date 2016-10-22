package move;

import lejos.nxt.Button;
import lejos.nxt.ColorSensor;

public class RunRobot {
	
	public static void main(String args[]){
		ControlColorSensor test = new ControlColorSensor();
		Moteur mTest = new Moteur();
		ColorSensor cs = test.setColorSenor();
		int setcolor = -1;
		
		while(!Button.ESCAPE.isDown()){
			
			if(Button.ENTER.isDown()){
				setcolor = test.returnColorId(cs);
			}
			int id = test.returnColorId(cs);
			if(id == setcolor){
				mTest.smallmove();
			}else{
				mTest.stop();
			}
		}
	}

}
