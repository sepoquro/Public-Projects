/** Contains a list of string constants for specific actions */
package com.mustard.server;

public class Commands {
	/* Valid action commands */
	public static final int NEW_USER = 0;
	public static final int GET_USER = 1;
	public static final int VALIDATE_USER = 2;
	public static final int UPGRADE_ABILITIES = 3;
	public static final int UPGRADE_EQUIPMENT = 4;
	public static final int QUERY_PLAYER = 5;
	public static final int MOVE = 6;
	public static final int ATTACK = 7;
	public static final int PASS = 8;
	public static final int SET_USER_CHARACTER = 9;
	public static final int CAN_START_GAME = 10;
	public static final int GET_ALL_USERS = 11;
	public static final int CREATE_GUEST_USER = 12;
	public static final int IS_MY_TURN = 13;
	public static final int NEXT_TURN = 14;
	public static final int UPDATE = 15;
	public static final int SEND_MOVE_FORCE = 16;
	public static final int REFRESH_SCREEN = 17;
	public static final int USE_SKILL = 18;
	public static final int WHO_WON = 19;
	public static final int CHECK_GAME_ENDED = 20;
	public static final int DELETE_SESSION = 21;
	public static final int GIVE_RESOURCE = 22;
	public static final int DELETE_THREAD = 23;
	public static final int DELETE_CHARACTER = 24;

	
	/* Pair ref. values */
	public static final int USERNAME = 0;
	public static final int PASSWORD = 1;
	public static final int NICKNAME = 2;
	public static final int ON_SELF = 3;
	
	/* Ability ref. values */
	public static final int ABILITY_ATTACK_RANGE = 0;
	public static final int ABILITY_MOVEMENT_RANGE = 1;
	public static final int ABILITY_DODGE = 2;
	
	/* Equipment ref. values */
	public static final int EQUIPMENT_WEAPON = 0;
	public static final int EQUIPMENT_ARMOR = 1;
	
}
