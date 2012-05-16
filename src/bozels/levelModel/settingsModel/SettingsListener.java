package bozels.levelModel.settingsModel;

public interface SettingsListener {
	
	void onGravityChanged(SettingsModel source);
	void onTimeSettingsChanged(SettingsModel source);
	/**
	 * The Y threshold is the y-value under which every object should be removed.
	 * This prevents objects from falling 'to infinity'
	 * 
	 * Use Double.NEGATIVE_INFINITY to disable
	 * @param source
	 */
	void onYThresholdChanged(SettingsModel source);
	void onKatapultSettingsChanged(SettingsModel source);
	
}
