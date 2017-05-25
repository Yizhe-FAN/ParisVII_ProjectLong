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

	//1 white, 2 vert, 3 rouge, 4 noir, 5 violet, 6 marron 7 orange
	static final int WHITE = 1; 
	static final int LINE = 2; 
	static final int STOP = 4; 
	static final int LEFT = 3; 
	static final int RIGHT = 5; 
	static final int STRAIGHT = 5;
	static final int BEREADY = 7;
	static int mline = 1;
	static int addTime = 0;
	private ControlColorSensor sensor1;
	private ControlColorSensor sensor2;
	private int status = -1;//status of LEFF/RIGHT/STRAIGHT
	private boolean changed = false;//status of object line/corBase/offset
	
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
		double lineColorSqrt = Math.sqrt(line.r*line.r + line.g*line.g + line.b*line.b);
		
		NXTMotor ma = new NXTMotor(MotorPort.A);
		NXTMotor mb = new NXTMotor(MotorPort.B);
		
		double error = 0;
		double integral = 0;
		double powerStandard = 35;//70
		double powerVal = 5;//19
		
		double corBase = calculCor(background.r, background.g, background.b, line, lineColorSqrt);
		
		double offSet = (corBase + 1) / 2;
		
		double kp = 1 / (corBase - offSet);
		double ki = -140;//-23
		double kd = -280;//-105
		
		int res = -1;
		
        /* optimisation */

		ArrayList<Double> errorList = new ArrayList<Double>();
		double[] paras = new double[4];
		paras[0] = powerStandard;
		paras[1] = powerVal;
		paras[2] = ki;
		paras[3] = kd;
		int times = 0;
	
		
		while(!Button.ESCAPE.isDown()){
			times++;
			ColorSensor.Color vals = sensor1.colorSensor.getColor();
			res = sensor1.colorChecker();
			
			if(res == STOP) {
				ma.stop();
				mb.stop();/*
				FileHandler mFileHandler = new FileHandler(Settings.PID_ERROR_FILE);
				mFileHandler.append(errorList,paras);*/
				break;	
			}else if(res == LINE && changed == true){//change == 0 -> status changed
				changed = false;
				line = getRgbState(1);
				lineColorSqrt = Math.sqrt(line.r*line.r + line.g*line.g + line.b*line.b);
				corBase = calculCor(background.r, background.g, background.b, line, lineColorSqrt);
				offSet = (corBase + 1) / 2;
			}else if(res == LEFT){
				status = LEFT;
				changed = true;
				line = getRgbState(3);
				lineColorSqrt = Math.sqrt(line.r*line.r + line.g*line.g + line.b*line.b);
				corBase = calculCor(background.r, background.g, background.b, line, lineColorSqrt);
				offSet = (corBase + 1) / 2;
			}else if(res == RIGHT){
				status = RIGHT;
				changed = true;
				line = getRgbState(4);
				lineColorSqrt = Math.sqrt(line.r*line.r + line.g*line.g + line.b*line.b);
				corBase = calculCor(background.r, background.g, background.b, line, lineColorSqrt);
				offSet = (corBase + 1) / 2;
			}else if(res == STRAIGHT){
				status = STRAIGHT;
				changed = true;
				line = getRgbState(5);
				lineColorSqrt = Math.sqrt(line.r*line.r + line.g*line.g + line.b*line.b);
				corBase = calculCor(background.r, background.g, background.b, line, lineColorSqrt);
				offSet = (corBase + 1) / 2;
			}else if(res == BEREADY){
				if(status == LEFT){
					changePower(50, ma);//
					changePower(25, mb);
					while(res != LINE){
						res = sensor1.colorChecker();
					}
				}else if(status == RIGHT){
					changePower(25, ma);//
					changePower(50, mb);
					while(res != LINE){
						res = sensor1.colorChecker();
					}
				}else if(status == STRAIGHT){
					changePower(50, ma);
					changePower(50, mb);
					while(res != LINE){
						res = sensor1.colorChecker();
					}
				}
			}	
			
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
	
	private double calculCor(int r, int g, int b, RgbState base, double baseColor){
		double newColor = Math.sqrt(r*r + g*g + b*b);
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
