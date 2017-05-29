package com.mustard.server.skills;

import com.mustard.server.object.Constants;

public class Soldier_Skill extends Skill {
	private static final long serialVersionUID = 1L;
	
	/* DATA MEMBERS */
	protected double attack_modifier;
	
	public Soldier_Skill () {
		skill_name = Constants.SOLDIER_SKILL_NAME;
		skill_cooldown = Constants.SOLDIER_SKILL_COOLDOWN_TIMER;
		current_cooldown = 0;
	}
	
	/* GETTERS */
	
	/** Returns the skill's attack modifier */
	public double getAttackModifier () { return attack_modifier; }
	
	/* SETTERS */
	
	/** Updates the skill's attack modifier to the @param a */
	public void setAttackModifier (double a) { attack_modifier = a; }
	
	@Override
	public void startCooldownTimer () { current_cooldown = skill_cooldown; }
}
