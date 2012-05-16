package bozels.levelModel.core;

import org.jbox2d.common.Vec2;

import bozels.exceptions.BozelSkillAlreadyUsedException;
import bozels.exceptions.BozelStillPlayingException;
import bozels.exceptions.NoBozelLoadedException;
import bozels.exceptions.NoBozelPlayingException;
import bozels.exceptions.NoMoreBozelsException;
import bozels.levelModel.gameObjects.GameObject;
import bozels.levelModel.gameObjects.bozel.Bozel;
import bozels.levelModel.gameObjects.bozel.BozelTypes;
import bozels.levelModel.settingsModel.SettingsModel;
import bozels.physicsModel.material.Materials;
import bozels.physicsModel.shapes.Box;

/**
 * Bozels
 * 
 * Door: Pieter Vander Vennet 1ste Bachelor Informatica Universiteit Gent
 * 
 * Level with katapult (bozel/angry bird launcher) and queue for bozels. Bozels
 * are automatically subtracted from the world logic.
 * 
 * The number of bozels and targets is counted, and an event is thrown when
 * these numbers change.
 */
public class Level extends BozelLevel {

	private double catapultMaxLength;
	private double catapultForce;
	private Vec2 catapultPos;

	private Bozel inGameBozel = null;
	private Vec2 relBozelLoc = new Vec2(0.0f, 0.0f);
	private boolean bozelIsLaunched = false;
	private boolean bozelUsedSkill = false;

	private final SettingsModel settings;

	public Level(SettingsModel settings) {
		super(settings);
		// creation of a floor
		super.createGameObject(Materials.SOLID_TRANSPARENT.getMaterial(),
				new Box(1000, -settings.getYThreshold()), new Vec2(500,
						(float) settings.getYThreshold() / 2), false, 0);

		this.catapultMaxLength = settings.getKatapultLength();
		this.catapultForce = settings.getKatapultForce();

		this.settings = settings;
	}

	public synchronized void charge() throws NoMoreBozelsException,
			BozelStillPlayingException {
		if (inGameBozel != null) {
			throw new BozelStillPlayingException();
		}
		if (getBozelQueue().isEmpty()) {
			throw new NoMoreBozelsException();
		}
		inGameBozel = getBozelQueue().poll();
		bozelIsLaunched = false;
		bozelUsedSkill = false;
		setLaunchPosition(catapultPos.x, catapultPos.y);
		throwBozelCharged(inGameBozel);
		throwObjectsChanged();
	}

	public synchronized void setLaunchPosition(double x, double y)
			throws NoMoreBozelsException, BozelStillPlayingException {
		if (inGameBozel == null) {
			charge();
			setLaunchPosition(x, y);
		}
		if (bozelIsLaunched) {
			throw new BozelStillPlayingException();
		}

		Vec2 absolLoc = new Vec2((float) x, (float) y);
		Vec2 relLoc = catapultPos.sub(absolLoc);
		double distance = relLoc.length();

		if (distance > catapultMaxLength) {
			relLoc.normalize();
			relLoc.mulLocal((float) catapultMaxLength);
		}
		relBozelLoc.set(relLoc);
		inGameBozel.getBody().getPosition().set(catapultPos.sub(relLoc));
		throwBozelMoved(inGameBozel);
		throwObjectsChanged();
	}

	public synchronized void launch() throws NoBozelLoadedException {
		if (inGameBozel == null) {
			throw new NoBozelLoadedException();
		}
		if (bozelIsLaunched) {
			return;
		}
		Vec2 launchPos = catapultPos.sub(relBozelLoc);
		double force = Math.min(relBozelLoc.length(), catapultMaxLength)
				* catapultForce / catapultMaxLength;

		Vec2 e = relBozelLoc.mul((float) force);
		double oldDist = relBozelLoc.normalize();
		e.mulLocal((float) (1 / oldDist));
		bozelIsLaunched = true;
		bozelUsedSkill = false;
		synchronized (inGameBozel.getBody().getWorld()) {
			inGameBozel.getBody().setActive(true);
			inGameBozel.getBody().setTransform(launchPos, 0);
			inGameBozel.getBody().setLinearVelocity(e);
		}
		throwBozelLaunched(inGameBozel);
		throwObjectsChanged();
	}

	public synchronized void doBozelAction()
			throws BozelSkillAlreadyUsedException, NoBozelPlayingException {
		if (inGameBozel == null || !isLaunched()) {
			throw new NoBozelPlayingException();
		}
		if (bozelUsedSkill) {
			throw new BozelSkillAlreadyUsedException();
		}
		bozelUsedSkill = true;
		inGameBozel.doSpecialAction();
		throwBozelUsedSkill(inGameBozel);
	}

	@Override
	public GameObject createBozelObject(BozelTypes type, Vec2 startPos) {
		if (catapultPos == null) {
			this.catapultPos = new Vec2((float) settings.getKatapultX(),
					(float) settings.getKatapultY()).add(startPos);
		}
		return super.createBozelObject(type, startPos);
	}

	@Override
	public void onKatapultSettingsChanged(SettingsModel source) {
		this.catapultForce = source.getKatapultForce();
		this.catapultMaxLength = source.getKatapultLength();
	}

	@Override
	protected synchronized void cleanUp() {
		if (inGameBozel != null && inGameBozel.getBody().isActive()
				&& !inGameBozel.getBody().isAwake()) {
			remove(inGameBozel);
		}
		super.cleanUp();
	}

	public void remove(GameObject obj) {
		if (obj == inGameBozel) {
			inGameBozel = null;
			((Bozel) obj).cleanUp();
			throwBozelExpired(inGameBozel);
			throwNumberOfBozelsChanged();
		}
		super.remove(obj);
	}

	public Vec2 getKatapultLoc() {
		return catapultPos;
	}

	public double getKatapultDev() {
		return catapultMaxLength;
	}

	public Bozel getInGameBozel() {
		return inGameBozel;
	}

	public boolean isLaunched() {
		return bozelIsLaunched;
	}

	public boolean hasBozelUsedSkill() {
		return bozelUsedSkill;
	}

	public boolean hasWon() {
		return getNumberOfRemainingTargets() == 0;
	}

	public boolean hasLost() {
		return getNumberOfRemainingTargets() > 0
				&& getNumberOfBozelsInQueue() == 0 && isLaunched()
				&& isSleeping();
	}
}
