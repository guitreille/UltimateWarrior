package MenuItems;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import Main.GameFrame;
import Main.Parameters;
import Main.SpriteSheet;



public class ChoosePlayer extends MenuItem implements  ActionListener {

	private BufferedImage characterImage;
	private JButton miniGokuButton, gokusanButton;
	private JLabel miniGokuLabel, gokusanLabel;
	private SpriteSheet ss;
	
	public ChoosePlayer(GameFrame gameframe) {
		super(gameframe);
		backbutton.addActionListener(this);
		
		
		characterImage = loader.loadImage("/Images/goku.png");
		ss = new SpriteSheet(characterImage);
		characterImage = ss.grabImage(0, 20, 70, 110);
		miniGokuButton = new JButton(new ImageIcon(characterImage));
		miniGokuButton.setBounds(100,300,70,110);
		miniGokuButton.setBorder(BorderFactory.createEmptyBorder());
		miniGokuButton.setContentAreaFilled(false);
		this.add(miniGokuButton);
		miniGokuButton.addActionListener(this);
		
		characterImage = loader.loadImage("/Images/dbz.gif");
		ss = new SpriteSheet(characterImage);
		characterImage =ss.grabImage(825, 5, 85, 150);
		gokusanButton = new JButton(new ImageIcon(characterImage));
		gokusanButton.setBounds(400,300,70,110);
		gokusanButton.setBorder(BorderFactory.createEmptyBorder());
		gokusanButton.setContentAreaFilled(false);
		this.add(gokusanButton);
		gokusanButton.addActionListener(this);
		
		miniGokuLabel = new JLabel("Mini Goku");
		miniGokuLabel.setFont(new Font("TimesRoman", Font.PLAIN, 40)); 
		miniGokuLabel.setBounds(100,300, 600, 300);
		this.add(miniGokuLabel);
	
		gokusanLabel = new JLabel("GOKUSAN");
		gokusanLabel.setFont(new Font("TimesRoman", Font.PLAIN, 40)); 
		gokusanLabel.setBounds(400,300, 600, 300);
		this.add(gokusanLabel);
		
		title.setText("Choose Your Player");
		
		if (Parameters.character == 1) {
			miniGokuLabel.setForeground(Color.GREEN);
			gokusanLabel.setForeground(Color.WHITE);
		}
		
		if (Parameters.character == 2) {
			miniGokuLabel.setForeground(Color.WHITE);
			gokusanLabel.setForeground(Color.GREEN);
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backbutton)
		{
			gameframe.switchPanel(new MenuPanel(gameframe));
		}
		
		if (e.getSource() == miniGokuButton)
		{
			miniGokuLabel.setForeground(Color.GREEN);
			gokusanLabel.setForeground(Color.WHITE);
			Parameters.character = 1;
	
		}
		
		if (e.getSource() == gokusanButton)
		{
			miniGokuLabel.setForeground(Color.WHITE);
			gokusanLabel.setForeground(Color.GREEN);
			Parameters.character = 2;
	
		}
	
	}


	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(background, -50, 0, null);
	    g.drawImage(backarrow, 0, gameframe.gameheight - backarrow.getHeight() - 50, null);
	}


	@Override
	public void setButton(JButton button, int x, int y) {	
	}

}