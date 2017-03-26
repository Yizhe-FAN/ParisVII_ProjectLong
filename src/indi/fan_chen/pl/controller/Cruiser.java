package indi.fan_chen.pl.controller;

import indi.fan_chen.pl.model.ColorType;
import indi.fan_chen.pl.model.RgbState;
import indi.fan_chen.pl.model.Settings;
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
	
	double leftPower;
	double rightPower;
	
	RgbState background;
	RgbState line;
	
	NXTMotor ma = new NXTMotor(MotorPort.A);
	NXTMotor mb = new NXTMotor(MotorPort.B);

	public Cruiser(ControlColorSensor sensor){
		sensor1 = sensor;
	}
	
	public void run(){
		/*
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
			
			leftPower = 50 - correction;
			rightPower = 50 + correction;
			
			if(error< -45){
				ma.setPower(40);
				ma.forward();
				mb.setPower(40);
				mb.forward();
			}else{
				ma.setPower(new Double(leftPower).intValue());
				ma.forward();
				mb.setPower(new Double(rightPower).intValue());
				mb.forward();
			}
			
					
			lastError = error;
		}*/
	}
	
	
	private int getValue(){
		return 0;
	}

}

