package move;
import lejos.nxt.*;
import lejos.robotics.*;
import lejos.util.TextMenu;
import lejos.nxt.ColorSensor;

public class ControlColorSensor {
	
	private String ports[] = {"Port-1", "Port-2", "Port-3", "Port-4"};
	private TextMenu portMenu = new TextMenu(ports, 1, "Sensor port");
	private String modes[] = {"Full", "Red", "Green", "Blue", "White", "None"};
	private int colors[] = {Color.WHITE, Color.RED, Color.GREEN, Color.BLUE, Color.BLACK, Color.NONE};
	private String colorNames[] = {"None", "Red", "Green", "Blue", "Yellow", "Megenta",
			"Orange", "White", "Black", "Pink", "Grey", "Light Grey", "Dark Grey", "Cyan"};
	
	public void displayColor(String name, int raw, int calibreated, int line){
		LCD.drawString(name, 0, line);
		LCD.drawInt(raw, 5, 6, line);
		LCD.drawInt(calibreated, 5, 11, line);
	}
	
	public void ColorSensorReader(){
		
		TextMenu modeMenu = new TextMenu(modes, 1, "Color Mode");
		
		int portNo = portMenu.select();
		if(portNo < 0)return;
		for(;;){
			ColorSensor cs = new ColorSensor(SensorPort.getInstance(portNo));
			int mode = modeMenu.select();
			if(mode < 0) return;
			cs.setFloodlight(colors[mode]);
			LCD.clear();
			while(!Button.ESCAPE.isDown()){
				LCD.drawString("Mode: " + modes[mode], 0, 0	);
				LCD.drawString("Color Raw Cal", 0, 1);
				if(mode == 0){
					ColorSensor.Color vals = cs.getColor();//return un valeur de couleur avec rouge vert bleu
					ColorSensor.Color rawVals = cs.getRawColor();//return la couleur brute
					displayColor("Red", rawVals.getRed(), vals.getRed(), 2);
					displayColor("Green", rawVals.getGreen(), vals.getGreen(), 3);
					displayColor("Blue", rawVals.getBlue(), vals.getBlue(), 4);
					displayColor("None", rawVals.getBackground(), vals.getBackground(), 5);
					LCD.drawString("Color:  ", 0, 6);
					LCD.drawString(colorNames[vals.getColor() + 1], 7, 6);//afficher couleur sur écran
					LCD.drawString("Color val:  ", 0, 7);
					LCD.drawInt(vals.getColor(), 3, 11, 7);
				}else{
					LCD.drawString(modes[mode], 0, 3);
					int raw = cs.getRawLightValue();
					int val = cs.getLightValue();
					LCD.drawInt(raw, 5, 6, 3);
					LCD.drawInt(val, 5, 11, 3);
				}
			}
			LCD.clearDisplay();
		}

	}
	

	public void show(){
			System.out.println("black "+ColorSensor.Color.BLACK +"blue "+ColorSensor.Color.BLUE
					+"CYan "+ColorSensor.Color.CYAN+"Darkgray "+ColorSensor.Color.DARK_GRAY+"gray "
					+ColorSensor.Color.GRAY+"green "+ColorSensor.Color.GREEN+"light gray "+
					ColorSensor.Color.LIGHT_GRAY+"magenta "+ColorSensor.Color.MAGENTA+"none"
					+ColorSensor.Color.NONE+"Orange"+ColorSensor.Color.ORANGE+"pink "+
					ColorSensor.Color.PINK+"red "+ColorSensor.Color.RED+"white "+ColorSensor.Color.WHITE+"yellow "
					+ColorSensor.Color.YELLOW);
		}
	
}
