package bozels.visualisatie.controllers;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

import bozels.visualisatie.painterSettingsModel.PainterSettingsModel;


/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class ScreenSizeUpdater {

	private final PainterSettingsModel model;
	private final JPanel source;
	
	public ScreenSizeUpdater(JPanel source, PainterSettingsModel model) {
		this.model = model;
		this.source = source;
		source.addComponentListener(new Updater());
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
	private class Updater implements ComponentListener {
		@Override
		public void componentHidden(ComponentEvent arg0) {
		}

		@Override
		public void componentMoved(ComponentEvent arg0) {
		}

		@Override
		public void componentResized(ComponentEvent arg0) {
			model.setHeight(source.getHeight());
			model.setWidth(source.getWidth());
		}

		@Override
		public void componentShown(ComponentEvent arg0) {
		}
	}
}
