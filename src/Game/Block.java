package Game;

/**
 *A block is an object in the game to jump onto, it can also protect you from being shot by dragons or other 
 *flying enemies. 
 */

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Main.BufferedImageLoader;

public class Block extends GameObject{
	//contains the image of one block
	private BufferedImage blockImg = null;
	
	//in WarriorPanel, we need to know if the block is the first one or last one in a suit of blocks
	private boolean isLastBlock;
	private boolean isFirstBlock;

	public Block(double x, double y) {
		super(x, y);
		ImageInit();
	}
	
	
	@Override
	void ImageInit() {
		loader = new BufferedImageLoader();
		blockImg = (loader.loadImage("/Images/blockone.png"));

		syncDimensions(blockImg);
	}
	
	
	//UpdateMe and drawMe functions :
	@Override
	void UpdateMe() {
		x += vx;
	}

	@Override
	void drawMe(Graphics g) {
		g.drawImage(blockImg, (int)x, (int)y, null);
	}

	
	//getters and setters :
		//to return the name of the object :
	@Override
	NameObject getName() {
		return NameObject.BLOCK;
	}
	
		//to know if the block itself is a first or last block, or to change that
	public boolean getIsFirstBlock() {
		return isFirstBlock;
	}
	
	public boolean getIsLastBlock() {
		return isLastBlock;
	}
	
	public void setIsFirstBlock(boolean firstblock) {
		isFirstBlock = firstblock;
	}
	
	public void setIsLastBlock(boolean lastblock) {
		isLastBlock = lastblock;
	}
	

	//these abstract functions of the superclass are not necessary IN the class 'Block'
	@Override
	void imageChange(String action) {}



	

}
