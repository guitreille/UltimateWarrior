package Main;

import java.util.LinkedList;

//this class keeps a few static variables that we have to use in all parts of the game
public class Parameters {
	public static int money;
	public static int beginNbrBullets;
	public static int beginNbrHearts;
	public static int lastLevel;
	public static int playerBulletDamage;
	public static int playerJumpHeight;
	public static int playerMoveSpeed;
	public static int playerbeginHP;
	public static int character;
	public static LinkedList<Integer> bonusList = new LinkedList<Integer>() ;
	
	public Parameters(){
		lastLevel = 10; //this is the last unlocked level
		money = 0;
		beginNbrBullets = 100;
		playerBulletDamage = 1;
		playerMoveSpeed = 8;
		playerJumpHeight = 11;
		playerbeginHP = 5;
		beginNbrHearts = 3;
		
		//the character variable keeps which player is selected
		character = 1;
		
		//a number "O" is when you don't have the bonus yet, a 1 is when you already bought it.
		for (int i = 0; i < 12 ; i++) {
			bonusList.add(0);
		}
	}

}
