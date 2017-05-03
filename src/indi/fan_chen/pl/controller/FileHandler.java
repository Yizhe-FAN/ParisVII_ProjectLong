package indi.fan_chen.pl.controller;

import java.io.*;
import java.util.ArrayList;

import indi.fan_chen.pl.model.ColorType;
import lejos.nxt.Button;
import lejos.nxt.LCD;

public class FileHandler {
	
	private File file;
	
	public FileHandler(String fileName){
		file = new File(fileName);
	}
	
	
	public void deleteFile(){
		file.delete();
	}
	
	public void append(ArrayList<Double> arrayList,double[] paras){
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			OutputStreamWriter r = new OutputStreamWriter(new FileOutputStream(file));
			BufferedWriter bw = new BufferedWriter(r);
			int size =  arrayList.size();
			String content = "";
			
			content += paras[0]+","+paras[1]+","+paras[2]+","+paras[3]+",";
			
			for(int i = 0; i <= size; i++){
				if(i == 0 ){
					content += arrayList.get(0);
				}else if (i < size) {
					content += ","+arrayList.get(i);
				}else{
					content += "\n";
				}
			}
			bw.append(content);
			bw.close();			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		
	}
	
	public void writeInFile(ControlColorSensor controlColorSensor){
		try{
			
			if(!file.exists()){
				file.createNewFile();
			}
			OutputStreamWriter r = new OutputStreamWriter(new FileOutputStream(file));
			BufferedWriter bw = new BufferedWriter(r);
			int size =  controlColorSensor.colorTypeList.size();
			String content = "";
			for(int i = 0; i <= size; i++){
				if(i < size){
				ColorType c = (ColorType)controlColorSensor.colorTypeList.get(i);
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
	
	public void readInList(ControlColorSensor controlColorSensor){
		
		try{
			if(!file.exists()){
				LCD.clear();
				LCD.drawString("File is not exists!", 0, 0);
				Button.waitForAnyPress();
			}else{
				InputStreamReader r = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(r);
                String lineTxt = "";              
                while(!(lineTxt = bufferedReader.readLine()).equals("Finish")){
            		ColorType colorState = new ColorType();
            		colorState.rgbMin.r= Integer.parseInt(lineTxt.substring(0, 3));
            		colorState.rgbMin.g= Integer.parseInt(lineTxt.substring(3, 6));
            		colorState.rgbMin.b= Integer.parseInt(lineTxt.substring(6, 9));
            		colorState.rgbMax.r= Integer.parseInt(lineTxt.substring(9, 12));
            		colorState.rgbMax.g= Integer.parseInt(lineTxt.substring(12, 15));
            		colorState.rgbMax.b= Integer.parseInt(lineTxt.substring(15, 18));
            		colorState.rgbAvg.r= Integer.parseInt(lineTxt.substring(18, 21));
            		colorState.rgbAvg.g= Integer.parseInt(lineTxt.substring(21, 24));
            		colorState.rgbAvg.b= Integer.parseInt(lineTxt.substring(24, 27));
            		controlColorSensor.colorTypeList.add(colorState);           	
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


