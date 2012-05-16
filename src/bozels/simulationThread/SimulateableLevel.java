package bozels.simulationThread;

public interface SimulateableLevel {

	void addListener(SimulateableLevelListener simulationThread);
	void removeListener(SimulateableLevelListener simulationThread);

	/**
	 * Is a new step needed?
	 * @return
	 */
	boolean isSleeping();
	
	/**
	 * Step the level one timestep.
	 * Returns the time in milliseconds the simulationthread should wait
	 * @return
	 */
	int step();

}
