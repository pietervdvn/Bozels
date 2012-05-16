package bozels.gui.basicComponents.coloringTextField;

import bozels.gui.basicComponents.coloringTextField.parser.RangedDoubleParser;
import bozels.gui.resourceModel.guiColorModel.GUIColorModel;
import bozels.valueWrappers.Value;
import bozels.valueWrappers.ValueListener;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class DoubleValueTextField extends ColoringTextField<Double> implements ValueListener<Double>{
	private static final long serialVersionUID = 7935559637265433617L;

	public DoubleValueTextField(GUIColorModel colorSettings, final Value<Double> value, double min, double max, int round, int scale) {
		super(colorSettings, new RangedDoubleParser(round, min, max, scale) {
			
			@Override
			public void newValueAction(Double newVal) {
				value.set(newVal);
			}
		});
		value.addListener(this);
		setValue(value.get());
	}
	
	public DoubleValueTextField(GUIColorModel colorSettings, final Value<Double> value, double min, double max, int round) {
		this(colorSettings, value, min, max, round, 1);
	}


	@Override
	public void onValueChanged(Value<Double> source, Double newValue) {
		setValue(newValue);
	}


}
