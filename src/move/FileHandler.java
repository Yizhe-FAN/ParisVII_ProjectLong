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
			String content = "";
			for(int i = 0; i <= size; i++){
				
				if(i < size){
				ColorState c = (ColorState)ControlColorSensor.colorTypeList.get(i);
				
				content+= format(c.rgbMin.r);
				content+= format(c.rgbMin.g);
				content+= format(c.rgbMin.b);
				
				content+= format(c.rgbMax.r);
				content+= format(c.rgbMax.g);
				content+= format(c.rgbMax.b);
				
				content+= format(c.rgbAvg.r);
				content+= format(c.rgbAvg.g);
				content+= format(c.rgbAvg.b);
					
					content+="\n";
				}
				else{
					content+= "Finish\n";
				}
				
			}
			bw.write(content);
			bw.close();
			
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
                String lineTxt = "";
               
                while(!(lineTxt = bufferedReader.readLine()).equals("Finish")){
                	
                		LCD.clear(7);
                		ColorState colorState = new ColorState();
                		colorState.rgbMin.r= Integer.parseInt(lineTxt.substring(0, 3));
                		colorState.rgbMin.g= Integer.parseInt(lineTxt.substring(3, 6));
                		colorState.rgbMin.b= Integer.parseInt(lineTxt.substring(6, 9));
                		colorState.rgbMax.r= Integer.parseInt(lineTxt.substring(9, 12));
                		colorState.rgbMax.g= Integer.parseInt(lineTxt.substring(12, 15));
                		colorState.rgbMax.b= Integer.parseInt(lineTxt.substring(15, 18));
                		colorState.rgbAvg.r= Integer.parseInt(lineTxt.substring(18, 21));
                		colorState.rgbAvg.g= Integer.parseInt(lineTxt.substring(21, 24));
                		colorState.rgbAvg.b= Integer.parseInt(lineTxt.substring(24, 27));
                		ControlColorSensor.colorTypeList.add(colorState);
                		LCD.drawString(""+colorState.rgbAvg.r, 0, 7);
                		Button.waitForAnyPress();
                	
                }
                bufferedReader.close();
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


