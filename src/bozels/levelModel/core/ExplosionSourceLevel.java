package bozels.levelModel.core;

import bozels.SafeList;
import bozels.physicsModel.explosions.Explosion;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
abstract class ExplosionSourceLevel extends LevelEventSource implements ExplosionLogger{

	private final SafeList<ExplosionLogger> loggers = new SafeList<ExplosionLogger>();
	
	@Override
	public void onNewExplosion(Explosion source) {
		synchronized (loggers) {
			loggers.resetCounter();
			while(loggers.hasNext()){
				loggers.next().onNewExplosion(source);
			}
		}
		
	}
	
	public void addLogger(ExplosionLogger logger){
		loggers.add(logger);
	}
	
	public void removeLogger(ExplosionLogger logger){
		loggers.remove(logger);
	}

}
