package com.mustard.server.object;

public class Constants {
	/** Server constants */
	public final static int MAX_SERVER_CAP = 4;
	public final static int SERVER_PING_RATE = 20;
	public final static String server_file = "data.dat";
	
	/** Player stats */
	public final static int MAX_PLAYER_LEVEL = 10;
	
	/** Class specific constants */
	/* Class ID */
	public final static int SOLDIER_ID = 0;
	public final static int TANK_ID = 1;
	public final static int SUPPORT_ID = 2;
	public final static int MARKSMAN_ID = 3;
	
	/* Universal */
	public final static int BASE_ACTION_POINTS = 1;
	public final static int STARTING_LEVEL = 1;
	
	/* Soldier */
	public final static String SOLDIER_NAME = "Commander Sheepherd";
	public final static String SOLDIER_SKILL_NAME = "Run 'n' Gun";
	public final static int SOLDIER_BASE_HEALTH = 300;
	public final static int SOLDIER_BASE_ACCURACY = 100;
	public final static int SOLDIER_BASE_DODGE = 0;
	public final static int SOLDIER_BASE_ATTACK_STRENGTH = 140;
	public final static int SOLDIER_BASE_ARMOR = 20;
	public final static int SOLDIER_BASE_MOVEMENT_RANGE = 14;
	public final static int SOLDIER_BASE_DAMAGE = 300;
	public final static int SOLDIER_BASE_ATTACK_RANGE = 5;
	public final static int SOLDIER_SKILL_COOLDOWN_TIMER = 3;
	
	/* Tank */
	public final static String TANK_NAME = "The Enforcer";
	public final static String TANK_SKILL_NAME = "Force Field";
	public final static int TANK_BASE_HEALTH = 500;
	public final static int TANK_BASE_ACCURACY = 100;
	public final static int TANK_BASE_DODGE = 0;
	public final static int TANK_BASE_ATTACK_STRENGTH = 400;
	public final static int TANK_BASE_ARMOR = 40;
	public final static int TANK_BASE_MOVEMENT_RANGE = 12;
	public final static int TANK_BASE_DAMAGE = 380;
	public final static int TANK_BASE_ATTACK_RANGE = 2;
	public final static int TANK_SKILL_COOLDOWN_TIMER = 4;
	
	/* Support */
	public final static String SUPPORT_NAME = "Cake";
	public final static String SUPPORT_SKILL_NAME = "Morphine";
	public final static int SUPPORT_BASE_HEALTH = 300;
	public final static int SUPPORT_BASE_ACCURACY = 100;
	public final static int SUPPORT_BASE_DODGE = 0;
	public final static int SUPPORT_BASE_ATTACK_STRENGTH = 100;
	public final static int SUPPORT_BASE_ARMOR = 0;
	public final static int SUPPORT_BASE_MOVEMENT_RANGE = 12;
	public final static int SUPPORT_BASE_DAMAGE = 200;
	public final static int SUPPORT_BASE_ATTACK_RANGE = 5;
	public final static int SUPPORT_SKILL_COOLDOWN_TIMER = 4;
	
	/* Marksman */
	public final static String MARKSMAN_NAME = "Kerrigan";
	public final static String MARKSMAN_SKILL_NAME = "Steady Hands";
	public final static int MARKSMAN_BASE_HEALTH = 250;
	public final static int MARKSMAN_BASE_ACCURACY = 100;
	public final static int MARKSMAN_BASE_DODGE = 0;
	public final static int MARKSMAN_BASE_ATTACK_STRENGTH = 290;
	public final static int MARKSMAN_BASE_ARMOR = 0;
	public final static int MARKSMAN_BASE_MOVEMENT_RANGE = 12;
	public final static int MARKSMAN_BASE_DAMAGE = 300;
	public final static int MARKSMAN_BASE_ATTACK_RANGE = 10;
	public final static int MARKSMAN_SKILL_COOLDOWN_TIMER = 3;
	
	/* Equipment */
	public final static int MAX_EQUIPMENT_LEVEL = 10;
	public final static String ARMOR_NAME = "Armor";
	public final static String BOOTS_NAME = "Boots";
	public final static String WEAPON_NAME = "Weapon";
	public final static int ARMOR_BONUS = 20;
	public final static int BOOTS_BONUS = 1;
	public final static int WEAPON_BONUS = 20;
	
	/* Rotation directions */
	public final static int ROTATE_UP = 180;
	public final static int ROTATE_DOWN = 0;
	public final static int ROTATE_LEFT = 270;
	public final static int ROTATE_RIGHT = 90;
}
