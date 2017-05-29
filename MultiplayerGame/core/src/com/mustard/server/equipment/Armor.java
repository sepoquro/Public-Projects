package com.mustard.server.equipment;

import com.mustard.server.object.Constants;

public class Armor extends Equipment{
	private static final long serialVersionUID = 1L;
	
	protected int resistance; // Blocks specific amount of incoming damage
	
	/** Constructs a new armor object
	 * 		@param r - Base armor resistance for the character */
	public Armor(int r){
		resistance = r;
		equipment_level = 0;
		equipment_name = Constants.ARMOR_NAME;
	}
	
	/* GAMEPLAY */
	
	/** Levels up the equipment to the next level and upgrades its armor rating
	 * 	by a set amount. */
	public void upgrade (){
		if (equipment_level < Constants.MAX_EQUIPMENT_LEVEL) {
			resistance += Constants.ARMOR_BONUS;
			equipment_level += 1;
		}
	}
	
	/* SETTERS */
	
	/** Sets the armor's resistance value to the @param r */
	public void setResistance (int r) { resistance = r; }
	
	/* GETTERS */
	
	/** Returns the armor's resistance value */
	public int getResistance () { return resistance; }
}
