package bozels.levelModel.gameObjects.bozel;


import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import bozels.levelModel.core.Level;
import bozels.physicsModel.material.Material;
import bozels.physicsModel.material.Materials;
import bozels.physicsModel.shapes.ShapeWrapper;
import bozels.physicsModel.shapes.bozelImageShapes.AcceleratingBozelShape;


/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class AcceleratingBozel extends Bozel{
	
	private final static BozelTypes TYPE = BozelTypes.ACCELERATING;

	private final static Material MATERIAL = Materials.ACCELERATING_BOZEL.getMaterial();
	private final static ShapeWrapper SHAPE = new AcceleratingBozelShape();
	
	private final static int ACCELERATION = 3;
	
	public AcceleratingBozel(Level level) {
		super(level, MATERIAL, SHAPE, TYPE);
	}

	public AcceleratingBozel(Level level,
			Vec2 startPos) {
		super(level, MATERIAL, SHAPE, startPos, true, 0, TYPE);
	}
	
	public AcceleratingBozel(Level level, 
			Vec2 startPos, double angle) {
		super(level, MATERIAL, SHAPE, startPos, true, angle, TYPE);
	}

	@Override
	public void doSpecialAction() {
		Body b = getBody();
		Vec2 lv = b.getLinearVelocity();
		b.setLinearVelocity(new Vec2(lv.x * ACCELERATION, lv.y * ACCELERATION));
	}

	@Override
	public void cleanUp() {}
	
}
