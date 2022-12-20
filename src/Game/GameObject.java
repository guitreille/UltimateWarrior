package Game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Main.BufferedImageLoader;
import Main.SpriteSheet;

public abstract class GameObject {
	protected double x, y, vx, vy;
	protected int width, heigth ;
	protected NameObject name;
	protected BufferedImage spriteSheet = null;
	protected BufferedImageLoader loader ;
	protected SpriteSheet ss;
	protected int HP ;
	
	public GameObject(double x2, double y2) {
		this.x = x2;
		this.y = y2 ;
	}
	
	//FOR GAMEOBJECTS
	public Rectangle DrawRectangle(){
		return new Rectangle((int)x,(int)y,width,heigth);
		}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getVx() {
		return vx;
	}
	
	public double getVy() {
		return vy;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeigth() {
		return heigth;
	}
	
	public void setVx(double vx) {
		this.vx = vx;
	}
	
	public void setVy(double vy) {
		this.vy = vx;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.heigth = height;
	}
	
	
	protected void syncDimensions(BufferedImage image){
		this.width = image.getWidth(null);
		this.heigth = image.getHeight(null);
	}
	
	
	
	public boolean IsInScreen(int gamewidth, int gameheight){
		if ((x <= gamewidth) && (x >= 0) && (y <= gameheight) && (y >= 0)) {
			return true;
		} else {
			return false;
		}
	}
	
	public int getHP(){
		return HP;
	}
	
	public void setHP(int newHP) {
		HP = newHP;
	}
	

	abstract void drawMe(Graphics g);
	abstract void UpdateMe();
	abstract NameObject getName();
	abstract void ImageInit();
	abstract void imageChange(String action);
	

}