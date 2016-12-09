package move;

import lejos.nxt.Button;
import lejos.nxt.LCD;

public class FollowLine {
	
	public void runFollowLIne(ControlColorSensor mControlColorSensor){
		LCD.clear();
		LCD.drawString("---Follow Line---", 0, 0);
		
		int lastTime = -1; // left 0, right 1
		Moteur mMoteur = new Moteur();
		int res;
		while(!Button.ESCAPE.isDown()){
			res = mControlColorSensor.colorChecker();
			LCD.clear(3);
			LCD.drawString("color is : "+res, 0, 3);
			LCD.refresh();
			try {
                Thread.sleep(2);
            } catch (InterruptedException ie){
            	ie.printStackTrace();
            }
			//color 1 is white normally
			if(res == 1){
				if(lastTime == 1){
					mMoteur.rotateLeft();
					lastTime = 0;
				}
				else
				{
					mMoteur.rotateRight();
					lastTime = 1;
				}
			}
			else{
				mMoteur.mForward(100);
				/*
				if(lastTime == 0){
					mMoteur.rotateRight();
					lastTime = 1;
				}
				else{
					mMoteur.rotateLeft();
					lastTime = 0;
				}
				*/
			}
			
		}			

	}
	
}
