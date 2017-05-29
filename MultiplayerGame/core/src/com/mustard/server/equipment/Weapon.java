package com.mustard.server.equipment;

import com.mustard.server.object.Constants;

public class Weapon extends Equipment {
	private static final long serialVersionUID = 1L;
	
	/* DATA MEMBERS */
	protected int damage; // The amount of damage a weapon deals
	protected int range; // The range of a weapon
	
	/** Constructs a new weapon object
	 * 		@param d - Base damage for the weapon
	 * 		@param r - Base range for the weapon */
	public Weapon(int d, int r){
		damage = d;
		range = r;
		equipment_level = 0;
		equipment_name = Constants.WEAPON_NAME;
	}
	
	/** Levels up the equipment to the next level and upgrades its weapon damage
	 * 	by a set amount. */
	public void upgrade () {
		if (equipment_level < Constants.MAX_EQUIPMENT_LEVEL) {
			damage += Constants.WEAPON_BONUS;
			equipment_level += 1;
		}
	}
	
	/* SETTERS */
	
	/** Sets the weapon's damage to the @param d */
	public void setDamage(int d){ damage = d; }
	
	/** Sets the weapon's range to the @param r */
	public void setRange(int r){ range = r; }
	
	/** Increments the weapon's attack range by 1 */
	public void incrementAttackRange () { ++range; }
	
	/* GETTERS */
	
	/** Returns the weapon's damage */
	public int getDamage(){ return damage; }
	
	/** Returns the weapon's range */
	public int getRange(){ return range; }
	
}
