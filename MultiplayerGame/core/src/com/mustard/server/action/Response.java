/** This is the data that will be sent back to the client */

package com.mustard.server.action;

import java.io.Serializable;
import java.util.Vector;

import com.mustard.server.host.ClientThread;
import com.mustard.server.object.User;
import com.mustard.util.Pair;

public class Response implements Serializable {
	private static final long serialVersionUID = 1L;
	protected int command;							// Stores the command tied to the action
	protected boolean response;						// Determines if the server has successfully processed the info. without errors
	protected User user;							// Updated user object
	protected Vector<Pair<Integer, String>> output;	// A list of requested data
	protected Vector<User> users;
	protected boolean endGameStatus;
	protected String error_message;
	protected ClientThread ct;
	
	/** Creates a new response object */
	public Response (int command) {
		this.command = command;
		response = false;
		user = null;
		output = null;
	}
	
	/* SETTERS */
	public void setCommandType (int c) { command = c; }
	
	public void setServerResponse (boolean r) { response = r; }
	
	public void setUserObject (User u) { user = u; }
	
	public void setOutputDataList (Vector <Pair <Integer, String>> l) { output = l; }
	
	public void setUserList(Vector<User> users) { this.users = users; }
	
	public void setEndGameStatus(boolean end) { 
		endGameStatus = end;
	}
	
	public void setErrorMessage (String m) { error_message = m; }
	
	public void setClientThread (ClientThread ct) { this.ct = ct; }
	
	/* GETTERS */
	public int getCommandType () { return command; }
	
	public boolean getServerResponse () { return response; }
	
	public User getUserObject () { return user; }
	
	public Vector<Pair<Integer, String>> getOutputDataList () { return output; }
	
	public Vector<User> getUsersList() { return users; }
	
	public boolean getEndGameStatus() { return endGameStatus; }
	
	public String getErrorMessage() { return error_message; }
	
	public ClientThread getClientThread() { return ct; }
}
