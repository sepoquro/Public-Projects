package com.mustard.server.host;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Vector;

import com.mustard.server.Commands;
import com.mustard.server.action.Action;
import com.mustard.server.action.Response;
import com.mustard.server.object.User;

public class ClientThread extends Thread implements Serializable {
	private static final long serialVersionUID = 1L;
	private Socket s;
	private Server server;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	protected String username;
	protected User user;
	protected boolean threadStatus;
	
	public ClientThread (Socket s, Server server) {
		this.s = s;
		this.server = server;
		username = "";
		out = null;
		in = null;
		threadStatus = false;
		this.start();
	}
	
	public void run () {
		try {
			Action action;
			while (in == null) {
				in = new ObjectInputStream (s.getInputStream());
				action = (Action) in.readObject();
				
				if (action.getSenderUsername() != null && server.getUser(action.getSenderUsername()) != null) {
					user = server.getUser(action.getSenderUsername());
					// System.out.println("Acquired user object");
				}
				Response r = server.parseAction(action, this);
				if (out == null && r != null) {
					out = new ObjectOutputStream (s.getOutputStream());
					out.writeObject((Response)r);
					out.flush();
				}
				in = null;
				out = null;
			}
		}
		
		catch (IOException e) {
			System.out.println("A user has disconnected from the server");
		}
		
		catch (ClassNotFoundException e) {
			System.out.println("A user has disconnected from the server");
		}
		
		finally  {
			if (user != null) {
				GameSession sess = server.getGameSession(user.getSessionID());
				user = server.getUser(user.getUsername());
				
				if (sess != null) {
					if (sess.isFull()) {
						if (user.getCharacter() != null && !user.getCharacter().isDead() && sess != null) {
							user.getCharacter().setHealth(0);
							sess.incrementDeadCounter();
							sess.updateDeathStatus(user);
							sess.checkDeathStatus();
							
							System.out.println("Killed user because he/she exited the game prematurely");
							
							if (user.getCharacter().getPlayerTurnStatus()) {
								sess.nextTurn();
								System.out.println("Forcing user to skip his turn because he/she is dead");
							}
						}
					}
					
					else {
						sess.removePlayer(user.getUsername());
					}
				}
			}
		}
	}

	public void updateScreen(Vector<User> usr_list) {
		Response moveResponse = new Response(Commands.SEND_MOVE_FORCE);
		moveResponse.setUserList(usr_list);
		try {
			System.out.println("Attempting to update screen for " + username + "...");
			out = new ObjectOutputStream (s.getOutputStream());
			out.writeObject((Response)moveResponse);
			out.flush();
			out = null;
			System.out.println("Sent update screen command to client " + username);
		} catch (IOException e) {
			System.out.println("Fatal Error while sending response to client " + s.getInetAddress() + ": " + e.getMessage());
		}
	}
	
	/* GETTERS */
	public String getUsername () { return username; }
	
	/* SETTERS */
	public void setUsername (String u){ username = u; }
	
	public void setUser (User u) { user = u; }
	
	public void endThread (boolean status) { threadStatus = true; }
	
}
