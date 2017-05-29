package com.mustard.server.object.character;

import java.util.Random;

import com.mustard.server.equipment.Armor;
import com.mustard.server.equipment.Boots;
import com.mustard.server.equipment.Weapon;
import com.mustard.server.object.Constants;
import com.mustard.server.object.User;
import com.mustard.server.skills.Tank_Skill;

public class Tank extends Character {
	private static final long serialVersionUID = 1L;
	
	/* DATA MEMBERS */
	private Tank_Skill skill;
	protected Character saved_target;
	protected int original_Health;
	
	/** Constructor for the tank class. Initializes all internal variables
	 * 	specific for this tank class.
	 * 
	 *  @param u - Object of the User that is holding this character*/
	public Tank (User u) {
		usr = u;
		level = Constants.STARTING_LEVEL;
		health = Constants.TANK_BASE_HEALTH;
		maxHealth = Constants.TANK_BASE_HEALTH;
		className = Constants.TANK_NAME;
		actionPoints = Constants.BASE_ACTION_POINTS;
		accuracy = Constants.TANK_BASE_ACCURACY;
		dodge = Constants.TANK_BASE_DODGE;
		isMyTurn = false;
		boots = new Boots(Constants.TANK_BASE_MOVEMENT_RANGE);
		armor = new Armor(Constants.TANK_BASE_ARMOR);
		weapon = new Weapon(Constants.TANK_BASE_ATTACK_STRENGTH , Constants.TANK_BASE_ATTACK_RANGE);
		skill = new Tank_Skill ();
		phases = 0;
		spriteImage = "CharacterSprite/Tank_black.png";
		rotation = 0;
		usedPoints = Constants.TANK_BASE_MOVEMENT_RANGE;
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
			original_Health = target.getHealth();
			target.setTankSkillReceivedOn();
			target.setHealth(1000000000);
			saved_target = target;
			this.skill.startCooldownTimer();
		}
	}
	
	public void deactivateSkill() {
		saved_target.setHealth(original_Health);
		saved_target.setTankSkillReceivedOff();
	}
	
	public void updateSkillTimer() { skill.updateCooldownTimer(); }
	public int getCooldownTimer () { return skill.getCooldownTimer(); }
	public boolean checkSkillActive() { return skill.isActive(); }
	public int getMaxSkillCooldown() { return skill.getSkillCooldown(); }
	
	public void resetUsedPoints() {
		usedPoints = Constants.TANK_BASE_MOVEMENT_RANGE;
	}
}
