package Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Main.BufferedImageLoader;

public class Heart extends GameObject {
	protected BufferedImage heartImg = null;
	
	public Heart(double x, double y) {
		super(x, y);
		ImageInit();
	}

	@Override
	void drawMe(Graphics g) {
		g.drawImage(heartImg, (int)x, (int)y, null);
	}

	@Override
	void UpdateMe() {
	}

	@Override
	NameObject getName() {
		return NameObject.HEART;
	}

	@Override
	void ImageInit() {
		loader = new BufferedImageLoader();
		heartImg = (loader.loadImage("/Images/heart.png"));

		syncDimensions(heartImg);
	}

	@Override
	void imageChange(String action) {
		// TODO Auto-generated method stub
		
	}
}
