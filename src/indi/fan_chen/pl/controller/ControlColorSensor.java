package indi.fan_chen.pl.controller;

import java.util.ArrayList;
import indi.fan_chen.pl.model.ColorType;
import lejos.nxt.*;
import lejos.robotics.Color;

public class ControlColorSensor{
	
	public ArrayList<ColorType> colorTypeList;
	public ColorSensor colorSensor;

	
	public ControlColorSensor(int port){
			colorTypeList = new ArrayList<ColorType>();
			if(port == 1){
				colorSensor = new ColorSensor(SensorPort.getInstance(0));
			}else if(port == 2){
				colorSensor = new ColorSensor(SensorPort.getInstance(1));
			}
			colorSensor.setFloodlight(Color.WHITE);
	}
	
	public void colorReader(){
		LCD.clear();
		LCD.drawString("---Read Color---", 0, 0);		
		while(!Button.ESCAPE.isDown()){
			
			if(Button.RIGHT.isDown()){//new color
				ColorType colorType = new ColorType();
				colorTypeList.add(colorType);
				LCD.clear(1);
				LCD.drawString("Set color: "+colorTypeList.size(), 0, 1);
				try {
	                Thread.sleep(1000);
	            } catch (InterruptedException ie)
	            {
	              ie.printStackTrace();
	            }
			}
			if(Button.LEFT.isDown()){//add RGB status to new color
				int size = colorTypeList.size();
				ColorType colorType = colorTypeList.get(size-1);
				ColorSensor.Color vals = colorSensor.getColor();
				updateMin(vals, colorType);
				updateMax(vals, colorType);
				updateAvg(vals, colorType);
				colorType.rgbInfo.add(vals);
				LCD.clear(2);
				LCD.drawString("Get: "+colorType.rgbInfo.size()+" times", 0, 2);
				LCD.clear(3);LCD.clear(4);LCD.clear(5);LCD.clear(6);				
				LCD.drawString(colorType.rgbMin.r+" "+colorType.rgbMin.g+" "+colorType.rgbMin.b, 0, 3);
				LCD.drawString(colorType.rgbMax.r+" "+colorType.rgbMax.g+" "+colorType.rgbMax.b, 0, 4);
				LCD.drawString(colorType.rgbAvg.r+" "+colorType.rgbAvg.g+" "+colorType.rgbAvg.b, 0, 5);
				LCD.drawString(vals.getRed()+" "+vals.getGreen()+" "+vals.getBlue(), 0, 6);
				try {
	                Thread.sleep(1000);
	            } catch (InterruptedException ie){
	            	ie.printStackTrace();
	            }
			}
		}
		
	}
	
	public int colorChecker(){				
		int r,g,b;
		int size = colorTypeList.size();
		ColorSensor.Color vals = colorSensor.getColor();
		r = vals.getRed(); g = vals.getGreen(); b = vals.getBlue();
		int i;
		int indice = 0;
		double min = Double.MAX_VALUE;
		ColorType tempCT;
		for(i = 0;i < size; i++){
			tempCT = colorTypeList.get(i);
			double distance = Math.pow(r-tempCT.rgbAvg.r,2) + 
					Math.pow(g-tempCT.rgbAvg.g,2) + 
					Math.pow(b-tempCT.rgbAvg.b,2);
			if(distance < min) {
				min = distance;
				indice = i;
			}
		}
		return indice + 1;
	}
	
	public void updateMin(ColorSensor.Color c, ColorType s){
		if(s.rgbInfo.size() == 0){
			s.rgbMin.r = c.getRed();
			s.rgbMin.g = c.getGreen();
			s.rgbMin.b = c.getBlue();
		}else{
			if(s.rgbMin.r > c.getRed()){
				s.rgbMin.r = c.getRed();
			}
			if(s.rgbMin.g > c.getGreen()){
				s.rgbMin.g = c.getGreen();
			}
			if(s.rgbMin.b > c.getBlue()){
				s.rgbMin.b = c.getBlue();
			}
		}
	}
	
	public void updateMax(ColorSensor.Color c, ColorType s){
		if(s.rgbInfo.size() == 0){
			s.rgbMax.r = c.getRed();
			s.rgbMax.g = c.getGreen();
			s.rgbMax.b = c.getBlue();
		}else{
			if(s.rgbMax.r < c.getRed()){
				s.rgbMax.r = c.getRed();
			}
			if(s.rgbMax.g < c.getGreen()){
				s.rgbMax.g = c.getGreen();
			}
			if(s.rgbMax.b < c.getBlue()){
				s.rgbMax.b = c.getBlue();
			}
		}
	}
	
	public void updateAvg(ColorSensor.Color c, ColorType s){
		if(s.rgbInfo.size() == 0){
			s.rgbAvg.r = c.getRed();
			s.rgbAvg.g = c.getGreen();
			s.rgbAvg.b = c.getBlue();
		}else{
			int size = s.rgbInfo.size();
			s.rgbAvg.r = (s.rgbAvg.r*size + c.getRed())/(size+1);
			s.rgbAvg.g = (s.rgbAvg.g*size + c.getGreen())/(size+1);
			s.rgbAvg.b = (s.rgbAvg.b*size + c.getBlue())/(size+1);
		}
	}
	
}