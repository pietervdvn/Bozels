package bozels.physicsModel;


import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import bozels.SafeList;
import bozels.exceptions.AlreadyExplodedException;
import bozels.levelModel.core.ExplosionLogger;
import bozels.physicsModel.explosions.Explosion;
import bozels.physicsModel.explosions.ExplosionSettingsModel;
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
public class PhysicalObject extends AbstractObject {
	
	private final SafeList<ExplosionLogger> loggers = new SafeList<ExplosionLogger>();
	private ExplosionSettingsModel expSettings;

	protected PhysicalObject(World world, Material material,
			ShapeWrapper shape, Vec2 startPos, boolean dynamic, boolean bullet, double angle) {
		super(world, material, shape, startPos, dynamic, bullet, angle);
	}

	protected PhysicalObject(World world, Material material, ShapeWrapper shape, boolean bullet) {
		super(world, material, shape, bullet);
	}
	
	public void explode(){
		Explosion exp = new Explosion(this, expSettings);
		synchronized (loggers) {
			loggers.resetCounter();
			while(loggers.hasNext()){
				loggers.next().onNewExplosion(exp);
			}
		}
		try {
			exp.explode();
		} catch (AlreadyExplodedException e) {
		}
	}
	
	public void addExplosionLogger(ExplosionLogger logger){
		loggers.add(logger);
	}
	
	public void removeExplosionLogger(ExplosionLogger logger){
		loggers.remove(logger);
	}
	
	public ExplosionSettingsModel getExplosionSettings() {
		return expSettings;
	}

	public void setExpSettings(ExplosionSettingsModel expSettings) {
		this.expSettings = expSettings;
	}

}
