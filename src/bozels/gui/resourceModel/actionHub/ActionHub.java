package bozels.gui.resourceModel.actionHub;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import bozels.gui.about.AboutDialog;
import bozels.gui.actions.AutoAction;
import bozels.gui.basicComponents.ColorDialog;
import bozels.gui.resourceModel.ResourceTracker;
import bozels.gui.resourceModel.localeConstant.LocaleConstant;
import bozels.levelModel.core.Level;
import bozels.simulationThread.SimulationThread;
import bozels.simulationThread.SimulationThreadListener;
import bozels.superModel.SuperModel;
import bozels.superModel.SuperModelListener;
import bozels.visualisatie.painterSettingsModel.PainterSettingsModel;
import bozels.xml.XMLConverter;
import bozels.xml.levels.InvalidBozelObjectException;
import bozels.xml.levels.LevelBuilder;
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
public class ActionHub implements SuperModelListener{

	private Map<ActionKey, Action> actions = new HashMap<ActionKey, Action>();
	
	private final SuperModel supModel;
	private final JFrame window;
	
	private final ResourceTracker tracker;
	
	private ColorDialog colorDialog;
	private AboutDialog aboutDialog;
	private JFileChooser fileChooser;
	
	private File previousSettingsFile;
	
	public ActionHub(SuperModel supM, ResourceTracker tracker) {
		this.supModel = supM;
		this.tracker = tracker;
		this.window = tracker.getWindow();
		try{
			this.previousSettingsFile = new File(System.getProperty("user.home"));
		}catch (Exception e) {}
		supM.addListener(this);
		actions.put(ActionKey.NOTHING, new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
		actions.put(ActionKey.PAUSE, new PauseAction(tracker));
		actions.put(ActionKey.RESTART, new RestartAction(tracker));
		actions.put(ActionKey.QUIT, new QuitAction(tracker));
		actions.put(ActionKey.OPEN_FILE, new OpenAction(tracker));
		actions.put(ActionKey.BACKGROUND_COLOR, new ChangeBackgroundAction(tracker, supM.getPainterSettingsModel()));
		
		actions.put(ActionKey.LOAD_SETTINGS, new LoadSettingsAction(tracker));
		actions.put(ActionKey.SAVE_SETTINGS, new SaveSettingsAction(tracker));
		actions.put(ActionKey.RESET_SETTINGS, new ResetSettingsAction(tracker));
	}
	
	@Override
	public void doSlowInit(SuperModel source) {
		if(colorDialog == null){
			colorDialog = new ColorDialog(tracker.getWindow(), tracker, LocaleConstant.COLOR_PICKER_TITLE_FOR, LocaleConstant.OK);
		}
		if(aboutDialog == null){
			aboutDialog = new AboutDialog(tracker);
		}
		if(fileChooser == null){
			fileChooser = new JFileChooser();
		}
	}
	
	public ColorDialog getColorDialog() {
		if(colorDialog == null){
			doSlowInit(supModel);
		}
		return colorDialog;
	}
	
	public AboutDialog getAboutDialog() {
		if(aboutDialog == null){
			aboutDialog = new AboutDialog(tracker);
		}
		return aboutDialog;
	}
	
/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
	private class ChangeBackgroundAction extends AutoAction {
		private static final long serialVersionUID = 1L;
		
		private final PainterSettingsModel model;

		public ChangeBackgroundAction(ResourceTracker tracker, PainterSettingsModel model) {
			super(LocaleConstant.BACKGROUND_COLOR_MENU_ITEM, tracker, true);
			this.model = model;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Color c = getColorDialog().showDialog(model.getBackgroundColor(), LocaleConstant.BACKGROUND_COLOR);
			if(c!=null){
				model.setBackgroundColor(c);
			}
		}
		
	}
	
/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
	private class ResetSettingsAction extends AutoAction{
		private static final long serialVersionUID = 4893392948995413281L;

		public ResetSettingsAction(ResourceTracker tracker) {
			super(LocaleConstant.RESET_SETTINGS, tracker);
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK));
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			URL defSets = tracker.getResource(ResourceTracker.DEFAULT_SETTINGS);
			try {
				SAXBuilder b = new SAXBuilder();
				Element settings = b.build(defSets).getRootElement();
				new XMLConverter(supModel).restore(settings);
			} catch (Exception e) {
				return;
			}
		}
		
	}
	
/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
	private class SaveSettingsAction extends AutoAction{
		private static final long serialVersionUID = -2539633401777927008L;

		public SaveSettingsAction(ResourceTracker tracker) {
			super(LocaleConstant.SAVE_SETTINGS, tracker, true);
			putValue(ACCELERATOR_KEY, 
					KeyStroke.getKeyStroke(KeyEvent.VK_S,
							KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK));
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					if(fileChooser == null){
						doSlowInit(supModel);
					}
					fileChooser.setCurrentDirectory(previousSettingsFile);
					fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
					int ret = fileChooser.showSaveDialog(window);
					if(ret == JFileChooser.APPROVE_OPTION){
						previousSettingsFile = fileChooser.getSelectedFile();
						if(previousSettingsFile.isDirectory()){
							previousSettingsFile = new File(previousSettingsFile.getPath()+"/bozelSettings.xml");
						}
						
						Element el = new XMLConverter(supModel).createElement();
						FileOutputStream out = null;
						ResourceTracker t = supModel.getResourceModel();
						try {
							out = new FileOutputStream(previousSettingsFile);
							XMLOutputter outputter = new XMLOutputter();
							outputter.output(el, out);
						} catch (IOException e) {
							JOptionPane.showMessageDialog(window, 
									t.getU(LocaleConstant.FILE_SAVE_ERROR), 
									t.getU(LocaleConstant.ERROR), JOptionPane.ERROR_MESSAGE);
						}finally{
							if(out != null){
							try {
								out.close();
							} catch (IOException e) {
							}
							}
						}
					}
				}
			}).start();
		}
		
	}
	
/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
	private class LoadSettingsAction extends AutoAction{
		private static final long serialVersionUID = 4306986392492152219L;

		public LoadSettingsAction(ResourceTracker tracker) {
			super(LocaleConstant.LOAD_SETTINGS, tracker, true);
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK));
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					if(fileChooser == null){
						doSlowInit(supModel);
					}
					fileChooser.setCurrentDirectory(previousSettingsFile);
					fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					int ret = fileChooser.showOpenDialog(window);
					if(ret == JFileChooser.APPROVE_OPTION){
						ResourceTracker t = supModel.getResourceModel();
						FileInputStream in = null;
						try {//to bad eclipse doesn't recognize the new try with...
							previousSettingsFile = fileChooser.getSelectedFile();
							SAXBuilder builder = new SAXBuilder();
							in =  new FileInputStream(previousSettingsFile);
							Element settings = builder.build(in).getRootElement();
							new XMLConverter(supModel).restore(settings);
						} catch (IOException e) {
							JOptionPane.showMessageDialog(window, 
									t.getU(LocaleConstant.FILE_ERROR), 
									t.getU(LocaleConstant.ERROR), JOptionPane.ERROR_MESSAGE);
						} catch (Exception e) {
							JOptionPane.showMessageDialog(window, 
									t.getU(LocaleConstant.INVALID_SETTINGS_FILE_MESSAGE), 
									t.getU(LocaleConstant.ERROR), JOptionPane.ERROR_MESSAGE);
						}finally{
							if(in != null){
								try {
									in.close();
								} catch (IOException e) {
								}
							}
						}
						
					}
					
				}
			}).start();
			
		}
		
	}
	
/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
	private class OpenAction extends AutoAction implements SuperModelListener{
		private static final long serialVersionUID = 1L;

		private final LevelBuilder builder = new LevelBuilder();
		private File previousFile;
		
		public OpenAction(ResourceTracker tracker) {
			super(LocaleConstant.OPEN, tracker, true);
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
			supModel.addListener(this);
			try {
				previousFile = new File(tracker.getResource(ResourceTracker.LEVELS+"/s1-1.xml").toURI());
			} catch (Exception e) {
			}
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					if(fileChooser == null){
						doSlowInit(supModel);
					}
					try{
						fileChooser.setCurrentDirectory(previousFile);
					}catch (Exception e) {
					}
					fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					int ret = fileChooser.showOpenDialog(window);
					if(ret == JFileChooser.APPROVE_OPTION){
						previousFile = fileChooser.getSelectedFile();
						fileChooser.setCurrentDirectory(previousFile);
						ResourceTracker t = supModel.getResourceModel();
						try {
							LevelDefinition def = builder.getLevelDefinition(previousFile);
							Level l = new Level(supModel.getGameSettingsModel());
							def.addToLevel(l);
							supModel.setLevelDefinition(def);
							supModel.setLevel(l);
							
						} catch (InvalidBozelObjectException e) {
							JOptionPane.showMessageDialog(window, 
									t.getU(LocaleConstant.INVALID_FILE_MESSAGE), 
									t.getU(LocaleConstant.ERROR), JOptionPane.ERROR_MESSAGE);
						} catch (JDOMException e) {
							JOptionPane.showMessageDialog(window, 
									t.getU(LocaleConstant.INVALID_FILE_MESSAGE), 
									t.getU(LocaleConstant.ERROR), JOptionPane.ERROR_MESSAGE);
						} catch (IOException e) {
							JOptionPane.showMessageDialog(window, 
									t.getU(LocaleConstant.FILE_ERROR), 
									t.getU(LocaleConstant.ERROR), JOptionPane.ERROR_MESSAGE);
						}
						
					}
				}
			}).start();
		}

		@Override
		public void modelChanged(SuperModel source) {
		}

		@Override
		public void levelChanged(SuperModel source, Level newLevel) {
		}

		@Override
		public void levelDefinitionChanged(SuperModel source,
				LevelDefinition newLevelDef) {
		}

		@Override
		public void doSlowInit(SuperModel source) {
			if(fileChooser == null){
				fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			}
		}
		
	}
	
/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
	private class RestartAction extends AutoAction implements SuperModelListener{
		private static final long serialVersionUID = 382456672365418931L;

		public RestartAction(ResourceTracker tracker) {
			super(LocaleConstant.RESTART, tracker);
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, 0));
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if(supModel.getLevelDefinition() == null){
				new Thread(new Runnable() {
					@Override
					public void run() {
						
				JOptionPane.showMessageDialog(window, 
						supModel.getResourceModel().getU(LocaleConstant.NO_LEVEL), 
						supModel.getResourceModel().getU(LocaleConstant.ERROR), JOptionPane.ERROR_MESSAGE);
					}
				}).start();
				return;
			}
			Level l = new Level(supModel.getGameSettingsModel());
			supModel.getLevelDefinition().addToLevel(l);
			supModel.setLevel(l);
		}

		@Override
		public void modelChanged(SuperModel source) {}

		@Override
		public void levelChanged(SuperModel source, Level newLevel) {}

		@Override
		public void levelDefinitionChanged(SuperModel source,
				LevelDefinition newLevelDef) {
			setEnabled(supModel.getLevelDefinition() != null);
		}

		@Override
		public void doSlowInit(SuperModel source) {
		}
		
	}
	
/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
	private class QuitAction extends AutoAction{
		private static final long serialVersionUID = 1L;

		public QuitAction(ResourceTracker tracker) {
			super(LocaleConstant.QUIT, tracker);
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Q, 
					KeyEvent.ALT_DOWN_MASK));
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			int optP = JOptionPane.showConfirmDialog(window, 
					supModel.getResourceModel().getU(LocaleConstant.CONFIRM_EXIT_QUESTION),
					supModel.getResourceModel().getU(LocaleConstant.CONFIRM_EXIT_TITLE), JOptionPane.OK_OPTION & JOptionPane.NO_OPTION);
			if(optP == JOptionPane.OK_OPTION){
				System.exit(0);
			}
		}
		
	}
	
/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
	private class PauseAction extends AutoAction implements
			SimulationThreadListener {
		private static final long serialVersionUID = 1L;

		public PauseAction(ResourceTracker tracker) {
			super(LocaleConstant.STATIC_PAUSE, tracker);
			SimulationThread.getTheSimulationThread().addListener(this);
			onStateChanged();
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_P, 0));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			SimulationThread st = SimulationThread.getTheSimulationThread();
			st.pause(!st.isPauzed());
		}

		@Override
		public void onStateChanged() {
			putValue(SELECTED_KEY, SimulationThread.getTheSimulationThread().isPauzed());
		}

	}

	public Action getAction(ActionKey key) {
		return actions.get(key);
	}

	@Override
	public void modelChanged(SuperModel source) {
	}

	@Override
	public void levelChanged(SuperModel source, Level newLevel) {
	}

	@Override
	public void levelDefinitionChanged(SuperModel source,
			LevelDefinition newLevelDef) {
	}

}
