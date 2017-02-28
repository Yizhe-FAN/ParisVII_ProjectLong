package indi.fan_chen.pl.controller;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;

public class FollowLine {
	
	static final int WHITE = 1;
	static final int LEFT = 0;
	static final int RIGHT = 1;
	static int mline = 1;
	static int addTime = 0;
	
	public void runFollowLineMode2(ControlColorSensor sensor1, ControlColorSensor sensor2){
		LCD.clear();
		LCD.drawString("---Follow Line---", 0, 0);
		int res1, res2;
		int speed = 300;
		int turn = 0;
		Moteur mMoteur = new Moteur();
		mMoteur.mForward(speed);
		
		while(!Button.ESCAPE.isDown()){
			res1 = sensor1.colorChecker();
			res2 = sensor2.colorChecker();
			printColors(res1, res2);
			
			while((!Button.ESCAPE.isDown()) && (res1 != WHITE) && (res2 == WHITE)){
				//turn right
				turn++;
				Motor.A.setSpeed(20);
				Motor.B.setSpeed(20 + turn*turn);
			
				res1 = sensor1.colorChecker();
				res2 = sensor2.colorChecker();
				printColors(res1, res2);
			}
			turn = 0;
			mMoteur.mForward(speed);
			
			while((!Button.ESCAPE.isDown()) && (res1 == WHITE) && (res2 != WHITE)){
				//turn left
				turn++;
				Motor.A.setSpeed(20 + turn*turn);
				Motor.B.setSpeed(20);
		
				res1 = sensor1.colorChecker();
				res2 = sensor2.colorChecker();
				printColors(res1, res2);
			}
			turn = 0;
			mMoteur.mForward(speed);
			
			while((!Button.ESCAPE.isDown()) && (res1 != WHITE) && (res2 != WHITE)){
				turn++;
				Motor.A.setSpeed(20 + turn*turn);
				Motor.B.setSpeed(20);
				res1 = sensor1.colorChecker();
				res2 = sensor2.colorChecker();
				printColors(res1, res2);
			}
			turn = 0;
			mMoteur.mForward(speed);
			
			if(speed < 600){
				speed += 50;
			}	
		}
	
	}
	
	public void printColors(int res1, int res2){
		LCD.clear(3);
		LCD.clear(4);
		LCD.drawString("Sensor1 "+res1+" Detected", 0, 3);
		LCD.drawString("Sensor2 "+res2+" Detected", 0, 4);
		LCD.refresh();
	}
	
	
	public void runFollowLineMode1(ControlColorSensor controlColorSensor){
		LCD.clear();
		LCD.drawString("---Follow Line---", 0, 0);
		
		int lastTime = LEFT;
		Moteur mMoteur = new Moteur();
		int res;
		mMoteur.mForward(100);
		
		while(!Button.ESCAPE.isDown()){
			res = controlColorSensor.colorChecker();
			LCD.clear(3);
			LCD.drawString("Color "+res+" Detected", 0, 3);
			LCD.refresh();
			
			while((!Button.ESCAPE.isDown())&&(res == WHITE)){
				if(lastTime == LEFT){
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
			
			while((!Button.ESCAPE.isDown())&&(res != WHITE)){
				if(lastTime == LEFT){
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
	
	/*public void runFollowLineMode3(ControlColorSensor sensor1, ControlColorSensor sensor2){
		LCD.clear();
		LCD.drawString("---Follow Line---", 0, 0);
		int res1, res2, times = 1;
		int mTimerOK = 1, mWaitOK = 1;
		int speed = 400;
		
		
		Wait mWait = new Wait(100);
		Timer mTimer = new Timer(50);
		
		Moteur mMoteur = new Moteur();
		mMoteur.mForward(speed);
		
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
				if(mTimerOK == 1){
					new Thread(mTimer).start();
					mTimerOK = 0;
				}	
				if(mline == 1){
					Motor.A.setSpeed(speed - 100);
					Motor.B.setSpeed(speed);
				}else{
					speed = 400;
					Motor.A.setSpeed(speed - 100*times);
					Motor.B.setSpeed(speed);
					if(mWaitOK == 1)
						new Thread(mWait).start();
					if(addTime == 1){
						if((speed-100*times) > 0)
							times++;
						addTime = 0;
						mWaitOK = 1;
					}
				}
				res1 = sensor1.colorChecker();
				res2 = sensor2.colorChecker();
			}
			mMoteur.mForward(speed);
			times = 1;
			mline = 1;
			mTimerOK = 1;
			mTimer.stopRequest();
			
			while((!Button.ESCAPE.isDown()) && (res1 == 1) && (res2 != 1)){
				//turn left
				if(mTimerOK == 1){
					new Thread(mTimer).start();
					mTimerOK = 0;
				}	
				if(mline == 1){
					Motor.A.setSpeed(speed);
					Motor.B.setSpeed(speed - 100);
				}else{
					speed = 400;
					Motor.A.setSpeed(speed);
					Motor.B.setSpeed(speed - 100*times);
					if(mWaitOK == 1)
						new Thread(mWait).start();
					if(addTime == 1){
						if((speed-100*times) > 0)
							times++;
						addTime = 0;
						mWaitOK = 1;
					}
				}
				res1 = sensor1.colorChecker();
				res2 = sensor2.colorChecker();
			}
					
			mMoteur.mForward(speed);
			times = 1;
			mline = 1;
			mTimerOK = 1;
			mTimer.stopRequest();
			
			if(speed < 600){
				speed+=50;
			}
		}
	
	}*/
	
}
