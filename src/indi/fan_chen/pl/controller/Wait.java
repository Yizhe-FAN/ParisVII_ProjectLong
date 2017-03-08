package indi.fan_chen.pl.controller;

import lejos.nxt.*;

public class Wait implements Runnable{
	
	private int msec;
	private Thread runThread;
	
	Wait(int sc){
		msec = sc;
	}
	
	public void run(){
		try {
			runThread = Thread.currentThread();
            Thread.sleep(msec);
        } catch (InterruptedException ie){
        	Thread.currentThread().interrupt();
        }
		FollowLine.addTime = 1;
		stopRequest();
	}
	
	public void stopRequest(){
		if(runThread != null){
			runThread.interrupt();
		}
	}

}
