package indi.fan_chen.pl.controller;

import lejos.nxt.*;

public class Cruiser extends Thread{
	
	private ControlColorSensor sensor1;
	
	double kp = 1.2;
	double ki = 0.0008;
	double kd = 5;
	int median = 5;
	int value = 0;
	int error = 0;
	int integral = 0;
	int derivative = 0;
	int lastError = 0;
	double correction = 0;
	
	double aPower;
	double bPower;
	
	int color1 = 0;
	int color2 = 0;
	
	NXTMotor ma = new NXTMotor(MotorPort.A);
	NXTMotor mb = new NXTMotor(MotorPort.B);

	public Cruiser(ControlColorSensor sensor){
		sensor1 = sensor;
	}
	
	public void run(){
		while(!Button.ESCAPE.isDown()){
			value = getValue();
			error = value - median;
			integral += error;
			derivative = error - lastError;
			
			correction = kp*error + ki*integral + kd*derivative;
			
			LCD.clear(2);
			LCD.drawString("Error " + correction, 0, 2);
			LCD.clear(3);
			LCD.drawString("Correction " + correction, 0, 3);
			
			if(correction > 50){
				correction = 50;
			}else if(correction < -50){
				correction = -50;
			}
			
			aPower = 50 - correction;
			bPower = 50 + correction;
			
			if(error< -45){
				ma.setPower(40);
				ma.forward();
				mb.setPower(40);
				mb.forward();
			}else{
				ma.setPower(new Double(aPower).intValue());
				ma.forward();
				mb.setPower(new Double(bPower).intValue());
				mb.forward();
			}
			
					
			lastError = error;
		}
	}
	
	private int getValue(){
		int res;
		color1 = 0;
		color2 = 0;
		for(int i = 0; i < 10; ++i){
			res = sensor1.colorChecker();
			if(res == 1){
				color1++;
			}else if(res == 2){
				color2++;
			}
		}
		return (color1/(color1+color2))*10;//return color of background
	}

}

