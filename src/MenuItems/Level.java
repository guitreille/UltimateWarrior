package MenuItems;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import Game.WarriorPanel;
import Main.GameFrame;
import Main.Parameters;


//in the level-panel, we want the image of a 'locked' level when it's locked, and the real image when not
public class Level extends MenuItem implements  ActionListener {
	private BufferedImage level1Img, level2Img, level3Img, level4Img, level5Img, level6Img, level7Img, level8Img, level9Img, levelFinalImg ;
	private BufferedImage lockImg, lockBigImg;

	
	private LinkedList<BufferedImage> imgList = new LinkedList<BufferedImage>() ;
	private LinkedList<JButton> buttonList = new LinkedList<JButton>() ;

	
	public Level(GameFrame gameframe) {
		super(gameframe);

		backbutton.addActionListener(this);

		level1Img = loader.loadImage("/Images/level1img.jpg");
		level2Img = loader.loadImage("/Images/level2img.jpg");		
		level3Img = loader.loadImage("/Images/level3img.jpg");
		level4Img = loader.loadImage("/Images/level4img.png");
		level5Img = loader.loadImage("/Images/level5img .png");
		level6Img = loader.loadImage("/Images/level6img.png");
		level7Img = loader.loadImage("/Images/level7img.png");
		level8Img = loader.loadImage("/Images/level8img.png");
		level9Img = loader.loadImage("/Images/level9img.png");
		levelFinalImg = loader.loadImage("/Images/levelFinalimg.jpg");
		lockImg = loader.loadImage("/Images/locked.jpg");
		lockBigImg = loader.loadImage("/Images/lockedBig.jpg");

		imgList.addAll(Arrays.asList(level1Img,level2Img,level3Img,level4Img, level5Img,
				level6Img, level7Img, level8Img, level9Img, levelFinalImg, lockImg,lockBigImg));
		
		for (int i = 0; i < imgList.size() ; i++) {
			JButton button ;
			if (Parameters.lastLevel >= i + 1)
				button = new JButton(new ImageIcon(imgList.get(i)));
			else
				button = new JButton(new ImageIcon(imgList.get(imgList.size() - 2)));
			int y = 0;
			int x = i;
			if (i < 3) {
				 y = 70;
			}
			if (i >= 3 && i < 6) {
				 y = 220;
				 x = i -3;
			}
			if (i >= 6 && i < 9) {
				 y = 370;
				 x = i - 6;
			}
			
			if (i == 9) {
				if (Parameters.lastLevel < 10)
					button = new JButton(new ImageIcon(imgList.get(imgList.size() - 1)));
			
				x = 3;
				y = gameframe.gameheight/2  - lockBigImg.getHeight() ;
			}
			if (i < 9) 
				button.setBounds((gameframe.gamewidth/5)*x + 50 ,y,250,125);
			else
				button.setBounds((gameframe.gamewidth/5)*x + 50 ,y,340,298);

			button.setBorder(BorderFactory.createEmptyBorder());
			button.setContentAreaFilled(false);
			this.add(button);
			button.addActionListener(this);
			buttonList.add(button);
		}
	
	}
	public void paintComponent(Graphics g)
	{
	  g.drawImage(background, -50, 0, null);
      g.drawImage(backarrow, 0, gameframe.gameheight - backarrow.getHeight() - 50, null);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backbutton)
		{
			gameframe.switchPanel(new MenuPanel(gameframe));
		}
			
		
		
		int level = buttonList.indexOf(e.getSource()) + 1;
		if (e.getSource() != backbutton && level <= Parameters.lastLevel)
			gameframe.switchPanel(new WarriorPanel(gameframe, level));
		
		else{
			if (e.getSource() != backbutton) {
				int levelminus = level - 1 ;
				String levelmessage = "Please first finish level " + levelminus + " !"; 
				JOptionPane.showMessageDialog(null,levelmessage ,
						"Unblocked Level", JOptionPane.ERROR_MESSAGE);
			}
				
		}
		

	}
	
	@Override
	public void setButton(JButton button, int x, int y) {
	}



}