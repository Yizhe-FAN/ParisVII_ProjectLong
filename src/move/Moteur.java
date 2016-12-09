package move;

import lejos.nxt.Motor;

public class Moteur {
	
	public void mForward(float speed){
		Motor.A.setSpeed(speed);
		Motor.B.setSpeed(speed);
		//Motor.C.setSpeed(smallSpeed);
		Motor.A.forward();
		Motor.B.forward();
		//Motor.C.forward();
	}
		
	public void rotateLeft(){
		Motor.A.setSpeed(getSpeed() + 50);
	}
	
	public void rotateRight(){
		Motor.B.setSpeed(getSpeed() + 50);
	}
	
	public void stop(){
		Motor.A.stop();
		Motor.B.stop();
		//Motor.C.stop();
	}
	
	public float getSpeed(){
		int a = Motor.A.getSpeed();
		int b = Motor.B.getSpeed();
		if(a == b){
			return Motor.A.getSpeed();
		}
		else if (a < b){
			return Motor.B.getSpeed();
		}
		else{
			return Motor.A.getSpeed();
		}
		
	}

}
