package bozels.physicsModel;

import bozels.physicsModel.material.Material;
import bozels.physicsModel.material.MaterialListener;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
abstract class BreakableObject implements MaterialListener{
	
	private double mass;

	private boolean canBreak;
	private double strength; //strength per kg
	private double powerThreshold;
	private double hp;
	
	public BreakableObject(double strength, double powerThreshold, double mass, boolean canBreak) {
		this.mass = mass;

		this.hp = strength*mass;
		this.strength = strength;
		this.powerThreshold = powerThreshold;
		this.canBreak = canBreak;
	}
	
	public boolean onImpact(double impact){
		if(!canBreak || impact <= powerThreshold){
			return stillAlive();
		}
		this.hp -= impact;
		return stillAlive();
	}
	
	//for all the people who are ...
	public boolean stillAlive(){
		return !canBreak || hp>0;
	}
	
	public double getHP(){
		return hp;
	}
	
	public double getStrength() {
		return strength;
	}

	public double getPowerThreshold() {
		return powerThreshold;
	}
	
	@Override
	public void onStrengthSettingChanged(Material source) {
		this.canBreak = source.canBreak();
		double oldHP = hp;
		double oldMaxHp = mass*strength;
		strength = source.getStrength();
		this.hp = mass*strength*oldHP/oldMaxHp;
		if(Double.isNaN(this.hp) && !Double.isInfinite(this.hp)){
			this.hp = mass*strength;
		}
		this.powerThreshold = source.getPowerThreshold();
	}

}
