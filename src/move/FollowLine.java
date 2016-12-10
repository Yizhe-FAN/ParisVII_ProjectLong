package move;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;

public class FollowLine {
	private final int STANDARD = 120;
	private int sleep;
	
	public FollowLine(){
		sleep = STANDARD;
	}
	
	public void runFollowLIne(ControlColorSensor controlColorSensor){
		LCD.clear();
		LCD.drawString("---Follow Line---", 0, 0);
		
		int lastTime = 0; // left 0, right 1
		Moteur mMoteur = new Moteur();
		int res;
		int times = 0;
		//boolean deuxime;
		mMoteur.mForward(50);
		
		while(!Button.ESCAPE.isDown()){
			res = controlColorSensor.colorChecker();
			LCD.clear(3);
			LCD.drawString("Color "+res+" Detected", 0, 3);
			LCD.refresh();
			
			//color 1 is white normally
			
				while(res == 1){
					times++;
					//mMoteur.mForward(50);
					if(lastTime == 1){	
						Motor.B.setSpeed(10);
						Motor.A.setSpeed(180);
						//mMoteur.rotateLeft(50);
						
						if(times > 300){
							lastTime = 0;
							times = 0;
						}
					}
					else
					{
						Motor.B.setSpeed(180);
						Motor.A.setSpeed(10);
						//mMoteur.rotateRight(200);
						if(times > 300){
							lastTime = 1;
							times = 0;
						}
					}
					//sleep = 800;
					res = controlColorSensor.colorChecker();
				}
				
				times=0;
				
				if(lastTime == 0){
					Motor.A.setSpeed(100);
					Motor.B.setSpeed(180);
					//mMoteur.rotateRight(100);
					lastTime = 1;
				}
				else{
					Motor.A.setSpeed(180);
					Motor.B.setSpeed(100);
					//mMoteur.rotateLeft(100);
					lastTime = 0;
				}
				//sleep = 100;
			
			try {
                Thread.sleep(sleep);
            } catch (InterruptedException ie){
            	ie.printStackTrace();
            }
			
		}			

	}
	
}
