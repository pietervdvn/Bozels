package bozels.physicsModel.explosions;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jbox2d.callbacks.RayCastCallback;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

import bozels.exceptions.AlreadyExplodedException;
import bozels.physicsModel.PhysicalObject;


/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class Explosion implements RayCastCallback {

	/*
	 * An Explosion raycasts expSettings.getNumberOfRays(), uniformly around the object that's exploding.
	 * The length of the ray is expSettings.getRange(), and is not shortened on first impact.
	 * 
	 * This means explosions go through walls
	 * 
	 * B --- [] ---- []
	 * Both objects "[]" will receive an impact.
	 * 
	 * Each time an object is found, the force is applied.
	 * The applied force is exp.maxForce * Math.sqrt( range² -  distance² )
	 */
	
	private final World world;
	private final PhysicalObject source;
	
	private Boolean exploded = false;

	private final List<ExplosionVectorLogger> loggers = new ArrayList<ExplosionVectorLogger>();

	private final List<PhysicalObject> hitObjects = new ArrayList<PhysicalObject>();
	private final List<ExplosionVector> forces = new ArrayList<Explosion.ExplosionVector>();
	
	private final ExplosionSettingsModel expSettings;
	
	public Explosion(PhysicalObject source, ExplosionSettingsModel settings){
		this.source = source;
		this.world = getSource().getBody().getWorld();
		if (world == null) {
			throw new NullPointerException(
					"It seems the world can not be found");
		}
		this.expSettings = settings;
	}

	public void explode() throws AlreadyExplodedException {
		synchronized (exploded) {
			if(exploded == true){
				throw new AlreadyExplodedException();
			}
			exploded = true;
		}
		synchronized (world) {
			for (int i = 0; i < expSettings.getNumberOfRays(); i++) {
				double degrees = i * 2 * Math.PI / expSettings.getNumberOfRays();
				Vec2 pos = getSource().getBody().getPosition();
				world.raycast(this, pos,
						new Vec2((float) (pos.x + Math.cos(degrees) * expSettings.getRange()),
								(float) (pos.y + Math.sin(degrees) * expSettings.getRange())));
			}
			
			Iterator<ExplosionVector> vectIt = forces.iterator();
			for (Iterator<PhysicalObject> iterator = hitObjects.iterator(); iterator.hasNext();) {
				PhysicalObject hit = iterator.next();
				ExplosionVector vec = vectIt.next();
				
				hit.getBody().applyForce(vec.getForce().clone(), vec.getImpactPoint().clone());
				
				for (ExplosionVectorLogger logger : loggers) {
					logger.captureExplosionVector(this, hit, vec.getImpactPoint(),
							vec.getForce());
				}
			}
		}
	}

	@Override
	public float reportFixture(Fixture fixture, Vec2 point, Vec2 normal,
			float fraction) {

		PhysicalObject obj = (PhysicalObject) fixture.getBody().m_userData;

		Vec2 dir = point.sub(source.getBody().getPosition());
		float dist = dir.normalize();
		double factor = expSettings.getRange() * expSettings.getRange() - dist*dist;
		if (factor <= 0) {
			return (float) expSettings.getRange();
		}
		factor = Math.sqrt(factor) * expSettings.getMaxForce();
		hitObjects.add(obj);
		forces.add(new ExplosionVector(point.clone(), dir.mul((float) (factor)).clone()));

		return (float) expSettings.getRange();
	}

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
	private class ExplosionVector {

		private final Vec2 impactPoint;
		private final Vec2 force;

		public ExplosionVector(Vec2 impactPoint, Vec2 force) {
			this.impactPoint = impactPoint.clone();
			this.force = force.clone();
		}

		public Vec2 getForce() {
			return force;
		}

		public Vec2 getImpactPoint() {
			return impactPoint;
		}

	}

	public void addLogger(ExplosionVectorLogger logger) {
		loggers.add(logger);
	}

	public PhysicalObject getSource() {
		return source;
	}
	
	public ExplosionSettingsModel getExpSettingsModel(){
		return expSettings;
	}

}
