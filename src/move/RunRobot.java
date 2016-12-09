package move;

import java.io.File;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.util.TextMenu;

public class RunRobot {
	
	public static void main(String args[]){
		String []modes={"New Colors","Old Colors"};
		File file = new File("ListBackUp.txt");
		TextMenu modeMenu = new TextMenu(modes, 1, "File Mode");
		ControlColorSensor mControlColorSensor = new ControlColorSensor();
		int num = modeMenu.select();
		if(num == 0){
			file.delete();
			mControlColorSensor.colorReader();
			
			//press Enter to next
			mControlColorSensor.testColorChecker();

			FileHandler f = new FileHandler();
			f.writeInFile(mControlColorSensor);
			
		}else if(num == 1){
			
			if(!file.exists()){
				LCD.clear();
				LCD.drawString("file not exists", 0, 0);
				Button.waitForAnyPress();
				
			}
			else{
				LCD.clear();
				LCD.drawString("---Load File---", 0, 0);
				FileHandler f = new FileHandler();
				f.readInList("ListBackUp.txt",mControlColorSensor);
				Button.waitForAnyPress();
				
				//press Enter to next
				mControlColorSensor.testColorChecker();
				
				String lastTime = "";
				Moteur mMoteur = new Moteur();
				int res;
				while(!Button.ESCAPE.isDown()){
					LCD.clear();
					res = mControlColorSensor.colorChecker();
					LCD.clear(3);
					LCD.drawString("res: "+res, 0, 3);
					LCD.refresh();
					try {
		                Thread.sleep(2);
		            } catch (InterruptedException ie){
		            	ie.printStackTrace();
		            }
					if(mControlColorSensor.colorChecker() == 0){
						mMoteur.normalMove();
						if(lastTime == "right"){
							mMoteur.rotateLeft();
							lastTime="left";
						}
						else
						{
							mMoteur.rotateRight();
							lastTime="right";
						}
					}
					else if(mControlColorSensor.colorChecker()!= 0){
						mMoteur.normalMove();
						if(lastTime == "left"){
							mMoteur.rotateRight();
							lastTime="right";
						}
						else{
							mMoteur.rotateLeft();
							lastTime = "left";
						}
					}
					
				}			
			}
		
		}
			
	}

}
