package Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Coin extends GameObject{
	BufferedImage coinImg;
	
	public Coin(double x, double y, BufferedImage coinImg) {
		super(x, y);
		this.coinImg = coinImg;
	}

	@Override
	void drawMe(Graphics g) {
		g.drawImage(coinImg, (int)x, (int)y, null);
	}

	@Override
	void UpdateMe() {
		x += vx;
	}
	
	
	public void setRandomLocation(int minimumX, int maximumX) {
		Random randX = new Random();
		x = randX.nextInt(maximumX - minimumX) + minimumX;		
	}

	public void setY(double y) {
		this.y = y;
	}
	
	
	
	@Override
	NameObject getName() {
		return NameObject.COIN;
	}

	@Override
	void ImageInit() {		
	}

	@Override
	void imageChange(String action) {		
	}
	
	
}
