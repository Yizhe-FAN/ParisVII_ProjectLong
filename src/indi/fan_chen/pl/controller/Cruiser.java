package indi.fan_chen.pl.controller;

import indi.fan_chen.pl.model.ColorType;
import indi.fan_chen.pl.model.RgbState;
import indi.fan_chen.pl.model.Settings;
import lejos.nxt.*;

public class Cruiser extends Thread{
	
	private ControlColorSensor sensor1;
	private ColorSensor colorSensor;
	int rInter,gInter,bInter;
	
	double kp = 1.2;
	double ki = 0.0008;
	double kd = 5;
	int error = 0;
	int integral = 0;
	int derivative = 0;
	int lastError = 0;
	double correction = 0;
	
	double leftPower;
	double rightPower;
	
	RgbState background;
	RgbState line;
	RgbState median;
	
	NXTMotor ma = new NXTMotor(MotorPort.A);
	NXTMotor mb = new NXTMotor(MotorPort.B);

	public Cruiser(ControlColorSensor sensor){
		sensor1 = sensor;
		colorSensor = new ColorSensor(SensorPort.getInstance(0));
		background = new RgbState(0);
		line = new RgbState(0);
		median = new RgbState(0);
		getRgbAvg(0, background);
		getRgbAvg(1, line);
		getRgbMedian(background, line, median);
	}
	
	public void run(){
		while(!Button.ESCAPE.isDown()){
			ColorSensor.Color vals = colorSensor.getColor();
			error = getError(median, vals.getRed(), vals.getGreen(), vals.getBlue());
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
		}
	}
	
	private void getRgbAvg(int index, RgbState state){
		ColorType tempCT = sensor1.colorTypeList.get(index);
		state.r = tempCT.rgbAvg.r;
		state.g = tempCT.rgbAvg.g;
		state.b = tempCT.rgbAvg.b;
	}
	
	void getRgbMedian(RgbState back, RgbState line, RgbState mid){
		rInter = back.r-line.r;
		gInter = back.g-line.g;
		bInter = back.b-line.b;
		mid.r = line.r + rInter/2;
		mid.g = line.g + gInter/2;
		mid.b = line.b + bInter/2;
	}
	
	private int getError(RgbState mid, int r, int g, int b){
		int rDis,gDis,bDis;
		rDis = ((r - mid.r)/rInter)*100;
		gDis = ((g - mid.g)/gInter)*100;
		bDis = ((b - mid.b)/bInter)*100;
		return (rDis+gDis+bDis)/3;
	}

}

