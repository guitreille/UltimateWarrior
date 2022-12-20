package MenuItems;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JLabel;
import Main.GameFrame;

public class HowToPlay extends MenuItem implements  ActionListener {
	
	private BufferedImage mouseimg;
	private BufferedImage arrowsimg;
	private JLabel leftArrow;
	private JLabel rightArrow;
	private JLabel upArrow;
	private JLabel mouseClick;
	private JLabel instructionsLabel;
	

	
	public HowToPlay(GameFrame gameframe) {
		
		super(gameframe);
		backbutton.addActionListener(this);
		title.setText("How To Play");
		
		
		mouseimg = loader.loadImage("/Images/computermouse.png");
		arrowsimg = loader.loadImage("/Images/arrowskeys.png");
		
		
		leftArrow = new JLabel("Move to the left");
		leftArrow.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		leftArrow.setForeground(Color.RED);
		leftArrow.setBounds(240, 260, 140, 260);
		this.add(leftArrow);
	
	    
		rightArrow = new JLabel("Move to the right");
		rightArrow.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		rightArrow.setForeground(Color.RED);
		rightArrow.setBounds(650, 380, 320, 380);
	    this.add(rightArrow);

	    
	    upArrow = new JLabel("Jump");
		upArrow.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		upArrow.setForeground(Color.RED);
		upArrow.setBounds(440, 296, 140, 296);
	    this.add(upArrow);
	    
	    instructionsLabel = new JLabel("<html> Shoot the enemies with bullets, and don't touch them...<p>"
	    		+ "the flag is the end of a level... Go to 'shop' to buy some bonus powers if you have enough coins."
	    		+ "You can also choose your player in  'select your player', enjoy!  </html>");
	    instructionsLabel.setFont(new Font("TimesRoman", Font.BOLD, 20));
	    instructionsLabel.setForeground(Color.WHITE);
	    instructionsLabel.setBounds(330, 00, 500, 400);
	    this.add(instructionsLabel);
	    
	    mouseClick = new JLabel("Shoot bullets");
	    mouseClick.setText("Shoot bullets");
	    mouseClick.setFont(new Font("TimesRoman", Font.PLAIN, 20));
	    mouseClick.setForeground(Color.RED);
		this.setLayout(null);
		mouseClick.setBounds(60, 100, 180, 100);
		this.setLayout(null);
	    this.add(mouseClick);
	    
	    
	    
	    
	}
	
	public void paintComponent(Graphics g)
	{
      g.drawImage(background, -50, 0, null);
      g.drawImage(backarrow, 0, gameframe.gameheight - backarrow.getHeight() - 50, null);
      g.drawImage(mouseimg, 60, 170, null);
      g.drawImage(arrowsimg,320 ,490 ,null);
      g.setColor(Color.RED);
      g.drawLine(60, 170, 120, 190);// MOUSE
      g.drawLine(360, 640, 300, 400); // left
      g.drawLine(460, 520, 460, 460); // JUMP
      g.drawLine(620, 640, 650, 580);// RIGHT
      
      
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backbutton)
		{
			gameframe.remove(backbutton);
			gameframe.switchPanel(new MenuPanel(gameframe));
		}
	}

	@Override
	public void setButton(JButton button, int x, int y) {	}


}