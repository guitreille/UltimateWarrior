package Main;

import java.awt.image.BufferedImage;

//this SpriteSheet class let us  make a SpriteImage where we grab a part of a bigger image, it's used for many images in the game
public class SpriteSheet {
	private BufferedImage image;
	
	public SpriteSheet(BufferedImage image) {
		this.image = image;
	}
	
	public BufferedImage grabImage(int x, int y, int width, int height) {
		BufferedImage img = image.getSubimage(x , y, width, height) ;
		return img;
	}

}
