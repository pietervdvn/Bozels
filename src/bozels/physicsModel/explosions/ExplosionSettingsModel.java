package bozels.physicsModel.explosions;

import java.util.ArrayList;
import java.util.List;

import bozels.EventSource;
import bozels.SafeList;
import bozels.valueWrappers.DoubleValue;
import bozels.valueWrappers.IntegerValue;
import bozels.valueWrappers.Value;
import bozels.valueWrappers.ValueListener;
import bozels.valueWrappers.validators.DoubleRangeValidator;
import bozels.valueWrappers.validators.IntegerRangeValidator;
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
public class ExplosionSettingsModel extends EventSource<ExplosionSettingsModelListener> implements PropertiesList{

	private Value<Double> range = new DoubleValue(10.0,"range", new DoubleRangeValidator(0.0, Double.POSITIVE_INFINITY));
	private Value<Double> maxForce =  new DoubleValue(500.0, "maxforce", new DoubleRangeValidator(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY));
	private Value<Integer> numberOfRays = new IntegerValue(720, "numberofrays", new IntegerRangeValidator(1, 1440));
	
	private List<XMLable> xmlables = new ArrayList<XMLable>();
	
	public ExplosionSettingsModel() {
		xmlables.add(range);
		xmlables.add(maxForce);
		xmlables.add(numberOfRays);
		
		ValueListener<Double> l = new ValueListener<Double>() {
			@Override
			public void onValueChanged(Value<Double> source, Double newValue) {
				throwSettingsChanged();
			}
		};
		
		range.addHardListener(l);
		maxForce.addHardListener(l);
		
		numberOfRays.addHardListener(new ValueListener<Integer>() {

			@Override
			public void onValueChanged(Value<Integer> source, Integer newValue) {
				throwSettingsChanged();
			}
		});
		
	}
	
	private void throwSettingsChanged(){
		SafeList<ExplosionSettingsModelListener> listeners = getListeners();
		synchronized (listeners) {
			listeners.resetCounter();
			while(listeners.hasNext()){
				listeners.next().onSettingsChanged(this);
			}
		}
	}
	
	public double getRange() {
		return range.get();
	}
	
	public Value<Double> getRangeValue(){
		return range;
	}
	
	public void setRange(double range) {
		this.range.set(range);
	}
	public double getMaxForce() {
		return maxForce.get();
	}
	
	public Value<Double> getMaxForceValue(){
		return maxForce;
	}

	public void setMaxForce(double maxForce) {
		this.maxForce.set(maxForce);
	}
	public int getNumberOfRays() {
		return numberOfRays.get();
	}
	public Value<Integer> getNumberOfRaysValue(){
		return numberOfRays;
	}
	
	public void setNumberOfRays(int numberOfRays) {
		this.numberOfRays.set(numberOfRays);
	}

	@Override
	public String getName() {
		return "explosionSettings";
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
