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
				LCD.drawString("File is not exists!", 0, 6);
			}
			OutputStreamWriter r = new OutputStreamWriter(new FileOutputStream(file));
			BufferedWriter bw = new BufferedWriter(r);
			for(int i = 0; i < ControlColorSensor.colorTypeList.size(); ++i){
				String content = "";
				int index = 1000+i;
				ColorState c = (ColorState)ControlColorSensor.colorTypeList.get(i);
				content+=index+" ";
				content+= c.rgbMin.r+" ";
				content+= c.rgbMin.g+" ";
				content+= c.rgbMin.b+" ";
				content+= c.rgbMax.r+" ";
				content+= c.rgbMax.g+" ";
				content+= c.rgbMax.b+" ";
				content+= c.rgbAvg.r+" ";
				content+= c.rgbAvg.g+" ";
				content+= c.rgbAvg.b+"\n";
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
		try{
			File file =new File("ListBackUp.txt");
			if(!file.exists()){
				LCD.drawString("File is not exists!", 0, 6);
			}else{
				InputStreamReader r = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(r);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                	while(true){
                		
                	}
                }
                bufferedReader.close();
                r.close();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	
	}
	
}


