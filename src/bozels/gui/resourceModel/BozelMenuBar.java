package bozels.gui.resourceModel;


import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import bozels.gui.actions.AutoAction;
import bozels.gui.actions.BooleanValueSelectAction;
import bozels.gui.resourceModel.actionHub.ActionHub;
import bozels.gui.resourceModel.actionHub.ActionKey;
import bozels.gui.resourceModel.localeConstant.LocaleConstant;
import bozels.superModel.SuperModel;
import bozels.valueWrappers.BooleanValue;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class BozelMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;

	private final ResourceTracker tracker;

	public BozelMenuBar(SuperModel supModel) {
		this.tracker = supModel.getResourceModel();
		final ActionHub hub = tracker.getActionHub();
		final JMenu bestand = createFile(hub);
		this.add(bestand);
		this.add(createGame(hub));
		this.add(createExtra(supModel, hub));
		
		this.getActionMap().put(ActionKey.SELECT_MENU, new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				bestand.setSelected(true);
			}
		});
		
	}
	
	private JMenu createExtra(SuperModel supModel, ActionHub hub){
		JMenu extra = create(LocaleConstant.EXTRA);
		extra.setMnemonic(KeyEvent.VK_X);
		extra.add(createSound());
		extra.add(createBackground(supModel, hub));
		extra.add(createLanguage());
		return extra;
	}
	
	private JMenu createLanguage(){
		JMenu langs = create(LocaleConstant.LANGUAGES);
		List<String> langLst = tracker.getSupportedLanguages();

		ButtonGroup group = new ButtonGroup();
		for (final String lang : langLst) {
			JMenuItem item = new JRadioButtonMenuItem();
			
			group.add(item);
			langs.add(item);
			if (tracker.getLocale().getLanguage().equals(lang)) {
				item.setSelected(true);
			}
			item.setAction(new AbstractAction(tracker.getU(lang)) {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent arg0) {
					tracker.setLocale(new Locale(lang));
				}
			});
			item.setMnemonic(tracker.getU(lang).charAt(0));
			item.setAccelerator(KeyStroke.getKeyStroke(lang.toUpperCase().charAt(0), KeyEvent.ALT_DOWN_MASK));
			
		}
		return langs;
	}
	
	private JMenu createBackground(SuperModel supModel, ActionHub hub){
		JMenu bcgGr = create(LocaleConstant.BACKGROUND);
		JMenuItem background = new JMenuItem(hub.getAction(ActionKey.BACKGROUND_COLOR));
		BooleanValue useImage = supModel.getPainterSettingsModel().getUseBackgroundImage();
		BooleanValueSelectAction toggleBackground = new BooleanValueSelectAction(useImage, LocaleConstant.BACKGROUND_IMAGE, tracker);
		JCheckBoxMenuItem bckgrIm = new JCheckBoxMenuItem(toggleBackground);
		
		toggleBackground.getDisablesWith().add(background.getAction());
		background.getAction().setEnabled(!useImage.get());
		
		bcgGr.add(bckgrIm);
		bcgGr.add(background);
		return bcgGr;
	}
	
	private JMenu createSound(){
		JMenu sound = create(LocaleConstant.SOUND);
		
		BooleanValue muteAllSwitch = tracker.getMuteAll();
		BooleanValue muteBackGrSw = tracker.getSoundPool().getBackgroundWavPlayer().getMuteSwitch();
		BooleanValue muteForeGrSw = tracker.getSoundPool().getPlayer().getMuteSwitch();

		BooleanValueSelectAction muteAllAction = new BooleanValueSelectAction(muteAllSwitch, LocaleConstant.MUTE_ALL, tracker);
		muteAllAction.putValue(AbstractAction.ACCELERATOR_KEY,
				KeyStroke.getKeyStroke(KeyEvent.VK_M, 0));
		JCheckBoxMenuItem muteAll = new JCheckBoxMenuItem(muteAllAction);
		JCheckBoxMenuItem muteBackground = new JCheckBoxMenuItem(new BooleanValueSelectAction(muteBackGrSw, LocaleConstant.MUTE_BACKGROUND, tracker));
		JCheckBoxMenuItem muteForeground = new JCheckBoxMenuItem(new BooleanValueSelectAction(muteForeGrSw, LocaleConstant.MUTE_FOREGROUND, tracker));
		
		sound.add(muteAll);
		sound.addSeparator();
		sound.add(muteForeground);
		sound.add(muteBackground);
		return sound;
	}
	
	private JMenu createGame(ActionHub hub){
		JMenu spel = create(LocaleConstant.GAME);
		JMenuItem pause = new JCheckBoxMenuItem(hub.getAction(ActionKey.PAUSE));
		
			
		JMenuItem restart = new JMenuItem(hub.getAction(ActionKey.RESTART));
		spel.add(pause);
		spel.add(restart);
		return spel;
	}
	
	private JMenu createFile(final ActionHub hub){
		JMenu bestand = create(LocaleConstant.FILE);
		
		JMenuItem open = new JMenuItem(hub.getAction(ActionKey.OPEN_FILE));
		
		JMenuItem loadSettings = new JMenuItem(hub.getAction(ActionKey.LOAD_SETTINGS));
		JMenuItem saveSettings = new JMenuItem(hub.getAction(ActionKey.SAVE_SETTINGS));
		JMenuItem resetSettings = new JMenuItem(hub.getAction(ActionKey.RESET_SETTINGS));
		
		JMenuItem quit = new JMenuItem(hub.getAction(ActionKey.QUIT));
		JMenuItem about = new JMenuItem(new AutoAction(LocaleConstant.ABOUT_MENU_ITEM, tracker, true) {
			private static final long serialVersionUID = -3119976826714431672L;
			@Override
			public void actionPerformed(ActionEvent arg0) {
				hub.getAboutDialog().setVisible(true);
			}
		});
		bestand.add(open);
		bestand.addSeparator();
		bestand.add(saveSettings);
		bestand.add(loadSettings);
		bestand.add(resetSettings);
		bestand.addSeparator();
		bestand.add(about);
		bestand.add(quit);
		return bestand;
	}
	
	private JMenu create(LocaleConstant name) {
		String nameS = tracker.getU(name);
		JMenu jMenu = new JMenu(nameS);
		jMenu.setMnemonic(nameS.charAt(0));
		return jMenu;
	}
}
