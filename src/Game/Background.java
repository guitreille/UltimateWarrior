package Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Main.BufferedImageLoader;

public class Background extends GameObject{
	private BufferedImage backgroundImg;  		//contains the Image
	private BufferedImageLoader loader ;   	//loader to load the image (package Main/BufferedImageLoader)
 	
	//to know if the image still can move  right
	public boolean canMoveRight;
	
	//every background has another Y-value of the "ground", this is the initial Y-value of the player and the soldiers 
	int groundHeight;
	
	//the background may be different due to the selected level, this int contains the level of the game;
	int level;
	
	
	
	public Background(double x2, double y2, int level )  {
		super(x2, y2);
		
		//move : 
		vx = 0;  			//the speed to let the background move when pressing on the left or right key 
		canMoveRight = true; //the background can only move (to the left) when the player moves to the right, we need this 
							// boolean variable to control that;
		this.level = level;
		ImageInit();   
	}
	
	
	@Override
	void ImageInit() {
		//this function loads the correct Img, it depends on the selected level 
		
		loader = new BufferedImageLoader();
		
		if (level >= 1 && level <= 3) {
			backgroundImg = loader.loadImage("/Images/gamebackground.jpg");	
			groundHeight = 50 ;
		}
		if (level >= 4 && level <= 6) {
			backgroundImg = loader.loadImage("/Images/gameBgLevel2.png");	
			groundHeight = 160 ;
		}
		
		if (level >= 7 && level <= 9) {
			backgroundImg = loader.loadImage("/Images/gameBgLevel3.png");	
			groundHeight = 50 ;
		}
		if (level == 10) {
			backgroundImg = loader.loadImage("/Images/gameBgFinalLevel.gif");	
			groundHeight = 50 ;
		}
			

		//To define the background width and height, we will use it in WarriorPanel (we don't need an observer --> null)
		syncDimensions(backgroundImg);
	}

	
	//if canMoveRight is true, this function will give a value to vx to let the background move (double speed).
	public void moveRight(double speed){
		if (canMoveRight) {
			vx = -speed;
		}
	}
	
	//this function can stop the background from moving
	public void stop(){
		vx = 0;
	}
	
	//UpdateMe and drawMe functions :
	@Override
	void UpdateMe() {
		x += vx;
	}
	
	@Override
	void drawMe(Graphics g) {
		g.drawImage(backgroundImg, (int)x, (int)y, null);	
	}
	
	//these abstract functions of the superclass are not necessary in the class 'Background'
	@Override
	NameObject getName() {return null;}

	@Override
	void imageChange(String action) {}

}
