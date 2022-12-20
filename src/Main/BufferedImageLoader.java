package Main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

//this litle class with only one method is inspired by video that we found on youtube, this let us very quickly load every
// BufferedImage 

public class BufferedImageLoader {
	private BufferedImage image;
	
	public BufferedImageLoader(){}
	
	public BufferedImage loadImage(String file) {
		try { image = ImageIO.read(getClass().getResourceAsStream(file)); }
		catch (IOException e) { e.printStackTrace(); }	
		
		return image;
	}
	
}
