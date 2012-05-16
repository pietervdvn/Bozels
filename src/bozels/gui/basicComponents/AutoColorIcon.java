package bozels.gui.basicComponents;

import java.awt.Color;
import java.awt.Component;
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
public class AutoColorIcon implements Icon{
	
	private final int size;
	private final int colorKey;
	private final GameColorModel cm;
	
	public AutoColorIcon(int size, int key, GameColorModel cm) {
		this.size = size;
		this.colorKey = key;
		this.cm = cm;
	}

	@Override
	public int getIconHeight() {
		return size;
	}

	@Override
	public int getIconWidth() {
		return size;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		g.setColor(cm.getColor(colorKey));
		
		g.fillPolygon(new int[]{x, x, x+getIconWidth()},
						new int[]{y, y+getIconHeight(), y}, 3);
		
		g.setColor(Color.WHITE);
		g.fillPolygon(new int[]{x, x+getIconWidth(), x+getIconWidth()},
				new int[]{y+getIconHeight(), y+getIconHeight(), y}, 3);

		g.setColor(cm.getSleepingColor(colorKey));
		g.fillPolygon(new int[]{x, x+getIconWidth(), x+getIconWidth()},
				new int[]{y+getIconHeight(), y+getIconHeight(), y}, 3);


		
		g.setColor(c.getForeground());
		int h = size;
		g.drawRect(x, y, h-1, h-1);
	}

}
