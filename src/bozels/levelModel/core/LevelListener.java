package bozels.levelModel.core;

import bozels.levelModel.gameObjects.GameObject;
import bozels.levelModel.gameObjects.bozel.Bozel;
import bozels.physicsModel.material.Material;


public interface LevelListener {
	
	void onObjectsChanged(Level source);
	void onWorldStepped(Level source);
	void onSleepingStatusChanged(Level levelEventSource);
	
	void onNumberOfTargetsChanged(Level source);
	void onNumberOfBozelsChanged(Level source);
	
	void onBozelCharged(Level source, Bozel bozel);
	void onBozelLaunched(Level source, Bozel bozel);
	void onBozelExpired(Level source, Bozel bozel);
	void onBozelUsedSkill(Level level, Bozel bozel);
	void onBozelMoved(Level level, Bozel bozel);
	
	void onObjectRemoved(Level source, GameObject removed, Material material);
	void onObjectContact(Level source, GameObject obj1, GameObject obj2, int force);
}
