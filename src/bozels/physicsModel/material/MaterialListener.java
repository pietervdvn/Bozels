package bozels.physicsModel.material;

public interface MaterialListener {
	
	void onFixtureSettingChanged(Material source);
	void onStrengthSettingChanged(Material source);

}
