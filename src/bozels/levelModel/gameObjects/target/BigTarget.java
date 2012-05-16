package bozels.levelModel.gameObjects.target;


import org.jbox2d.common.Vec2;

import bozels.levelModel.core.Level;
import bozels.physicsModel.material.Material;
import bozels.physicsModel.material.Materials;
import bozels.physicsModel.shapes.ShapeWrapper;
import bozels.physicsModel.shapes.bozelImageShapes.BigPigShape;


/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
class BigTarget extends Target {
// PigTarget
	
	private final static Material MATERIAL = Materials.BIG_TARGET.getMaterial();
	private final static ShapeWrapper SHAPE = new BigPigShape();
	
	public BigTarget(Level level) {
		super(level, MATERIAL, SHAPE, TargetType.BIG);
	}
	
	public BigTarget(Level level, Vec2 startPos) {
		super(level, MATERIAL, SHAPE, startPos, true, 0, TargetType.BIG);
	}

	public BigTarget(Level level, 
			Vec2 startPos, double angle) {
		super(level, MATERIAL, SHAPE, startPos, true, angle, TargetType.BIG);
	}

}
