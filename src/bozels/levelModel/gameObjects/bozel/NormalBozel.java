package bozels.levelModel.gameObjects.bozel;

import org.jbox2d.common.Vec2;

import bozels.levelModel.core.Level;
import bozels.physicsModel.material.Material;
import bozels.physicsModel.material.Materials;
import bozels.physicsModel.shapes.Triangle;
import bozels.physicsModel.shapes.bozelImageShapes.NormalBozelShape;


/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class NormalBozel extends Bozel{
	
	private final static BozelTypes TYPE = BozelTypes.NORMAL;

	private final static Material MATERIAL = Materials.NORMAL_BOZEL.getMaterial();
	private final static Triangle SHAPE = new NormalBozelShape();//new Triangle(2, Math.sqrt(3), 1);
	
	public NormalBozel(Level level) {
		super(level, MATERIAL, SHAPE, TYPE);
	}

	public NormalBozel(Level level,
			Vec2 startPos) {
		super(level, MATERIAL, SHAPE, startPos, true, 0, TYPE);
	}
	
	public NormalBozel(Level level, 
			Vec2 startPos, double angle) {
		super(level, MATERIAL, SHAPE, startPos, true, angle, TYPE);
	}
	
	@Override
	public void doSpecialAction() {
		getLevel().remove(this);
	}

	@Override
	public void cleanUp() {}
	
}
