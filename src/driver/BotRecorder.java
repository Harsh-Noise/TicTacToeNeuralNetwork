package driver;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BotRecorder {
	int size;
	int pointer = 0;
	BufferedImage img;
	
	public BotRecorder(int size, int timer) {
		this.size = size;
		img = new BufferedImage(size, timer, BufferedImage.TYPE_INT_RGB);
	}
	
	public void record(ArrayList<Integer> list) {
		if(list.size() != size) {
			System.out.println("Err: " + list.size() + ", " + size);
			return;
		}
		for(int x = 0; x < size; x++) {
			
			
			if(list.get(x) > 0) {
				img.setRGB(x, pointer, new Color(0, (int)25.5 * list.get(x), 0).getRGB());
			}else if(list.get(x) < 0) {
				img.setRGB(x, pointer, new Color((int)25.5 * (list.get(x) * -1), 0, 0).getRGB());
			}else {
				img.setRGB(x, pointer, new Color(0, 0, 0).getRGB());
			}
		}
		
		pointer++;
		
		File outputfile = new File("botViewer.png");
	    try {
	    	ImageIO.write(img, "png", outputfile);
	    }catch(IOException e) {
	    	
	    }
	}
}
