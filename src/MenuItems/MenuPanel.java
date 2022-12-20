package MenuItems;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JPanel;
import Main.BufferedImageLoader;
import Main.GameFrame;


public class MenuPanel extends JPanel implements ActionListener{
	private BufferedImage backgroundImg;
	private BufferedImage titleImg;
	private BufferedImageLoader loader = new BufferedImageLoader();
	private JButton PlayButton,playerButton,HowToPlayButton,shopButton;
	private GameFrame gameframe;
	private int buttonWidth ;
	private int buttonHeight;

	
	public MenuPanel(GameFrame gameframe) {
		this.gameframe = gameframe ;
		setFocusable(true);
		

		backgroundImg = loader.loadImage("/Images/menubackground.jpg");
		titleImg = loader.loadImage("/Images/title.png");
		
		PlayButton = new JButton("Play");
		playerButton = new JButton ("Select your player");
		HowToPlayButton = new JButton("How to play?");
		shopButton = new JButton("Shop");

		
		PlayButton.addActionListener(this);
		playerButton.addActionListener(this);
		HowToPlayButton.addActionListener(this);
		shopButton.addActionListener(this);


		buttonWidth = gameframe.gamewidth /5 ;
		buttonHeight = gameframe.gameheight / 14;
		
		this.setLayout(null);
		PlayButton.setBounds(gameframe.gamewidth/6 - buttonWidth/2, 4*(gameframe.gameheight)/18, buttonWidth, buttonHeight);
		playerButton.setBounds(gameframe.gamewidth/6 - buttonWidth/2, 7*(gameframe.gameheight)/18, buttonWidth, buttonHeight);
		HowToPlayButton.setBounds(gameframe.gamewidth/6 - buttonWidth/2, 10*(gameframe.gameheight)/18, buttonWidth, buttonHeight);
		shopButton.setBounds(gameframe.gamewidth/6 - buttonWidth/2, 13*(gameframe.gameheight)/18, buttonWidth, buttonHeight);
		this.add(PlayButton);
		this.add(playerButton);
		this.add(HowToPlayButton);
		this.add(shopButton);
		repaint();
		
		

	
	}
	
	
		

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
      g.drawImage(backgroundImg, -50, -0, null);
      g.drawImage(titleImg, 50, 35, null);
      

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == PlayButton)
		{
			gameframe.switchPanel(new Level(gameframe));
		}
		if(e.getSource()== playerButton)	{
			gameframe.switchPanel(new ChoosePlayer(gameframe));
		}

		if (e.getSource()== shopButton){
			gameframe.switchPanel(new Bonus(gameframe));
		}
		if (e.getSource()== HowToPlayButton){
			gameframe.switchPanel(new HowToPlay(gameframe));
			

	}
	
	}
	
}

