package Game;

import java.awt.Graphics;

import java.awt.image.BufferedImage;
import Main.BufferedImageLoader;
import Main.Parameters;
import Main.SpriteSheet;

public class Player extends GameObject {
	
	private int character;
	private int gamewidth;
	public int beginHP = Parameters.playerbeginHP;
	public int HP;
	public int life;
	
	//move
	public boolean canmove ;
	private boolean isMoving;
	private int moveSpeed = Parameters.playerMoveSpeed;
	
	//jump
	public Boolean canJump;
	private double t;
	private double endJumpY;
	private boolean isJumping;
	private double jumpSpeed = Parameters.playerJumpHeight;
	private int maxJumpX;
	
	//images
	private BufferedImage spriteSheetRev = null;
	private BufferedImage playerImg ;
	SpriteSheet ssrev;

	
	public Player(double x, double y, int gamewidth, int character, int maxJumpX) {
		super(x, y);
		this.width = 70;
		this.heigth = 110;
		canmove = true;	
		vx = 0;
		vy = 0;
		HP = beginHP;
		isMoving = false;
		this.gamewidth = gamewidth;
		life = 3;
		canJump = true;
		this.character = character;
		ImageInit();
		this.maxJumpX = maxJumpX;
	}
	
	@Override
	void ImageInit() {
		loader = new BufferedImageLoader();
		if (character == 1) {
			spriteSheet = loader.loadImage("/Images/goku.png");
			spriteSheetRev = loader.loadImage("/Images/gokurev.png");
		}
		if (character == 2) {
			spriteSheet = loader.loadImage("/Images/dbz.gif");
		}
		
		ss = new SpriteSheet(spriteSheet);
		ssrev = new SpriteSheet(spriteSheetRev);
		if (character ==  1) {
			playerImg = ss.grabImage(5, 25, 70, 105);
		}
		
		if (character ==  2) {
			playerImg = ss.grabImage(825, 5, 85, 150);
		}
		syncDimensions(playerImg);
		
	}
	




	public void moveLeft() {
		if (canmove == true) {vx = -moveSpeed;}
		imageChange("moveleft");
		isMoving = true;
	}

	public void moveRight() {
		if (canmove == true) {vx = moveSpeed;}
		imageChange("moveright");
		isMoving = true;
	}
			
	
	public void moveStop() {
		vx = 0;
		isMoving = false;
	}

	public void animationStop(){
		imageChange("normal");
	}
	
	@Override
	void drawMe(Graphics g) {
		g.drawImage(playerImg, (int)x, (int)y, null);
		
	}

	

	@Override
	void UpdateMe() {
		x += vx;
		
		if (x < 0) {
			vx = 0;
			x = 0;
		}
		
		if (x > gamewidth - width) {
			vx = 0;
			//x = 550;
		}
		
		if (isJumping && x < maxJumpX) {
			if (y < endJumpY){
				vy = -(-(9.81)*t + jumpSpeed);
				t += 0.025;

			}
			else{
				isJumping = false;
				vy = 0;
				y = endJumpY;
			}
			
		}
		y += vy;
		
		
	}
	
	NameObject getName() {
		return NameObject.PLAYER;
	}

	public int getHP() {
		return HP;
	}
	
	public  void setHP(int newHP) {
		HP = newHP;
	}
	
	public int getLife() {
		return life;
	}
	
	public  void setLife() {
		life -= 1;
	}
	
	public void setEndJumpY(double y) {
		endJumpY = y;
	}
	
	public void setIsJumping(boolean isj) {
		isJumping = isj;
	}


	
	public void throwAnimation(){
		if (!isMoving) {
		
			imageChange("throw");
		}
		
	}
	
	public void jump(double endjumpy) {
		if (canJump) {
		this.endJumpY = endjumpy;
		isJumping = true;
		y = y - 2;
		t = 0.01;
		imageChange("jump");
		}
	}
	
	public Boolean isJumping(){
		return isJumping;
	}
	
	public int getBeginHP() {
		return beginHP;
	}
	



	@Override
	void imageChange(String action) {
		if (action == "normal") {
			if (character == 1) 
				playerImg = ss.grabImage(0, 25, 70, 110);

			if (character == 2)
				playerImg = ss.grabImage(825, 5, 85, 150);	
		}
		
		if (action == "throw") {
			if (character == 1)
				playerImg = ss.grabImage(105, 835, 70, 110);
			if(character == 2) 
				playerImg = ss.grabImage(1044, 1035, 85, 155);
		}
		
		if (action == "moveleft") {
			if (character == 1)
				playerImg = ssrev.grabImage(650, 40,  110, 110);
			if (character == 2)
				playerImg = ss.grabImage(720, 1060, 95, 142);
		}
		if (action == "moveright") {
			if (character ==1) 
				playerImg = ss.grabImage(310, 40, 110, 110);
			
			if (character == 2) 
				playerImg = ss.grabImage(825, 1060, 95, 142);
		}
		if (action == "jump") {
			if (character == 1) 
				playerImg = ss.grabImage(13, 1360, 85, 110);
			if (character == 2)
				playerImg = ss.grabImage(825, 900, 95, 142);
		}
		syncDimensions(playerImg);
	}
	
	
	public void setCanMove(boolean b) {
		canmove = b;
	}

	public void setCanJump(boolean b) {
		canJump = b;
	}
	
	public Boolean isMoving() {
		return isMoving;
	}

}