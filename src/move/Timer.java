package move;

import lejos.nxt.*;

public class Timer extends Thread{
	
	private int msec;
	public int ok = 1;
	
	Timer(int ms){
		msec = ms;
	}
	
	public void run(){
		ok = 0;
		try {
            Thread.sleep(msec);
        } catch (InterruptedException ie){
        	ie.printStackTrace();
        }
		FollowLine.mline = 0;
	}
	

}
