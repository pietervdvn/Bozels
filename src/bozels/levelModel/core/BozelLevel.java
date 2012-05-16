package bozels.levelModel.core;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.jbox2d.common.Vec2;

import bozels.levelModel.gameObjects.GameObject;
import bozels.levelModel.gameObjects.bozel.Bozel;
import bozels.levelModel.gameObjects.bozel.BozelTypes;
import bozels.levelModel.gameObjects.target.Target;
import bozels.levelModel.gameObjects.target.TargetType;
import bozels.levelModel.settingsModel.SettingsModel;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public abstract class BozelLevel extends LevelWithBreakables {

	private final Queue<Bozel> bozelQueue = new LinkedList<Bozel>();
	private final Set<Target> remainingTargets = new HashSet<Target>();

	public BozelLevel(SettingsModel settings) {
		super(settings);
	}

	public GameObject createBozelObject(BozelTypes type, Vec2 startPos) {
		return createBozelObject(type, startPos, 0);
	}

	public Bozel createBozelObject(BozelTypes type, Vec2 startPos, double angle) {
		Bozel bozel = type.create((Level) this, startPos, angle);
		add(bozel);
		bozelQueue.add(bozel);
		bozel.getBody().setActive(false);
		throwNumberOfBozelsChanged();
		return bozel;
	}

	public GameObject createTargetObject(TargetType type, Vec2 startPos) {
		return createTargetObject(type, startPos, 0);
	}

	public Target createTargetObject(TargetType type, Vec2 startPos,
			double angle) {
		Target targ = type.create((Level) this, startPos, angle);
		add(targ);
		remainingTargets.add(targ);
		throwNumberOfTargetsChanged();
		return targ;
	}

	public void remove(GameObject obj) {
		if(obj.isTarget()){
			remainingTargets.remove(obj);
			throwNumberOfTargetsChanged();
		}
		super.remove(obj);
	}

	public int getNumberOfBozelsInQueue() {
		return bozelQueue.size();
	}

	public int getNumberOfRemainingTargets() {
		return remainingTargets.size();
	}

	public Queue<Bozel> getBozelQueue() {
		return bozelQueue;
	}
}
