package com.mustard.server.equipment;

import java.io.Serializable;

public abstract class Equipment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* DATA MEMBERS */
	protected String equipment_name;
	protected int equipment_level;
	
	/* GAMEPLAY */
	/** Increases the equipment's level to the next level
	 * 	and upgrade its bonuses by a certain amount.
	 * 
	 * 	To be overwritten in inherited classes.*/
	public abstract void upgrade ();
	
	/* GETTERS */
	
	/** Returns the name of the equipment */
	public String getName () { return equipment_name; }
	
	/** Returns the current level of this equipment */
	public int getEquipmentLevel () { return equipment_level; }
	
	/* SETTERS */
	
	/** Sets the name of the equipment to the @param e */
	public void setEquipmentName (String e) { equipment_name = e; }
	
	/** Sets the level of the equipment to the @param l */
	public void setEquipmentLevel (int l) { equipment_level = l; }
}
