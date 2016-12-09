package move;

import lejos.nxt.LCD;
import lejos.util.TextMenu;

public class Menu {
	
	int selectNumber;
	String[] items = {"New Colors","Check Colors","Follow Line"};
	String []fileSystem = { "ColorsBackUp.txt" };
	TextMenu mainMenu;
	
	public Menu(){
		mainMenu = new TextMenu(items, 1, "---My Robot---");
	}
	
	public void startUp(){
		selectNumber = mainMenu.select();
		FileHandler colorsFileHandler = new FileHandler(fileSystem[0]);
		ControlColorSensor mControlColorSensor = new ControlColorSensor();
		
		switch (selectNumber){
			case 0:
				//New Colors
				colorsFileHandler.deleteFile();				
				mControlColorSensor.colorReader();
				colorsFileHandler.writeInFile(mControlColorSensor);
				break;
			case 1:
				//test ColorChecker
				colorsFileHandler.readInList(mControlColorSensor);
				mControlColorSensor.testColorChecker();
				//press Enter to next
				break;			
			case 2:
				//Follow Line
				colorsFileHandler.readInList(mControlColorSensor);
				FollowLine follow = new FollowLine();
				follow.runFollowLIne(mControlColorSensor);
				break;
			default:
				LCD.clear();
				LCD.drawString("not Match", 0, 0);
				break;
		}
	}
	
}
