package com.mustard.game;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mustard.screen.StartPageScreen;
import com.mustard.screen.LoginPageScreen;
import com.mustard.screen.NewUserPageScreen;
import com.mustard.server.Commands;
import com.mustard.server.action.Action;
import com.mustard.server.action.Response;
import com.mustard.server.object.User;
import com.mustard.util.Pair;

public class Client {
	/* DATA MEMBERS */
	public Mustard game;
	public Socket socket;
	public Vector<User> users;
	/* PRIVATE DATA MEMBERS */
	private User user;
	
	/* STREAM OBJECTS */
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public enum move{
		UP, DOWN, LEFT, RIGHT;
	}
	
	public Client(Mustard mustard, int port, String IPAddress) {
		try {
			socket = new Socket(IPAddress, port);
			System.out.println ("Connected to server");
			
			out = null;
			in = null;
			
		} catch (Exception e) {
			System.out.println (e.getMessage());
		}
		
		System.out.println ("Connection acquired");
		game = mustard;
		user = null;
		game.batch = new SpriteBatch();
		game.setScreen(new StartPageScreen(game));
	}
	
	/* LOGIC */
	
	/** Attempts to login into the game with the given credentials.
	 * 		@param username - User's username
	 * 		@param password - User's password
	 * 		@return True if the given credentials equates to a sucessfull login.
	 * 				False otherwise. */
	public boolean login(String username, String password)
	{
		Vector <Pair <Integer, String >> input = new Vector<Pair<Integer, String>> ();
		input.add(new Pair <Integer, String> (Commands.USERNAME, username));
		input.add(new Pair <Integer, String> (Commands.PASSWORD, password));
		
		Action action = new Action (Commands.VALIDATE_USER);
		action.setInputList(input);
		
		Response r = sendActiontoServer (action);
		
		if (r != null && r.getServerResponse()) {
			user = r.getUserObject();
			user.setClient(this);
		}
		else
		{
			LoginPageScreen currentScreen = (LoginPageScreen)game.getScreen();
			currentScreen.printError(r.getErrorMessage());
		}
		
		return r.getServerResponse();
	}
	
	/** Attempts to create a new user and add it into the database upon success.
	 * 		@param username - User's username
	 * 		@param password - User's password
	 * 		@param nickname - User's nickname
	 * 		@return True if a new player has been sucessfully made.
	 * 				False otherwise. */
	public boolean signup(String username, String password, String nickname)
	{
		/* Returns false if at least one of the fields are empty */
		NewUserPageScreen currentScreen = (NewUserPageScreen)game.getScreen();
		if (username.equals("") || password.equals("") || nickname.equals(""))
		{
			if(username.equals(""))
			{
				currentScreen.printError("Error: Username is Empty");
			}
			else if(password.equals(""))
			{
				currentScreen.printError("Error: Password is Empty");
			}
			else if(nickname.equals(""))
			{
				currentScreen.printError("Error: Nickname is Empty");
			}
			return false;
		}
		
		Vector <Pair <Integer, String >> input = new Vector<Pair<Integer, String>> ();
		input.add(new Pair <Integer, String> (Commands.USERNAME, username));
		input.add(new Pair <Integer, String> (Commands.PASSWORD, password));
		input.add(new Pair <Integer, String> (Commands.NICKNAME, nickname));
		
		Action action = new Action (Commands.NEW_USER);
		action.setInputList(input);
		
		Response r = sendActiontoServer (action);
		
		if (r != null && r.getServerResponse()) {
			user = r.getUserObject();
		}
		else if(r != null && !r.getServerResponse())
		{
			currentScreen.printError(r.getErrorMessage());
		}
		
		return r.getServerResponse();
	}
	
	/** Attempts to create a new character for the player.
	 * 		@param selection - Character index */
	public boolean createCharacter (int selection) {
		String character_selection = selection  + "";
		
		Action action = new Action (Commands.SET_USER_CHARACTER);
		action.setInput(character_selection);
		action.setUserObject(user);
		
		Response r = sendActiontoServer (action);
		
		if (r.getServerResponse())
		{
			user = r.getUserObject(); // Updates the user object based on the server update
		}
		
		return r.getServerResponse();
	}
	
	/** Attempts to let the user either start a new game if there are no active game sessions,
	 *  or join a pre-existing game that is not full.
	 *  	@return True if the user successfully joins/creates a game.
	 *  			False otherwise. */
	public boolean joinGame () {
		Action action = new Action (Commands.QUERY_PLAYER);
		action.setUserObject(user);
		action.setSenderName(user.getUsername());
		
		Response r = sendActiontoServer (action);
		
		if (r == null)
			System.out.println("Error");
		
		if (r != null && r.getServerResponse())
			user = r.getUserObject(); // Updates the user object based on the server update
		
		return r.getServerResponse();
	}
	
	/** Checks with the server if the user can start the game */
	public boolean canStartGame () {
		Action action = new Action (Commands.CAN_START_GAME);
		action.setInput (user.getUsername());
		action.setUserObject(user);
		
		Response r = sendActiontoServer (action);
		
		return r.getServerResponse();
	}
	
	/** Creates a new guest user */
	public boolean createGuestUser () {
		Action action = new Action (Commands.CREATE_GUEST_USER);
		Response r = sendActiontoServer (action);
		user = r.getUserObject();
		user.setClient(this);
		return r.getServerResponse();
	}
	
	/** Asks for server for the users list from the GameSession to get other users images/data **/
	public void getAllUsers() {
		Action action = new Action (Commands.GET_ALL_USERS);
		action.setInput (user.getUsername());
		action.setUserObject(user);
		Response r = sendActiontoServer (action);
		if(r.getServerResponse()) {
			// System.out.println("Got other users through server");
			users = r.getUsersList();
		} else {
			// System.out.println("Got other users through server failed");
		}
	}
	
	/** Updates the user object stored in this client from the server */
	public void updateUserObject () {
		Action action = new Action (Commands.GET_USER, user.getUsername());
		
		if (user.isGuest())
			action.setInput("true"); // User is a guest
		else
			action.setInput("false"); // User is not a guest
		
		Response r = sendActiontoServer (action);
		
		if (r.getServerResponse())
			user = r.getUserObject(); // Updates the user object based on the server update
	}
	
	public boolean isMyTurn () {
		Action action = new Action (Commands.IS_MY_TURN);
		action.setUserObject(user);
		Response r = sendActiontoServer (action);
		return r.getServerResponse();
	}
	
	public boolean nextTurn () {
		Action action = new Action (Commands.NEXT_TURN);
		action.setUserObject(user);
		Response r = sendActiontoServer (action);
		return r.getServerResponse();
	}
	
	public boolean sendMovement(boolean ifMoved)
	{
		Action action = new Action (Commands.UPDATE);
		action.setUserObject(user);
		if(ifMoved)
			action.setSenderName("isMoved");
		if (user.isGuest())
			action.setInput("true"); // User is a guest
		else
			action.setInput("false"); // User is not a guest
		// System.out.println("trying send movment in client");
		Response r = sendActiontoServer(action);
		users = r.getUsersList();
		
		return r.getServerResponse();
	}
	
	public boolean attack (String target) {
		Action action = new Action (Commands.ATTACK);
		action.setSenderName(user.getUsername());
		action.setInput(target);
		
		Response r = sendActiontoServer (action);
		return r.getEndGameStatus();
	}
	
	public boolean useSkills(String target) {
		Action action = new Action (Commands.USE_SKILL);
		//this.user.getCharacter().setMovementPoints(battleScreen.getTilesMoved());
		System.out.println("movement left: " + user.getCharacter().getRemainingPoints());
		action.setUserObject(this.user);
		action.setSenderName(user.getUsername());
		action.setInput(target);
		
		Response r = sendActiontoServer (action);
		
		if (r.getServerResponse())
			user = r.getUserObject(); // Updates the user object based on the server update
		
		return r.getServerResponse();
		
	}
	
	/** Allows a user to upgrade his/her ability, given that he/she has enough
	 *  action points to perform this task.
	 *  	@param type - The ability getting upgraded. */
	public boolean upgradeAbilities (int type) {
		Action action = new Action (Commands.UPGRADE_ABILITIES);
		action.setSenderName(user.getUsername());
		action.setInputIndex(type);
		
		Response r = sendActiontoServer (action);
		
		if (r.getServerResponse())
			user = r.getUserObject();
		
		return r.getServerResponse();
	}
	
	/** Allows a user to upgrade his/her equipment, given that he/she has enough
	 *  action points to perform this task.
	 *  	@param type - The ability getting upgraded. */
	public boolean upgradeEquipment (int type) {
		Action action = new Action (Commands.UPGRADE_EQUIPMENT);
		action.setSenderName(user.getUsername());
		action.setInputIndex(type);
		
		Response r = sendActiontoServer (action);
		
		if (r.getServerResponse())
			user = r.getUserObject();
		
		return r.getServerResponse();
	}
	
	public boolean checkIfGameEnded() {
		Action action = new Action (Commands.CHECK_GAME_ENDED);
		action.setSenderName(user.getUsername());
		
		Response r = sendActiontoServer (action);
		
		return r.getServerResponse();
	}
	
	public boolean getWhoWon () {
		Action action = new Action (Commands.WHO_WON);
		action.setSenderName(user.getUsername());
		
		Response r = sendActiontoServer (action);

		return r.getServerResponse();
	}
	
	public void deleteSession(String username) {
		Action action = new Action (Commands.DELETE_SESSION);
		action.setSenderName(username);
		
		@SuppressWarnings("unused")
		Response r = sendActiontoServer (action);
		
		System.out.println("deleted session");
	}
	
	public void giveResources(String username) {
		Action action = new Action (Commands.GIVE_RESOURCE);
		action.setSenderName(username);
		
		Response r = sendActiontoServer (action);
		
		if (r.getServerResponse())
			user = r.getUserObject();
		
	}
	
	public void deleteCharacter() {
		Action action = new Action (Commands.DELETE_CHARACTER);
		action.setSenderName(user.getUsername());
		
		Response r = sendActiontoServer (action);
		
		user = r.getUserObject();
	}
	/** Sends an action object to the server, who will process the action object and send back a
	 * 	response object containing data requested from the sent action object.
	 * 		@param action - Action object being sent
	 * 		@return A response object containing the data requested by the @param action object. */
	public Response sendActiontoServer (Action action) {
		Response result = null;
		try { 
			/*  Sends out the action object to the server*/
			if (out == null) {
				out = new ObjectOutputStream (socket.getOutputStream());
				out.writeObject(action);
				out.flush();
				
				/* Awaits and retrieves the request object */
				
				if (in == null) {
					in = new ObjectInputStream (socket.getInputStream());
					result = (Response) in.readObject();
				}
				
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			in = null;
			out = null;
		}
		return result;
	}
	
	/* GETTERS */
	
	/** Gets the user object appended to this client program */
	public User getUser () { return user; }
	
	/** Gets the user vector which saves all the users in the same gession **/
	public Vector<User> getUserList () { return users; }
	
	/* SETTERS */
	
	/** Sets the client's stored user object to the @param u
	 * 		@param u - User object that is replacing the client's user object */
	public void setUser(User u) { user = u; }
	
	public void resetUser() { user = null; }
	

}
