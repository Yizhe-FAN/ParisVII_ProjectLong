package indi.fan_chen.pl.controller;

import java.util.ArrayList;

import indi.fan_chen.pl.model.ColorType;
import indi.fan_chen.pl.model.RgbState;
import indi.fan_chen.pl.model.Settings;
import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;

import lejos.nxt.MotorPort;
import lejos.nxt.NXTMotor;

public class FollowLine {

	static final int WHITE = 1;
	static final int STOP = 3;
	static final int LEFT = 0;
	static final int RIGHT = 1;
	static int mline = 1;
	static int addTime = 0;
	private ControlColorSensor sensor1;
	private ControlColorSensor sensor2;
	
	public FollowLine(ControlColorSensor sensor1){
		this.sensor1 = sensor1;
	}

	public FollowLine(ControlColorSensor sensor1, ControlColorSensor sensor2){
		this.sensor1 = sensor1;
		this.sensor2 = sensor2;
	}
	
	public void runFollowLineMode1(){
		LCD.clear();
		LCD.drawString("---Follow Line---", 0, 0);
		
		RgbState background = getRgbState(0);
		RgbState line = getRgbState(1);
		
		NXTMotor ma = new NXTMotor(MotorPort.A);
		NXTMotor mb = new NXTMotor(MotorPort.B);
		
		double error = 0;
		double integral = 0;
		double powerStandard = 70;
		double powerVal = 19;
		
		double corBase = calculCor(background.r, background.g, background.b, line);
		double offSet = (corBase + 1) / 2;
		
		double kp = 1 / (corBase - offSet);
		double ki = -23;
		double kd = -105;
		
		int res = -1;
		
        /* optimisation */
		int round = 0;
		ArrayList<Double> errorList = new ArrayList<Double>();
		double[] paras = new double[4];
		paras[0] = powerStandard;
		paras[1] = powerVal;
		paras[2] = ki;
		paras[3] = kd;
		
		while(!Button.ESCAPE.isDown()){
			ColorSensor.Color vals = sensor1.colorSensor.getColor();
			res = sensor1.colorChecker();
			
			if(res == STOP) {
				round++;
				FileHandler mFileHandler = new FileHandler(Settings.PID_ERROR_FILE);
				mFileHandler.append(errorList,paras);
				if(round == 2){
					ma.stop();
					mb.stop();					
					break;
				}	
				setSamePower(ma, mb);	
			}
			
			double newCor = calculCor(vals.getRed(), vals.getGreen(), vals.getBlue(), line);
			
			double newError = newCor - offSet;
			errorList.add(newCor);
			
			double derivative = newError - error;
			
			if ( (newError * error) < 0 ){
				integral = 0;
			}
			error = newError;
			
			integral += error;
			
			double turn = powerVal * kp * error + ki * integral + kd * derivative;
			
			double powerA = powerStandard - turn;
			double powerB = powerStandard + turn;
			
			
			changePower(powerA, ma);
			changePower(powerB, mb);
			
			/*if(powerA > 0){
				ma.setPower(new Double(powerA).intValue());
				ma.forward();
			}
			else {
				powerA = powerA*(-1);
				ma.stop();
				ma.backward();
				ma.setPower(new Double(powerA).intValue());
				
			}
			
			if(powerB > 0){
				mb.setPower(new Double(powerB).intValue());
				mb.forward();
			}
			else {
				powerB = powerB*(-1);
				mb.stop();
				mb.backward();
				mb.setPower(new Double(powerB).intValue());
				
			}*/
		}
		
	}
	
	private void setSamePower(NXTMotor ma, NXTMotor mb){
		ma.setPower(30);
		mb.setPower(30);	
		ma.forward();
		mb.forward();
	}
	
	private void changePower(double power, NXTMotor mm){
		
		if(power > 0){
			mm.setPower(new Double(power).intValue());
			mm.forward();
		}
		else {
			power = power*(-1);
			mm.stop();
			mm.backward();
			mm.setPower(new Double(power).intValue());
			
		}
	}
	
	private RgbState getRgbState(int index){
		
		ColorType tempCT = sensor1.colorTypeList.get(index);
		RgbState rgbState = new RgbState(tempCT.rgbAvg.r, tempCT.rgbAvg.g, tempCT.rgbAvg.b);
		
		return rgbState;
	}
	
	private double calculCor(int r, int g, int b, RgbState base){
		double newColor = Math.sqrt(r*r + g*g + b*b);
		double baseColor = Math.sqrt(base.r*base.r + base.g*base.g + base.b*base.b);
		
		double colorMix = r * base.r + g * base.g + b * base.b;  
	    double cor = colorMix / (newColor * baseColor);
		return cor;
	}
	
	public void runFollowLineMode2(){
		LCD.clear();
		LCD.drawString("---Follow Line---", 0, 0);
		int[] res;
		int speed = 300;
		int turn = 0;
		Moteur mMoteur = new Moteur();
		mMoteur.mForward(speed);

		int directionFlag = -1;
		
		while(!Button.ESCAPE.isDown()){
			res = sensorReadShow();

			while((!Button.ESCAPE.isDown()) && (res[0] != WHITE) && (res[1] == WHITE)){//turn right
				directionFlag = 1;
				speed = 300;
				turn++;
				mMoteur.right(turn);
				res = sensorReadShow();
			}
			turn = 0;
			mMoteur.mForward(speed);

			while((!Button.ESCAPE.isDown()) && (res[0] == WHITE) && (res[1] != WHITE)){//turn left
				directionFlag = 0;
				speed = 300;
				turn++;
				mMoteur.left(turn);
				res = sensorReadShow();
			}
			turn = 0;
			mMoteur.mForward(speed);
			
			while((!Button.ESCAPE.isDown()) && (res[0] == WHITE) && (res[1] == WHITE)){//find line
				turn++;
				//speed = 400;
				if(directionFlag == 0){
					mMoteur.left(turn);
				}else if(directionFlag == 1){
					mMoteur.right(turn);
				}
				res = sensorReadShow();
			}
			turn = 0;
			mMoteur.mForward(speed);
			
			if(speed < 600){
				speed += 50;
			}	

		}

	}

	public int[] sensorReadShow(){
		int[] res = new int[2];
		res[0] = sensor1.colorChecker();
		res[1] = sensor2.colorChecker();
		printColors(res[0], res[1]);
		return res;
	}


	public void printColors(int res1, int res2){
		LCD.clear(3);
		LCD.clear(4);
		LCD.drawString("Sensor1 "+res1+" Detected", 0, 3);
		LCD.drawString("Sensor2 "+res2+" Detected", 0, 4);
		LCD.refresh();
	}
	
	/*private void init(){
		Thread curiser = new Thread(new Cruiser(sensor1));
		curiser.start();
	}*/
	
	/*public void runFollowLineMode1(){
	LCD.clear();
	LCD.drawString("---Follow Line---", 0, 0);
	init();
	}*/

}
