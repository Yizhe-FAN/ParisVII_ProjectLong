package indi.fan_chen.pl.controller;


public class Timer implements Runnable{
	
	private int msec;
	private Thread runThread;
	
	Timer(int ms){
		msec = ms;
	}
	
	public void run(){
		try {
			runThread = Thread.currentThread();
            Thread.sleep(msec);
        } catch (InterruptedException ie){
        	Thread.currentThread().interrupt();
        }
		FollowLine.mline = 0;
	}
	
	public void stopRequest(){
		if(runThread != null){
			runThread.interrupt();
		}
	}
	

}