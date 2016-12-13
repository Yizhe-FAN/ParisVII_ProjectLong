package move;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;

public class FollowLine {
	
	public FollowLine(){
		
	}
	
	public void runFollowLIne(ControlColorSensor controlColorSensor){
		LCD.clear();
		LCD.drawString("---Follow Line---", 0, 0);
		
		int lastTime = 0; // left 0, right 1
		Moteur mMoteur = new Moteur();
		int res;
		mMoteur.mForward(100);
		
		while(!Button.ESCAPE.isDown()){
			res = controlColorSensor.colorChecker();
			LCD.clear(3);
			LCD.drawString("Color "+res+" Detected", 0, 3);
			LCD.refresh();
			
			//color 1 is white normally
			
			while((!Button.ESCAPE.isDown())&&(res == 1)){
				if(lastTime == 0){
					//turn right
					
					Motor.A.setSpeed(60);
					Motor.B.setSpeed(260);
				}
				else{
					//turn left
					
					Motor.A.setSpeed(260);
					Motor.B.setSpeed(60);
				}
				res = controlColorSensor.colorChecker();
			}
			
			while((!Button.ESCAPE.isDown())&&(res != 1)){
				if(lastTime == 0){
					Motor.A.setSpeed(60);
					Motor.B.setSpeed(260);
				}
				else{
					Motor.A.setSpeed(260);
					Motor.B.setSpeed(60);
				}
				res = controlColorSensor.colorChecker();
			}
					
			lastTime = Math.abs(lastTime-1);
			
		}			

	}
	
}
