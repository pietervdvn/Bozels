package bozels.gui.sound;

import java.util.HashMap;
import java.util.Map;

import bozels.gui.resourceModel.localeConstant.LocaleConstant;
import bozels.levelModel.core.ExplosionLogger;
import bozels.levelModel.core.Level;
import bozels.levelModel.core.LevelListener;
import bozels.levelModel.gameObjects.GameObject;
import bozels.levelModel.gameObjects.bozel.Bozel;
import bozels.levelModel.gameObjects.bozel.BozelTypes;
import bozels.physicsModel.explosions.Explosion;
import bozels.physicsModel.material.Material;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class LevelSoundPlayer implements LevelListener, ExplosionLogger {

	private final SoundsPool pool;

	private final static Map<BozelTypes, Integer> BOZEL_SOUNDS = new HashMap<BozelTypes, Integer>();
	static {
		BOZEL_SOUNDS.put(BozelTypes.ACCELERATING, SoundsPool.ACCELERATING_BOZEL);
		BOZEL_SOUNDS.put(BozelTypes.EXPLODING, SoundsPool.EXPLODING_BOZEL);
		BOZEL_SOUNDS.put(BozelTypes.NORMAL, SoundsPool.NORMAL_BOZEL);
		BOZEL_SOUNDS.put(BozelTypes.SPLIT, SoundsPool.SPLIT_BOZEL);
	}

	private final static Map<LocaleConstant, Integer> DESTROYED_SOUNDS = new HashMap<LocaleConstant, Integer>();
	static {
		DESTROYED_SOUNDS.put(LocaleConstant.WOOD, SoundsPool.WOOD_DESTROYED);
		DESTROYED_SOUNDS.put(LocaleConstant.CONCRETE,
				SoundsPool.CONCRETE_DESTROYED);
		DESTROYED_SOUNDS.put(LocaleConstant.SOLID,
				SoundsPool.CONCRETE_DESTROYED);
		DESTROYED_SOUNDS.put(LocaleConstant.ICE, SoundsPool.ICE_DESTROYED);
		DESTROYED_SOUNDS.put(LocaleConstant.METAL, SoundsPool.METAL_DESTROYED);
	}

	private final static Map<LocaleConstant, Integer> CONTACT_SOUNDS = new HashMap<LocaleConstant, Integer>();
	static {
		CONTACT_SOUNDS.put(LocaleConstant.WOOD, SoundsPool.WOOD_CONTACT);
		CONTACT_SOUNDS
				.put(LocaleConstant.CONCRETE, SoundsPool.CONCRETE_CONTACT);
		CONTACT_SOUNDS.put(LocaleConstant.ICE, SoundsPool.ICE_CONTACT);
		CONTACT_SOUNDS.put(LocaleConstant.METAL, SoundsPool.METAL_CONTACT);

		CONTACT_SOUNDS.put(LocaleConstant.ACCELERATING_BOZEL,
				SoundsPool.WOOD_CONTACT);
		CONTACT_SOUNDS.put(LocaleConstant.EXPLODING_BOZEL,
				SoundsPool.WOOD_CONTACT);
		CONTACT_SOUNDS.put(LocaleConstant.SPLIT_BOZEL, SoundsPool.WOOD_CONTACT);
		CONTACT_SOUNDS
				.put(LocaleConstant.NORMAL_BOZEL, SoundsPool.WOOD_CONTACT);

	}

	public LevelSoundPlayer(Level level, SoundsPool pool) {
		this.pool = pool;
		level.addListener(this);
		level.addLogger(this);
	}

	@Override
	public void onNewExplosion(Explosion source) {
		pool.play(SoundsPool.EXPLOSION);
	}

	@Override
	public void onObjectRemoved(Level source, GameObject removed,
			Material material) {
		if (removed.isBozel()) {
			pool.play(SoundsPool.BOZEL_DELETED);
		} else if (removed.isTarget()) {
			pool.play(SoundsPool.TARGET_DELETED);
		} else {
			if (DESTROYED_SOUNDS.get(removed.getMaterial().getMaterialName()) != null) {
				pool.play(DESTROYED_SOUNDS.get(removed.getMaterial().getMaterialName()));
			}
		}

	}

	@Override
	public void onObjectContact(Level source, GameObject obj1, GameObject obj2,
			int force) {
		if (force < 5) {
			return;
		}
		Integer sound = CONTACT_SOUNDS.get(obj1.getMaterial().getMaterialName());
		if (sound != null) {
			pool.play(sound);
		}
		sound = CONTACT_SOUNDS.get(obj2.getMaterial().getMaterialName());
		if (sound != null) {
			pool.play(sound);
		}
	}

	@Override
	public void onBozelLaunched(Level source, Bozel bozel) {
		pool.play(BOZEL_SOUNDS.get(bozel.getType()));
	}

	@Override
	public void onBozelCharged(Level source, Bozel bozel) {
		pool.play(SoundsPool.CATAPULT);
	}

	@Override
	public void onBozelUsedSkill(Level level, Bozel bozel) {
		pool.play(SoundsPool.BOOST);
	}

	@Override
	public void onBozelExpired(Level source, Bozel bozel) {
	}

	@Override
	public void onObjectsChanged(Level source) {
	}

	@Override
	public void onWorldStepped(Level source) {
	}

	@Override
	public void onSleepingStatusChanged(Level source) {
		if (source.hasLost()) {
			pool.play(SoundsPool.LEVEL_FAILED);
		}
	}

	@Override
	public void onNumberOfTargetsChanged(Level source) {
		if (source.hasWon()) {
			pool.play(SoundsPool.LEVEL_SUCCESFULL);
		}
	}

	@Override
	public void onNumberOfBozelsChanged(Level source) {
	}

	@Override
	public void onBozelMoved(Level level, Bozel bozel) {
	}

}
