package bozels.physicsModel.material;


import java.util.ArrayList;
import java.util.List;

import org.jbox2d.dynamics.FixtureDef;

import bozels.EventSource;
import bozels.SafeList;
import bozels.gui.resourceModel.localeConstant.LocaleConstant;
import bozels.valueWrappers.BooleanValue;
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
public class Material extends EventSource<MaterialListener> implements ValueListener<Boolean>, PropertiesList{
	
	private final LocaleConstant name;

	private final Value<Boolean> canBreak;
	private final Value<Double> powerThrshold;
	private final Value<Double> strngth;
	
	private final Value<Double> density;
	private final Value<Double> friction;
	private final Value<Double> restitution;
	
	/**
	 * Int key to a viewSetting color key
	 */
	private final Value<Integer> colorKey;
	
	private final List<XMLable> xmlables = new ArrayList<XMLable>();
	
	public Material(double density, 
			double restitution, 
			double friction, 
			double powerThreshold, 
			double strength, int colorKey,
			LocaleConstant name) {
		this.name = name;
		this.colorKey = new IntegerValue(colorKey, "colorKey");

		this.powerThrshold = new DoubleValue(powerThreshold, "powerthreshold");
		this.strngth = new DoubleValue(strength, "strength");
		this.canBreak = new BooleanValue(false, "isbreakable");
		canBreak.set((powerThreshold!=Double.POSITIVE_INFINITY) || (strength != Double.POSITIVE_INFINITY));
		canBreak.addListener(this);

		ValueListener<Double> strengthL = new ValueListener<Double>() {
			@Override
			public void onValueChanged(Value<Double> source, Double newValue) {
				canBreak.set((powerThrshold.get()!=Double.POSITIVE_INFINITY) ||
						(strngth.get()!= Double.POSITIVE_INFINITY));
				throwStrengthSettingsChanged();
			}
		};
		
		//Hard listeners, as there will be no other reference saved
		this.powerThrshold.addHardListener(strengthL);
		this.strngth.addHardListener(strengthL);
 
		
		this.density = new DoubleValue(density, "density");
		this.friction = new DoubleValue(friction, "friction");
		this.restitution = new DoubleValue(restitution, "restitution");
		
		ValueListener<Double> fixtL = new ValueListener<Double>() {
			@Override
			public void onValueChanged(Value<Double> source, Double newValue) {
				throwFixtSettingsChanged();
			}
		}; 
		
		this.density.addHardListener(fixtL);
		this.friction.addHardListener(fixtL);
		this.restitution.addHardListener(fixtL);
		
		xmlables.add(this.canBreak);
		xmlables.add(this.powerThrshold);
		xmlables.add(this.strngth);
		
		xmlables.add(this.density);
		xmlables.add(this.friction);
		xmlables.add(this.restitution);
		
		xmlables.add(this.colorKey);
	}
	
	/**
	 * Copies all the settings of the given material into this material.
	 * 
	 * Warning: name and colorkey are NOT copied
	 * @param mat
	 */
	public void copy(Material mat){
		canBreak.set(mat.canBreak());
		powerThrshold.set(mat.getPowerThreshold());
		strngth.set(mat.getStrength());
		density.set(mat.getDensity());
		friction.set(mat.getFriction());
		restitution.set(mat.getRestitution());
		
	}
	
	public Material clone(){
		return new Material(getDensity(), getRestitution(), getFriction(), getPowerThreshold(), getStrength(), getColorKey(), getMaterialName());
	}
	
	public FixtureDef createFixtureDefinition(){
		FixtureDef fixtDef = new FixtureDef();
		fixtDef.density = (float) (double) density.get();
		fixtDef.friction = (float) (double) friction.get();
		fixtDef.restitution = (float) (double) restitution.get();
		return fixtDef;
	}
	
	@Override
	public boolean equals(Object object) {
		
		if(!(object instanceof Material)){
			return false;
		}
		
		if(super.equals(object)){
			return true;
		}
		
		Material obj = (Material) object;
		return obj.getDensity() == density.get() && 
				obj.getRestitution() == restitution.get() &&
				obj.getFriction() == friction.get() &&
				obj.getPowerThreshold() == powerThrshold.get() &&
				obj.getStrength() == strngth.get() &&
				obj.getColorKey() == colorKey.get();
	}
	
	@Override
	public String toString() {
		
		return "Material: "+this.getClass().getName()+", dens: "+getDensity()+", rest: "+getRestitution()
				+", frict: "+getFriction()+", powerThres: "+getPowerThreshold()+", strength: "+getStrength()
				+", colorkey: "+getColorKey();
	}
	
	public double getDensity() {
		return density.get();
	}
	
	public Value<Double> getDensitValue(){
		return density;
	}

	public double getRestitution() {
		return restitution.get();
	}
	
	public Value<Double> getRestitutionValue(){
		return restitution;
	}

	public double getFriction() {
		return friction.get();
	}
	
	public Value<Double> getFrictionValue(){
		return friction;
	}

	public double getPowerThreshold() {
		return powerThrshold.get();
	}
	
	public Value<Double> getPowerThresholdValue(){
		return powerThrshold;
	}

	public double getStrength() {
		return strngth.get();
	}
	
	public Value<Double> getStrengthValue(){
		return strngth;
	}

	public int getColorKey() {
		return colorKey.get();
	}
	
	public Value<Integer> getColorKeyValue(){
		return colorKey;
	}
	
	public void setPowerThreshold(double powerThreshold) {
		this.powerThrshold.set(powerThreshold);
	}
	
	public void setStrength(double strength) {
		this.strngth.set(strength);
	}
	
	public void setDensity(double density) {
		this.density.set(density);
	}
	
	public void setFriction(double friction) {
		this.friction.set(friction);
	}
	
	public void setRestitution(double restitution) {
		this.restitution.set(restitution);
	}
	
	private void throwFixtSettingsChanged(){
		SafeList<MaterialListener> l = getListeners();
		synchronized (l) {
			l.resetCounter();
			while(l.hasNext()){
				l.next().onFixtureSettingChanged(this);
			}
		}
	}
	
	private void throwStrengthSettingsChanged(){
		SafeList<MaterialListener> l = getListeners();
		synchronized (l) {
			l.resetCounter();
			while(l.hasNext()){
				l.next().onStrengthSettingChanged(this);
			}
		}
	}

	public boolean canBreak(){
		return canBreak.get();
	}
	
	public Value<Boolean> getCanBreak(){
		return canBreak;
	}
	
	@Override
	public void onValueChanged(Value<Boolean> source, Boolean curVal) {
		throwStrengthSettingsChanged();
	}

	public LocaleConstant getMaterialName() {
		return name;
	}

	@Override
	public String getName() {
		return name.toString();
	}

	@Override
	public List<? extends PropertiesList> getProperties() {
		return null;
	}

	@Override
	public List<? extends XMLable> getXMLables() {
		return xmlables;
	}


}
