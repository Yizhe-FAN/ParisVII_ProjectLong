package move;

import lejos.nxt.Button;
import lejos.nxt.LCD;

public class FollowLine {

	private ControlColorSensor mControlColorSensor;
	
	FollowLine(ControlColorSensor m){
		this.mControlColorSensor = m;
	}
	
	public void loadFile(){
		LCD.clear();
		LCD.drawString("---Load File---", 0, 0);
		FileHandler f = new FileHandler();
		f.readInList("ListBackUp.txt",mControlColorSensor);
		Button.waitForAnyPress();
		
		//press Enter to next
		mControlColorSensor.testColorChecker();
	}
	
	public void runFollowLIne(){
		int lastTime = -1;
		Moteur mMoteur = new Moteur();
		int res;
		while(!Button.ESCAPE.isDown()){
			LCD.clear();
			res = mControlColorSensor.colorChecker();
			LCD.clear(3);
			LCD.drawString("res: "+res, 0, 3);
			LCD.refresh();
			try {
                Thread.sleep(2);
            } catch (InterruptedException ie){
            	ie.printStackTrace();
            }
			if(mControlColorSensor.colorChecker() == 0){
				mMoteur.normalMove();
				if(lastTime == 1){//right
					mMoteur.rotateLeft();
					lastTime = 0;//light
				}
				else
				{
					mMoteur.rotateRight();
					lastTime = 1;
				}
			}
			else if(mControlColorSensor.colorChecker()!= 0){
				mMoteur.normalMove();
				if(lastTime == 0){
					mMoteur.rotateRight();
					lastTime = 1;
				}
				else{
					mMoteur.rotateLeft();
					lastTime = 0;
				}
			}
			
		}			

	}
	
}
