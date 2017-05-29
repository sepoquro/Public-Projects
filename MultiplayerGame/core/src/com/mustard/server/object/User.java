package com.mustard.server.object;

import java.io.Serializable;

import com.mustard.game.Client;
import com.mustard.server.host.ClientThread;
import com.mustard.server.object.character.Character;
import com.mustard.server.object.character.Marksman;
import com.mustard.server.object.character.Soldier;
import com.mustard.server.object.character.Support;
import com.mustard.server.object.character.Tank;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* DATA MEMBERS */
	protected String username;
	protected String password;
	protected String nickname;
	protected int currency;
	protected int session_id;
	protected int character_type;
	protected boolean isGuest;
	protected Character character;
	protected boolean team;
	
	/* Serverside objects */
	transient ClientThread client_thread;
	transient Client client;
	
	/** Creates a new user.
	 * 		@param u - User's username
	 * 		@param p - User's password */
	public User (String u, String p) {
		username = u;
		password = p;
		nickname = "";
		currency = 0;
		session_id = -1;
		character = null;
		isGuest = false;
		team = false;
		character_type = 0;
	}
	
	/** Creates a new user.
	 * 		@param u - User's username
	 * 		@param p - User's password 
	 * 		@param n - User's nickname
	 * 		@param guest - Determines if the user is logging in as a guest account*/
	public User (String u, String p, String n, boolean guest) {
		username = u;
		password = p;
		nickname = n;
		currency = 0;
		session_id = -1;
		character = null;
		isGuest = guest;
		team = false;
		character_type = 0;
	}
	
	/** Character functions */
	
	/** Creates a new character based on the @param character_index 
	 * 	that is tied to this user. 
	 * 		@param character_index - Index of the character being made.
	 * 		 Note [Index table]:
	 *  		0 - Create a new Soldier character
	 *  		1 - Create a new Tank character
	 *  		2 - Create a new Support character
	 *  		3 - Create a new Marksman character */
	public void createCharacter (int character_index) {
		character_type = character_index;
		switch (character_index) {
			case Constants.SOLDIER_ID:
				character = new Soldier (this);
				break;
			case Constants.TANK_ID:
				character = new Tank (this);
				break;
			case Constants.SUPPORT_ID:
				character = new Support (this);
				break;
			default:
				character = new Marksman (this);
				break;
		}
		
		System.out.println("A new character has been created! " + character.className());
	}
	
	/** Removes the user's character */
	public void deleteCharacter() { character = null; }
	
	/** Returns a boolean value determining if this user has a character */
	public boolean hasCharacter() { return (character == null ? false : true); }
	
	/** SETTER METHODS */
	
	/** Sets the user's currency to the @param c */
	public void setCurrency (int c) { 
		currency = c; 
		if (currency <= 0 )
			currency = 0;
	}
	
	/** Adds the value stored in @param c to the user's current currency */
	public void addCurrency (int c) { currency += c; }
	
	/** Sets the user's character object to the @param chara */
	public void setCharacter (Character chara) { character = chara; }
	
	/** Sets the user's nickname to the @param n */
	public void setNickname (String n) { nickname = n; }
	
	/** Sets the session id that the user is currently in */
	public void setSessionID (int id) { session_id = id; }
	
	/** Sets the client thread that the user is using. */
	public void setClientThread (ClientThread ct) { client_thread = ct; }
	
	/** Sets the client that the user is connected to */
	public void setClient (Client c) { client = c; }
	
	/** Sets the player's team to the @param t. */
	public void setTeam (boolean t) { team = t; }
	
	/** GETTER METHODS */
	
	/** Returns the user's username */
	public String getUsername () { return username; }
	
	/** Returns the user's password */
	public String getPassword() { return password; }
	
	/** Returns the user's nickname */
	public String getNickname () { return nickname; }
	
	/** Returns the user's currency value */
	public int getCurrency () { return currency; }
	
	/** Returns the user's stored character */
	public Character getCharacter () { return character; }
	
	/** Returns the session id that the player is currently in */
	public int getSessionID () { return session_id; }
	
	/** Determines if the user under a guest account */
	public boolean isGuest () { return isGuest; }
	
	/** Returns the client thread that the user is using */
	public ClientThread getClientThread () { return client_thread; }
	
	/** Returns the client object that the user is connected to */
	public Client getClient () { return client; }
	
	/** Returns the player's team */
	public boolean getTeam () { return team; }
	
	/** Gets the character's type as an integer */
	public int getCharacterType () { return character_type; }
	
	public void resetSessionID() { session_id = -1; }
}