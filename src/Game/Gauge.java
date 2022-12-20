package Game;

import java.awt.Color;
import java.awt.Graphics;

public class Gauge extends GameObject{
	public int currentHP;
	private int maxHP;
	private boolean isMoving;
	private int limitYMin;
	private int limitYMax;
	private double randomspeed;
	

	public Gauge(double x2, double y2, int width, int heigth, int maxHP) {
		super(x2, y2);
		this.width = width;
		this.heigth = heigth;
		isMoving = false;
		this.maxHP = maxHP;
	}

	@Override
	void drawMe(Graphics g) {
		if (currentHP <= maxHP/4) {
			g.setColor(Color.RED);
		}
		
		if (currentHP > maxHP/4 & currentHP <= 2*maxHP/3) {
			g.setColor(Color.ORANGE);
		}
		if (currentHP > 2*maxHP/3) {
			g.setColor(Color.GREEN);
		}
		g.fillRect((int)x, (int)y, (width/5)*currentHP, heigth);
		g.setColor(Color.BLACK);
		g.drawRect((int)x, (int)y, (width/5)*maxHP, heigth);
		
		
	}

	@Override
	void UpdateMe() {
		x += vx;
		if (isMoving) {
			if (y >= limitYMax) {
				vy = - randomspeed;
			}
			if (y <= limitYMin) {
				vy =  randomspeed;
			}
		
			y += vy;
		}
		}

	@Override
	NameObject getName() {
		return NameObject.GAUGE;
	}
	
	public void setCurrentHP(int hp) {
		currentHP = hp;
	}

	@Override
	void ImageInit() {
	}
	
	public void SetIsMoving(Boolean moving) {
		isMoving = moving;
	}
	
	public void setMove(int limitYMin, int limitYMax, double randomSpeed2){
		this.limitYMin = limitYMin;
		this.limitYMax = limitYMax;
		this.randomspeed = randomSpeed2;	
	}

	@Override
	void imageChange(String action) {	
	}
	

	
	
}
