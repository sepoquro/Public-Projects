package com.mustard.server.skills;

import com.mustard.server.object.Constants;

public class Marksman_Skill extends Skill{
	private static final long serialVersionUID = 1L;
	
	/* DATA MEMBERS */
	protected int range_modifier = 2;
	protected float accuracy_modifier = 0.5f;
	
	public Marksman_Skill () {
		skill_name = Constants.MARKSMAN_SKILL_NAME;
		skill_cooldown = Constants.MARKSMAN_SKILL_COOLDOWN_TIMER;
		current_cooldown = 0;
	}
	
	
	/* GETTERS */
	
	/** Returns the skill's range modifier */
	public int getRangeModifier () { return range_modifier; }
	
	public float getAccuracyModifier () { return accuracy_modifier; }
	
	/* SETTERS */
	
	/** Updates the skill's range modifier to the @param r */
	public void setRangeModifier (int r) { range_modifier = r; }
	
	@Override
	public void startCooldownTimer () { current_cooldown = skill_cooldown; }
}
