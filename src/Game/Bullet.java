package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Bullet extends GameObject {
	
	//general : 
	private BulletType bullettype ; 
	private double beginx, beginy, endx, endy;
	private Boolean canshoot ;
	
	//Image
	private BufferedImage bulletImg ;   //contains the Image
	
	//parameters of the standard Bullet
	private int stdBulletSpeed = 11;;
	
	
	//parameters of the enemy Bullet
	private int EBulletSpeed = 5;
	public  int bulletDamage ;
	public Color bulletColor;
	

	public Bullet(double x, double y, int width, int height, BulletType bullettype, int bulletDamage, Color bulletColor) {
		super(x, y);
		
		//initialize the parameters: 
		this.width = width;
		this.heigth = height;
		this.bullettype = bullettype;
		
		//the boolean canshoot is initially set on false, the WarriorPanel controls when to shoot
		canshoot = false;
		
		//to only work with one parameter 'stdBulletSpeed', we need to change the value of it if we want to give 
		// another speed to the bullet. That depends on the type of the bullet.
		
		if (bullettype == BulletType.ENNEMYBULLET) {
			stdBulletSpeed = EBulletSpeed;
		}
		
		if (bullettype == BulletType.STANDARD)
			bulletImg = WarriorPanel.stdBulletImg;
		
		this.bulletDamage = bulletDamage;
		this.bulletColor = bulletColor ;
	}
	
	//We don't use a ImageInit() for a bullet
	@Override
	void ImageInit() {}


	public void InitShoot(double x, double y, int eindx, int eindy) {
		this.beginx = x;
		this.beginy = y ;
		this.endx = eindx;
		this.endy = eindy;
		canshoot = true ;	
	}
	
	@Override
	void UpdateMe() {
		if (canshoot) {
			if (endx == beginx) {
				endx += 1;
			}
			double rico = (endy - beginy) /(endx - beginx) ;
			double hoek = Math.atan(rico);
			
			y  =    (rico*(x - beginx) + beginy);
			vx = Math.cos(hoek)*stdBulletSpeed;
			vy = Math.sin(hoek)*stdBulletSpeed;

			if (endx < beginx) {
				vx = -vx ;
			}
			if (endy > beginy) {
				vy = -vy ;
			}
			x += vx;
			y += vy;
		}
		
		
	}
	
	
	NameObject getName() {
		switch (bullettype) {
		case ENNEMYBULLET: 
			return NameObject.ENEMYBULLET;
	
		default :  
			return NameObject.STANDARDBULLET;
		
		}	
	}

	public BulletType getBulletType() { return bullettype; }
	
	
	@Override
	void drawMe(Graphics g) {
		switch (bullettype) {
		case STANDARD:
			g.drawImage(bulletImg, (int)x, (int)y, null);
		
		
		case ENNEMYBULLET:
			g.setColor(bulletColor);
			g.fillOval((int)x, (int)y, width, heigth);
		}
	}

	@Override
	void imageChange(String action) {}

	public void setColor(Color color) {
		bulletColor = color;
	}
	

	
}