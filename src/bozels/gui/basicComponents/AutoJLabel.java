package bozels.gui.basicComponents;


import javax.swing.JLabel;

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
public class AutoJLabel extends JLabel implements ResourceTrackerListener{
	private static final long serialVersionUID = -8430174910120914248L;

	private final LocaleConstant name;
	private final String endSeq;
	
	public AutoJLabel(ResourceTracker tracker, LocaleConstant name){
		this(tracker, name, ":");
	}
	
	public AutoJLabel(ResourceTracker tracker, LocaleConstant name, String endSeq) {
		this.endSeq = endSeq;
		this.setText(tracker.getU(name)+endSeq);
		this.name = name;
		tracker.addListener(this);
	}
	
	@Override
	public void onLocaleChanged(ResourceTracker source) {
		this.setText(source.getU(name)+endSeq);
	}

}
