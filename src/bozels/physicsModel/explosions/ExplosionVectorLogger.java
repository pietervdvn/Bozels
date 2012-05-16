package bozels.physicsModel.explosions;

import org.jbox2d.common.Vec2;

import bozels.physicsModel.PhysicalObject;


public interface ExplosionVectorLogger {
	
	public void captureExplosionVector(Explosion sourceExplosion, 
			PhysicalObject hitObject,
			Vec2 pointOfImpact, Vec2 force);

}
