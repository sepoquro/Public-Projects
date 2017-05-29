package com.mustard.server.equipment;

import com.mustard.server.object.Constants;

public class Boots extends Equipment {
	private static final long serialVersionUID = 1L;
	
	/* DATA MEMEBERS */
	protected int movement; // Number of tiles a character can move
	
	/** Constructs a new boots object
	 * 		@param m - The initial base movement radius for the character */
	public Boots(int m){
		movement = m;
		equipment_level = 0;
		equipment_name = Constants.BOOTS_NAME;
	}
	
	/* GAMEPLAY */
	
	/** Levels up the equipment to the next level and upgrades its movement radius
	 * 	by a set amount. */
	public void upgrade () {
		if (equipment_level < Constants.MAX_EQUIPMENT_LEVEL) {
			movement += Constants.BOOTS_BONUS;
			equipment_level += 1;
		}
	}
	/* SETTERS */
	
	/** Sets the boot's movement radius to the @param m */
	public void setMovementRange (int m) { movement = m; }
	
	/** Increments the boot's movement range by one */
	public void incrementMovementRange () { ++movement; }
	
	/* GETTERS */
	
	/** Returns the boot's movement radius */
	public int getMovementRange () { return movement; }
}
