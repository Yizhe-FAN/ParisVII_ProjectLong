package indi.fan_chen.pl.view;

import indi.fan_chen.pl.controller.ControlColorSensor;
import indi.fan_chen.pl.controller.FileHandler;
import indi.fan_chen.pl.model.Settings;
import lejos.nxt.LCD;
import lejos.util.TextMenu;

public class Menu {
	
	String[] items = {"New Colors","Check Colors","Follow Line"};
	TextMenu textMenu;
	
	public Menu(){
		textMenu = new TextMenu(items, 1, "---My Robot---");
	}
	
	public void run(){
		int selectNumber = textMenu.select();
		switch (selectNumber){
			case 0:
				//New Colors
				ControlColorSensor mControlColorSensor = new ControlColorSensor(1);
				FileHandler colorsFileHandler = new FileHandler(Settings.COLOR_FILE_NAME);
				colorsFileHandler.deleteFile();				
				mControlColorSensor.colorReader();
				colorsFileHandler.writeInFile(mControlColorSensor);
				break;
			case 1:
				//Check Color
				MenuColorChecker menuColorChecker = new MenuColorChecker();
				menuColorChecker.run();
				break;			
			case 2:
				//Follow Line
				MenuFollowLine menuFollowLine = new MenuFollowLine();
				menuFollowLine.run();
				break;
			default:
				LCD.clear();
				LCD.drawString("not Match", 0, 0);
				break;
		}
	}
	
}
