package bozels.levelModel.gameObjects;

import org.jbox2d.common.Vec2;

import bozels.levelModel.core.Level;
import bozels.physicsModel.PhysicalObject;
import bozels.physicsModel.material.Material;
import bozels.physicsModel.shapes.ShapeWrapper;


/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public abstract class GameObject extends PhysicalObject{
	
	private boolean isBozel;
	private boolean isTarget;
	private final Level level;

	protected GameObject(Level level, Material material, ShapeWrapper shape,
			Vec2 startPos, boolean dynamic, double angle, boolean isBozel, boolean isTarget) {
		super(level.getWorld(), material, shape, startPos, dynamic, isBozel, angle);
		if(isBozel && isTarget){
			throw new IllegalArgumentException("Something can't be a bozel AND a target");
		}
		this.level = level;
		this.isBozel = isBozel;
		this.isTarget = isTarget;
		
	}

	protected GameObject(Level level, Material material, ShapeWrapper shape, boolean isBozel, boolean isTarget) {
		super(level.getWorld(), material, shape, isBozel);
		if(isBozel && isTarget){
			throw new IllegalArgumentException("Something can't be a bozel AND a target");
		}
		this.level = level;
		this.isBozel = isBozel;
		this.isTarget = isTarget;
	}
	
	public void setBozel(boolean isBozel){
		this.isBozel = isBozel;
	}
	
	public void setTarget(boolean isTarget){
		this.isTarget = isTarget;
	}
	
	public Level getLevel(){
		return level;
	}

	/**
	 * "is bozel" marks that this object destroys targets on touch.
	 * Each child of type "bozel" has got the "isBozel" flag set.
	 * However, they may be "decor objects" which have the flag set 
	 * 	(e.g. the "offsplit" of the splitting, blue bozel: these are just "balls", but are not instanceof Bozel
	 * 
	 * Thus:
	 * (object.isBozel() == object instanceof Bozel) is not always true.
	 * 
	 */
	public boolean isBozel() {
		return isBozel;
	}

	public boolean isTarget() {
		return isTarget;
	}

}
