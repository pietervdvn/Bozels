package bozels.levelModel.gameObjects.target;


import org.jbox2d.common.Vec2;

import bozels.levelModel.core.Level;
import bozels.physicsModel.explosions.ExplosionSettingsModel;
import bozels.physicsModel.material.Material;
import bozels.physicsModel.material.Materials;
import bozels.physicsModel.shapes.ShapeWrapper;
import bozels.physicsModel.shapes.bozelImageShapes.LittlePigShape;


/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
class LittleTarget extends Target {
	
	private final static Material MATERIAL = Materials.LITTLE_TARGET.getMaterial();
	private final static ShapeWrapper SHAPE = new LittlePigShape();
	
	public LittleTarget(Level level) {
		super(level, MATERIAL, SHAPE, TargetType.LITTLE);
	}
	
	public LittleTarget(Level level, 
			Vec2 startPos, ExplosionSettingsModel expSettings) {
		super(level, MATERIAL, SHAPE, startPos, true, 0, TargetType.LITTLE);
	}

	public LittleTarget(Level level, 
			Vec2 startPos, double angle) {
		super(level, MATERIAL, SHAPE, startPos, true, angle, TargetType.LITTLE);
	}
}
