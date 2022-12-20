package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Main.BufferedImageLoader;
import Main.GameFrame;
import Main.Parameters;
import Main.PlaySound;
import Main.SpriteSheet;
import MenuItems.Level;

public class WarriorPanel extends JPanel implements MouseMotionListener, MouseListener, KeyListener, ActionListener {
	private GameFrame gameframe;
	public static WarriorPanel _this;
	
	//Player :
	public  Player man;
	public  boolean playerIsOnBlock;
	private LinkedList<GameObject> toRemoveFromPlayer = new LinkedList<GameObject>() ;
	private int character = Parameters.character;
	
	//Enemy
	private int numberOfDragons = 10; 
	private int dragonMarge = 500;
	private int soldierMarge = 500;
	private int numberOfSoldiers = 10; ;
	private int randomAttackTime ;
	private int randomMaximum = 5;
	private LinkedList<Enemy> enemies = new LinkedList<Enemy>() ;
	private long dragonstarttime;
	private long bigbossStartTime;
	public static BufferedImage dragonImg;
	public static BufferedImage soldierImg;
	public static BufferedImage redskullImg;
	public static BufferedImage ironcladImg;
	public static BufferedImage superbuuImg;
	public static BufferedImage bigBossImg;
	private Boolean hasRedSkull;
	private int redSkullX;
	private Boolean hasIronClad;
	private int numberOfIronClads;
	private int ironCladMarge;
	private Boolean hasSuperBuu;
	private int numberOfSuperBuu;
	private int superBuuMarge;
	
	
	//bullets
	private int standardBulletSize = 15;
	private int enemyBulletSize = 15; 
	private int soldiershoot;
	public static  BufferedImage stdBulletImg;
	private int remainingBullets;

	
	//coins
	private int numberOfCoins ;
	private BufferedImage coinImg;
	private int coinMarge;
	private  LinkedList<Coin> coins = new LinkedList<Coin>() ;
	
		
	//Gauge
	private Gauge gauge;
	private double gaugeX = 10;
	private double gaugeY = 10;
	private int gaugeWidth ;
	private int gaugeHeight;
	private LinkedList<Gauge> gaugeList= new LinkedList<Gauge>() ;
	
	
	//blocks
	private Block blockOne;
	private int blockY;
	private double blockCoordinateVx;
	public  LinkedList<Block> blockList= new LinkedList<Block>() ;
	private LinkedList<Double> blockCoordinates = new LinkedList<Double>() ;
	private LinkedList<Double> specialBlockCoordinates = new LinkedList<Double>() ;
	private LinkedList<Integer> isSpecialBlock  = new LinkedList<Integer>();


	//Images & gifs
	private Background bg ;
	private BufferedImageLoader loader ;
	private int nbrBackground ;
	private double bgMoveSpeed = 2.0;
	private LinkedList<Background> backgrounds = new LinkedList<Background>() ;

	
	//Timer of the game :
	private long start;
	private final int timerInterval = 10 ;
	Timer tmrmove = new Timer();
	
	//Bullseye to know where the mouse at
	private Bullseye eye = new Bullseye();
	
	//hearts 
	private int beginNbrHearts = Parameters.beginNbrHearts;
	private LinkedList<GameObject> hearts = new LinkedList<GameObject>() ;
	
	//These lists keeps witch objects are actually in the game and witch bullets are in the screen,  witch objects to move
	//with the keyListener
	public  LinkedList<GameObject> objects = new LinkedList<GameObject>() ;
	private LinkedList<Bullet> bulletsInScreen = new LinkedList<Bullet>() ;
	private LinkedList<GameObject> objectsToMove = new LinkedList<GameObject>() ;
	private LinkedList<GameObject> toRemove = new LinkedList<GameObject>() ;

	//random
	private Random rand = new Random();
	
	//level
	private int selectedLevel ;
	private int endLevelX ;
	
	//flag
	private Flag endFlag;

	//statusbar
	StatusBar statusBar;
	JLabel bulletLabel ;
	JLabel moneyLabel ; 
	
	//sounds & music : 
	PlaySound playerThrowSound = new PlaySound("throw.wav", false);
	PlaySound playerAieSound = new PlaySound("aie.wav", false);
	PlaySound playerJumpSound = new PlaySound("jump.wav", false);
	PlaySound playerHitSound = new PlaySound("playerHit.wav", false);
	PlaySound soldierShootSound = new PlaySound("soldiershoot.wav", false);
	PlaySound redSkullShootSound = new PlaySound("redskullShoot.wav", false);
	PlaySound dragonShootSound = new PlaySound("dragonshoot.wav", false);
	PlaySound ironCladShootSound = new PlaySound("ironcladshoot.wav", false);
	PlaySound bigBossShootSound = new PlaySound("bigBossShoot.wav", false);
	PlaySound coinSound = new PlaySound("coin.wav", false);
	PlaySound lastLevelSound;
	PlaySound youLostSound;
	PlaySound youWinSound;
	
	//endPanel
	JButton playAgainButton;
	JButton menuButton;
	Boolean ended ;
	private int endStatus;
	
	public WarriorPanel(GameFrame gameframe, int level) {
		_this = this;
		this.gameframe = gameframe ;
		this.setLayout(null);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		imageLoader();
		
		selectedLevel = level;

		initLevel(selectedLevel);
		initBackground();
		initPlayer();
		initStatusBar();
		initBlock();
		initEnemy();
		initGauge();
		initHearts();
		initCoins();

		//starts the timer with as interval the final int  'timerInterval' 
		tmrmove.schedule(new MyTask(), 0, timerInterval);
		
		//this will keep the starttime of the game
		start = System.nanoTime();
		ended = false;
		
	
	
	}


	private void initStatusBar() {
		remainingBullets = Parameters.beginNbrBullets;
		statusBar = new StatusBar(gameframe.gamewidth - 250, 0, 10);
		objects.add(statusBar);
		moneyLabel = new JLabel("" + Parameters.money + " x" );
		setLabel(moneyLabel, 24, (int)statusBar.getX() - 80, (int) statusBar.getY() + 10, 70, 70, Color.BLACK);
		bulletLabel = new JLabel("" + remainingBullets + " x" );
		setLabel(bulletLabel, 25, (int)statusBar.getX() + 80, (int) statusBar.getY() + 10, 70, 70, Color.BLACK);
	}
	
	
	private void addBlocks(double firstX, int number){
		for (int i = 0; i < number ; i++) {
			blockCoordinates.add(firstX + i*(50.0));
			if (i == 0) {
				isSpecialBlock.add(1);
				specialBlockCoordinates.add(firstX + i*(50.0));
			}
			else if (i == number - 1) {
				isSpecialBlock.add(2);
				specialBlockCoordinates.add(firstX + i*(50.0));
			}
			else 
				isSpecialBlock.add(0);
		}
	}
	private void initLevel(int level) {

		//first of all, we need to initialize  the level, every level has another number of enemies (with different marges), 
		// has other blockcoordinates and another amount of coins to get
		if (level == 1) {
			numberOfDragons = 3; ;
			dragonMarge = 1000;
			numberOfSoldiers = 2; ;
			soldierMarge = 2500;
			
			//how many backgrounds to draw, this has influence on the endX-value of the level
			nbrBackground = 2;
			
			//for the blocks we are working with 3 different lists. blockCoordinates contains the initial coordinates of all
			// blocks, to know where to draw them, specialBlockCoordinates contains the coordinates of the "special" blocks
			// A block is consider "special" when it's the first or one of a row. the "isSpecialBlock"-list contains the numbers
			// 0 for a normal block, 1 for the first block in a row and 2 for the last one
			//  This is necessary in the function "intersect" and "blockSync" for the gravity-effects.
			addBlocks(2500.0, 5);
			
			numberOfCoins = 10;
			coinMarge = 230;
			
			hasRedSkull = false;
			hasIronClad = false;
			hasSuperBuu = false;
			
		}
		
		
		if (level == 2) {
			numberOfDragons = 6; ;
			dragonMarge = 800;
			numberOfSoldiers = 7; ;
			soldierMarge = 900;
		
			//how many backgrounds to draw, this has influence on the endX-value of the level
			nbrBackground = 3;
			
			addBlocks(500.0, 3);
			addBlocks(1500.0, 7);
			addBlocks(2500.0, 4);
			
			
			numberOfCoins = 15;
			coinMarge = 330;
			
			hasRedSkull = false;
			hasIronClad = false;
			hasSuperBuu = false;
		}
		
		if (level == 3) {
			numberOfDragons = 10; ;
			dragonMarge = 600;
			numberOfSoldiers = 10; ;
			soldierMarge = 500;
			
			//how many backgrounds to draw, this has influence on the endX-value of the level
			nbrBackground = 4;
			

			addBlocks(200.0, 6);
			addBlocks(1900.0, 7);
			addBlocks(2800.0, 4);
			addBlocks(3500.0, 8);
			addBlocks(4400.0, 3);
			addBlocks(5100.0, 3);
			
			numberOfCoins = 17;
			coinMarge = 600;
			
			hasRedSkull = true;
			redSkullX = 5600;
			
			hasIronClad = false;
			hasSuperBuu = false;

		}
		
		if (level == 4) {
			numberOfDragons = 8; ;
			dragonMarge = 450;
			numberOfSoldiers = 8; ;
			soldierMarge = 400;

			nbrBackground = 1;
			
			addBlocks(200.0, 6);
			addBlocks(1500.0, 7);
			addBlocks(2300.0, 4);
			addBlocks(3100.0, 5);
			
			numberOfCoins = 15;
			coinMarge = 200;
			
			hasRedSkull = true;
			redSkullX = 3500;
			
			hasIronClad = false;
			hasSuperBuu = false;
		}
		
		if (level == 5) {
			numberOfDragons = 10; ;
			dragonMarge = 650;
			numberOfSoldiers = 10; ;
			soldierMarge = 700;

			nbrBackground = 2;
			
			addBlocks(200.0, 6);
			addBlocks(1900.0, 7);
			addBlocks(2800.0, 4);
			addBlocks(3500.0, 8);
			addBlocks(4400.0, 3);
			addBlocks(5100.0, 3);
			addBlocks(5600.0, 7);
			addBlocks(6200.0, 3);
			addBlocks(6500.0, 2);
			addBlocks(7000.0, 5);
			
			numberOfCoins = 20;
			coinMarge = 350;
			
			hasRedSkull = true;
			redSkullX = 7700;
			
			hasIronClad =false;
			hasSuperBuu = false;
		}
		
		if (level == 6) {
			numberOfDragons = 13; ;
			dragonMarge = 570;
			numberOfSoldiers = 10; ;
			soldierMarge = 700;

			nbrBackground = 2;
			
			addBlocks(400.0, 4);
			addBlocks(1000.0, 3);
			addBlocks(1900.0, 7);
			addBlocks(2800.0, 4);
			addBlocks(3500.0, 4);
			addBlocks(4000.0, 2);
			addBlocks(4400.0, 4);
			addBlocks(5100.0, 3);
			addBlocks(5600.0, 12);
			addBlocks(7000.0, 2);
			addBlocks(7800.0, 5);
			
			numberOfCoins = 20;
			coinMarge = 350;
			
			hasRedSkull = true;
			redSkullX = 6800;
			
			hasIronClad = true;
			numberOfIronClads = 1;
			ironCladMarge = 7000;
			
			hasSuperBuu = false;
		}
		
		if (level == 7) {
			numberOfDragons = 10; ;
			dragonMarge = 570;
			numberOfSoldiers = 10; ;
			soldierMarge = 700;

			nbrBackground = 5;
			
			addBlocks(200.0, 6);
			addBlocks(1500.0, 7);
			addBlocks(2300.0, 4);
			addBlocks(3100.0, 5);
			addBlocks(5100.0, 3);
			addBlocks(5600.0, 7);
			
			numberOfCoins = 25;
			coinMarge = 150;
			
			hasRedSkull = true;
			redSkullX = 6500;
			
			hasIronClad = false;
			
			hasSuperBuu = true;
			numberOfSuperBuu = 5;
			superBuuMarge = 1200;
		}
		
		if (level == 8) {
			numberOfDragons = 12; ;
			dragonMarge = 670;
			numberOfSoldiers = 12; ;
			soldierMarge = 700;

			nbrBackground = 6;
			

			addBlocks(400.0, 4);
			addBlocks(1000.0, 3);
			addBlocks(1900.0, 7);
			addBlocks(2800.0, 4);
			addBlocks(3500.0, 4);
			addBlocks(4000.0, 2);
			addBlocks(4400.0, 4);
			addBlocks(5100.0, 3);
			addBlocks(5600.0, 12);
			addBlocks(7000.0, 2);
			addBlocks(7800.0, 5);
			
			numberOfCoins = 25;
			coinMarge = 150;
			
			hasRedSkull = true;
			redSkullX = 7500;
			
			hasIronClad = true;
			numberOfIronClads = 2;
			ironCladMarge = 3000;
			
			hasSuperBuu = true;
			numberOfSuperBuu = 4;
			superBuuMarge = 1500;
		}
		if (level == 9) {
			numberOfDragons = 10; ;
			dragonMarge = 970;
			numberOfSoldiers = 25; ;
			soldierMarge = 300;

			nbrBackground = 7;
			
			addBlocks(200.0, 6);
			addBlocks(1900.0, 7);
			addBlocks(2800.0, 4);
			addBlocks(3500.0, 8);
			addBlocks(4400.0, 3);
			addBlocks(5100.0, 3);
			addBlocks(5600.0, 7);
			addBlocks(6200.0, 3);
			addBlocks(6500.0, 2);
			addBlocks(7000.0, 5);
			
			numberOfCoins = 25;
			coinMarge = 150;
			
			hasRedSkull = true;
			redSkullX = 8100;
			
			hasIronClad = true;
			numberOfIronClads = 4;
			ironCladMarge = 2000;
			
			hasSuperBuu = true;
			numberOfSuperBuu = 5;
			superBuuMarge = 1500;
		}
		
		if (level == 10) {
			numberOfDragons = 50; ;
			dragonMarge = 400;
			numberOfSoldiers = 28; ;
			soldierMarge = 700;

			nbrBackground = 7;
			

			addBlocks(1500.0, 7);
			addBlocks(3000.0, 4);
			addBlocks(5600.0, 17);
			addBlocks(8500.0, 7);
			addBlocks(9300.0, 20);
			addBlocks(11600.0, 17);
			addBlocks(13000.0, 7);
			addBlocks(14000.0, 20);
		
			
			numberOfCoins = 100;
			coinMarge = 100;
			
			hasRedSkull = true;
			redSkullX = 15900;
			
			hasIronClad = true;
			numberOfIronClads = 10;
			ironCladMarge = 1800;
			
			hasSuperBuu = true;
			numberOfSuperBuu = 20;
			superBuuMarge = 1000;
			
			GameFrame.backMusic.stop();
			lastLevelSound = new PlaySound("finalLevel.wav", true);
			lastLevelSound.run();
			
		}
		
		
	}
	
	private void imageLoader() {
		//we don't want bullets and coins to load a new BufferedImage every time, so we have to define them in the PLayPanel
		loader = new BufferedImageLoader();
		SpriteSheet ss = new SpriteSheet(loader.loadImage("/Images/goku.png"));
		stdBulletImg = ss.grabImage(13, 967, 23, 24);
		coinImg = loader.loadImage("/Images/coin.png");
		dragonImg = loader.loadImage("/Images/dragon.png");
		soldierImg = loader.loadImage("/Images/soldier.png");
		redskullImg = loader.loadImage("/Images/RedSkull.png");
		ironcladImg = loader.loadImage("/Images/Ironclad.png");
		superbuuImg = loader.loadImage("/Images/superBuu.png");
		bigBossImg = loader.loadImage("/Images/bigBoss.png");
	}

	
	private void initBackground() {
		
		//now that we know how many backgrounds the level contains, we can draw them
		bg = new Background(0, 0, selectedLevel);
		
		for (int i = 0; i < nbrBackground ; i++) {
			bg = new Background((bg.width)*i, 0, selectedLevel);
			int bgY = -( bg.getHeigth() - gameframe.gameheight) ; //this is to set correctly the Y-value on every screen
			bg.setY(bgY - 1);
			//we only add backgrounds to the object list and not "objectsToMove" because we want backgrounds to move 
			//in another way than objects like enemies, blocks, ...
			objects.add(bg);
			backgrounds.add(bg); 
		}
		
		endLevelX = (nbrBackground) * bg.width - 400;
		endFlag = new Flag(endLevelX, gameframe.gameheight - bg.groundHeight - 260);
		objects.add(endFlag);
		objectsToMove.add(endFlag);
		
	}
	
	private void initPlayer() {
		//after the backgrounds, we can now load the groundHieght of it and initialize the player
		
		int shifting = 0;
		if (character ==  1) {
			 shifting = 127;
		}
		
		if (character ==  2) {
			shifting = 165;
		}
		
		int manBeginY = gameframe.gameheight - bg.groundHeight - shifting;
		man = new Player(0, manBeginY, gameframe.gamewidth, character, gameframe.gamewidth);
		objects.add(man);
		
		//every time the player will be on a block, this parameter will be true
		playerIsOnBlock = false;
	}

	private void initBlock() {
		//the Y-value of a block depends on the gameheight and the groundHeight of the background
		blockY = gameframe.gameheight - bg.groundHeight - 230;
		
		//we want at least one block as general parameter in the game, with that we can get his dimensions later 
		blockOne = new Block(blockCoordinates.get(0), blockY);
		blockOne.setIsFirstBlock(true);
		objects.add(blockOne);
		objectsToMove.add(blockOne);
		blockList.add(blockOne);
		
		//for all other blocks, we use a for loop to add each one
		for (int i = 1; i < blockCoordinates.size(); i++) {
			Block block = new Block(blockCoordinates.get(i), blockY);
			objects.add(block);
			objectsToMove.add(block);
			blockList.add(block);
		
			//we have to set special blocks if their correspondent index in the isSpecialBlock-List is different than 0
			if ( isSpecialBlock.get(i) == 1) {
				block.setIsFirstBlock(true);
			}
			
			if ( isSpecialBlock.get(i) == 2) {
				block.setIsLastBlock(true);
			}
			
		}

		blockCoordinateVx = 0;
		
	}

	

	private void initHearts() {
		for (int i = 0; i < beginNbrHearts ; i++) {
			Heart heart = new Heart(gaugeX + (gaugeWidth/5)*Parameters.playerbeginHP + i*(40) + 20, 10);
			hearts.add(heart);
			objects.add(heart);
		}
		
	}

	private void initGauge() {
		//this function only initialize the gauge of the player, the other little gauges of the enemies will be initialized in
		// 'initEnemy()'
		
		gaugeWidth =   gameframe.gamewidth/7;
		gaugeHeight = gameframe.gameheight / 30;
		gauge = new Gauge(gaugeX, gaugeY, gaugeWidth, gaugeHeight, man.getBeginHP());
		gauge.setCurrentHP(man.getBeginHP()); 
		objects.add(gauge);
	}

	private void initEnemy() {
		//we have to create new dragons and soldiers apart, each one has a new gauge 
		for (int i = 0; i < numberOfDragons ; i++){
			//the location of them will be set up in "setRandomLocation", so it doesn't matter what coordinates you write 
			// in the first line
			Enemy dragon = new Enemy(0,0, EnemyType.DRAGON, 5);
			
			//the setRandomLocation function for a dragon needs 4 parameters ;
			// int minimumX, int maximumX, int minimumY and an int maximumY
			dragon.setRandomLocation(dragonMarge*i, dragonMarge*i + dragonMarge, 10, gameframe.gameheight/2);
			
			//some of them has a litle "move" animation, this can be set up with the setRandomAnimation() method :
			dragon.setRandomAnimation();
			
			//lists
			enemyListAdd(dragon);
			
			//randomAttackTime will be necessary to let the dragons shoot with random time-intervals
			randomAttackTime = rand.nextInt(randomMaximum);
			
			//gauge :
			Gauge enemygauge = addGauge(dragon.x, dragon.y, 30, 10, dragon.getHP());
			//if the dragon moves, the gauge also has to move with him
			if (dragon.isMoving() == true) {
				enemygauge.setMove(dragon.getlimitYMin(), dragon.getlimitYMax(), dragon.getrandomSpeed());
				enemygauge.SetIsMoving(true);
			}
			
		
		}
		
		
		for (int i = 0; i < numberOfSoldiers; i++) {
			//same thing for soldiers, they need a gauge and we put them in the correct lists
			Enemy soldier = new Enemy(0, gameframe.gameheight - bg.groundHeight - 95, EnemyType.SOLDIER, 5);
			soldier.setRandomLocation((soldierMarge)*i + 150, (soldierMarge)*(i+1) + 150);

			enemyListAdd(soldier);
			addGauge(soldier.x, soldier.y, 30, 10, soldier.getHP());
		}
		
		if (hasRedSkull) {	
			Enemy redskull =  new Enemy(redSkullX, gameframe.gameheight - bg.groundHeight - 235, EnemyType.REDSKULL, 10);
			enemyListAdd(redskull);
			addGauge(redskull.x, redskull.y, 60, 10, redskull.getHP());
		}
		
		if (hasIronClad) {
			for (int i = 0; i < numberOfIronClads; i++) {
				Enemy ironclad =  new Enemy(500, gameframe.gameheight - bg.groundHeight - 225, EnemyType.IRONCLAD, 20);
				ironclad.setRandomLocation((ironCladMarge)*i + 500, (ironCladMarge)*(i+1) + 500);
				enemyListAdd(ironclad);
				addGauge(ironclad.x, ironclad.y - 30, 50, 10,ironclad.getHP());
			}
		}
		
		if (hasSuperBuu) {
			for (int i = 0; i < numberOfSuperBuu; i++) {
				Enemy superbuu = new Enemy(500,gameframe.gameheight - bg.groundHeight - 130,EnemyType.SUPERBUU,2);	
				superbuu.setRandomLocation((superBuuMarge)*i + 500, (superBuuMarge)*(i+1) + 500);
				enemyListAdd(superbuu);
				addGauge(superbuu.x,  superbuu.y - 10, 40, 10,superbuu.getHP());
			}
		}
		
		//this parameter will be necessary in the attack-method
		dragonstarttime = start;
		bigbossStartTime = start;
		
		if (selectedLevel == 10) {
			Enemy bigboss = new Enemy(800,gameframe.gameheight - bg.groundHeight - 550,EnemyType.BIGBOSS,50);
			enemyListAdd(bigboss);
			addGauge(bigboss.x, bigboss.y - 10, 40, 10, bigboss.getHP());
		}
		
		
	}
	
	private void enemyListAdd(Enemy e) {
		objects.add(e);	
		objectsToMove.add(e);
		enemies.add(e);
	}
	
	private Gauge addGauge(double x, double y, int width ,int heigth, int maxHP) {
		Gauge gauge = new Gauge(x, y, width, heigth , maxHP);
		gauge.setCurrentHP(maxHP);
		objectsToMove.add(gauge);
		objects.add(gauge);
		gaugeList.add(gauge);
		return gauge;
	}

	
	private void initCoins() {
		//the money-variable keeps the money of the player, every time you shoot an enemy or you get a coin you recieve money

		//here we are creating  all the coins and set random locations to them
		Coin coin;
		for (int i =0 ; i < numberOfCoins ; i++) {
			coin = new Coin(0, gameframe.gameheight - bg.groundHeight - 95, coinImg);
			
			//the width en height of a coin are a litle bit more than the Img dimensions
			coin.setWidth(coinImg.getWidth() + 20);
			coin.setHeight(coinImg.getHeight() + 20);
			
			//lists :
			objects.add(coin);
			objectsToMove.add(coin);
			coins.add(coin);
			
			//every coin will have a random location, but if the random x-value is on the same location as a block,
			// we want the coin on it, so we change the y-value of the coin
			coin.setRandomLocation(coinMarge*i + 500, coinMarge*i + coinMarge + 500);
			for (int j = 0; j < specialBlockCoordinates.size() - 1 ; j++) {
				if (isOnBlock(coin.getX(), j))
						coin.setY(blockY - 50);
			}
		}
	}

	private Boolean isOnBlock(double objectX, int blockElementIndex){
		if (objectX > specialBlockCoordinates.get(blockElementIndex) - blockOne.getWidth() &&
				objectX < specialBlockCoordinates.get(blockElementIndex + 1) + blockOne.getWidth() && blockElementIndex % 2 == 0) {
					return true;
				}
		else 
			return false;
		
	}

	//timertask
	class MyTask extends TimerTask {
		public void run() {		
			//all gameobjects in the list have to be updated every time
			
			for(int i = 0 ; i < objects.size() ; ++i)
			{
				objects.get(i).UpdateMe();
			}
			IntersectPlayer();
			Intersect();
			EnemyAttack();
			GaugeSync();
			BlockSync();
			endControl();
			
			moneyLabel.setText("" +Parameters.money + " x");
			bulletLabel.setText("" + remainingBullets + " x");
			repaint();
			
		}
	}
	
	
	 private void IntersectPlayer()
	 {
		 for (Coin c : coins) {
				if (c.DrawRectangle().intersects(man.DrawRectangle())){
					Parameters.money += 1;
					toRemoveFromPlayer.add(c);
					coinSound.run();
				}
		 }
		 
		 for (Block b : blockList) {
			//in this case the player intersects the block but can not jump on it.
			// in other words, this is to make sure the player and the block will not be on the same location when you jump
			if (b.DrawRectangle().intersects(man.DrawRectangle()) && (man.isJumping() == true) ) {
				if (man.getX()  < b.getX() && man.getY() + man.getHeigth() > b.getY()) {
					man.setVx(0);
				}
			}
			else {
				man.setCanMove(true);
			}
			
			//in this case the player and a block intersects, but the player is above the block, so it stays on it 
			if (b.DrawRectangle().intersects(man.DrawRectangle()) && 
					man.getY() <= b.getY() && man.getX() > b.getX() - man.getWidth()  && man.getX() < (b.getX() + b.getWidth())) {
				playerIsOnBlock = true;
	
				man.setEndJumpY(b.getY() - man.getHeigth() + 5);
			}
		
		}
		  for (Enemy e : enemies) {
			  if (man.DrawRectangle().intersects(e.DrawRectangle()) & ended == false){
				  endPanel(0);
				  for (GameObject go : objectsToMove) {
						toRemove.add(go);
					}
					for (Background b : backgrounds) {
						b.stop();
						b.canMoveRight = false;
					}
					ended = true;
			  }
		  }
		 
		 
		 if(toRemoveFromPlayer.size() > 0)
			 DeletePlayer();
	 }
	 
	 private void DeletePlayer() {
			
			for (GameObject g : toRemoveFromPlayer) {
				if (g.getName() == NameObject.COIN ) 
				{
					objects.remove(g);
					coins.remove(g);
				}
			}
			
			toRemoveFromPlayer.clear();
			
		}

	private void Intersect() {
		//important method in the game : it checks many different intersections between objects,
		// for example when standardbullets touch enemies, ...
		
		
		//to check all bullets in the screen 
		for (int i = bulletsInScreen.size() - 1 ; i >= 0; --i){ //(int i = 0; i < bulletsInScreen.size() - 1; i ++ )  {
			
			Bullet b = bulletsInScreen.get(i);
			//if the bullet is a standardbullet, it means that it was shot by the player
			
			if (b.getName() == NameObject.STANDARDBULLET){
				
				for (Enemy e : enemies) {
					
					//if the bullet touches an enemy
					if (b.DrawRectangle().intersects(e.DrawRectangle()))  {
						
						//remove the bullet (add it to a list "toRemove" to avoid concurrentModificationError
						toRemove.add(b);
						
						//changes HP of the enemy 
						e.setHP(e.getHP() - Parameters.playerBulletDamage);
						
						//changing the gauge 
						int index = enemies.indexOf(e);
						Gauge gaugeToChange = gaugeList.get(index); 
						gaugeToChange.setCurrentHP(e.getHP());
						
						//if HP = 0, this removes the  enemy and the gauge 
						if (e.getHP() <= 0){
							playerHitSound.run();
							toRemove.add(gaugeToChange);
							gaugeList.remove(gaugeToChange);
							toRemove.add(e);
							//dragons.remove(e);	
						}

						
						
						
					}
					
				}
				
				for (Block bl : blockList) {
					if  (b.DrawRectangle().intersects( 
									bl.DrawRectangle()) ) {
						toRemove.add(b);
					}
				}
			}
			
			if (b.getName() == NameObject.ENEMYBULLET){
			
					if (b.DrawRectangle().intersects(man.DrawRectangle()) ) {
						playerAieSound.run();
						toRemove.add(b);
						man.setHP(man.getHP() - b.bulletDamage);	
						if ((man.HP <= 0) && (man.getLife() > 0)){
							man.setLife();
							man.setHP(Parameters.playerbeginHP);
							for (GameObject h : objects) {
								if (h.getName() == NameObject.HEART) {
									toRemove.add(h);
									break;
								}
							}
						}
						if ((man.HP <= 0) && (man.getLife() == 0) && ended == false) {
							endPanel(0);
							  for (GameObject go : objectsToMove) {
									toRemove.add(go);
								}
								for (Background back : backgrounds) {
									back.stop();
									back.canMoveRight = false;
								}
								ended = true;
						}
						
						
					}

					for (Block bl : blockList) {
						if  (b.DrawRectangle().intersects( 
										bl.DrawRectangle()) ) {
							toRemove.add(b);
							
						}
					}
			}
			
			
		}

		if(toRemove.size() > 0)
			Delete();
	
	}


	private void Delete() {
		
		for (GameObject g : objects) {
			if ((g.getName() == NameObject.STANDARDBULLET || g.getName() == NameObject.ENEMYBULLET)
					&& (!g.IsInScreen(gameframe.gamewidth, gameframe.gameheight)))  {
				
				toRemove.add(g);
			}
			if ((g.getName() == NameObject.ENEMY)  && (g.getX() < -g.width)) {
				toRemove.add(g);
			
				int index = enemies.indexOf(g);
				Gauge gaugeToChange = gaugeList.get(index); 
				enemies.remove(g);
				toRemove.add(gaugeToChange);
				gaugeList.remove(gaugeToChange);
			}
		}
		
		for (GameObject g : toRemove) {
			objects.remove(g);
			if (g.getName() == NameObject.ENEMYBULLET || g.getName() == NameObject.STANDARDBULLET) 
				bulletsInScreen.remove(g);
			if (g.getName() == NameObject.COIN ) 
				coins.remove(g);
			if (g.getName() == NameObject.ENEMY) 
				enemies.remove(g);
			
		}
		toRemove.clear();
	}
	
	
	private void EnemyAttack() {
		//we have the same random attack method for every enemytype except for dragons
		Boolean changed;
		for (Enemy e : enemies) {
			long currentTime = System.nanoTime();
			if (e.IsInScreen(gameframe.gamewidth - e.width - 5, gameframe.gameheight) && (e.getHP() > 1)) {
				//for dragons we use the current time as indicator to let the dragon shoot, that gives a more chosen time-interval
				//between each shot, and that lets only the first dragon in the screen shoot..
				//otherwise, our game will be too difficult and annoying
				if (currentTime/100000000 > dragonstarttime/100000000 + 10 + randomAttackTime*10
						&& e.getType() == EnemyType.DRAGON) {
					randomAttackTime = rand.nextInt(randomMaximum);
					dragonstarttime = currentTime;
					Bullet bull = new Bullet(e.getX() , e.getY(), enemyBulletSize, enemyBulletSize,
							BulletType.ENNEMYBULLET, 1, Color.yellow);
					objects.add(bull);
					bulletsInScreen.add(bull);
					bull.InitShoot(e.getX(), e.getY() , (int)man.getX() + man.width/2, (int)man.getY() + man.heigth/2);
					dragonShootSound.run();
					
				}
				
				if (e.getType() == EnemyType.SOLDIER)  {
					randomShooter(650, e, enemyBulletSize, Color.gray, 1, soldierShootSound);
				} 	
				
				if (e.getType() == EnemyType.REDSKULL)  {
					randomShooter(250, e, enemyBulletSize + 3, Color.red, 3, redSkullShootSound);
				} 	
				
				if (e.getType() == EnemyType.IRONCLAD)  {
					randomShooter(300, e, enemyBulletSize - 4, Color.BLACK, 1, ironCladShootSound);
				} 	
				
				if (e.getType() == EnemyType.SUPERBUU)  {
					int randomBulletSize = rand.nextInt(15) + 5;
					randomShooter(70, e, randomBulletSize, Color.WHITE, 1, soldierShootSound);
				} 	
				
				if (e.getType() == EnemyType.BIGBOSS)  {
					randomShooter(100, e, enemyBulletSize + 10, Color.BLUE, 2, bigBossShootSound);
					if (currentTime/100000000 > bigbossStartTime/100000000 + 10) {
						changed = false;
						if (e.getActualImage() == "normal"){
							e.imageChange("bigBossWait");
							changed = true;
						}
						if (e.getActualImage() != "normal" 
								&& changed == false ) {
							e.imageChange("normal");
						}
						
						bigbossStartTime = currentTime;
					}
					}

			}
				
		}
		
	}
	
	private void randomShooter(int randMaximum, Enemy e, int bulletSize, Color bullcolor, int damage, PlaySound shootsound) {
		soldiershoot = rand.nextInt(randMaximum);
		if (soldiershoot ==10) {
			if (e.getType() == EnemyType.IRONCLAD) {
				for (int i = 0; i < 8; i++) {
				Bullet extrabull = new Bullet(e.getX(), e.getY(), bulletSize, bulletSize,
						BulletType.ENNEMYBULLET, damage, bullcolor);
				objects.add(extrabull);
				bulletsInScreen.add(extrabull);
				extrabull.InitShoot(e.getX() + 50, e.getY() + 50 , (int)man.getX() + man.width/2, i*(gameframe.gameheight/9));
				}
			}
			else {
				Bullet bull = new Bullet(e.getX() + e.getWidth()/3, e.getY() + e.getHeigth()/3, bulletSize, bulletSize,
						BulletType.ENNEMYBULLET, damage, bullcolor);
				objects.add(bull);
				bulletsInScreen.add(bull);
				bull.InitShoot(e.getX(), e.getY() + e.getHeigth()/3 , (int)man.getX() , (int)man.getY());
			}
			e.imageChange("shoot");
			shootsound.run();
			
		}
		if (soldiershoot >= 11 && soldiershoot <= 20 && e.getType() != EnemyType.BIGBOSS) {
			e.imageChange("normal");
		}
		
		
		if (soldiershoot == 25 && e.getType() == EnemyType.BIGBOSS) {
			e.imageChange("bigBossPunch");
		}
	}
	
	private void GaugeSync() {
		gauge.setCurrentHP(man.getHP());
	}
	
	private void BlockSync() {
	for (Block b : blockList) {
			
			if (b.getIsLastBlock() == true && man.getY() < b.getY() && man.getX() > b.getX() + b.width - 20 &&
				man.getX() < b.getX() + 3*b.width && playerIsOnBlock == true){
				man.setIsJumping(true);
				if (character == 1) 
					man.setEndJumpY(gameframe.gameheight - bg.groundHeight - man.getHeigth() - 20);
				if (character == 2) 
					man.setEndJumpY(gameframe.gameheight - bg.groundHeight - man.getHeigth() - 28);
				playerIsOnBlock = false;
				
			}
			
			if (b.getIsFirstBlock() == true && man.getY() < b.getY() && man.getX() < b.getX() - b.width  &&
					man.getX() > b.getX() - 3*b.width  && playerIsOnBlock == true){
				man.setIsJumping(true);
				if (character == 1) 
					man.setEndJumpY(gameframe.gameheight - bg.groundHeight - man.getHeigth() - 20);
				if (character == 2) 
					man.setEndJumpY(gameframe.gameheight - bg.groundHeight - man.getHeigth() - 28);
				playerIsOnBlock = false;	
			}
			
		
		}
		
		for (int i = 0; i < specialBlockCoordinates.size() - 1 ; i++) {
				if (isOnBlock(man.getX(), i) && man.getY() > blockY) {
					man.setCanJump(false);
					break;
				}
			else {
				man.setCanJump(true);
			}
		}
		
		
		for (int i = 0; i < specialBlockCoordinates.size() ; i++) {
			specialBlockCoordinates.set(i, specialBlockCoordinates.get(i) + blockCoordinateVx);
		
		}
		
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
		for (GameObject go : objects) {
			go.drawMe(g);
		}
		
	
		
		eye.DrawBullseye(g);

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int c = e.getKeyCode() ;
		
			
		if (c == KeyEvent.VK_LEFT )
				man.moveLeft() ;
		
		
		if (c == KeyEvent.VK_RIGHT ) {
			man.moveRight() ;
			if (man.getX() > gameframe.getWidth()/2) {
				for (GameObject go : objectsToMove) {
					go.setVx(-2.5);
					man.moveStop();
					blockCoordinateVx = go.getVx();	
				}
			
				for (Background bgr : backgrounds) {
					bgr.moveRight(bgMoveSpeed);
				}	
				
			}
		}
		if (c == KeyEvent.VK_UP && man.isJumping() == false){
			man.jump(man.getY());
			playerJumpSound.run();
		}
		
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		man.moveStop();
		for (GameObject go : objectsToMove) {
			go.vx = 0;
		}
		man.animationStop();
		for (Background bgr : backgrounds) {
			bgr.stop();
		}	
		blockCoordinateVx = 0;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == playAgainButton)
			gameframe.switchPanel(new WarriorPanel(gameframe, selectedLevel));
		
		
		
		if(e.getSource()== menuButton)	
			gameframe.switchPanel(new Level(gameframe));
		if (endStatus == 1)
			youWinSound.stop();
		if (endStatus == 0)
			youLostSound.stop();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (remainingBullets > 0 && !ended) {
			Bullet bull = new Bullet(man.x , man.y , standardBulletSize, standardBulletSize, BulletType.STANDARD, Parameters.playerBulletDamage, Color.YELLOW);
			objects.add(bull);
			bulletsInScreen.add(bull);
			bull.InitShoot(man.x, man.y , e.getX(), e.getY());
			man.throwAnimation();
			remainingBullets -= 1;
			playerThrowSound.run();
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		eye.x = e.getX();
		eye.y = e.getY();
	}
	
	public int getGameWidth() {
		return getWidth();
	}
	public int getGameHeight() {
		return getHeight();
	}
	
	private void endControl() {
	
		if (man.getX() >= endFlag.getX() && ended == false){
			for (GameObject go : objectsToMove) {
				toRemove.add(go);
			}
			for (Background b : backgrounds) {
				b.stop();
				b.canMoveRight = false;
			}
			endPanel(1);
			ended = true;
		}
	}
	
	private void endPanel(int status){
		JLabel statusLabel;
		JLabel actualMoneyLabel;
		if (status == 0) {
			youLostSound = new PlaySound("youLost.wav", false);
			statusLabel =  new JLabel("Game Over, you lost !");
			youLostSound.run();
			endStatus = 0;
		}
		else {
			statusLabel =  new JLabel("Congrulations, you won !");
			if (Parameters.lastLevel == selectedLevel && Parameters.lastLevel < 10) {
				Parameters.lastLevel += 1;
				JLabel newLevelLabel = new JLabel("Unlocked level : " + Parameters.lastLevel);
				setLabel(newLevelLabel, 35, gameframe.gamewidth/2 - 250, gameframe.gameheight/2 - 100, 700, 70, Color.WHITE);
			}
			youWinSound = new PlaySound("youWon.wav", false);
			youWinSound.run();
			endStatus = 1;
		}
		
		if (selectedLevel == 10) 
			lastLevelSound.stop();
		
		setLabel(statusLabel, 60, gameframe.gamewidth/2 - 320, gameframe.gameheight/2 - 200, 700, 70, Color.WHITE);
		actualMoneyLabel = new JLabel("You have actually " + Parameters.money + " coins." );
		setLabel(actualMoneyLabel, 35, gameframe.gamewidth/2 - 250, gameframe.gameheight/2 - 150, 700, 70, Color.WHITE);
		
		playAgainButton = new JButton("Play Again");
		playAgainButton.addActionListener(_this);
		playAgainButton.setBounds(gameframe.gamewidth/2 - 320,  gameframe.gameheight/2 , 200, 50);
		this.add(playAgainButton);
		
		menuButton = new JButton("Back To Menu");
		menuButton.addActionListener(_this);
		menuButton.setBounds(gameframe.gamewidth/2 ,  gameframe.gameheight/2 , 200, 50);
		this.add(menuButton);
				
	}
	
	private void setLabel(JLabel label, int size, int x, int y, int width, int height, Color color) {
		label.setFont(new Font("TimesRoman", Font.BOLD, size));
		label.setBounds(x, y, width, height);
		label.setForeground(color);
		_this.add(label);
	}


}