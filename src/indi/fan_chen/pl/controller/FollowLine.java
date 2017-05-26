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
	static final int LINE = 2; 
	static final int STOP = 3; 
	static final int LEFT = 4; 
	static final int RIGHT = 5; 
	static final int STRAIGHT = 6;
	static final int BEREADY = 7;
	private ControlColorSensor sensor1;
	private ControlColorSensor sensor2;
	
	//var Mode1
	RgbState background = getRgbState(0);
	RgbState line = getRgbState(1);
	double lineColorSqrt = Math.sqrt(line.r*line.r + line.g*line.g + line.b*line.b);
	
	NXTMotor ma = new NXTMotor(MotorPort.A);
	NXTMotor mb = new NXTMotor(MotorPort.B);
	
	int status = -1;//status of LEFF/RIGHT/STRAIGHT
	boolean changed = false;//status of object line/corBase/offset
	
	double corBase = calculCor(background.r, background.g, background.b, line, lineColorSqrt);
	double offSet = (corBase + 1) / 2;
	
	//var Mode2
	int speed = 300;
	int turn = 0;
	Moteur mMoteur = new Moteur();
	int directionFlag = -1;
	
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
		int res = -1;
		
        /* optimisation 

		ArrayList<Double> errorList = new ArrayList<Double>();
		double[] paras = new double[4];
		paras[0] = powerStandard;
		paras[1] = powerVal;
		paras[2] = ki;
		paras[3] = kd;*/
	
		
		while(!Button.ESCAPE.isDown()){
			ColorSensor.Color vals = sensor1.colorSensor.getColor();
			res = sensor1.colorChecker();
				
			checkResStatus(res);
			if(res == STOP){
				break;
			}
			
			pid(vals);
		
		}
		
	}
	
	private void pid(ColorSensor.Color vals){
		
		double powerStandard = 35;//70
		double powerVal = 5;//19
		double error = 0;
		double integral = 0;
		
		double kp = 1 / (corBase - offSet);
		double ki = -140;//-23
		double kd = -280;//-105
		
		double corNew = calculCor(vals.getRed(), vals.getGreen(), vals.getBlue(), line, lineColorSqrt);
		double newError = corNew - offSet;
		//if(times%50 == 0) errorList.add(Math.abs(newError));
		
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
	}
	
	private void setSamePower(NXTMotor ma, NXTMotor mb){
		ma.setPower(50);
		mb.setPower(50);	
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
	
	private double calculCor(int r, int g, int b, RgbState base, double baseColor){
		double newColor = Math.sqrt(r*r + g*g + b*b);
		double colorMix = r * base.r + g * base.g + b * base.b;  
	    double cor = colorMix / (newColor * baseColor);
		return cor;
	}
	
	private void checkResStatus(int result){
		switch(result){
			case STOP:
				setPowerWithRes(result);/*
				FileHandler mFileHandler = new FileHandler(Settings.PID_ERROR_FILE);
				mFileHandler.append(errorList,paras);*/
				break;
			case LINE:
				if(changed == true){
					changed = false;
					resetPidLineData(1);
				}
				break;
			case LEFT:
				status = LEFT;
				changed = true;
				resetPidLineData(3);
				break;
			case RIGHT:
				status = RIGHT;
				changed = true;
				resetPidLineData(4);
				break;
			case STRAIGHT:
				status = STRAIGHT;
				changed = true;
				resetPidLineData(5);
				break;
			case BEREADY:
				setPowerWithRes(result);
				break;
		}
	}
	
	private void resetPidLineData(int result){
		line = getRgbState(result);
		lineColorSqrt = Math.sqrt(line.r*line.r + line.g*line.g + line.b*line.b);
		corBase = calculCor(background.r, background.g, background.b, line, lineColorSqrt);
		offSet = (corBase + 1) / 2;
	}
	
	private void setPowerWithRes(int result){
		switch(result){
			case STOP:
				ma.stop();
				mb.stop();
				break;
			case LEFT:
				changePower(50, ma);//
				changePower(25, mb);
				while(sensor1.colorChecker() != LINE){}
			case RIGHT:
				changePower(25, ma);//
				changePower(50, mb);
				while(sensor1.colorChecker() != LINE){}
			case STRAIGHT:
				changePower(50, ma);
				changePower(50, mb);
				while(sensor1.colorChecker() != LINE){}		
		}
	}
	
	public void runFollowLineMode2(){
		LCD.clear();
		LCD.drawString("---Follow Line---", 0, 0);
		int[] res;
		mMoteur.mForward(speed);
		
		while(!Button.ESCAPE.isDown()){
			res = sensorReadShow();

			twoSensorFollowLine(res, 1);
			twoSensorFollowLine(res, 2);
			twoSensorFollowLine(res, 3);
			
			if(speed < 600){
				speed += 50;
			}	

		}

	}
	
	private void twoSensorFollowLine(int[] res, int mode){
		switch(mode){
			case 1:
				goLeftOrRight(res, 1);
				break;
			case 2:
				goLeftOrRight(res, 2);
				break;
			case 3:
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
				break;
		}		
	}

	private void goLeftOrRight(int[] res, int mode){
		int a = 0;
		int b = 0;
		int d = 0;
		switch(mode){
			case 1:
				a = 0;
				b = 1;
				d = 1;
				break;
			case 2:
				a = 1;
				b = 0;
				d = 0;
				break;
		}
		while((!Button.ESCAPE.isDown()) && (res[a] != WHITE) && (res[b] == WHITE)){//turn right
			directionFlag = d;
			speed = 300;
			turn++;
			if(mode == 1){
				mMoteur.right(turn);
			}else{
				mMoteur.left(turn);
			}
			res = sensorReadShow();
		}
		turn = 0;
		mMoteur.mForward(speed);
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
