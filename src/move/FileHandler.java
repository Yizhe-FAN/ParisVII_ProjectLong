package move;

import java.io.*;
import java.util.*;

import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;

public class FileHandler {
	
	FileHandler(){
	
	}
	
	public void writeInFile(){
		try{
			File file =new File("ListBackUp.txt");
			if(!file.exists()){
				file.createNewFile();
			}else{
				LCD.drawString("File is not exists!", 0, 6);
			}
			OutputStreamWriter r = new OutputStreamWriter(new FileOutputStream(file));
			BufferedWriter bw = new BufferedWriter(r);
			for(int i = 0; i < ControlColorSensor.colorTypeList.size(); ++i){
				String content = "";
				int index = 1000+i;
				for(int j = 0; j < ControlColorSensor.colorTypeList.get(i).rgbInfo.size(); ++j){
					ColorSensor.Color c = ControlColorSensor.colorTypeList.get(i).rgbInfo.get(j);
					if(j == 0) content+=index+" ";
					content += c.getRed()+" ";
					content += c.getGreen()+" ";
					content += c.getBlue()+" ";
					if(j == ControlColorSensor.colorTypeList.get(i).rgbInfo.size()-1) content+="\n";
				}
				bw.write(content);
			}
			bw.close();
			r.close();
		}catch(IOException e){
			 e.printStackTrace();
		}
	}
	
	public void readInList(String fileP){
		new ControlColorSensor();
	
	}
	
}


