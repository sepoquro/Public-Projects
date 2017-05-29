package com.mustard.server.object.character;

import java.util.Random;

import com.mustard.server.equipment.Armor;
import com.mustard.server.equipment.Boots;
import com.mustard.server.equipment.Weapon;
import com.mustard.server.object.Constants;
import com.mustard.server.object.User;
import com.mustard.server.skills.Marksman_Skill;

public class Marksman extends Character {
	private static final long serialVersionUID = 1L;
	
	/* DATA MEMBERS */
	protected Marksman_Skill skill;
	
	/** Constructor for the marksman class. Initializes all internal variables
	 * 	specific for this marksman class.
	 * 
	 *  @param u - Object of the User that is holding this character*/
	public Marksman (User u) {
		usr = u;
		level = Constants.STARTING_LEVEL;
		health = Constants.MARKSMAN_BASE_HEALTH;
		maxHealth = Constants.MARKSMAN_BASE_HEALTH;
		className = Constants.MARKSMAN_NAME;
		actionPoints = Constants.BASE_ACTION_POINTS;
		accuracy = Constants.MARKSMAN_BASE_ACCURACY;
		dodge = Constants.MARKSMAN_BASE_DODGE;
		isMyTurn = false;
		boots = new Boots(Constants.MARKSMAN_BASE_MOVEMENT_RANGE);
		armor = new Armor(Constants.MARKSMAN_BASE_ARMOR);
		weapon = new Weapon(Constants.MARKSMAN_BASE_ATTACK_STRENGTH, Constants.MARKSMAN_BASE_ATTACK_RANGE);
		skill = new Marksman_Skill ();
		phases = 0;
		spriteImage = "CharacterSprite/sniper_mask.png";
		rotation = 0;
		usedPoints = Constants.MARKSMAN_BASE_MOVEMENT_RANGE;
		rand = new Random ();
		tankSkillOn  = false;
		isDead = false;
	}
	
	/** Tells the player to use a class specific special skill on the @param target, or
	 *  on himself if the @param onSelf is set to true.
	 *  	@param onSelf - Determines if the player is using the skill on himself or not
	 * 		@param target - The character object that the player is targeting */
	public void useSkill (boolean onSelf, Character target) {
		if(skill.isActive())
		{
			weapon.setRange(Constants.MARKSMAN_BASE_ATTACK_RANGE * skill.getRangeModifier());
			//accuracy = (int) (getAccuracy() * skill.getAccuracyModifier());
		//	System.out.println("weaponRange: " + weapon.getRange() + " accuracy: " + accuracy);
			skill.startCooldownTimer();	
		}
	}
	
	public void deactivateSkill() {
		weapon.setRange(Constants.MARKSMAN_BASE_ATTACK_RANGE);
		accuracy = Constants.MARKSMAN_BASE_ACCURACY;
	}
	
	public void updateSkillTimer() { skill.updateCooldownTimer(); }
	public int getCooldownTimer () { return skill.getCooldownTimer(); }
	public boolean checkSkillActive() { return skill.isActive(); }
	public int getMaxSkillCooldown() { return skill.getSkillCooldown(); }
	
	public void resetUsedPoints() {
		usedPoints = Constants.MARKSMAN_BASE_MOVEMENT_RANGE;
	}
}
