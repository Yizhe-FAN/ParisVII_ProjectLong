package indi.fan_chen.pl.model;

public class RgbState {
	public int r;
	public int g;
	public int b;
	public RgbState(int v){
		r = v;
		g = v;
		b = v;
	}
	
	public RgbState(int r, int g, int b){
		this.r = r;
		this.g = g;
		this.b = b;
	}
}
