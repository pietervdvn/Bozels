package bozels.levelModel.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import bozels.levelModel.gameObjects.DecorObject;
import bozels.levelModel.gameObjects.GameObject;
import bozels.levelModel.settingsModel.SettingsListener;
import bozels.levelModel.settingsModel.SettingsModel;
import bozels.physicsModel.material.Material;
import bozels.physicsModel.shapes.ShapeWrapper;
import bozels.simulationThread.SimulateableLevel;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
abstract class AbstractLevel extends ExplosionSourceLevel implements
		SimulateableLevel, ContactListener, SettingsListener {

	private final World world;

	private final SettingsModel settings;

	private final List<GameObject> gameObjects = new ArrayList<GameObject>();
	private final Queue<GameObject> removeFromWorld = new LinkedList<GameObject>();

	private boolean everyonesSleeping = false;

	public AbstractLevel(SettingsModel settings) {
		this.settings = settings;
		world = new World(settings.getGravity(), true);
		world.setContactListener(this);
		settings.addListener(this);
	}

	public synchronized int step() {
		synchronized (world) {
			world.step(settings.getWorldDt(), settings.getVelIterations(),
					settings.getPosIterations());
		}
		cleanUp();
		return settings.getSleepDt();
	}

	protected synchronized void cleanUp() {
		double minY = settings.getYThreshold();
		boolean objectsChanged = false;

		boolean sleeping = true;
		synchronized (removeFromWorld) {
			synchronized (gameObjects) {
				for (GameObject obj : gameObjects) {
					if (obj.getY() < minY) {
						remove(obj);
					} else {
						sleeping = sleeping
								&& (!obj.getBody().isAwake()
										|| !obj.getBody().isActive() || obj
										.getBody().m_type == BodyType.STATIC);
					}
				}
			}
			
			while (!removeFromWorld.isEmpty()) {
				objectsChanged = true;
				GameObject toRemove = removeFromWorld.poll();
				world.destroyBody(toRemove.getBody());
				gameObjects.remove(toRemove);

				throwObjectRemoved(toRemove);
				throwObjectsChanged();
			}
		}
		if (everyonesSleeping != sleeping) {
			this.everyonesSleeping = sleeping;
			throwSleepStateChanged();
		}
		if (objectsChanged) {
			throwObjectsChanged();
		}
		throwWorldStepped();
	}

	public void add(GameObject obj) {
		if(obj.getWorld() != world){
			throw new IllegalArgumentException("This object is not part of this world!");
		}
		synchronized (gameObjects) {
			gameObjects.add(obj);
		}
		obj.setExpSettings(settings.getExpSettings());
		obj.addExplosionLogger(this);
		obj.setLevel((Level) this);
		throwObjectsChanged();
	}

	public GameObject createGameObject(Material material, ShapeWrapper shape,
			Vec2 startPos, boolean dynamic, double angle) {
		GameObject obj = new DecorObject((Level) this, material, shape, startPos,
				dynamic, angle);
		add(obj);
		return obj;
	}

	public void remove(GameObject obj) {
		synchronized (gameObjects) {
			if (!gameObjects.contains(obj)) {
				return;
			}
		}
		synchronized (removeFromWorld) {
			removeFromWorld.add(obj);
		}
	}

	@Override
	public synchronized void onYThresholdChanged(SettingsModel source) {
		cleanUp();
	}

	@Override
	public void onGravityChanged(SettingsModel source) {
		synchronized (world) {
			world.setGravity(source.getGravity());
			synchronized (gameObjects) {
				for (GameObject obj : gameObjects) {
					obj.getBody().setAwake(true);
				}
			}
			everyonesSleeping = false;
			throwSleepStateChanged();
		}
	}

	public World getWorld() {
		return world;
	}

	public SettingsModel getSettingsModel() {
		return settings;
	}

	@Override
	public void onTimeSettingsChanged(SettingsModel source) {
	}

	public List<GameObject> getGameObjects() {
		return gameObjects;
	}

	public boolean isSleeping() {
		return this.everyonesSleeping;
	}
}
