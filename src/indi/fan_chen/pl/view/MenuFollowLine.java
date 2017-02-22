package indi.fan_chen.pl.view;

import indi.fan_chen.pl.controller.ControlColorSensor;
import indi.fan_chen.pl.controller.FileHandler;
import indi.fan_chen.pl.controller.FollowLine;
import indi.fan_chen.pl.model.Settings;
import lejos.nxt.LCD;
import lejos.util.TextMenu;

public class MenuFollowLine {
	String[] items = {"One Sensor","Two Sensor"};
	TextMenu textMenu;
	
	public MenuFollowLine(){
		LCD.clear();
		textMenu = new TextMenu(items, 1, "---Follow Line---");
	}
	
	public void run(){
		int selectNumber = textMenu.select();
		
		FileHandler colorsFileHandler = new FileHandler(Settings.COLOR_FILE_NAME);
		ControlColorSensor mControlColorSensor1 = new ControlColorSensor(1);
		colorsFileHandler.readInList(mControlColorSensor1);
		FollowLine mFollowLine = new FollowLine();
		
		switch (selectNumber){
			case 0:
				mFollowLine.runFollowLineMode1(mControlColorSensor1);
				break;
			case 1:
				ControlColorSensor mControlColorSensor2 = new ControlColorSensor(2);
				colorsFileHandler.readInList(mControlColorSensor2);
				mFollowLine.runFollowLineMode2(mControlColorSensor1, mControlColorSensor2);
				break;
			default: 
				LCD.clear();
				LCD.drawString("not Match", 0, 0);
				break;
		}	
			
	}
}
