package bozels.gui.mainWindow;

import javax.swing.JOptionPane;

import bozels.gui.resourceModel.localeConstant.LocaleConstant;
import bozels.levelModel.core.Level;
import bozels.levelModel.core.LevelListenerAdapter;
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
public class WonLostDialogDisplayer extends LevelListenerAdapter implements SuperModelListener{

	private final SuperModel supM;
	
	private boolean optionWasShowed = false;
	
	public WonLostDialogDisplayer(SuperModel superModel) {
		this.supM = superModel;
		superModel.addListener(this);
	}

	@Override
	public void onSleepingStatusChanged(Level source) {
		if (source.hasLost()){
			createOptionPane(LocaleConstant.LOST_MESSAGE, LocaleConstant.GAME_OVER_TITLE);
		}
	}
	
	@Override
	public void onNumberOfTargetsChanged(Level source) {
		if(source.hasWon()){
			createOptionPane(LocaleConstant.WON_MESSAGE, LocaleConstant.GAME_OVER_TITLE);
		}
	}
	
	private synchronized void createOptionPane(final LocaleConstant message,
			final LocaleConstant title) {
		
		if(optionWasShowed){
			return;
		}
		optionWasShowed = true;
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				JOptionPane.showMessageDialog(supM.getResourceModel().getWindow(), 
						supM.getResourceModel().getU(message), 
						supM.getResourceModel().getU(title),
						JOptionPane.INFORMATION_MESSAGE);
			}
		}).start();

	}

	@Override
	public void modelChanged(SuperModel source) {
	}

	@Override
	public void levelChanged(SuperModel source, Level newLevel) {
		newLevel.addListener(this);
		optionWasShowed = false;
	}

	@Override
	public void levelDefinitionChanged(SuperModel source,
			LevelDefinition newLevelDef) {
	}

	@Override
	public void doSlowInit(SuperModel source) {
	}
	

	
}
