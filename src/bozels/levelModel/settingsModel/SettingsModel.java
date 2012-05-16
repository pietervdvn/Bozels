package bozels.levelModel.settingsModel;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.common.Vec2;

import bozels.physicsModel.explosions.ExplosionSettingsModel;
import bozels.valueWrappers.DoubleValue;
import bozels.valueWrappers.IntegerValue;
import bozels.valueWrappers.Value;
import bozels.valueWrappers.ValueListener;
import bozels.xml.PropertiesList;
import bozels.xml.XMLable;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class SettingsModel extends SettingsEventSource implements PropertiesList {

	private final Value<Double> gravityX = new DoubleValue(0.0, "gravityY");
	private final Value<Double> gravityY = new DoubleValue(-9.81, "gravityX");

	private final Value<Double> katapultForce = new DoubleValue(75.0,
			"catapultforce");
	private final Value<Double> katapultLength = new DoubleValue(7.0,
			"katapultLength");
	private final Value<Double> katapultX = new DoubleValue(0.0, "katapultX");
	private final Value<Double> katapultY = new DoubleValue(8.0, "katapultY");

	private final Value<Double> worldDt = new DoubleValue(1.0 / 60.0, "worldDt");// the timestep of the world
	private final Value<Integer> sleepDt = new IntegerValue(50 / 3, "sleepDt");// how many millisec the thread sleeps per sec
	private final Value<Integer> velIterations = new IntegerValue(8, "velIt");
	private final Value<Integer> posIterations = new IntegerValue(3, "posIt");

	private final ExplosionSettingsModel expSettings = new ExplosionSettingsModel();

	private double yValueThreshold = -25;
	
	private final List<PropertiesList> subModels;
	private final List<XMLable> xmlables;
	
	public SettingsModel() {
		
		subModels = new ArrayList<PropertiesList>();
		subModels.add(expSettings);

		xmlables = new ArrayList<XMLable>();
		ValueListener<Double> catapultSettings = new ValueListener<Double>() {
			@Override
			public void onValueChanged(Value<Double> source, Double newValue) {
				throwKatapultSettingsChanged();
			}
		};
		katapultForce.addHardListener(catapultSettings);
		katapultLength.addHardListener(catapultSettings);
		katapultX.addHardListener(catapultSettings);
		katapultY.addHardListener(catapultSettings);
		
		xmlables.add(katapultForce);
		xmlables.add(katapultLength);
		xmlables.add(katapultX);
		xmlables.add(katapultY);
		
		worldDt.addHardListener(new ValueListener<Double>() {
			@Override
			public void onValueChanged(Value<Double> source, Double newValue) {
				throwTimeSettingsChanged();
			}
		});
		
		ValueListener<Integer> timeSettings = new ValueListener<Integer>() {
			@Override
			public void onValueChanged(Value<Integer> source, Integer newValue) {
				throwTimeSettingsChanged();
			}
		};
		sleepDt.addHardListener(timeSettings);
		velIterations.addHardListener(timeSettings);
		posIterations.addHardListener(timeSettings);
		
		xmlables.add(worldDt);
		xmlables.add(sleepDt);
		xmlables.add(velIterations);
		xmlables.add(posIterations);
		
		ValueListener<Double> gravSettings = new ValueListener<Double>() {
			@Override
			public void onValueChanged(Value<Double> source, Double newValue) {
				throwGravityChanged();
			}
		};
		
		gravityX.addHardListener(gravSettings);
		gravityY.addHardListener(gravSettings);
		xmlables.add(gravityX);
		xmlables.add(gravityY);

		
	}

	public Vec2 getGravity() {
		return new Vec2((float) (double) gravityX.get(), (float) (double) gravityY.get());
	}

	public void setGravity(Vec2 gravity) {
		gravityX.set((double) gravity.x);
		gravityY.set((double) gravity.y);
	}
	
	public Value<Double> getGravityXValue(){
		return gravityX;
	}
	
	public Value<Double> getGravityYValue(){
		return gravityY;
	}

	public float getWorldDt() {
		return (float) (double) worldDt.get();
	}
	
	public Value<Double> getWorldDtValue(){
		return worldDt;
	}

	public void setWorldDt(double worldDt) {
		this.worldDt.set(worldDt);
	}

	public int getVelIterations() {
		return velIterations.get();
	}
	
	public Value<Integer> getVelIterationsValue(){
		return velIterations;
	}

	
	public void setVelIterations(int velIterations) {
		this.velIterations.set(velIterations);
	}

	public int getSleepDt() {
		return sleepDt.get();
	}

	public Value<Integer> getSleepDtValue(){
		return sleepDt;
	}
	
	public void setSleepDt(int sleepDt) {
		this.sleepDt.set(sleepDt);
	}

	public int getPosIterations() {
		return posIterations.get();
	}
	
	public Value<Integer> getPosIterationValue(){
		return posIterations;
	}

	public void setPosIterations(int posIterations) {
		this.posIterations.set(posIterations);
	}

	public void setYThreshold(double removeYValue) {
		if (this.yValueThreshold == removeYValue) {
			return;
		}
		if (removeYValue > 0) {
			throw new IllegalArgumentException(
					"The yThreshold MUST be a negative value");
		}
		if (removeYValue > -25) {
			throw new IllegalArgumentException(
					"The yThreshold MUST be less then -25");
		}
		this.yValueThreshold = removeYValue;

	}

	public void setKatapultForce(double katapultForce) {
		this.katapultForce.set(katapultForce);
	}

	public void setKatapultLength(double katapultLength) {
		this.katapultLength.set(katapultLength);
	}

	public void setKatapultY(double katapultY) {
		this.katapultY.set(katapultY);
	}

	public void setKatapultX(double katapultX) {
		this.katapultX.set(katapultX);
	}

	public double getKatapultY() {
		return katapultY.get();
	}

	public Value<Double> getKatapultYValue() {
		return katapultY;
	}

	public double getKatapultForce() {
		return katapultForce.get();
	}

	public Value<Double> getKatapultForceValue() {
		return katapultForce;
	}

	public double getKatapultLength() {
		return katapultLength.get();
	}

	public Value<Double> getKatapultLengthValue() {
		return katapultLength;
	}

	public double getYThreshold() {
		return yValueThreshold;
	}

	public double getKatapultX() {
		return katapultX.get();
	}

	public Value<Double> getKatapultXValue() {
		return katapultX;
	}

	public ExplosionSettingsModel getExpSettings() {
		return expSettings;
	}

	@Override
	public String getName() {
		return "gamesettings";
	}

	@Override
	public List<? extends PropertiesList> getProperties() {
		return subModels;
	}

	@Override
	public List<? extends XMLable> getXMLables() {
		return xmlables;
	}

}
