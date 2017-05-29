/** Contains the basic structure/functions that defines a generic character skill */
package com.mustard.server.skills;

import java.io.Serializable;

public abstract class Skill implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* Data members */
	protected int skill_cooldown;
	protected int current_cooldown;
	protected String skill_name;
	
	/* LOGIC */
	
	/** Decrements the skill's cooldown timer after the player's turn has passed.
	 *  The timer will not go below zero. */
	public void updateCooldownTimer () {
		if (current_cooldown > 0)
			--current_cooldown;
	}
	
	/** Starts the skill's cooldown timer */
	public void startCooldownTimer () { current_cooldown = skill_cooldown; }
	
	/* GETTERS */
	
	/** Returns the skill's current cooldown timer (amount of turns left before skill can be used).
	 *  If the skill's cooldown timer = 0, then the skill can be used. */
	public int getCooldownTimer () { return current_cooldown; }
	
	/** Returns the skill's MAX cooldown */
	public int getSkillCooldown () { return skill_cooldown; }
	
	/** Returns the skill's name */
	public String getSkillName () { return skill_name; }
	
	/** Determines if this skill can be used or not depending on the
	 * status of the cooldown timer */
	public boolean isActive () { return (current_cooldown > 0 ? false : true); }
	
	/* SETTERS */
	
	/** Updates the skill's name to the @param n */
	public void setSkillName (String n) { skill_name = n; }
	


}
