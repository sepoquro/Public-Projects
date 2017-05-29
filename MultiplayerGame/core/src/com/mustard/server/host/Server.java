package com.mustard.server.host;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import com.mustard.server.Commands;
import com.mustard.server.action.Action;
import com.mustard.server.action.Response;
import com.mustard.server.object.Constants;
import com.mustard.server.object.User;
import com.mustard.server.object.character.Character;
import com.mustard.util.Pair;

public class Server {
	private int session_count;
	private Vector <ClientThread> clients;
	private HashMap <String, User> usr_database;		// Database of user objects
	private HashMap <String, User> guest_database;
	private HashMap <Integer, GameSession> sessions;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Random rand;
	
	/** Creates a new server client for Cyberarena. */
	@SuppressWarnings("unchecked")
	public Server (int port) throws IOException {
		/* TODO: Initialize database by connecting to MySQL */
		oos = null;
		
		/* Attempts to read the serialized hash table from data.dat. */
		try {
			ois = new ObjectInputStream (new FileInputStream (Constants.server_file));
			usr_database = (HashMap<String, User>) ois.readObject();
		}
		
		/* If not found, then create a new database. */
		catch (Exception e) {
			usr_database = new HashMap <String, User> ();
		}
		
		try {
			@SuppressWarnings("resource")
			ServerSocket ss = new ServerSocket (port);
			System.out.println("Server has been sucessfully created!");
			clients = new Vector<ClientThread> ();
			guest_database = new HashMap <String, User> ();
			sessions = new HashMap <Integer, GameSession> ();
			rand = new Random ();
			session_count = 0;
			
			/* Test code */
			User tester = new User("test", "tester");
			usr_database.put(tester.getUsername(), tester);
			
			while (true) {
				Socket s = ss.accept();
				System.out.println("Recieved connection from " + s.getInetAddress());
				ClientThread buffer = new ClientThread (s, this);
				clients.add(buffer);
			}
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** Parses the action referenced by the @param a and returns a response object
	 * 	back to the user.
	 * 		@param a - The action object sent by a client.
	 * 		@return A response object containing requested data. */
	public Response parseAction (Action a, ClientThread th) {
		Response response = null;
		
		/* Prints out DEBUG console lines. Will not print DEBUG lines if
		 * the command is start game to reduce log spam, since START GAME
		 * instructs the client to constantly ping the server until all players
		 * have joined the session.  */
		if (a.getCommandType() != Commands.CAN_START_GAME &&
				a.getCommandType() != Commands.UPDATE && 
				a.getCommandType() != Commands.GET_ALL_USERS && 
				a.getCommandType() != Commands.IS_MY_TURN &&
				a.getCommandType() != Commands.CHECK_GAME_ENDED) {
			System.out.println("=======================================");
			System.out.println("Recieved action request from client");
			System.out.println("Command type: " + a.getCommandType());
		}
		
		/* Parses the action object */
		switch (a.getCommandType()) {
			/* Creates a new user */
			case Commands.NEW_USER:
				response = createUser (a);
				saveData();
				break;
			/* Validates the user */
			case Commands.VALIDATE_USER:
				response = validateUser (a);
				break;
			/* Creates a new character for the user */
			case Commands.SET_USER_CHARACTER:
				response = createNewCharacter (a);
				saveData();
				break;
			
			/* Either creates a new game or adds the player into a prexisting game */
			case Commands.QUERY_PLAYER:
				response = queryPlayer (a, th);
				break;
				
			/* Checks if the user can start the game */
			case Commands.CAN_START_GAME:
				response = canStartGame (a);
				break;
				
			/*Gets other users so we can print the sprite images according to the user in the battle screen **/
			case Commands.GET_ALL_USERS:
				response = getAllUsers(a);
				break;
				
			/* Creates a new guest user */
			case Commands.CREATE_GUEST_USER:
				response = createGuestUser(a);
				break;
			
			/* Returns a updated user object to the client */
			case Commands.GET_USER:
				response = getUser(a);
				break;
			
			/* Determines if the player can start his/her turn */
			case Commands.IS_MY_TURN:
				response = isMyTurn(a);
				break;
				
			/* Sends a move request from a user to the server, and sends back a vector
			 * containing all users in the user's gamesession. */
			case Commands.UPDATE:
				response = updateUser(a);
				break;
				
			/* Tells the request session to advance to the next turn */
			case Commands.NEXT_TURN:
				response = nextTurn (a);
				break;
			
			/* Sends an request to attack the player */
			case Commands.ATTACK:
				response = attack (a);
				break;
			
			/* Allows a user to use his/her skill */
			case Commands.USE_SKILL:
				response = useSkill (a);
				break;
			
			/* Allows the user to upgrade his/her abilities */
			case Commands.UPGRADE_ABILITIES:
				response = upgradeAbilities (a);
				saveData();
				break;
			
			/* Allows the user to upgrade his/her equipment */
			case Commands.UPGRADE_EQUIPMENT:
				response = upgradeEquipment (a);
				saveData();
				break;
			case Commands.WHO_WON:
				response = checkWhoWon (a);
				break;
			case Commands.CHECK_GAME_ENDED:
				response = checkGameEnded (a);
				break;
			case Commands.DELETE_SESSION:
				response = deleteSession (a);
				break;
			case Commands.GIVE_RESOURCE:
				response = giveResource (a);
				saveData();
				break;
			case Commands.DELETE_CHARACTER:
				response = deleteCharacter (a);
				saveData();
				break;
			default:
				System.out.println ("Invalid command");
				break;
		}
		return response;
	}
	
	private Response updateUser(Action a) {
		Response response = new Response (a.getCommandType());
		User user = getUser (a.getUser().getUsername());
		if (user == null) {
			response.setServerResponse(false);
			return response;
		}
			
		/* Updates user assets through serverside */
		user.getCharacter().setXCoord(a.getUser().getCharacter().getXCoord());
		user.getCharacter().setYCoord(a.getUser().getCharacter().getYCoord());
		user.getCharacter().setRotation(a.getUser().getCharacter().getRotation());
		if(a.getSenderUsername() != null && a.getSenderUsername().equals("isMoved"))
			user.getCharacter().usedMove();
			
			
		int userID = user.getSessionID();
		GameSession ses = sessions.get(userID);
		response.setUserList(ses.getUserList());
		response.setServerResponse(true);
		return response;
	}

	/** Creates a new user and adds that user into the database. */
	private Response createUser (Action a) {
		String username = "", password = "", nickname = "";
		Response response = new Response (a.getCommandType());
		
		Vector<Pair<Integer, String>> buffer = a.getInputList();
		
		System.out.println("Parsing username / password / nickname pairs...");
		
		for (Pair <Integer, String> p : buffer) {
			switch (p.getKey()) {
				case Commands.USERNAME:
					username = p.getValue();
					break;
				case Commands.PASSWORD:
					password = p.getValue();
					break;
				case Commands.NICKNAME:
					nickname = p.getValue();
					break;
				default:
					break;
			}
		}
		
		/* Checks if the requested username is never used in the database */
		if (!usr_database.containsKey(username)) {
			// If so, create a new user.
			User tmp = new User (username, password, nickname, false);
			response.setUserObject(tmp);
			response.setServerResponse(true);
			usr_database.put(username, tmp);
			System.out.println("Created a new user!");
		}
		
		else {
			response.setErrorMessage("Error: Username already exists");
			response.setServerResponse(false);
		}
		
		return response;
	}
	
	/** Validates the user login information. If the user has inputed valid information, then
	 * return a request object containing the user object and also a boolean value indicating that
	 * valid credentials have been entered.
	 * 		@param a - Action object*/
	private Response validateUser (Action a) {
		User temp;
		String username = "", password = "";
		Response response = new Response (a.getCommandType());
		
		Vector<Pair<Integer, String>> buffer = a.getInputList();
		
		for (Pair <Integer, String> p : buffer) {
			switch (p.getKey()) {
				case Commands.USERNAME:
					username = p.getValue();
					break;
				case Commands.PASSWORD:
					password = p.getValue();
					break;
				default:
					break;
			}
		}
		
		/* Checks if the user entity is a valid entry in the database */
		if (!usr_database.containsKey(username)) {
			response.setServerResponse(false);
		}
		else {
			temp = usr_database.get(username);
			/* Checks if the password matches the password stored within the user */
			if (temp.getPassword().equals(password)) {
				response.setServerResponse(true);
				response.setUserObject(temp);
			}
			else {
				response.setServerResponse(false);
			}
		}
		
		if (!response.getServerResponse()) {
			System.out.println ("Error: Invalid credentials");
			response.setErrorMessage("Error: Invalid Credentials");
		}
		
		return response;
	}
	
	/** Creates a new character for a pre-existing user 
	 * 		@param a - Action object*/
	private Response createNewCharacter (Action a) {
		int character_index = -1;
		Response response = new Response (a.getCommandType());
		
		character_index = Integer.parseInt(a.getInput());
		User temp = getUser (a.getUser().getUsername());
		
		System.out.println ("Character index: " + character_index);
		
		if (temp != null) {
			temp.createCharacter(character_index);
			response.setServerResponse(true);
			response.setUserObject(temp);
		}
		
		else
			response.setServerResponse(false);
		
		return response;
	}
	
	/** Allows the user to either create a new game if the server has no active game sessions,
	 *  or join a game if the server has at least one active game sessions who are still 
	 *  waiting for more players. 
	 *  	@param a - Action object*/
	private Response queryPlayer (Action a, ClientThread th) {
		Response response = new Response (a.getCommandType());
			
		User u = getUser (a.getUser().getUsername());
		if (u == null) {
			response.setServerResponse(false);
			return response;
		}
			
		System.out.println(u.getUsername());
			
		u.setSessionID(session_count);
		u.setClientThread(th);
		th.setUsername(u.getUsername());
		th.setUser(u);
			
		/* Checks if there are no active games. If so, create a new game
		 * and add the character in. */
			
		Boolean createdNewSession = false;
		if (sessions.isEmpty()) {
			GameSession buffer = new GameSession (this, session_count);
			buffer.addUserToGame(u, th);
			sessions.put(session_count, buffer);
			createdNewSession = true;
		}
		/* Otherwise, traverse through the vector and add the user to the first non-full game session. */
		else {
			Set <Map.Entry <Integer, GameSession>> entrySet = sessions.entrySet();
			Iterator <Entry<Integer, GameSession>> it = entrySet.iterator();
			boolean addGame = false;
			while (it.hasNext()) {
				Entry <Integer, GameSession> e = it.next();
				if (!e.getValue().isFull()) {
					e.getValue().addUserToGame(u, th);
					u.setSessionID(session_count-1);
					addGame = true;
					break;
				}
			}
				
			if (!addGame) {
				GameSession buffer = new GameSession (this, session_count);
				buffer.addUserToGame(u, th);
				sessions.put(session_count, buffer);
				createdNewSession = true;
			}
		}
			
		if(createdNewSession)
			++session_count;
			
		response.setServerResponse(true);
		response.setUserObject(u);
		return response;
	}
	
	/** Checks if the user can switch to the game state
	 * 		@param a - Action object */
	private Response canStartGame (Action a) {
		Response response = new Response (a.getCommandType());
		User u = getUser (a.getUser().getUsername());
		
		if (u == null)
			response.setServerResponse(false);
		
		else {
			if (sessions.get(u.getSessionID()).isFull())
				response.setServerResponse(true);
			else
				response.setServerResponse(false);
		}
		
		return response;
	}
	
	/** Returns a vector containing all users stored in the user's session */
	private Response getAllUsers(Action a) {
		Response response = new Response(a.getCommandType());
		User u = getUser (a.getInput());
		
		if (u == null)
			response.setServerResponse(false);
		
		else {
			response.setUserList(sessions.get(u.getSessionID()).getUserList());
			response.setServerResponse(true);
		}
		
		return response;
	}
	
	/** Creates a new guest user 
	 * 		@param a - Action object*/
	private Response createGuestUser (Action a) {
		Response response = new Response (a.getCommandType());
		int id;
		String GUEST_UUID = "GUEST ";
		do  {
			id = rand.nextInt(100001) + 99999;
		} while (guest_database.containsKey(GUEST_UUID + id));
		
		User buffer = new User (GUEST_UUID + id, "", "GUEST", true);
		
		guest_database.put(GUEST_UUID + id, buffer);
		
		response.setUserObject(buffer);
		response.setServerResponse(true);
		return response;
	}
	
	/** Returns the user object associated with the sender's username.
	 * 		@param a - Action object */
 	private Response getUser (Action a) {
 		Response response = new Response (a.getCommandType());
		/* Checks if the user wrapped in the action object is a valid entity in the
		 * database. */
 		User u = getUser (a.getSenderUsername());
		
 		if (u != null) {
 			response.setServerResponse(true);
 			response.setUserObject(u);
 		}
 		else
 			response.setServerResponse(false);
 		
 		return response;
 	}
 	
 	/** Checks if the user wrapped in the action object can make his turn.
 	 * 		@param a - Action */
 	private Response isMyTurn (Action a) {
 		Response response = new Response (a.getCommandType());
 		User u;
 		if(a.getUser() != null)
 			u = getUser (a.getUser().getUsername());
 		else
 		{
 			System.out.println("error in isMyTurn");
 			response.setServerResponse(false);
 			return response;
 		}
		
		if (u == null)
			response.setServerResponse(false);
		
		else {
			int tmp_sess_id = u.getSessionID();
			GameSession session = sessions.get(tmp_sess_id);
				
			if (session != null)
				response.setServerResponse(session.checkTurn(u.getUsername()));
			else
				response.setServerResponse(false);
		}
 		
		return response;
 	}
 	
 	private Response nextTurn (Action a) {
 		Response response = new Response (a.getCommandType());
		User u = getUser (a.getUser().getUsername());
		if (u == null)
			response.setServerResponse(false);
		
		else {
			int tmp_sess_id = u.getSessionID();
			GameSession session = sessions.get(tmp_sess_id);
				
			u.getCharacter().resetUsedPoints();
			if(u.getCharacterType() == Constants.TANK_ID) {
				if(u.getCharacter().checkIfTankSkillOn()){
					if(u.getCharacter().getCooldownTimer() < u.getCharacter().getMaxSkillCooldown()){
						u.getCharacter().setTankSkillReceivedOff();
					}
				}
			} else {
				if(u.getCharacter().checkIfTankSkillOn()) {
	 				u.getCharacter().setTankSkillReceivedOff();
	 			}
			}
	    	u.getCharacter().updateSkillTimer();
	    	if(u.getCharacterType() == Constants.MARKSMAN_ID) {
 				if(u.getCharacter().checkSkillActive()) {
 					u.getCharacter().deactivateSkill();
 				}
 			}
			if (session != null) {
				session.nextTurn();
				response.setServerResponse(true);
			}
			else
				response.setServerResponse(false);
		}
		
		return response;
 	}
 	
 	/** Attacks the player. Once the attack request is executed, then
 	 *  switch the turns of the game session.
 	 * 		@param a - Action object */
 	private Response attack (Action a) {
 		Response response = new Response (a.getCommandType());
 		
 		User attacker = getUser (a.getSenderUsername());
 		User target = getUser (a.getInput());
 		
 		if ((attacker == null || target == null) || (attacker == target)) {
 			System.out.println("Failed");
 			response.setServerResponse(false);
 		}
 		
 		else {
 			Character a1 = attacker.getCharacter();
 			Character t = target.getCharacter();
 			a1.attack(t);
 			
 			if (t.getHealth() <= 0) {
 				// Set the target character as "dead" once its health is set to 0
 				sessions.get(attacker.getSessionID()).incrementDeadCounter();
 				sessions.get(attacker.getSessionID()).updateDeathStatus(target);
 				attacker.getCharacter().setDead(true);
 				System.out.println(target.getNickname() + " has died!");
 			}
 			response.setServerResponse(true);
 			sessions.get(attacker.getSessionID()).nextTurn();
 			attacker.getCharacter().resetUsedPoints();
 			if(attacker.getCharacterType() == Constants.TANK_ID) {
				if(attacker.getCharacter().checkIfTankSkillOn()){
					if(attacker.getCharacter().getCooldownTimer() < attacker.getCharacter().getMaxSkillCooldown()){
						attacker.getCharacter().setTankSkillReceivedOff();
					}
				}
			} else {
				if(attacker.getCharacter().checkIfTankSkillOn()) {
	 				attacker.getCharacter().setTankSkillReceivedOff();
	 			}
			}
 			attacker.getCharacter().updateSkillTimer();
 			if(attacker.getCharacterType() == Constants.MARKSMAN_ID) {
 				if(attacker.getCharacter().checkSkillActive()) {
 					attacker.getCharacter().deactivateSkill();
 				}
 			}	
 			
 			if(sessions.get(attacker.getSessionID()).checkDeathStatus()) {
 				response.setEndGameStatus(true);
 			} else {
 				response.setEndGameStatus(false);
 			}
 		}
 		
 		return response;
 	}
 	
 	private Response useSkill (Action a) {
 		Response response = new Response (a.getCommandType());
 
 		/* Gets the caster user object */
 		User caster = getUser (a.getSenderUsername());
 		
 		if (caster == null) {
 			System.out.println("Failed");
 			response.setServerResponse(false);
 			return response;
 		}
 		
 		if (caster.getCharacterType() == Constants.TANK_ID || caster.getCharacterType() == Constants.SUPPORT_ID) {
			/* Gets the target user object */
 			User target = getUser (a.getInput());
			
			if (target == null) {
	 			System.out.println("Failed");
	 			response.setServerResponse(false);
	 			return response;
	 		}
			
			else {
				caster.getCharacter().useSkill(false, target.getCharacter());
			}
		}
		
		else {
			caster.getCharacter().useSkill(true, caster.getCharacter());
		}
 		
 		response.setUserObject(caster);
 		response.setServerResponse(true);
 		return response;
 	}

 	
 	/** Upgrades user equipment based on the input stored in the action object.
 	 * 		@param a - Action object */
 	private Response upgradeEquipment (Action a) {
 		Response response = new Response (a.getCommandType());
 		User u = getUser (a.getSenderUsername());
 		
 		if (u == null)
 			response.setServerResponse(false);
 		
 		else {
 			if (u.getCharacter().upgradeEquipment(a.getInputIndex())) {
 				response.setUserObject(u);
 				response.setServerResponse(true);
 			}
 			else
 				response.setServerResponse(false);
 		}
 		
 		return response;
 	}
 	
 	/** Allows the user to upgrade his/her abilities.
 	 * 		@param a - Action object */
 	private Response upgradeAbilities (Action a) {
 		Response response = new Response (a.getCommandType());
 		User u = getUser (a.getSenderUsername());
 		
 		if (u == null)
 			response.setServerResponse(false);
 		
 		else {
 			if (u.getCharacter().upgradeAbility(a.getInputIndex())) {
 				response.setUserObject(u);
 				response.setServerResponse(true);
 			}
 			else
 				response.setServerResponse(false);
 		}
 		
 		return response;
 	}
 	
 	/** Checks who won, only should be called when the game is over.
 	 * 		@param a - Action object */
 	public Response checkWhoWon(Action a) {
 		Response response = new Response(a.getCommandType());
 		
 		User user = getUser (a.getSenderUsername());
 		
 		Boolean winningTeam = sessions.get(user.getSessionID()).getWhoWon();
 		
 		response.setServerResponse(winningTeam);
 		
 		return response;
 	}
 	
 	public Response checkGameEnded(Action a) {
 		Response response = new Response(a.getCommandType());
 		
 		User user = getUser (a.getSenderUsername());
 		
 		Boolean gameStatus = sessions.get(user.getSessionID()).checkIfGameEnded();
 		
 		response.setServerResponse(gameStatus);
 		return response;
 	}
 	
 	public Response deleteSession(Action a) {
 		Response response = new Response(a.getCommandType());
 		
 		User user = getUser (a.getSenderUsername());
 		sessions.remove(user.getSessionID());
 		
 		response.setServerResponse(true);
 		
 		return response;
 	}
 	
 	public Response giveResource(Action a) {
 		Response response = new Response(a.getCommandType());
 		User user = getUser (a.getSenderUsername());
 		
 		user.addCurrency(1000);
 		user.getCharacter().setHealth(user.getCharacter().getMaxHealth());
 		user.getCharacter().addEXP(100);
 		
 		response.setServerResponse(true);
 		response.setUserObject(user);
 		
 		return response;
 	}
 	
 	public Response deleteCharacter(Action a) {
 		Response response = new Response(a.getCommandType());
 		User user = getUser (a.getSenderUsername());
 		
 		user.deleteCharacter();
 		
 		response.setServerResponse(true);
 		response.setUserObject(user);
 		
 		return response;
 	}
 	
 	public ClientThread getClientThread (String username) {
 		for (ClientThread ct : clients) {
 			if (ct.getUsername().equals(username))
 				return ct;
 		}
 		return null;
 	}
	
 	public void removeUser(String username) {
 		User target = usr_database.get(username);
		
		/* Checks with guest database if not found */
		if (target == null) {
			target = guest_database.get(username);
			guest_database.remove(target);
		} else {
			usr_database.remove(target);
		}
		
 	}
 	
 	/** Serializes the usr_database hash table into the file, data.dat. */
 	public void saveData () {
 		try {
 			if (oos != null)
 				oos = null;
	 		oos = new ObjectOutputStream (new FileOutputStream (Constants.server_file));
	 		oos.writeObject (usr_database);
	 		oos.flush();
	 		oos.close();
 		}
 		
 		catch (Exception e) {
 			System.out.println("File parse error: " + e.getMessage());
 		}
 	}
 	
	/* GETTERS */
	public HashMap <String, User> getUserDatabase () { return usr_database; }
	
	public HashMap <String, User> getGuestDatabase () { return guest_database; }
	
	/** Scans both of the databases and returns the server-side stored user object.
	 * 		@param username - User's username.
	 * 		@return The user object ref. by username or null if not found. */
	public User getUser (String username) { 
		User target = usr_database.get(username);
		
		/* Checks with guest database if not found */
		if (target == null)
			target = guest_database.get(username);
		return target;
	}
	
	/** Returns the game session bounded by the game's session ID */
	public GameSession getGameSession (Integer ID) { return sessions.get(ID); }
}
