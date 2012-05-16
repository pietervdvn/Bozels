package bozels.levelModel.core;

import bozels.EventSource;
import bozels.SafeList;
import bozels.levelModel.gameObjects.GameObject;
import bozels.levelModel.gameObjects.bozel.Bozel;
import bozels.simulationThread.SimulateableLevel;
import bozels.simulationThread.SimulateableLevelListener;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
abstract class LevelEventSource extends  EventSource<LevelListener> {
	
	private final SafeList<SimulateableLevelListener> listeners = new SafeList<SimulateableLevelListener>();
	
	public void addListener(SimulateableLevelListener listener){
		listeners.add(listener);
	}
	
	public void addHardListener(SimulateableLevelListener listener){
		listeners.addHard(listener);
	}
	
	
	public void removeListener(SimulateableLevelListener listener){
		listeners.remove(listener);
	}
	
	public SafeList<SimulateableLevelListener> getSimulatableLevelListeners(){
		return listeners;
	}
	
	protected void throwRestartSimulation(){
		synchronized (listeners) {
			listeners.resetCounter();
			while(listeners.hasNext()){
				listeners.next().onRestartSimulation((SimulateableLevel) this);
			}
		}
	}
	
	protected void throwSleepStateChanged() {
		synchronized (listeners) {
			listeners.resetCounter();
			while(listeners.hasNext()){
				listeners.next().onSleepingStatusChanged((SimulateableLevel) this, 
						((SimulateableLevel) this).isSleeping());
			}
		}
		SafeList<LevelListener> listeners = getListeners();
		synchronized (listeners) {
			listeners.resetCounter();
			while(listeners.hasNext()){
				listeners.next().onSleepingStatusChanged((Level) this);
			}
		}
	};
	
	// \\//\\//\\//\\ NORMAL LEVELLISTENER //\\//\\//\\//\\

	
	protected void throwObjectsChanged() {
		SafeList<LevelListener> list = getListeners();
		synchronized (list) {
			list.resetCounter();
			while(list.hasNext()){
				list.next().onObjectsChanged((Level) this);
			}
		}
	}
	
	protected void throwWorldStepped() {
		SafeList<LevelListener> list = getListeners();
		synchronized (list) {
			list.resetCounter();
			while(list.hasNext()){
				list.next().onWorldStepped((Level) this);
			}
		}
	};
	
	protected void throwNumberOfBozelsChanged() {
		SafeList<LevelListener> list = getListeners();
		synchronized (list) {
			list.resetCounter();
			while(list.hasNext()){
				list.next().onNumberOfBozelsChanged((Level) this);
			}
		}
	};
	
	protected void throwNumberOfTargetsChanged() {
		SafeList<LevelListener> list = getListeners();
		synchronized (list) {
			list.resetCounter();
			while(list.hasNext()){
				list.next().onNumberOfTargetsChanged((Level) this);
			}
		}
	};
	
	protected void throwBozelLaunched(Bozel bozel) {
		SafeList<LevelListener> list = getListeners();
		synchronized (list) {
			list.resetCounter();
			while(list.hasNext()){
				list.next().onBozelLaunched((Level) this, bozel);
			}
		}
		throwRestartSimulation(); //level must be restarted when launched
	}
	
	protected void throwBozelMoved(Bozel bozel) {
		SafeList<LevelListener> list = getListeners();
		synchronized (list) {
			list.resetCounter();
			while(list.hasNext()){
				list.next().onBozelMoved((Level) this, bozel);
			}
		}
	};
	
	protected void throwBozelExpired(Bozel bozel) {
		SafeList<LevelListener> list = getListeners();
		synchronized (list) {
			list.resetCounter();
			while(list.hasNext()){
				list.next().onBozelExpired((Level) this, bozel);
			}
		}
	};
	
	protected void throwBozelCharged(Bozel bozel) {
		SafeList<LevelListener> list = getListeners();
		synchronized (list) {
			list.resetCounter();
			while(list.hasNext()){
				list.next().onBozelCharged((Level) this, bozel);
			}
		}
	};
	
	protected void throwBozelUsedSkill(Bozel bozel) {
		SafeList<LevelListener> list = getListeners();
		synchronized (list) {
			list.resetCounter();
			while(list.hasNext()){
				list.next().onBozelUsedSkill((Level) this, bozel);
			}
		}
	};
	
	protected void throwObjectRemoved(GameObject removed) {
		SafeList<LevelListener> list = getListeners();
		synchronized (list) {
			list.resetCounter();
			while(list.hasNext()){
				list.next().onObjectRemoved((Level) this, removed, removed.getMaterial());
			}
		}
	};
	
	protected void throwObjectContact(GameObject obj1, GameObject obj2, int force) {
		SafeList<LevelListener> list = getListeners();
		synchronized (list) {
			list.resetCounter();
			while(list.hasNext()){
				list.next().onObjectContact((Level) this, obj1, obj2, force);
			}
		}
	};

}
