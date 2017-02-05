package move;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.util.TextMenu;

public class FollowLine {
	
	String[] items = {"One Sensor","Two Sensor"};
	TextMenu mainMenu;
	int selectNumber;
	static int mline = 1;
	int addTime = 0;
	
	public FollowLine(){
		LCD.clear();
		mainMenu = new TextMenu(items, 1, "---My Robot---");
	}
	
	public void runFollowLIne(ControlColorSensor controlColorSensor){
		selectNumber = mainMenu.select();
		switch (selectNumber){
			case 0:
				runFollowLineMode1(controlColorSensor);
				break;
			case 1:
				ControlColorSensor mSensor = new ControlColorSensor(2);
				FileHandler colorsFileHandler = new FileHandler("ColorsBackUp.txt");
				colorsFileHandler.readInList(mSensor);
				runFollowLineMode2(controlColorSensor, mSensor);
				break;
			default: 
				LCD.clear();
				LCD.drawString("not Match", 0, 0);
				break;
		
		}	
			
	}
	
	
	public void runFollowLineMode1(ControlColorSensor controlColorSensor){
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
	
	public void runFollowLineMode2(ControlColorSensor sensor1, ControlColorSensor sensor2){
		LCD.clear();
		LCD.drawString("---Follow Line---", 0, 0);
		int res1, res2, times = 1;
		
		Wait mwait = new Wait(50);
		Timer mtimer = new Timer(200);
		
		Moteur mMoteur = new Moteur();
		mMoteur.mForward(400);
		
		while(!Button.ESCAPE.isDown()){
			res1 = sensor1.colorChecker();
			res2 = sensor2.colorChecker();
			LCD.clear(3);
			LCD.clear(4);
			LCD.drawString("Sensor1 "+res1+" Detected", 0, 3);
			LCD.drawString("Sensor2 "+res2+" Detected", 0, 4);
			LCD.refresh();
			
			while((!Button.ESCAPE.isDown()) && (res1 != 1) && (res2 == 1)){
				//turn right
				if(mtimer.ok == 1)
					mtimer.start();
				if(mline == 1){
					Motor.A.setSpeed(400 - 100);
					Motor.B.setSpeed(400);
				}/*else{
					Motor.A.setSpeed(400 - 100*times);
					Motor.B.setSpeed(400);
					if(mwait.ok == 1)
						mwait.start();
					if(addTime == 1){
						times++;
						addTime = 0;
						mwait.ok = 1;
					}
				}*/
				res1 = sensor1.colorChecker();
				res2 = sensor2.colorChecker();
			}
			mMoteur.mForward(400);
			times = 1;
			mline = 1;
			mtimer.ok = 1;
			
			while((!Button.ESCAPE.isDown()) && (res1 == 1) && (res2 != 1)){
				//turn left
				if(mtimer.ok == 1)
					mtimer.start();
				if(mline == 1){
					Motor.A.setSpeed(400);
					Motor.B.setSpeed(400 - 100);
				}/*else{
					Motor.A.setSpeed(400);
					Motor.B.setSpeed(400 - 100*times);
					if(mwait.ok == 1)
						mwait.start();
					if(addTime == 1){
						times++;
						addTime = 0;
						mwait.ok = 1;
					}
				}*/
				res1 = sensor1.colorChecker();
				res2 = sensor2.colorChecker();
			}
			mMoteur.mForward(400);
			times = 1;
			mline = 1;
			mtimer.ok = 1;
			
		}
	
	}	
	
	class Wait extends Thread{
		private int msec;
		public int ok = 1;
		Wait(int sc){
			msec = sc;
		}
		public void run(){
			ok = 0;
			try {
	            Thread.sleep(msec);
	        } catch (InterruptedException ie){
	        	ie.printStackTrace();
	        }
			addTime = 1;
		}
	}
	
	
}
