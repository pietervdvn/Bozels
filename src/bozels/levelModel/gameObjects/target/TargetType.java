package bozels.levelModel.gameObjects.target;

import org.jbox2d.common.Vec2;

import bozels.levelModel.core.Level;

/**
 * Bozels
 * 
 * Door: Pieter Vander Vennet 1ste Bachelor Informatica Universiteit Gent
 * 
 */
public enum TargetType {

	BIG {
		@Override
		public Target create(Level level, Vec2 startPos, double angle) {
			return new BigTarget(level, startPos, angle);
		}
	},

	LITTLE {
		@Override
		public Target create(Level level, Vec2 startPos, double angle) {
			return new LittleTarget(level, startPos, angle);
		}
	};

	public Target create(Level level, Vec2 startPos) {
		return create(level, startPos, 0);
	}

	public abstract Target create(Level level, Vec2 startPos, double angle);

}
