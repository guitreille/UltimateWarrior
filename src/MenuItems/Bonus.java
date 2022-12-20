package MenuItems;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import Main.GameFrame;
import Main.Parameters;

//for the bonus panel, we keep some lists with the bonus-buttons, the cost of it and a comment

public class Bonus extends MenuItem implements  ActionListener {
	private JButton bullet1Button, bullet2Button, bullet3Button, heart1Button, heart2Button, heart3Button, 
	bulletDamage1Button, bulletDamage2Button,bulletDamage3Button, jumpButton,speedButton, HPButton, buyButton;

	private LinkedList<JButton> buttonList = new LinkedList<JButton>() ;
	private LinkedList<Integer> costList = new LinkedList<Integer>() ;
	private LinkedList<String> commentList = new LinkedList<String>();
	
	private JLabel commentLabel ;
	private JLabel moneyLabel ;
	
	private int selectedButtonIndex;
	
	public Bonus(GameFrame gameframe) {
		super(gameframe);
		backbutton.addActionListener(this);
		title.setText("Shop");
		background = loader.loadImage("/Images/menubackground.jpg");
		
		selectedButtonIndex = -1; 
		bullet1Button = new JButton ("beginBullets +");
		bullet2Button = new JButton ("beginBullets ++");
		bullet3Button = new JButton ("beginBullets +++");
		
		heart1Button = new JButton ("heart +");
		heart2Button = new JButton ("heart ++");
		heart3Button = new JButton ("heart +++");
		
		bulletDamage1Button = new JButton ("bulletDamage +");
		bulletDamage2Button = new JButton ("bulletDamage ++");
		bulletDamage3Button = new JButton ("bulletDamage+++");
		
		jumpButton = new JButton ("jump higher");
		speedButton = new JButton ("more speed");
		HPButton = new JButton ("more HP");
		
		buyButton = new JButton("buy");
		buyButton.setBounds(900, 400, 100, 65);
		buyButton.setBackground(Color.YELLOW);
		buyButton.setOpaque(true);
		buyButton.addActionListener(this);
		this.add(buyButton);
		
		buttonList.addAll(Arrays.asList(bullet1Button, bullet2Button, bullet3Button,
				heart1Button, heart2Button, heart3Button, bulletDamage1Button, bulletDamage2Button, bulletDamage3Button,
				jumpButton, speedButton, HPButton));
		
		costList.addAll(Arrays.asList(30, 70, 150, 50, 100, 150, 50, 150, 500, 80, 50, 150));
		commentList.addAll(Arrays.asList("You get 200 bullets to begin with",
				"You get 500 bullets to begin with", "You get 999 bullets to begin with", 
				"Begin every game with 4 hearts", "Begin every game with 5 hearts", "Begin every game with 6 hearts", 
				"Increases your bulletDamage x2", "Increases your bulletDamage x4", "Increases your bulletDamage x8",
				"Jump 2x higher", "Move with more speed", "Increases your HP x2"));
		
		 commentLabel = new JLabel("Select a Bonus...");
		 commentLabel.setFont(new Font("TimesRoman", Font.BOLD, 35));
		 commentLabel.setForeground(Color.WHITE);
		 commentLabel.setBounds(60, 400, 800, 60);
		 this.add(commentLabel);
		
		 moneyLabel = new JLabel("Your money = " + Parameters.money);
		 moneyLabel.setFont(new Font("TimesRoman", Font.BOLD, 35));
		 moneyLabel.setForeground(Color.WHITE);
		 moneyLabel.setBounds(500, 40, 500, 60);
		 this.add(moneyLabel);
		 
		for (int i = 0; i < buttonList.size() ; i ++ ){
			if (Parameters.bonusList.get(i) == 0){
				if (i < 6) 
					buttonList.get(i).setBounds(30 + 135*i, 250, 130, 70);
				else
					buttonList.get(i).setBounds(30 + 135*(i-6), 500, 130, 70);
				this.add(buttonList.get(i));
				buttonList.get(i).addActionListener(this);
			}
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
			gameframe.remove(backbutton);
			gameframe.switchPanel(new MenuPanel(gameframe));
		}
		
		else if (e.getSource() == buyButton) {
			String errorMessage = "";
			if (selectedButtonIndex == -1) {
				errorMessage = "Please select a bonus.";
				JOptionPane.showMessageDialog(null, errorMessage,
					"Not Possible", JOptionPane.ERROR_MESSAGE);
			}
			else if (Parameters.money < costList.get(selectedButtonIndex)) {
				int moneyDifference = costList.get(selectedButtonIndex) - Parameters.money;
				errorMessage = "You need " + moneyDifference +
						" coins more!"; 
				JOptionPane.showMessageDialog(null, errorMessage,
						"Not Possible", JOptionPane.ERROR_MESSAGE);
			}
			
			else {
				int previousButtons = 0;
				if (selectedButtonIndex == 0) 
					Parameters.beginNbrBullets =200;
				if (selectedButtonIndex ==  1) {
					Parameters.beginNbrBullets = 500;
					previousButtons = 1;
				}
				if (selectedButtonIndex ==  2) {
					Parameters.beginNbrBullets = 999;
					previousButtons = 2;
				}
				if (selectedButtonIndex == 3) 
					Parameters.beginNbrHearts = 4;
				if (selectedButtonIndex ==  4) {
					Parameters.beginNbrHearts = 5;
					previousButtons = 1;
				}
				if (selectedButtonIndex ==  5) {
					Parameters.beginNbrHearts = 6;
					previousButtons = 2;
				}
				if (selectedButtonIndex ==  6)
					Parameters.playerBulletDamage = 2;
				if (selectedButtonIndex ==  7) {
					Parameters.playerBulletDamage = 4;
					previousButtons = 1;
				}
				if (selectedButtonIndex ==  8) {
					Parameters.playerBulletDamage = 8;
					previousButtons = 2;
				}
				if (selectedButtonIndex ==  9)
					Parameters.playerJumpHeight = 13;
				if (selectedButtonIndex ==  10)
					Parameters.playerMoveSpeed = 10;
				if (selectedButtonIndex ==  11)
					Parameters.playerbeginHP *= 2;
				Parameters.money  = Parameters.money - costList.get(selectedButtonIndex);
				JOptionPane.showMessageDialog(null, "You succesfully bought " + buttonList.get(selectedButtonIndex).getText() + " !",
						"Congratulations!", JOptionPane.INFORMATION_MESSAGE);
				moneyLabel.setText("Your money = " + Parameters.money);
				buttonRemover(selectedButtonIndex, previousButtons);
			}
		}
		
		else {
			selectedButtonIndex = buttonList.indexOf(e.getSource());
			commentLabel.setText(commentList.get(selectedButtonIndex) + " , Cost = " + costList.get(selectedButtonIndex));
		}
		
	}
	@Override
	public void setButton(JButton button, int x, int y) {
	}

	//if for example you buy a "bulletDamage+++" bonus, you can not buy the "bulletDamage++" and "bulletDamage+" one anymore
	//this method let us remove the previous buttons
	private void buttonRemover(int index, int previousButtonToRemove) {
		this.remove(buttonList.get(index));
		Parameters.bonusList.set(index, 1);
		if (previousButtonToRemove >= 1) {
			this.remove(buttonList.get(index - 1));
			Parameters.bonusList.set(index - 1, 1);
		}
		
		if (previousButtonToRemove == 2) {
			this.remove(buttonList.get(index - 2));
			Parameters.bonusList.set(index - 2, 1);
		}
	}


}