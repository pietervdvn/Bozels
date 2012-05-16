package bozels.consoleViews;

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
public class SimulationThreadWatcher implements SimulationThreadListener {

	private final SimulationThread toWatch;

	public SimulationThreadWatcher(SimulationThread toWatch) {
		this.toWatch = toWatch;
		toWatch.addHardListener(this);
		onStateChanged();
	}

	@Override
	public void onStateChanged() {
		String status = null;
		
		if(toWatch.isPauzed()){
			status = "paused";
		}else if(toWatch.isAsleep()){
			status = "sleeping";
		}else if(toWatch.isStopped()){
			status = "stopped";
		} else{
			status = "active";
		}
		System.out.println("Rendering thread is now "+status);
	}
}
