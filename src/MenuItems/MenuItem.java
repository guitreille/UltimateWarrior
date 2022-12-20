package MenuItems;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Main.BufferedImageLoader;
import Main.GameFrame;

//this is an abstract superclass of each panel in the menu
public abstract class MenuItem extends JPanel {
	protected GameFrame gameframe;
	protected BufferedImage backarrow;
	protected BufferedImage background ;
	protected JButton backbutton;
	protected BufferedImageLoader loader;
	protected BufferedImage spriteSheet = null;
	protected JLabel title;
		
	
	public MenuItem(GameFrame gameframe){
		this.gameframe = gameframe ;
		loader =  new BufferedImageLoader();
		background = loader.loadImage("/Images/menubackground.jpg");
		backarrow = loader.loadImage("/Images/backbutton.png");
	

		backbutton = new JButton(new ImageIcon(backarrow));
		backbutton.setBounds(0, gameframe.gameheight - backarrow.getHeight() - 50 , backarrow.getWidth(), backarrow.getHeight());
		backbutton.setBorder(BorderFactory.createEmptyBorder());
		backbutton.setContentAreaFilled(false);
		this.add(backbutton);
		
		title = new JLabel("");
		title.setFont(new Font("TimesRoman", Font.PLAIN, 70));
		title.setForeground(Color.WHITE);
		this.setLayout(null);
		title.setBounds(40, 10, 800, 90);
		this.setLayout(null);
		this.add(title);
	}
	
	protected void setImageButton(JButton button, int x, int y, int width, int height) {
		button.setBounds(x,y,width ,height);
		button.setBorder(BorderFactory.createEmptyBorder());
		button.setContentAreaFilled(false);
	}
	
	public abstract void paintComponent(Graphics g);
	public abstract void setButton(JButton button, int x, int y);

}
