package bozels.gui.basicComponents;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Icon;

import bozels.visualisatie.gameColorModel.GameColorModel;


/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class ColorIcon implements Icon{
	
	private final Dimension dim;
	private final int colorKey;
	private final GameColorModel cm;
	
	public ColorIcon(Dimension dim, int key, GameColorModel cm) {
		this.dim = dim;
		this.colorKey = key;
		this.cm = cm;
	}

	@Override
	public int getIconHeight() {
		return (int) dim.getHeight();
	}

	@Override
	public int getIconWidth() {
		return (int) dim.getWidth();
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.setColor(cm.getColor(colorKey));
		g.fillRect(x, y, getIconWidth(), getIconHeight());
	}
}
