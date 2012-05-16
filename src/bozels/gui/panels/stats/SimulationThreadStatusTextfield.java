package bozels.gui.panels.stats;

import javax.swing.JTextField;

import bozels.gui.resourceModel.ResourceTracker;
import bozels.gui.resourceModel.ResourceTrackerListener;
import bozels.gui.resourceModel.localeConstant.LocaleConstant;
import bozels.simulationThread.SimulationThread;
import bozels.simulationThread.SimulationThreadListener;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class SimulationThreadStatusTextfield extends JTextField implements SimulationThreadListener, ResourceTrackerListener{
	private static final long serialVersionUID = -2206997322015543546L;
	
	private final ResourceTracker tracker;
	
	public SimulationThreadStatusTextfield(ResourceTracker tracker) {
		this.tracker = tracker;
		tracker.addListener(this);
		
		this.setEditable(false);
		SimulationThread.getTheSimulationThread().addListener(this);
		onStateChanged();
	}

	@Override
	public void onStateChanged() {
		SimulationThread st = SimulationThread.getTheSimulationThread();
		if(st.isPauzed()){
			this.setText(LocaleConstant.PAUSED);
		}else if(st.isAsleep()){
			this.setText(LocaleConstant.SLEEPING);
		}else{
			this.setText(LocaleConstant.ACTIVE);
		}
	}

	public void setText(LocaleConstant name){
		this.setText(tracker.getU(name));
	}
	
	@Override
	public void onLocaleChanged(ResourceTracker source) {
		onStateChanged();
	}

}
