package bozels.levelModel.settingsModel;

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
class SettingsEventSource extends EventSource<SettingsListener>{
	
	protected void throwKatapultSettingsChanged(){
		SafeList<SettingsListener> listeners = getListeners();
		synchronized (listeners) {
			listeners.resetCounter();
			while(listeners.hasNext()){
				listeners.next().onKatapultSettingsChanged((SettingsModel) this);
			}
		}
	}
	
	protected void throwTimeSettingsChanged(){
		SafeList<SettingsListener> listeners = getListeners();
		synchronized (listeners) {
			listeners.resetCounter();
			while(listeners.hasNext()){
				listeners.next().onTimeSettingsChanged((SettingsModel) this);
			}
		}
	}
	
	protected void throwGravityChanged(){
		SafeList<SettingsListener> listeners = getListeners();
		synchronized (listeners) {
			listeners.resetCounter();
			while(listeners.hasNext()){
				listeners.next().onGravityChanged((SettingsModel) this);
			}
		}
	}

}
