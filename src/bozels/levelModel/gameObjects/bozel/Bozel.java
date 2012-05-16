package bozels.levelModel.gameObjects.bozel;


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
public abstract class Bozel extends GameObject{
	
	private final BozelTypes type;

	Bozel(Level level, Material material, ShapeWrapper shape,
			Vec2 startPos, boolean dynamic, double angle, BozelTypes type) {
		super(level, material, shape, startPos, dynamic, angle, true, false);
		this.type = type;
	}
	
	Bozel(Level level, Material material, ShapeWrapper shape, BozelTypes type) {
		super(level, material, shape, true, false);
		this.type = type;
	}

	public BozelTypes getType() {
		return type;
	}
	
	public abstract void doSpecialAction();

	public abstract void cleanUp();
}
