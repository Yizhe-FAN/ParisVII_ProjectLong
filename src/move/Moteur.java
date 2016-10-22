package move;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;

public class Moteur {

	public void testMoteur() {
		// TODO Auto-generated method stub
		Motor.A.forward();
		LCD.drawString("forward", 0, 1);
		Button.waitForAnyPress();
		Motor.A.backward();
		LCD.drawString("backward", 0, 2);
		Button.waitForAnyPress();
		Motor.A.stop();
		Button.waitForAnyPress();
		
		Motor.A.resetTachoCount();
		int angle = Motor.A.getTachoCount();
		LCD.drawInt(angle, 0, 3);
		LCD.drawInt(Motor.A.getSpeed(), 4, 3);
		Button.waitForAnyPress();
		
		Motor.A.setSpeed(900);
		LCD.drawInt(Motor.A.getSpeed(), 8, 3);
		Button.waitForAnyPress();
		

		Motor.A.forward();		
		while(angle < 1080) 
		{
			angle=Motor.A.getTachoCount();
		}
		LCD.drawInt(angle, 0, 4);
		
		Motor.A.resetTachoCount();
		Motor.A.flt();
		//Motor.A.stop();
		
		Button.waitForAnyPress();
		
		angle = Motor.A.getTachoCount();
		LCD.drawInt(angle, 0, 5);
		Button.waitForAnyPress();
		
	}	
	
	public void smallmove(){
		int smallSpeed = 180;
		Motor.A.setSpeed(smallSpeed);
		Motor.B.setSpeed(smallSpeed);
		Motor.C.setSpeed(smallSpeed);
		Motor.A.forward();
		Motor.B.forward();
		Motor.C.forward();
	}
	
	public void stop(){
		Motor.A.stop();
		Motor.B.stop();
		Motor.C.stop();
	}
	
	/*public static void main(String args[]){
		Moteur move = new Moteur();
		move.smallmove();
		Button.waitForAnyPress();
	}*/

}
