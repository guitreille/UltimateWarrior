package Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Main.BufferedImageLoader;

public class Flag extends GameObject{
	BufferedImage flagImg;
	
	public Flag(double x, double y) {
		super(x, y);
		ImageInit();
	}
	
	@Override
	void ImageInit() {	
		loader = new BufferedImageLoader();
		flagImg = loader.loadImage("/Images/flag.png");
	}

	@Override
	void drawMe(Graphics g) {
		g.drawImage(flagImg, (int)x, (int)y, null);
	}

	@Override
	void UpdateMe() {
		x += vx;
	}
	
	
	@Override
	NameObject getName() {
		return name;
	}



	@Override
	void imageChange(String action) {		
	}
	
	
}
