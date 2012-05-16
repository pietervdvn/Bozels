package bozels.simulationThread;

import java.lang.Thread.State;
import java.util.LinkedList;
import java.util.Queue;

import bozels.EventSource;
import bozels.SafeList;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class SimulationThread extends EventSource<SimulationThreadListener> implements SimulateableLevelListener {

	private SimulateableLevel abstractLevel;

	private final Thread thread;

	private boolean isPauzed = false;
	private boolean stop = false;

	private final Object pauseLock = new Object();
	private final Object joinLock = new Object();
	private final Object allAreSleepingLock = new Object();
	private boolean isAsleep =false;
	
	private final Queue<Runnable> queue = new LinkedList<Runnable>();

	private static SimulationThread simThread = new SimulationThread();

	public static SimulationThread getTheSimulationThread() {
		return simThread;
	}

	private SimulationThread() {
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				simulate();
			}
		});
		thread.setName("Simulation thread");
		start();
	}

	private void simulate() {
		while (!stop) {
			//pause 
			if (isPauzed) {
				synchronized (pauseLock) {
					try {
						throwStateChanged();
						pauseLock.wait();
						throwStateChanged();
					} catch (InterruptedException e) {
						if (stop) {
							return;
						}
					}
				}
			}
			
			
			//stop simulating when nothing happens or when no level is loaded
			if (abstractLevel == null || abstractLevel.isSleeping()) {
				synchronized (allAreSleepingLock) {
					try {
						isAsleep = true;
						throwStateChanged();
						allAreSleepingLock.wait();
						isAsleep = false;
						throwStateChanged();
					} catch (InterruptedException e) {
						if (stop) {
							return;
						}
					}
				}
			}

			long curTime = System.currentTimeMillis();
			int sleep = 0;
			if (abstractLevel != null) {
				synchronized (abstractLevel) {
					//steps one world step
					sleep = abstractLevel.step();
				}
			}
			synchronized (queue) {
				while (!queue.isEmpty()) {
					//execute queue
					queue.poll().run();
				}
			}
			synchronized (joinLock) {
				//notifies all joined threads
				joinLock.notifyAll();
			}
			//Benchmark how long the cycle took, and wait how long there should still be waited
			long lostTime = System.currentTimeMillis() - curTime;
			if (lostTime <= sleep) {
				try {
					Thread.sleep(sleep - lostTime);
				} catch (InterruptedException e) {
				}
			}

		}
	}

	/**
	 * Waits (blocks) till the next step of the world is calculated. If the loop
	 * is paused, this will not return until unpaused.
	 * 
	 * @throws InterruptedException
	 */
	public void joinStep() throws InterruptedException {
		synchronized (joinLock) {
			joinLock.wait();
		}
	}

	public void joinStep(int maxTimeoutMillis) throws InterruptedException {
		synchronized (joinLock) {
			joinLock.wait(maxTimeoutMillis);
		}
	}

	/**
	 * Injects a runnable in the thread as queue. This time adds up to one step,
	 * so the runnables should return quickly.
	 * 
	 * If the queue is being executed, this method will block. (This implies
	 * passed runnables should NEVER call this method)
	 * 
	 * The queue does an attempt to stabilize the waiting step, so as long as
	 * the execution time < sleepDt, the sleep will remain (more or less)
	 * constant
	 * 
	 * @param runnable
	 */
	public void inject(Runnable runnable) {
		synchronized (queue) {
			queue.add(runnable);
		}
	}

	public void start() {
		stop = false;
		isPauzed = false;
		synchronized (pauseLock) {
			pauseLock.notifyAll();
		}
		if (thread.getState() == State.NEW) {
			thread.start();
		}
	}

	public void stop() {
		stop = true;
		synchronized (pauseLock) {
			pauseLock.notifyAll();
		}
		synchronized (allAreSleepingLock) {
			allAreSleepingLock.notifyAll();
		}
	}

	public void pause(boolean pause) {
		isPauzed = pause;
		synchronized (pauseLock) {
			pauseLock.notifyAll();
		}
		throwStateChanged();
	}
	
	public void pause(){
		pause(true);
	}

	public void unpause() {
		pause(false);
	}

	public SimulateableLevel getLevel() {
		return abstractLevel;
	}

	public boolean isPauzed() {
		return isPauzed;
	}
	
	public boolean isStopped() {
		return stop;
	}
	
	public boolean isAsleep() {
		return isAsleep;
	}
	
	public synchronized void setLevel(SimulateableLevel newLevel) {
		if (abstractLevel == newLevel) {
			return;
		}
		synchronized (pauseLock) {
			if(this.abstractLevel != null){
				this.abstractLevel.removeListener(this);
			}
			this.abstractLevel = newLevel;
			this.abstractLevel.addListener(this);
		}
		synchronized (allAreSleepingLock) {
			allAreSleepingLock.notifyAll();
		}
	}
	
	@Override
	public void onRestartSimulation(SimulateableLevel source) {
		synchronized (allAreSleepingLock) {
			allAreSleepingLock.notifyAll();
		}
	}

	private void throwStateChanged() {
		SafeList<SimulationThreadListener> l = getListeners();
		synchronized (l) {
			l.resetCounter();
			while(l.hasNext()){
				l.next().onStateChanged();
			}
		}
	}
	
	@Override
	public void onSleepingStatusChanged(SimulateableLevel l, boolean isSleeping) {
		if(!isSleeping){
			synchronized (allAreSleepingLock) {
				allAreSleepingLock.notifyAll();
			}
		}
	}
}
