import lejos.nxt.*;
import lejos.robotics.*;

public class ControlColorSensor {
		
	public static void main(String args[]){
		 ColorSensor cs = new ColorSensor(SensorPort.S1);

	        for(int i = 0; i < 10; i++) {
	            Color color = cs.getColor();
	            System.out.println("Color = " + cs.getColorID() + " " + color.getColor() +
	                "(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() 
	                +") " + color.getColor());
	            Button.waitForAnyPress();
	        }
	}
}
