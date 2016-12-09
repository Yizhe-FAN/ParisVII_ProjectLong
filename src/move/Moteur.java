package move;

import lejos.nxt.Motor;

public class Moteur {
	
	public void smallMove(){
		int smallSpeed = 100;
		Motor.A.setSpeed(smallSpeed);
		Motor.B.setSpeed(smallSpeed);
		//Motor.C.setSpeed(smallSpeed);
		Motor.A.forward();
		Motor.B.forward();
		//Motor.C.forward();
	}
		
	public void rotateLeft(){
		int speedNow = Motor.A.getSpeed();
		Motor.A.setSpeed(speedNow+100);
	}
	
	public void rotateRight(){
		int speedNow = Motor.A.getSpeed();
		Motor.B.setSpeed(speedNow+100);
	}
	
	public void fastMove(){
		int fastSpeed = 260;
		Motor.A.setSpeed(fastSpeed);
		Motor.B.setSpeed(fastSpeed);
		//Motor.C.setSpeed(fastSpeed);
		Motor.A.forward();
		Motor.B.forward();
		//Motor.C.forward();
	}
	
	public void normalMove(){
		int normalSpeed = 180;
		Motor.A.setSpeed(normalSpeed);
		Motor.B.setSpeed(normalSpeed);
		//Motor.C.setSpeed(fastSpeed);
		Motor.A.forward();
		Motor.B.forward();
		//Motor.C.forward();
	}
	
	public void stop(){
		Motor.A.stop();
		Motor.B.stop();
		//Motor.C.stop();
	}

}
