package bozels.gui.actions;


import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.text.JTextComponent;

import bozels.gui.resourceModel.ResourceTracker;
import bozels.gui.resourceModel.localeConstant.LocaleConstant;
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
public class BooleanValueSelectAction extends AutoAction implements ValueListener<Boolean>{
	private static final long serialVersionUID = -5717236363170615996L;
	
	private final Value<Boolean> bSwitch;
	
	private final List<JTextComponent> switchesWith = new ArrayList<JTextComponent>();
	private final List<JTextComponent> switchesAgainst = new ArrayList<JTextComponent>();
	
	private final List<Action> enablesWith = new ArrayList<Action>();
	private final List<Action> disablesWith = new ArrayList<Action>();

	private Icon up;
	private Icon down;
	
	public BooleanValueSelectAction(Value<Boolean> switchAction, String on, String off, int size, ResourceTracker tracker){
		super(null, tracker);
		this.putValue(NAME, "");
		this.bSwitch = switchAction;
		bSwitch.addListener(this);
		this.up = tracker.getIcon(on, size);
		this.down = tracker.getIcon(off, size);
		onValueChanged(switchAction, switchAction.get());
	}
	
	public BooleanValueSelectAction(Value<Boolean> switchAction, LocaleConstant name, ResourceTracker tracker) {
		super(name, tracker);
		this.bSwitch = switchAction;
		bSwitch.addListener(this);
		onValueChanged(switchAction, switchAction.get());

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		bSwitch.set(!bSwitch.get());
	}
	
	public void revalidate(){
		onValueChanged(bSwitch, bSwitch.get());
	}

	@Override
	public void onValueChanged(Value<Boolean> source, Boolean b) {
		putValue(SELECTED_KEY, b);
		
		putValue(LARGE_ICON_KEY, b ? up : down);
		
		for (JTextComponent with : switchesWith) {
			with.setEditable(b);
		}
		for (JTextComponent with : switchesAgainst) {
			with.setEditable(!b);
		}
		
		for(Action en : enablesWith){
			en.setEnabled(b);
		}

		for(Action dis : disablesWith){
			dis.setEnabled(!b);
		}

		
	}

	public List<JTextComponent> getSwitchesWith() {
		return switchesWith;
	}

	public List<JTextComponent> getSwitchesAgainst() {
		return switchesAgainst;
	}
	
	public List<Action> getEnablesWith() {
		return enablesWith;
	}
	
	public List<Action> getDisablesWith() {
		return disablesWith;
	}

}
