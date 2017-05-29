package com.mustard.server.object.character;

import java.util.Random;

import com.mustard.server.equipment.Armor;
import com.mustard.server.equipment.Boots;
import com.mustard.server.equipment.Weapon;
import com.mustard.server.object.Constants;
import com.mustard.server.object.User;
import com.mustard.server.skills.Soldier_Skill;

public class Soldier extends Character {
	private static final long serialVersionUID = 1L;
	
	/* DATA MEMBERS */
	private Soldier_Skill skill;
	
	/** Constructor for the soldier class. Initializes all internal variables
	 * 	specific for this soldier class.
	 * 
	 *  @param u - Object of the User that is holding this character*/
	public Soldier (User u) {
		usr = u;
		level = Constants.STARTING_LEVEL;
		health = Constants.SOLDIER_BASE_HEALTH;
		maxHealth = Constants.SOLDIER_BASE_HEALTH;
		className = Constants.SOLDIER_NAME;
		actionPoints = Constants.BASE_ACTION_POINTS;
		accuracy = Constants.SOLDIER_BASE_ACCURACY;
		dodge = Constants.SOLDIER_BASE_DODGE;
		isMyTurn = false;
		boots = new Boots(Constants.SOLDIER_BASE_MOVEMENT_RANGE);
		armor = new Armor(Constants.SOLDIER_BASE_ARMOR);
		weapon = new Weapon(Constants.SOLDIER_BASE_ATTACK_STRENGTH, Constants.SOLDIER_BASE_ATTACK_RANGE);
		skill = new Soldier_Skill();
		phases = 0;
		spriteImage = "CharacterSprite/shooter_black.png";
		rotation = 0;
		usedPoints = Constants.SOLDIER_BASE_MOVEMENT_RANGE;
		rand = new Random ();
		tankSkillOn  = false;
		isDead = false;
	}
	
	/** Tells the player to use a class specific special skill on the @param target, or
	 *  on himself if the @param onSelf is set to true.
	 *  	@param onSelf - Determines if the player is using the skill on himself or not
	 * 		@param target - The character object that the player is targeting */
	public void useSkill (boolean onSelf, Character target) {
		System.out.println("current usedPoints when using skill " + usedPoints);
		usedPoints += Constants.SOLDIER_BASE_MOVEMENT_RANGE;
		this.skill.startCooldownTimer();
	}
	
	public void deactivateSkill() {
		boots.setMovementRange(Constants.SOLDIER_BASE_MOVEMENT_RANGE);
	}
	
	public void updateSkillTimer() { skill.updateCooldownTimer(); }
	public int getCooldownTimer () { return skill.getCooldownTimer(); }
	public boolean checkSkillActive() { return skill.isActive(); }
	public int getMaxSkillCooldown() { return skill.getSkillCooldown(); }
	
	public void resetMovementRange() {
		boots.setMovementRange(Constants.SOLDIER_BASE_MOVEMENT_RANGE);
	}
	
	public void resetUsedPoints() {
		usedPoints = Constants.SOLDIER_BASE_MOVEMENT_RANGE;
	}
}
