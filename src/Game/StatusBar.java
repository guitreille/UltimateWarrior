package Game;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import Main.BufferedImageLoader;

public class StatusBar extends GameObject{
	private BufferedImage moneybagImg;
	private BufferedImage coinImg;
	
	
	public StatusBar(double x, double y, int money) {
		super(x, y);
		ImageInit();
		
	
	}
	
	@Override
	void ImageInit() {	
		loader = new BufferedImageLoader();
		moneybagImg = loader.loadImage("/Images/moneybag.png");
		coinImg = loader.loadImage("/Images/statusbarcoin.png");

	}

	@Override
	void drawMe(Graphics g) {
		g.drawImage(moneybagImg, (int)x, (int)y, null);
		g.drawImage(coinImg, (int)x + 150, (int)y + 10, null);
	}

	@Override
	void UpdateMe() {}
	
	
	@Override
	NameObject getName() {return null;}

	@Override
	void imageChange(String action) {		
	}
	
	
}
