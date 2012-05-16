package bozels.levelModel.gameObjects.target;


import org.jbox2d.common.Vec2;

import bozels.levelModel.core.Level;
import bozels.levelModel.gameObjects.GameObject;
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
public abstract class Target extends GameObject{
	
	private final TargetType type;

	protected Target(Level level, Material material, ShapeWrapper shape,
			Vec2 startPos, boolean dynamic, double angle, TargetType type) {
		super(level, material, shape, startPos, dynamic, angle, false, true);
		this.type = type;
	}
	
	protected Target(Level level, Material material, ShapeWrapper shape, TargetType type) {
		super(level, material, shape, false, true);
		this.type = type;
	}
	
	public TargetType getType() {
		return type;
	}

}
