package move;

import java.io.*;
import java.util.*;

import lejos.nxt.Button;
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
			int size =  ControlColorSensor.colorTypeList.size();
			for(int i = 0; i < ControlColorSensor.colorTypeList.size(); ++i){
				String content = "";
				ColorState c = (ColorState)ControlColorSensor.colorTypeList.get(i);
				content+="i";
				content+= format(c.rgbMin.r);
				content+= format(c.rgbMin.g);
				content+= format(c.rgbMin.b)+"a";
				content+= format(c.rgbMax.r);
				content+= format(c.rgbMax.g);
				content+= format(c.rgbMax.b)+"v";
				content+= format(c.rgbAvg.r);
				content+= format(c.rgbAvg.g);
				content+= format(c.rgbAvg.b);
				if(i < size-1)		
					content+="\n";
				else{
					content+=" ";
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
		try{
			File file =new File(fileP);
			if(!file.exists()){
				LCD.drawString("File is not exists!", 0, 6);
			}else{
				LCD.drawString("Begin to Read", 0, 5);
				Button.waitForAnyPress();
				InputStreamReader r = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(r);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                	//LCD.drawString(lineTxt.substring(1, 4), 0, 3);
                	//Button.waitForAnyPress();
                		ColorState colorState = new ColorState();
                		colorState.rgbMin.r= Integer.parseInt(lineTxt.substring(1, 4));
                		colorState.rgbMin.g= Integer.parseInt(lineTxt.substring(4, 7));
                		colorState.rgbMin.b= Integer.parseInt(lineTxt.substring(7, 10));
                		colorState.rgbMax.r= Integer.parseInt(lineTxt.substring(11, 14));
                		colorState.rgbMax.g= Integer.parseInt(lineTxt.substring(14, 17));
                		colorState.rgbMax.b= Integer.parseInt(lineTxt.substring(17, 20));
                		colorState.rgbAvg.r= Integer.parseInt(lineTxt.substring(21, 24));
                		colorState.rgbAvg.g= Integer.parseInt(lineTxt.substring(24, 27));
                		colorState.rgbAvg.b= Integer.parseInt(lineTxt.substring(27, 30));
                		ControlColorSensor.colorTypeList.add(colorState);
                		LCD.drawString(""+colorState.rgbAvg.b, 0, 7);
                		Button.waitForAnyPress();
                	
                }
                bufferedReader.close();
               // LCD.clear(5);
               // LCD.drawString("Read finished", 0, 5);
               // Button.waitForAnyPress();
                r.close();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	
	}
	
	public String format(int r){
		String s="";
		if(r > 10 && r < 100){
			return s+"0"+r;
		}else if(r < 10){
			return s+"00"+r;
		}
		return s+r;
	}
	
}


