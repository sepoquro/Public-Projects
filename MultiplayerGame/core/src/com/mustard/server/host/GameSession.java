package com.mustard.server.host;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import com.mustard.server.object.Constants;
import com.mustard.server.object.User;
import com.mustard.util.Pair;

public class GameSession {
	/* DATA MEMBERS */
	protected final int session_id;									// Session's unique ID
	protected boolean isFull;										// Set to true if the server has reached max capacity.
	protected boolean gameEnd;
	protected int player_count;
	protected int game_capacity;									// MAX amount of players that could join this game
	protected int player_turn;
	protected int dead_count;
	protected int redTeamCount;
	protected int blueTeamCount;
	protected Vector <User> usr_list;								// List of active players in this session
	protected Vector<User> redTeam;
	protected Vector<User> blueTeam;
	protected Vector <ClientThread> client_list;					// List of client threads in this session
	protected Vector <Pair <Integer, Integer>> usr_coordinates;		// Keeps track of where all players are in the entire map
	protected Server server;										// Server object
	protected Queue <User> usr_queue;
	
	public GameSession (Server s, Integer ID) {
		isFull = false;
		gameEnd = false;
		session_id = ID.intValue();
		player_count = 0;
		player_turn = 0;
		dead_count = 0;
		redTeamCount = 0;
		blueTeamCount = 0;
		usr_queue = new LinkedList <User> ();
		game_capacity = Constants.MAX_SERVER_CAP;
		usr_list = new Vector<User> ();
		client_list = new Vector<ClientThread> ();
		usr_coordinates  = new Vector <Pair <Integer, Integer>> ();
		redTeam = new Vector<User>();
		blueTeam = new Vector<User>();
		server = s;
		System.out.println("Created a new session!");
	}
	
	/* LOGIC */
	
	/** Adds the user ref. by @param u into this game session. Once the game's player count reaches the
	 * 	game's player capacity, then a separate function is called, switching all players into the
	 *  GAME state.
	 * 		@param u - The new user object that is going to be added into the game */
	public void addUserToGame (User u, ClientThread th) {
		if (!isFull) {
			if (player_count < Constants.MAX_SERVER_CAP / 2) {
				u.setTeam(true); // Red team
				if (!u.isGuest()) redTeam.add(server.getUserDatabase().get(u.getUsername()));
				else redTeam.add(server.getGuestDatabase().get(u.getUsername()));
				redTeamCount++;
			}
				
			else {
				u.setTeam(false); // Blue team
				if (!u.isGuest()) blueTeam.add(server.getUserDatabase().get(u.getUsername()));
				else blueTeam.add(server.getGuestDatabase().get(u.getUsername()));
				blueTeamCount++;
			}
				
			
			if (!u.isGuest())
				usr_list.add(server.getUserDatabase().get(u.getUsername()));
			else
				usr_list.add(server.getGuestDatabase().get(u.getUsername()));
			
			if (th != null)
				client_list.add(th);
			
			usr_queue.add(u);
			
			++player_count;
			++dead_count;
			System.out.println("Added " + usr_list.get(player_count - 1).getUsername());
			
			System.out.println("Added a new player to session " + session_id);
			if (player_count >= game_capacity) {
				System.out.println("Can start game now...");
				isFull = true;
				renderTurns ();
			}
		}
	}
	
	/** Renders the coordinates list for all users in this game */
	public void renderUserCoordinates () {
		for (User u : usr_list) {
			usr_coordinates.add(new Pair <Integer, Integer> (u.getCharacter().getXCoord(), u.getCharacter().getYCoord()));
		}
	}
	
	public void renderTurns () {
		for (int i = 0; i < usr_list.size(); ++i) {
			if (i == player_turn && usr_list.get(i).getCharacter().getHealth() > 0)
				usr_list.get(i).getCharacter().setPlayerTurnStatus(true);
			else
				usr_list.get(i).getCharacter().setPlayerTurnStatus(false);
		}
	}
	
	public void nextTurn () {
			
		++player_turn;
		
		if (player_turn >= Constants.MAX_SERVER_CAP) {
			player_turn = 0;
		}
		/* Skips player's turn once this player is dead, thus calling this function again. */
		if (usr_list.get(player_turn).getCharacter().getHealth() <= 0)
			nextTurn();
		else
			renderTurns ();
		
		if(gameEnd){
			return;
		}
	}
	
	public void updateDeathStatus(User deadUser) {
		for(User u : redTeam) {
			if(u.getUsername().equals(deadUser.getUsername())) {
				redTeamCount--;
				u.getCharacter().setDead(true);
				System.out.println("redteam lose 1");
			}
		}
		for(User u : blueTeam) {
			if(u.getUsername().equals(deadUser.getUsername())) {
				blueTeamCount--;
				u.getCharacter().setDead(true);
				System.out.println("blueteam lose 1");
			}
		}
		
		System.out.println("redTeamCount : " + redTeamCount + " blueTeamCount: " + blueTeamCount);
	}
	
	public boolean checkDeathStatus() {
		if(redTeamCount == 0 || blueTeamCount == 0) {
			gameEnd = true;
			return true;
		}
		else return false;
	}
	
	public boolean getWhoWon() {
		if(redTeamCount != 0) return true;
		else return false;
	}
	
	public boolean checkIfGameEnded() {
		return gameEnd;
	}
	
	public boolean allDead () {
		for (User u : usr_list) {
			if (!u.getCharacter().isDead())
				return false;
		}
		return true;
	}
	
	/** Checks if the user associated with the @param username
	 *  can make his/her move. */
	public boolean checkTurn (String username) {
		for (User u : usr_list) {
			if (u.getUsername().equals(username)) {
				if(u.getCharacter() != null)
					return u.getCharacter().getPlayerTurnStatus();
			}
		}
		
		return false;
	}
	
	/** Removes a player from the server.
	 * 		@param u - Username of the user to be removed*/
	public void removePlayer (String u) {
		User usr = server.getUser(u);
		
		// Remove from red team
		if (usr.getTeam()) {
			redTeam.remove(usr);
			--redTeamCount;
		}
		else {
			blueTeam.remove(usr);
			--blueTeamCount;
		}
		
		usr_list.remove(usr);
		--player_count;
	}
 	
	/* GETTERS */
	
	/** Returns the game session's player count */
	public int getPlayerCount () { return player_count; }
	
	/** Returns the game's list of users  */
	public Vector <User> getUserList () { return usr_list; }
	
	/** Returns the list of clients  */
	public Vector <ClientThread> getClientList () {return client_list; }
	
	/** Returns a list of coordinates specifying where all players are at in the game board */
	public Vector <Pair <Integer, Integer>> getUserCoordinatesList () { return usr_coordinates; }
	
	/** Determines if the server is full or not */
	public boolean isFull () { return isFull; }
	
	/** Returns the session's unique ID */
	public int getSessionId () { return session_id; }
	
	/** Returns the amount of dead players in the game */
	public int getDeadCounter () { return dead_count; }
	
	/* SETTERS */
	
	/** Increments the count for the amount of dead players in this session */
	public void incrementDeadCounter () { ++dead_count; }
}
