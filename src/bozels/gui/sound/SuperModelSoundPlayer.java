package bozels.gui.sound;

import bozels.levelModel.core.Level;
import bozels.levelModel.settingsModel.SettingsListener;
import bozels.levelModel.settingsModel.SettingsModel;
import bozels.superModel.SuperModel;
import bozels.superModel.SuperModelListener;
import bozels.xml.levels.LevelDefinition;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class SuperModelSoundPlayer implements SuperModelListener {
	
	private final SoundsPool pool;
	private LevelSoundPlayer level;
	
	public SuperModelSoundPlayer(final SuperModel supModel) {
		supModel.addHardListener(this);
		pool = supModel.getResourceModel().getSoundPool();
		if(!pool.initIsDone()){
			pool.addHardListener(new SoundsPoolListener() {
				@Override
				public void initDone(SoundsPool source) {
					source.playAndLoop(SoundsPool.OPENING, SoundsPool.BACKGROUND);
					source.removeListener(this);
				}
			});
		}else{
			pool.playAndLoop(SoundsPool.OPENING, SoundsPool.BACKGROUND);
		}
		
		supModel.getGameSettingsModel().addHardListener(new SettingsListener() {
			@Override
			public void onYThresholdChanged(SettingsModel source) {
			}
			
			@Override
			public void onTimeSettingsChanged(SettingsModel source) {
			}
			
			@Override
			public void onKatapultSettingsChanged(SettingsModel source) {
			}
			
			@Override
			public void onGravityChanged(SettingsModel source) {
				if(source.getGravity().y>=0){
					supModel.getResourceModel().getSoundPool().play(SoundsPool.ANTI_GRAVITY);
					source.removeListener(this);
				}
				
			}
		});
		
	}

	@Override
	public void modelChanged(SuperModel source) {
		
	}

	@Override
	public void levelChanged(SuperModel source, Level newLevel) {
		level = new LevelSoundPlayer(newLevel, pool);
	}

	@Override
	public void levelDefinitionChanged(SuperModel source,
			LevelDefinition newLevelDef) {

		pool.play(SoundsPool.THEME);
		
	}

	@Override
	public void doSlowInit(SuperModel source) {
	}

	public LevelSoundPlayer getLevelSoundPlayer() {
		return level;
	}

}
