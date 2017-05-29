package com.mustard.game;

public class Effect {
	private String effectName;
	private int healthEffect;
	private int movementEffect;
	private int stunDuration;
	
	public String getEffectName() {
		return effectName;
	}
	public void setEffectName(String effectName) {
		this.effectName = effectName;
	}
	public int getHealthEffect() {
		return healthEffect;
	}
	public void setHealthEffect(int healthEffect) {
		this.healthEffect = healthEffect;
	}
	public int getMovementEffect() {
		return movementEffect;
	}
	public void setMovementEffect(int movementEffect) {
		this.movementEffect = movementEffect;
	}
	public int getStunDuration() {
		return stunDuration;
	}
	public void setStunDuration(int stunDuration) {
		this.stunDuration = stunDuration;
	}
}
