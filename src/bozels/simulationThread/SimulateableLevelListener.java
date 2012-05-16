package bozels.simulationThread;

public interface SimulateableLevelListener {
	
	void onRestartSimulation(SimulateableLevel source);
	void onSleepingStatusChanged(SimulateableLevel source, boolean isSleeping);
	

}
