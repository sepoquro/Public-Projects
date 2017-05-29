package com.mustard.server.object.character;

import java.io.Serializable;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mustard.server.Commands;
import com.mustard.server.equipment.Armor;
import com.mustard.server.equipment.Boots;
import com.mustard.server.equipment.Weapon;
import com.mustard.server.object.User;

public abstract class Character implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected User usr; // Stores a reference to the user who owns this specific character
	protected String className; // Name of the character class
	protected String spriteImage;
	protected Sprite sprite;
	/* Movement coordinates */
	public int x; // Character's x coord in the grid
	public int y; // Character's y coord in the grid
	
	/* Player stats */
	protected int level;
	protected int health;
	protected int maxHealth;
	protected int actionPoints;
	protected int accuracy;
	protected int dodge;
	protected int experience;
	protected int phases;
	protected int rotation;
	protected boolean tankSkillOn;
	protected int battleHealth;
	protected boolean isGameEnd = false;
	protected boolean isWon;
	protected boolean isDead;
	
	/* Player equipment */
	protected Boots boots;
	protected Armor armor;
	protected Weapon weapon;
	
	/* Turn status */
	protected boolean isMyTurn;
	protected int usedPoints;
	
	/* Util */
	transient Random rand;
	
	/* GAMEPLAY */
	/** Moves the character to a specificed location of the grid 
	 * 		@param _x - x coord
	 * 		@param _y - y coord*/
	public void move (int _x, int _y) {
		x = _x;
		y = _y;
	}
	
	/** Tells the player to attack another player.
	 *  	@param target - The character object that this player is targeting */
	public void attack (Character target) {
		double chance = rand.nextDouble();
		
		double attack_chance = Math.pow(accuracy / 100, level);
		
		if (chance <= attack_chance) {
			target.decrementHealth(this.getAttackStrength() - target.getArmorValue());
			System.out.println(this.usr.getNickname() + " attacked " + target.getUser().getNickname() + " (did " + (this.getAttackStrength() - target.getArmorValue()) + " damage)");
		}
		else {
			System.out.println(this.usr.getNickname() + " missed " + target.getUser().getNickname());
		}
	}
	
	/** Tells the player to use a class specific special skill on the @param target, or
	 *  on himself if the @param onSelf is set to true.
	 * 		@param target - The character object that the player is targeting */
	public abstract void useSkill (boolean onSelf, Character target);
	
	public abstract void deactivateSkill();
	
	/** Upgrades one of the character's abilities based on the @param index.
	 * 		@param index - Desired ability being upgraded */
	public boolean upgradeAbility (int index) {
		boolean success = false;
		switch (index) {
			case Commands.ABILITY_ATTACK_RANGE:
				if (actionPoints > 0) {
					weapon.incrementAttackRange();
					--actionPoints;
					success = true;
				}
				break;
			case Commands.ABILITY_MOVEMENT_RANGE:
				if (actionPoints > 0) {
					boots.incrementMovementRange();
					++usedPoints;
					--actionPoints;
					success = true;
				}
				break;
			case Commands.ABILITY_DODGE:
				if (actionPoints > 0) {
					++dodge;
					--actionPoints;
					success = true;
				}
				break;
			default:
				break;
		}
		return success;
	}
	
	/** Upgrades the character's equipment 
	 * 		@param index - Equipment being upgraded*/
	public boolean upgradeEquipment (int index) {
		boolean success = false;
		switch (index) {
			case Commands.EQUIPMENT_ARMOR:
				if (usr.getCurrency() >= 1000) {
					armor.upgrade();
					usr.setCurrency(usr.getCurrency() - 1000);
					success = true;
				}
				break;
			case Commands.EQUIPMENT_WEAPON:
				if (usr.getCurrency() >= 1000) {
					weapon.upgrade();
					usr.setCurrency(usr.getCurrency() - 1000);
					success = true;
				}
				break;
			default:
				break;
		}
		return success;
	}
	
	
	/* SETTER METHODS */
	
	public boolean getIsWon() {return isWon; }
	
	public void setIsWon(boolean iswon) {isWon = iswon;}
	
	
	
	public void setIsEnd(boolean isend) {isGameEnd = isend;}
	/** Increments the player's character level by 1 */
	public void incrementLevel () { 
		level += 1; 
		if(level >= 10) {
			level = 10;
		}
	}
 	
	/** Sets the player's level to the @param l */
	public void setLevel (int l) { level = l; }
	
	/** Sets the character's health to the @param h */
	public void setHealth (int h) { health = h; }
	
	/** Sets the character's actionPoints to the @param a */
	public void setActionPoints (int a) { actionPoints = a; }
	
	/** Sets the character's accuracy to the @param a */
	public void setAccuracy (int a) { accuracy = a; }
	
	/** Sets the character's dodge to the @param d */
	public void setDodge (int d) { dodge = d; }
	
	/** Sets the player's current turn status to the @param t */
	public void setPlayerTurnStatus (boolean t) { isMyTurn = t; }
	
	/** Sets the player's experience to the @param e */
	public void addEXP (int e) { 
		experience += e; 
		switch(experience) {
			case 100 : 
				incrementLevel();
				actionPoints++;
				break;
			case 200 :
				incrementLevel();
				actionPoints++;
				break;
			case 300 :
				incrementLevel();
				actionPoints++;
				break;
			case 400 :
				incrementLevel();
				actionPoints++;
				break;
			case 500 :
				incrementLevel();
				actionPoints++;
				break;
			case 600 :
				incrementLevel();
				actionPoints++;
				break;
			case 700 :
				incrementLevel();
				actionPoints++;
				break;
			case 800 :
				incrementLevel();
				actionPoints++;
				break;
			case 900 :
				incrementLevel();
				actionPoints++;
				break;
			case 1000 :
				incrementLevel();
				actionPoints++;
				break;
		}
		if(experience>= 1000){
			experience = 1000;
		}
	}
	
	/** Decrements the character's health by an amount specified by the @param h.
	 *  If the character's health reaches below 0, then it is set to 0.
	 * 		@param h - Amount of health being decremented.  */
	public void decrementHealth (int h) {
		health -= h;
		if (health < 0)
			health = 0;
	}
	
	/** Switches the player's phase.
	 * 		Note:
	 * 			Phase 0 - Waiting phase
	 * 			Phase 1 - Move phase
	 * 			Phase 2 - Attack phase */
	public void switchPhase () {
		phases++;
		if (phases >= 3)
			phases = 0;
	}
	
	/** Sets the character's current rotation */
	public void setRotation (int r) { rotation = r; }
	
	public void setPointsToZero() { usedPoints = 0; }
	
	/* GETTER METHODS */
	
	/** Returns the character's class name*/
	public String className () { return className; }
	
	/** Returns the character's user object */
	public User getUser () { return usr; }
	
	/** Returns the character's level */
	public int getLevel () { return level; }
	
	/** Return the character's health */
	public int getHealth () { return health; }
	
	/** Return the character's max health */
	public int getMaxHealth () { return maxHealth; }
	
	/** Returns the character's action points */
	public int getActionPoints () { return actionPoints; }
	
	/** Returns the character's accuracy */
	public int getAccuracy () { return accuracy; }
	
	/** Returns the character's dodge chance */
	public int getDodge() { return dodge; }
	
	/** Returns the player's turn status */
	public boolean getPlayerTurnStatus() { return isMyTurn; }
	
	/** Returns the player's attack strength */
	public int getAttackStrength () { return weapon.getDamage(); }
	
	/** Returns the player's attack range */
	public int getAttackRange () { return weapon.getRange(); }
	
	/** Returns the player's movement range */
	public int getMovementRange () { return boots.getMovementRange(); }
	
	/** Returns the player's armor rating */
	public int getArmorValue () { return armor.getResistance(); }
	
	/** Returns the player's weapon object */
	public Weapon getWeapon () { return weapon; }
	
	/** Returns the player's boots object */
	public Boots getBoots () { return boots; }
	
	/** Returns the player's armor object */
	public Armor getArmor () { return armor; }
	
	/** Returns the player's EXP */
	public int getEXP () { return experience; }
	
	/** Gets the character's x coordinates */
	public int getXCoord () { return x; }
	
	/** Gets the character's y coordinates */
	public int getYCoord () { return y; }
	
	/** Gets the character's x coordinates */
	public void setXCoord (int xIn) { x = xIn; }
	
	/** Gets the character's y coordinates */
	public void setYCoord (int yIn) { y = yIn; }
	
	/** Get the character Image **/
	public String getImage() { return spriteImage;}
	
	/** Get the sprite associated with character **/
	public Sprite getSprite() { return sprite; }
	
	/** Get the character's current attack phase  */
	public int getAttackPhase () { return phases; }
	
	/** Gets the character's current rotation */
	public int getRotation () { return rotation; }
	
	/** Determines if the character is dead */
	public boolean isDead () { return health <= 0; }
	
	public boolean movementDone() {
		if(usedPoints==0){
			return true;
		} else {
			return false;
		}
	}
	
	public void usedMove(){
			--usedPoints;
	}
	
	public int getRemainingPoints(){
		return usedPoints;
	}
	
	public abstract void resetUsedPoints();

	public void addMovementPoints(int i){
		usedPoints += i;
	}
	
	public void setMovementPoints(int i) {
		usedPoints = i;
	}
	
	public abstract void updateSkillTimer();
	public abstract int getCooldownTimer ();
	public abstract boolean checkSkillActive();
	public abstract int getMaxSkillCooldown();
	
	public boolean checkIfTankSkillOn() {
		return tankSkillOn;
	}
	
	public void setTankSkillReceivedOn() {
		tankSkillOn = true;
		battleHealth = health;
	}
	
	public void setTankSkillReceivedOff() {
		tankSkillOn = false;
		health = battleHealth;
	}
	
	public boolean checkDead() {
		return isDead;
	}
	
	public void setDead(boolean dead) {
		isDead = dead;
	}
	
	public boolean getGameStatus() { return isGameEnd; }
	
	public void setGameStatus(boolean gameStatus) { isGameEnd = gameStatus; }
}
