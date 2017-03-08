package indi.fan_chen.pl.model;

import java.util.ArrayList;

import indi.fan_chen.pl.model.RgbState;
import lejos.nxt.ColorSensor;

public class ColorType {
	public RgbState rgbMin;
	public RgbState rgbMax;
	public RgbState rgbAvg;
	public ArrayList<ColorSensor.Color> rgbInfo;
	
	public ColorType(){
		rgbInfo = new ArrayList<ColorSensor.Color>();
		rgbMin = new RgbState(0);
		rgbMax = new RgbState(0);
		rgbAvg = new RgbState(0);
	}
}
