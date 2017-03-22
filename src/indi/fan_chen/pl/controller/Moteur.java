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
	
	public void right(int turn){
		
		int speedInit = Motor.A.getSpeed();
		
		if(turn <= 50 ){
			Motor.A.setSpeed(speedInit - turn);
		}
		else{
			Motor.A.setSpeed(speedInit- turn*turn);
		}
		
		speedInit = Motor.B.getSpeed();
		Motor.B.setSpeed(speedInit+turn);
	}
	
	public void left(int turn){
		int speedInit = Motor.A.getSpeed();
		Motor.A.setSpeed(speedInit+turn);
		
		speedInit = Motor.B.getSpeed();
		
		if(turn <= 50){
			Motor.B.setSpeed(speedInit - turn);
		}
		else{
			Motor.B.setSpeed(speedInit - turn*turn);
		}
		
	}
	

}
