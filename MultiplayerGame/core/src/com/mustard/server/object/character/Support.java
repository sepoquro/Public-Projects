package com.mustard.server.object.character;

import java.util.Random;

import com.mustard.server.equipment.Armor;
import com.mustard.server.equipment.Boots;
import com.mustard.server.equipment.Weapon;
import com.mustard.server.object.Constants;
import com.mustard.server.object.User;
import com.mustard.server.skills.Support_Skill;

public class Support extends Character {
	private static final long serialVersionUID = 1L;
	
	/* DATA MEMBERS */
	private Support_Skill skill;
	
	/** Constructor for the support class. Initializes all internal variables
	 * 	specific for this support class.
	 * 
	 *  @param u - Object of the User that is holding this character*/
	public Support (User u) {
		usr = u;
		level = Constants.STARTING_LEVEL;
		health = Constants.SUPPORT_BASE_HEALTH;
		maxHealth = Constants.SUPPORT_BASE_HEALTH;
		className = Constants.SUPPORT_NAME;
		actionPoints = Constants.BASE_ACTION_POINTS;
		accuracy = Constants.SUPPORT_BASE_ACCURACY;
		dodge = Constants.SUPPORT_BASE_DODGE;
		isMyTurn = false;
		boots = new Boots(Constants.SUPPORT_BASE_MOVEMENT_RANGE);
		armor = new Armor(Constants.SUPPORT_BASE_ARMOR);
		weapon = new Weapon(Constants.SUPPORT_BASE_ATTACK_STRENGTH, Constants.SUPPORT_BASE_ATTACK_RANGE);
		skill = new Support_Skill();
		phases = 0;
		spriteImage = "CharacterSprite/support_white.png";
		rotation = 0;
		usedPoints = Constants.SUPPORT_BASE_MOVEMENT_RANGE;
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
			if(target.getHealth() + skill.getBonusHealth() >= target.getMaxHealth()) {
				target.setHealth(target.getMaxHealth());
			} else {
				target.setHealth(target.getHealth() + skill.getBonusHealth());
			}	
			skill.startCooldownTimer();
		}
	}
	
	public void deactivateSkill() {
		
	}
	
	public void updateSkillTimer() { skill.updateCooldownTimer(); }
	public int getCooldownTimer () { return skill.getCooldownTimer(); }
	public boolean checkSkillActive() { return skill.isActive(); }
	public int getMaxSkillCooldown() { return skill.getSkillCooldown(); }
	
	public void resetUsedPoints() {
		usedPoints = Constants.SUPPORT_BASE_MOVEMENT_RANGE;
	}
}
