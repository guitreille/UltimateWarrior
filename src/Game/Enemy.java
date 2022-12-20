package Game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import Main.BufferedImageLoader;
import Main.SpriteSheet;

public class Enemy extends GameObject{
	private BufferedImage enemyImg;
	private  int limitYMin, limitYMax;
	public boolean playAnimation;
	private double randomSpeed;
	private EnemyType enemytype;
	private String actualImage;

	
	public Enemy(double x, double y, EnemyType enemytype, int beginHP) {
		super(x, y);
		this.enemytype = enemytype;
		ImageInit();
		vy = 1;
		HP = beginHP;
	
	}
	

	@Override
	void ImageInit() {
		loader = new BufferedImageLoader();
		if (enemytype == EnemyType.DRAGON) {
			spriteSheet = WarriorPanel.dragonImg;
			ss = new SpriteSheet(spriteSheet);
			enemyImg = ss.grabImage(723, 1005, 57, 50);
			syncDimensions();
			actualImage = "normal";
		}
		
		if (enemytype == EnemyType.SOLDIER) {
			spriteSheet = WarriorPanel.soldierImg;
			ss = new SpriteSheet(spriteSheet);
			enemyImg = ss.grabImage(3, 5, 55, 80);
			syncDimensions();
			actualImage = "normal";
		}
		
		if (enemytype == EnemyType.REDSKULL) {
			spriteSheet = WarriorPanel.redskullImg;
			ss = new SpriteSheet(spriteSheet);
			enemyImg = ss.grabImage(210, 5, 125, 230);
			syncDimensions();
			actualImage = "normal";
		}
		
		if (enemytype == EnemyType.IRONCLAD) {
			spriteSheet = WarriorPanel.ironcladImg;
			ss = new SpriteSheet(spriteSheet);
			enemyImg = ss.grabImage(200, 5, 197, 250);
			syncDimensions();
			actualImage = "normal";
		}
		

		if (enemytype == EnemyType.SUPERBUU) {
			spriteSheet = WarriorPanel.superbuuImg;
			ss = new SpriteSheet(spriteSheet);
			enemyImg = ss.grabImage(70, 25, 72, 115);
			syncDimensions();
			actualImage = "normal";
		}
		
		if (enemytype == EnemyType.BIGBOSS) {
			spriteSheet = WarriorPanel.bigBossImg;
			ss = new SpriteSheet(spriteSheet);
			enemyImg = ss.grabImage(1750, 0, 440, 650);
			syncDimensions();
			actualImage = "normal";
		}
	
		playAnimation = false;	
	}

	private void syncDimensions() {
		this.width = enemyImg.getWidth(null);
		this.heigth = enemyImg.getHeight(null);
	}
	@Override
	void drawMe(Graphics g) {
		//g.setColor(Color.RED);
		//g.fillRect((int)x, (int)y, width, heigth);

		g.drawImage(enemyImg, (int)x, (int)y, null);
		
	}


	@Override
	void UpdateMe() {
		x += vx;
		if (playAnimation) {
			if (y >= limitYMax) {
				vy = - randomSpeed;
			}
			if (y <= limitYMin) {
				vy = randomSpeed;
			}
		
			y += vy;
		}
	
	}
	
	public void Move(int vx, int vy) {

	}
	
	public void setRandomLocation(int minimumX, int maximumX, int minimumY, int maximumY ) {
		Random randX = new Random();
		Random randY = new Random();
		x = randX.nextInt(maximumX - minimumX) + minimumX;
		y = randY.nextInt(maximumY - minimumY) + minimumY;

	
		
	}
	public void setRandomLocation(int minimumX, int maximumX ) {
		Random randX = new Random();
		x = randX.nextInt(maximumX - minimumX) + minimumX;
			
	}
	
	public void setRandomAnimation() {
		Random rand = new Random();
		int hasard = rand.nextInt(2) ;
		if (hasard == 1) {
			playAnimation = true;
			limitYMin = (int)y - rand.nextInt(100) + 60;
			limitYMax = (int)y + rand.nextInt(100) + 60;	
			randomSpeed = rand.nextInt(5)/5 + 0.5;
		}
		
		
	}
	
	public void dragonAnimation(int minY, int maxY) {}

	@Override
	NameObject getName() {
		return NameObject.ENEMY;
	}
	
	public EnemyType getType() {
		return enemytype;
	}

	
	public Boolean isMoving(){
		return playAnimation;
	}
	
	public int getlimitYMin(){
		return limitYMin;
	}
	
	public int getlimitYMax(){
		return limitYMax;
	}
	
	public double getrandomSpeed(){
		return randomSpeed;
	}


	@Override
	void imageChange(String action) {
		if (action == "shoot") {
			if (enemytype == EnemyType.SOLDIER)
				enemyImg = ss.grabImage(58, 5, 75, 80);
			
			if (enemytype == EnemyType.REDSKULL)
				enemyImg = ss.grabImage(5, 5, 200, 230);
			if (enemytype == EnemyType.IRONCLAD)
				enemyImg = ss.grabImage(0, 315, 197, 230);
			if (enemytype == EnemyType.SUPERBUU)
				enemyImg = ss.grabImage(0, 0, 70, 115);
			if (enemytype == EnemyType.BIGBOSS)
				enemyImg = ss.grabImage(600, 750, 490, 650);
		
			actualImage = "shoot";	
		}
		
		if (action == "normal") {
			if (enemytype == EnemyType.SOLDIER)
				enemyImg = ss.grabImage(3, 5, 55, 80);
			if (enemytype == EnemyType.REDSKULL)
				enemyImg = ss.grabImage(210, 5, 125, 230);
			if (enemytype == EnemyType.IRONCLAD)
				enemyImg = ss.grabImage(200, 5, 197, 250);
			if (enemytype == EnemyType.SUPERBUU)
				enemyImg = ss.grabImage(70, 25, 72, 115);
			if (enemytype == EnemyType.BIGBOSS)
				enemyImg = ss.grabImage(1750, 0, 440, 650);
			
			actualImage = "normal";
		}
		
		if (action == "bigBossWait") {
			enemyImg = ss.grabImage(700, 0, 460, 650);
			actualImage = "bigBossWait";
		}
		
		if (action == "bigBossPunch") {
			enemyImg = ss.grabImage(1650, 1430, 550, 650);
			actualImage = action;
		}
		syncDimensions();
		
	}
	
	public String getActualImage() {
		return actualImage;
	}


}