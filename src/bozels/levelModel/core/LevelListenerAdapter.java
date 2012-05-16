package bozels.levelModel.core;

import bozels.levelModel.gameObjects.GameObject;
import bozels.levelModel.gameObjects.bozel.Bozel;
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
public class LevelListenerAdapter implements LevelListener{

	@Override
	public void onObjectsChanged(Level source) {
	}

	@Override
	public void onWorldStepped(Level source) {
	}

	@Override
	public void onSleepingStatusChanged(Level levelEventSource) {
	}

	@Override
	public void onNumberOfTargetsChanged(Level source) {
	}

	@Override
	public void onNumberOfBozelsChanged(Level source) {
	}

	@Override
	public void onBozelCharged(Level source, Bozel bozel) {
	}

	@Override
	public void onBozelLaunched(Level source, Bozel bozel) {
	}

	@Override
	public void onBozelExpired(Level source, Bozel bozel) {
	}

	@Override
	public void onBozelUsedSkill(Level level, Bozel bozel) {
	}

	@Override
	public void onBozelMoved(Level level, Bozel bozel) {
	}

	@Override
	public void onObjectRemoved(Level source, GameObject removed,
			Material material) {
	}

	@Override
	public void onObjectContact(Level source, GameObject obj1, GameObject obj2,
			int force) {
	}

}
