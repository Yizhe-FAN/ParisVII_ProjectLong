package move;

import java.io.*;
import java.util.*;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;

public class FileHandler {
	
	FileHandler(){
	
	}
	
	public void writeInFile(){
		try{
			File file =new File("ListBackUp.txt");
			if(!file.exists()){
				file.createNewFile();
				LCD.drawString("File is not exists!", 0, 6);
			}
			OutputStreamWriter r = new OutputStreamWriter(new FileOutputStream(file));
			BufferedWriter bw = new BufferedWriter(r);
			for(int i = 0; i < ControlColorSensor.colorTypeList.size(); ++i){
				String content = "";
				ColorState c = (ColorState)ControlColorSensor.colorTypeList.get(i);
				content+="i";
				content+= format(c.rgbMin.r);
				content+= format(c.rgbMin.g);
				content+= format(c.rgbMin.b)+"a";
				content+= format(c.rgbMax.r);
				content+= format(c.rgbMax.g);
				content+= format(c.rgbMax.b)+"v";
				content+= format(c.rgbAvg.r);
				content+= format(c.rgbAvg.g);
				content+= format(c.rgbAvg.b)+"\n";
				bw.write(content);
			}
			bw.close();
			r.close();
		}catch(IOException e){
			 e.printStackTrace();
		}
	}
	
	public void readInList(String fileP){
		new ControlColorSensor();
		try{
			File file =new File("ListBackUp.txt");
			if(!file.exists()){
				LCD.drawString("File is not exists!", 0, 6);
			}else{
				InputStreamReader r = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(r);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                	ColorState cs = new ColorState();
                	char tmpArray[] = lineTxt.toCharArray();
                	int i = 0, size = ControlColorSensor.colorTypeList.size();
                	while(i != tmpArray.length-1){
                		String tmp;
                		if(tmpArray[i]=='i'){
                			tmp="";
                			tmp += tmpArray[i+1] + tmpArray[i+2] + tmpArray[i+3];
                			cs.rgbMin.r = (int)Integer.valueOf(tmp);
                			tmp="";
                			tmp += tmpArray[i+4] + tmpArray[i+5] + tmpArray[i+6];
                			cs.rgbMin.g = (int)Integer.valueOf(tmp);
                			tmp="";
                			tmp += tmpArray[i+7] + tmpArray[i+8] + tmpArray[i+9];
                			cs.rgbMin.b = (int)Integer.valueOf(tmp);
                			i += 10;
                		}else if(tmpArray[i]=='a'){
                			tmp="";
                			tmp += tmpArray[i+1] + tmpArray[i+2] + tmpArray[i+3];
                			cs.rgbMax.r = (int)Integer.valueOf(tmp);
                			tmp="";
                			tmp += tmpArray[i+4] + tmpArray[i+5] + tmpArray[i+6];
                			cs.rgbMax.g = (int)Integer.valueOf(tmp);
                			tmp="";
                			tmp += tmpArray[i+7] + tmpArray[i+8] + tmpArray[i+9];
                			cs.rgbMax.b = (int)Integer.valueOf(tmp);
                			i += 10;
                		}else if(tmpArray[i]=='v'){
                			tmp="";
                			tmp += tmpArray[i+1] + tmpArray[i+2] + tmpArray[i+3];
                			cs.rgbAvg.r = (int)Integer.valueOf(tmp);
                			tmp="";
                			tmp += tmpArray[i+4] + tmpArray[i+5] + tmpArray[i+6];
                			cs.rgbAvg.g = (int)Integer.valueOf(tmp);
                			tmp="";
                			tmp += tmpArray[i+7] + tmpArray[i+8] + tmpArray[i+9];
                			cs.rgbAvg.b = (int)Integer.valueOf(tmp);
                			i += 10;
                		}
                	}
                	ControlColorSensor.colorTypeList.add(cs);
                }
                bufferedReader.close();
                r.close();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	
	}
	
	public String format(int r){
		String s="";
		if(r > 10 && r < 100){
			return s+"0"+r;
		}else if(r < 10){
			return s+"00"+r;
		}
		return s+r;
	}
	
}


