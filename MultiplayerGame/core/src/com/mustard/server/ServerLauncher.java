package com.mustard.server;

import java.io.IOException;

import com.mustard.server.host.Server;

public class ServerLauncher {
	private static Server server;
	
	public static void main (String [] args) {
		
		// DEBUG LINE
		args = new String [1];
		args[0] = "8000";
		
		if (args.length < 1) {
			System.out.println("Error: Invald parameters.");
			System.out.println("Correct usage: java -jar server.jar [port]");
			return;
		}
		
		try {
			int port = Integer.parseInt(args[0]); // Gathers the IP address passed in the parameter.
			server = new Server (port);
		}
		
		catch (NumberFormatException e) {
			System.out.println("Error: Unable to parse the inputted port.");
			System.out.println("Correct usage: java -jar server.jar [port]");
		}
		
		catch (IOException e) {
			System.out.println("IO Exception while creating server: " + e.getMessage());
		}
		
		catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		finally {
			server.saveData();
		}
		
	}
}
