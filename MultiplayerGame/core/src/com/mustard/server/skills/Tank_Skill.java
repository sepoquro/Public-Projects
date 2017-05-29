package com.mustard.server.skills;

import com.mustard.server.object.Constants;

public class Tank_Skill extends Skill {
	private static final long serialVersionUID = 1L;
	
	/* DATA MEMBERS */
	protected int armor_modifier;
	
	public Tank_Skill () {
		skill_name = Constants.TANK_SKILL_NAME;
		skill_cooldown = Constants.TANK_SKILL_COOLDOWN_TIMER;
		current_cooldown = 0;
	}
	
	/* GETTERS */
	
	/** Returns the skill's armor modifier */
	public int getArmorModifier () { return armor_modifier; }
	
	/* SETTERS */
	
	/** Updates the skill's armor modifier to the @param a */
	public void setArmorModifier (int a) { armor_modifier = a; }
}
