package bozels.levelModel.gameObjects.bozel;

import org.jbox2d.common.Vec2;

import bozels.levelModel.core.Level;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public enum BozelTypes {
	
	NORMAL {
		@Override
		public Bozel create(Level level, Vec2 startPos, double angle) {
			return new NormalBozel(level, startPos, angle);
		}
	},
	SPLIT{
		@Override
		public Bozel create(Level level, Vec2 startPos, double angle) {
			return new SplitBozel(level, startPos, angle);
		}
	},
	EXPLODING{
		@Override
		public Bozel create(Level level, Vec2 startPos, double angle) {
			return new ExplodingBozel(level, startPos, angle);
		}
	},
	ACCELERATING{
		@Override
		public Bozel create(Level level, Vec2 startPos, double angle) {
			return new AcceleratingBozel(level, startPos, angle);
		}
	};
	
	public Bozel create(Level level, Vec2 startPos){
		return create(level, startPos, 0);
	}
	
	public abstract Bozel create(Level level, Vec2 startPos, double angle);

}
