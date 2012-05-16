package bozels.gui.mainWindow;

import bozels.visualisatie.LevelDrawer;

public interface MainWindowListener {
	
	void onLevelPanelChanged(MainWindow source, LevelDrawer newDrawer);

}
