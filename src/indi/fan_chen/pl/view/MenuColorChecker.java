package indi.fan_chen.pl.view;

import indi.fan_chen.pl.controller.ControlColorSensor;
import indi.fan_chen.pl.controller.FileHandler;
import indi.fan_chen.pl.model.Settings;
import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.util.TextMenu;

public class MenuColorChecker {
	String[] items = {"Sensor 1","Sensor 2"};
	TextMenu textMenu;
	
	public MenuColorChecker(){
		LCD.clear();
		textMenu = new TextMenu(items, 1, "---Check Colors---");
	}
	
	public void run(){
		int selectNumber = textMenu.select();
		FileHandler colorsFileHandler = new FileHandler(Settings.COLOR_FILE_NAME);
		ControlColorSensor mControlColorSensor;
		switch (selectNumber){
		case 0:
			mControlColorSensor = new ControlColorSensor(1);
			colorsFileHandler.readInList(mControlColorSensor);
			MainColorChecker(mControlColorSensor);
			break;
		case 1:
			mControlColorSensor = new ControlColorSensor(2);
			colorsFileHandler.readInList(mControlColorSensor);
			MainColorChecker(mControlColorSensor);
			break;			
		default:
			LCD.clear();
			LCD.drawString("not Match", 0, 0);
			break;
		}
	}
	
	public void MainColorChecker(ControlColorSensor mControlColorSensor){
		LCD.clear();
		LCD.drawString("---Check Color---", 0, 0);
		int colorNum;
		
		while(!Button.ESCAPE.isDown()){
			colorNum = mControlColorSensor.colorChecker();
			LCD.clear(1);
			LCD.drawString("This is color: "+colorNum, 0, 1);
			try {
	            Thread.sleep(2);
	        } catch (InterruptedException ie){
	        	ie.printStackTrace();
	        }
		}
	}
}
