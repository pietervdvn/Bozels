package bozels.consoleViews;

import bozels.levelModel.core.Level;
import bozels.levelModel.core.LevelListener;
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
public class LevelWatcher implements LevelListener {
	
	public LevelWatcher(Level l) {
		l.addHardListener(this);
	}

	@Override
	public void onObjectsChanged(Level source) {
	}

	@Override
	public void onWorldStepped(Level source) {
	}

	@Override
	public void onSleepingStatusChanged(Level levelEventSource) {
		if(levelEventSource.isSleeping()){
			print("is sleeping");
		}else{
			print("woke up");
		}
	}

	@Override
	public void onNumberOfTargetsChanged(Level source) {
		print("new number of targets: "+source.getNumberOfRemainingTargets());
	}

	@Override
	public void onNumberOfBozelsChanged(Level source) {
	}

	@Override
	public void onBozelCharged(Level source, Bozel bozel) {
		print("bozel loaded");
	}

	@Override
	public void onBozelLaunched(Level source, Bozel bozel) {
		print("bozel launched");
	}

	@Override
	public void onBozelExpired(Level source, Bozel bozel) {
		print("bozel removed");
	}

	@Override
	public void onBozelUsedSkill(Level level, Bozel bozel) {
		print("skill used"); 
	}

	@Override
	public void onBozelMoved(Level level, Bozel bozel) {
	}
	
	private void print(String toPrint){
		System.out.println("Level: "+toPrint); 
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
