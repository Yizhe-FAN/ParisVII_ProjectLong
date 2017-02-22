package indi.fan_chen.pl.controller;

import lejos.nxt.Motor;

public class Moteur {
	
	public void mForward(float speed){
		Motor.A.setSpeed(speed);
		Motor.B.setSpeed(speed);
		
		Motor.A.forward();
		Motor.B.forward();
		
	}
	
	public void stop(){
		Motor.A.stop();
		Motor.B.stop();
	}
	
	public float getSpeedA(){
		return Motor.A.getSpeed();
	}
	
	public float getSpeedB(){
		return Motor.B.getSpeed();
	}
	

}
