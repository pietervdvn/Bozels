package bozels.gui.mainWindow;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import bozels.SafeList;
import bozels.gui.panels.MainSettingsEditorPanel;
import bozels.gui.resourceModel.BozelMenuBar;
import bozels.gui.resourceModel.ResourceTracker;
import bozels.gui.resourceModel.ResourceTrackerListener;
import bozels.gui.resourceModel.actionHub.ActionKey;
import bozels.gui.resourceModel.localeConstant.LocaleConstant;
import bozels.levelModel.core.Level;
import bozels.superModel.SuperModel;
import bozels.superModel.SuperModelListener;
import bozels.visualisatie.LevelDrawer;
import bozels.visualisatie.RenderingThread;
import bozels.visualisatie.controllers.MouseController;
import bozels.visualisatie.controllers.ScreenSizeUpdater;
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
public class MainWindow extends JFrame implements SuperModelListener,
		ResourceTrackerListener, WindowListener {
	private static final long serialVersionUID = 1L;

	private JPanel levelPanel;
	private JMenuBar menuBar;

	private final SuperModel model;
	private final WonLostDialogDisplayer displayer;

	SafeList<MainWindowListener> listeners = new SafeList<MainWindowListener>();

	public MainWindow(SuperModel superModel) {
		super(superModel.getResourceModel().getU(LocaleConstant.WINDOW_TITLE));
		this.model = superModel;
		this.displayer = new WonLostDialogDisplayer(superModel);
		model.getResourceModel().setWindow(this);
		superModel.addListener(this);

		initLevelPanel(superModel, superModel.getLevel());

		menuBar = new BozelMenuBar(superModel);

		superModel.getResourceModel().addListener(this);
		setJMenuBar(menuBar);

		JPanel editor = new MainSettingsEditorPanel(model, this);
		editor.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		this.add(editor, BorderLayout.SOUTH);
		this.pack();
		this.setMinimumSize(this.getSize());
		this.setVisible(true);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(this);
	}

	public void addListener(MainWindowListener l) {
		listeners.add(l);
	}

	public void removeListener(MainWindowListener l) {
		listeners.remove(l);
	}

	private void throwLevelDrawerChanged(LevelDrawer drawer) {
		synchronized (listeners) {
			listeners.resetCounter();
			while (listeners.hasNext()) {
				listeners.next().onLevelPanelChanged(this, drawer);
			}
		}
	}

	private void initLevelPanel(SuperModel supM, Level newLevel) {
		if (levelPanel != null) {
			this.remove(levelPanel);
		}

		if (newLevel == null) {
			levelPanel = new JPanel();
			Dimension panelSize = new Dimension(supM.getPainterSettingsModel()
					.getWidth(), supM.getPainterSettingsModel().getHeight());
			levelPanel.setSize(panelSize);
			levelPanel.setPreferredSize(panelSize);

		} else {
			RenderingThread.getTheRenderingThread().removeAll();
			LevelDrawer levelPanel = new LevelDrawer(supM);
			MouseController controller = new MouseController(supM.getLevel(),
					supM.getPainterSettingsModel());
			levelPanel.addMouseListener(controller);
			levelPanel.addMouseMotionListener(controller);
			new ScreenSizeUpdater(levelPanel, supM.getPainterSettingsModel());
			levelPanel.refresh();
			this.levelPanel = levelPanel;
			throwLevelDrawerChanged(levelPanel);
		}
		levelPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(levelPanel, BorderLayout.CENTER);
		levelPanel.repaint();
		this.revalidate();
	}

	@Override
	public void modelChanged(SuperModel source) {
	}

	@Override
	public void levelChanged(SuperModel supM, Level newLevel) {
		initLevelPanel(supM, newLevel);
	}

	@Override
	public void levelDefinitionChanged(SuperModel source,
			LevelDefinition newLevelDef) {
	}

	@Override
	public void onLocaleChanged(ResourceTracker source) {
		menuBar = new BozelMenuBar(model);
		this.setJMenuBar(menuBar);
		this.revalidate();
		this.setTitle(source.get(LocaleConstant.WINDOW_TITLE));
	}

	public void onNumberOfTargetsChanged(Level source) {
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		model.getResourceModel().getActionHub().getAction(ActionKey.QUIT)
				.actionPerformed(null);
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
	}

	@Override
	public void doSlowInit(SuperModel source) {
	}

	public WonLostDialogDisplayer getDisplayer() {
		return displayer;
	}

}
