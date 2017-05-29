package com.mustard.server.action;

import java.io.Serializable;
import java.util.Vector;

import com.mustard.game.Client.move;
import com.mustard.server.host.ClientThread;
import com.mustard.server.object.User;
import com.mustard.util.Pair;

public class Action implements Serializable  {	
	private static final long serialVersionUID = 1L;
	
	protected String input;
	protected Vector<Pair<Integer, String>> input_list;
	protected int command;
	protected int input_index;
	protected User user;
	protected move mov;
	protected String sender; // Username of the user that is sending this message
	protected ClientThread th;
	
	/** Creates a new action object.
	 * 		@param command - The command specifying how the server should process the requested action. */
	public Action (int command) {
		this.command = command;
	}
	
	/** Creates a new action object.
	 * 		@param command - The command specifying how the server should process the requested action.
	 * 		@param sender - The username of the user sending this object. */
	public Action (int command, String sender) {
		this.command = command;
		this.sender = sender;
	}
	
	/** Creates a new action object.
	 * 		@param command - The command specifying how the server should process the requested action.
	 * 		@param user - The user sending this object. 
	 * 		@param mov - Movement type */
	public Action(int command, User user, move mov)
	{
		this.command = command;
		this.user = user;
		this.mov = mov;
	}
	
	
	/* GETTERS */
	
	public move getMove() { return mov;	}
	
	public ClientThread getClient(){ return th; }
	
	public void setClient(ClientThread th){ this.th = th; }
	
	public int getCommandType () { return command; }
	
	public String getInput () { return input; }
	
	public int getInputIndex () { return input_index; }
	
	public Vector<Pair<Integer, String>> getInputList () { return input_list; }
	
	public User getUser () { return user; }
	
	public String getSenderUsername () { return sender; }
	
	/* SETTERS */
	
	public void setCommandType (int c) { command = c; }
	
	public void setInput (String i) { input = i; }
	
	public void setInputList (Vector <Pair <Integer, String>> l) { input_list = l; }
	
	public void setUserObject (User u) { user = u; }
	
	public void setSenderName (String s) { sender = s; }
	
	public void setInputIndex (int i) { input_index = i; }
	
}
