package bozels.levelModel.gameObjects.bozel;

import org.jbox2d.common.Vec2;

import bozels.levelModel.core.Level;
import bozels.physicsModel.material.Material;
import bozels.physicsModel.material.Materials;
import bozels.physicsModel.shapes.ShapeWrapper;
import bozels.physicsModel.shapes.bozelImageShapes.ExplodingBozelShape;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class ExplodingBozel extends Bozel{
	
	private final static BozelTypes TYPE = BozelTypes.EXPLODING;

	private final static Material MATERIAL = Materials.EXPLODING_BOZEL.getMaterial();
	private final static ShapeWrapper SHAPE = new ExplodingBozelShape();
	
	public ExplodingBozel(Level level) {
		super(level, MATERIAL, SHAPE, TYPE);
	}

	public ExplodingBozel(Level level,
			Vec2 startPos) {
		super(level, MATERIAL, SHAPE, startPos, true, 0, TYPE);
	}
	
	public ExplodingBozel(Level world, 
			Vec2 startPos, double angle) {
		super(world, MATERIAL, SHAPE, startPos, true, angle, TYPE);
	}
	
	@Override
	public void doSpecialAction() {
		explode();
		getLevel().remove(this);
	}

	@Override
	public void cleanUp() {	}

}
