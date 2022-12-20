package Main;


import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import MenuItems.MenuPanel;


public class GameFrame extends JFrame {
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public  int gamewidth = screenSize.width;
	public int gameheight = screenSize.height - 100; //screenSize.height;
	private JPanel activePanel;
	public static PlaySound backMusic;
	
	public GameFrame() {
		//if the screen is higher than those values, we don't want the gameframe bigger
		if (screenSize.width > 1600) 
			gamewidth = 1600;
		if (screenSize.height > 800)
			gameheight = 800;
		this.setSize(gamewidth,gameheight);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Ultimate Warrior");
		activePanel = new MenuPanel(this);
		this.add(activePanel);
		this.setVisible(true);
		this.setResizable(false);
		
		//when we open the game, we want to start immediately the music 
		backMusic = new PlaySound("menu.wav", true);
		backMusic.run();

		
	}
	
	//to switch from MenuPanel to GamePanel and other panels
	public void switchPanel(JPanel toActivate)
	{
		this.remove(activePanel);
		activePanel = toActivate;
		this.add(activePanel);	
		activePanel.requestFocusInWindow();
		validate();
		repaint();
	}
	


}