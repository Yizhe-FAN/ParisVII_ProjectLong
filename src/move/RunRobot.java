package move;

public class RunRobot {
	
	public static void main(String args[]){
		
		ControlColorSensor mControlColorSensor = new ControlColorSensor();
		mControlColorSensor.RunTextMenu();
		mControlColorSensor.RunColorSensor(mControlColorSensor);
		
	}

}
