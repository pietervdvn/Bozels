package bozels.superModel;

import bozels.levelModel.core.Level;
import bozels.xml.levels.LevelDefinition;

public interface SuperModelListener {

	void modelChanged(SuperModel source);
	void levelChanged(SuperModel source, Level newLevel);
	void levelDefinitionChanged(SuperModel source, LevelDefinition newLevelDef);
	
	/**
	 * Slow init is there for initializing timeconsuming objects, which will be needed later by the user.
	 * e.g: making a filechooser takes time, and isn't needed the first seconds.
	 * therefore, the mainwindow is created, and afterwards the filechooser is created
	 * (but not visible)
	 * 
	 */
	void doSlowInit(SuperModel source);
	
}
