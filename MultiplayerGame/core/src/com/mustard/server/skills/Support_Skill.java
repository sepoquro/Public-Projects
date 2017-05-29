package com.mustard.server.skills;

import com.mustard.server.object.Constants;

public class Support_Skill extends Skill {
	private static final long serialVersionUID = 1L;
	
	/* DATA MEMBERS */
	protected int bonus_health = 100; // Heals the desired player with this amount of health
	
	public Support_Skill () {
		skill_name = Constants.SUPPORT_SKILL_NAME;
		skill_cooldown = Constants.SUPPORT_SKILL_COOLDOWN_TIMER;
		current_cooldown = 0;
	}
	
	/* GETTERS */
	/** Returns the skill's bonus health */
	public int getBonusHealth () { return bonus_health; }
	
	/** Sets the skill's bonus health to the @param h */
	public void setBonusHealth (int h) { bonus_health = h; }
	
	@Override
	public void startCooldownTimer () { current_cooldown = skill_cooldown; }
}
