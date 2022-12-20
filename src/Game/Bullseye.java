package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import Main.BufferedImageLoader;


public class Bullseye {
	public int x, y;
	private int width = 15;
	private int heigth = 15;
	private BufferedImageLoader loader;
	protected BufferedImage bullseyeImg = null;
	
	public Bullseye() {
		loader = new BufferedImageLoader();
		bullseyeImg = (loader.loadImage("/Images/BullsEye.png"));

		this.width = bullseyeImg.getWidth(null);
		this.heigth = bullseyeImg.getHeight(null);
	
	}
	
	public void DrawBullseye(Graphics g) {
		g.setColor(Color.darkGray);
		g.fillOval(x, y, width, heigth);
		g.drawImage(bullseyeImg, (int)x, (int)y, null);
	}

}
