package move;
import java.util.ArrayList;
import lejos.nxt.*;
import lejos.robotics.*;
import lejos.util.TextMenu;

public class ControlColorSensor {
	
	private String ports[] = {"Port-1", "Port-2", "Port-3", "Port-4"};
	private TextMenu portMenu = new TextMenu(ports, 1, "Sensor port");
	private String modes[] = {"Full", "Red", "Green", "Blue", "White", "None"};
	private int colors[] = {Color.WHITE, Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.NONE};
	private String colorNames[] = {"None", "Red", "Green", "Blue", "Yellow", "Megenta",
			"Orange", "White", "Black", "Pink", "Grey", "Light Grey", "Dark Grey", "Cyan"};
	private ArrayList<ColorState> colorType;
	
	public ControlColorSensor(){
		colorType = new ArrayList<ColorState>();
	}
	
	public void colorReader(ColorSensor cs){
		while(!Button.ESCAPE.isDown()){
			if(Button.RIGHT.isDown()){
				ColorState clst = new ColorState();
				colorType.add(clst);
				LCD.drawString("Set color: "+colorType.size(), 0, 1);
			}
			if(Button.LEFT.isDown()){
				int size = colorType.size();
				ColorState handleList = colorType.get(size-1);
				ColorSensor.Color vals = cs.getColor();
				updateMin(vals, handleList);
				updateMax(vals, handleList);
				updateAvg(vals, handleList);
				handleList.rgbInfo.add(vals);
				LCD.drawString("Get info of color: "+handleList.rgbInfo.size()+" times", 0, 2);
			}
		}
		
	}
	
	public void colorChecker(ColorSensor cs){
		while(!Button.ESCAPE.isDown()){
			if(Button.LEFT.isDown()){
				LCD.clearDisplay();
				int r,g,b;
				int size = colorType.size();
				ColorSensor.Color vals = cs.getColor();
				r = vals.getRed(); g = vals.getGreen();
				b = vals.getBlue();
				for(int i = 0; i < size; ++i){
					ColorState s = colorType.get(i);
					if((r>s.rgbMin.r && g>s.rgbMin.g && b>s.rgbMin.b)&&
							(r<s.rgbMax.r && g<s.rgbMax.g && b<s.rgbMax.b)){
						LCD.drawString("This is color: "+i, 0, 1);
						break;
					}
					//add verifier avg
				}
			}
		}
		
	}
	
	public void updateMin(ColorSensor.Color c, ColorState s){
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
			if(s.rgbMin.g > c.getGreen()){
				s.rgbMin.g = c.getGreen();
			}
		}
	}
	
	public void updateMax(ColorSensor.Color c, ColorState s){
		if(s.rgbInfo.size() == 0){
			s.rgbMax.r = c.getRed();
			s.rgbMax.g = c.getGreen();
			s.rgbMax.b = c.getBlue();
		}else{
			if(s.rgbMin.r < c.getRed()){
				s.rgbMax.r = c.getRed();
			}
			if(s.rgbMin.g < c.getGreen()){
				s.rgbMax.g = c.getGreen();
			}
			if(s.rgbMin.g < c.getGreen()){
				s.rgbMax.g = c.getGreen();
			}
		}
	}
	
	public void updateAvg(ColorSensor.Color c, ColorState s){
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
	
	public void displayColor(String name, int raw, int calibreated, int line){
		LCD.drawString(name, 0, line);
		LCD.drawInt(raw, 5, 6, line);
		LCD.drawInt(calibreated, 5, 11, line);
	}
	
	public void colorSensorReader(){
		
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
					LCD.drawString("Color: ", 0, 6);
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
	
	public ColorSensor setColorSenor(){
		TextMenu modeMenu = new TextMenu(modes, 1, "Color Mode");
		int portNo = portMenu.select();
		if(portNo < 0)return null;
		ColorSensor cs = new ColorSensor(SensorPort.getInstance(portNo));
		int mode = modeMenu.select();
		if(mode < 0) return null;
		cs.setFloodlight(colors[mode]);
		LCD.clear();
		return cs;
	}
	
	public int returnColorId(ColorSensor cs){	
		ColorSensor.Color vals = cs.getColor();
		LCD.drawString("Color now: "+colorNames[vals.getColor() + 1], 0, 2);
		int id = vals.getColor();
		return id;
	}
	
	public String returnColor(ColorSensor cs){
		ColorSensor.Color vals = cs.getColor();
		return colorNames[vals.getColor() + 1];
	}
	
}

//element de table colorType
class ColorState{
	
	public RgbState rgbMin;
	public RgbState rgbMax;
	public RgbState rgbAvg;
	public ArrayList<ColorSensor.Color> rgbInfo;
	
	public ColorState(){
		rgbInfo = new ArrayList<ColorSensor.Color>();
	}
	
}


//element de table
class RgbState{
	
	public int r;
	public int g;
	public int b;
	public RgbState(int r, int g, int b){
		this.r = r;
		this.g = g;
		this.b = b;
	}
}