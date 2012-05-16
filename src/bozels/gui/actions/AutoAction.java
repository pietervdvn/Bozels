package bozels.gui.actions;


import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import bozels.gui.resourceModel.ResourceTracker;
import bozels.gui.resourceModel.ResourceTrackerListener;
import bozels.gui.resourceModel.localeConstant.LocaleConstant;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public abstract class AutoAction extends AbstractAction implements ResourceTrackerListener{
	private static final long serialVersionUID = 1L;

	private final LocaleConstant name;
	private final boolean useDots;
	private final boolean useMnemomic;
	
	public AutoAction(LocaleConstant name, ResourceTracker tracker, boolean useDots, boolean useMnemomic) {
		tracker.addListener(this);
		this.name = name;
		this.useDots = useDots;
		this.useMnemomic = useMnemomic;
		onLocaleChanged(tracker);
	}
		
	public AutoAction(LocaleConstant name, ResourceTracker tracker, boolean useDots) {
		this(name, tracker, useDots, false);
	}
	
	public AutoAction(LocaleConstant name, ResourceTracker tracker) {
		this(name, tracker, false, false);
	}
	
	
	@Override
	public void onLocaleChanged(ResourceTracker source) {
		if(name != null){
			String text = source.getU(name) + getDots(source);
			putValue(NAME, text);
			char mnemomic = text.charAt(0);
			if(useMnemomic){
				putValue(MNEMONIC_KEY, KeyEvent.getExtendedKeyCodeForChar(mnemomic));
			}
		}
	}
	
	private String getDots(ResourceTracker tracker){
		return useDots ? tracker.get(LocaleConstant.DOTS) : "";
	}

}
