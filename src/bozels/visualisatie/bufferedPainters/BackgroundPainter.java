package bozels.visualisatie.bufferedPainters;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import bozels.gui.resourceModel.ResourceTracker;
import bozels.visualisatie.gameColorModel.GameColorModel;
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
public class BackgroundPainter extends BufferedPainter {

	private final GameColorModel gcm;
	private final ImageIcon imageUp;
	private final ImageIcon imageDown;
	
	public BackgroundPainter(ResourceTracker tracker, PainterSettingsModel psm, GameColorModel gcm) {
		super(psm);
		this.gcm = gcm;
		imageUp = tracker.getIcon(ResourceTracker.BACKGROUND_UPPER);
		imageDown = tracker.getIcon(ResourceTracker.BACKGROUND_DOWN);
	}
	
	@Override
	public void paintContents(Graphics2D g) {
		PainterSettingsModel psm = getPainterSettingsModel();
		if(psm.usesBackground()){
			g.setColor(new Color(183,234,240)); //background color
			g.fillRect(0, 0, psm.getWidth(), psm.getHeight());

			double xScale = (double) psm.getWidth()/imageUp.getIconWidth();
			g.scale(xScale, -1);
			imageUp.paintIcon(null, g, 0,psm.getYTranslation()-imageUp.getIconHeight());
			
			imageDown.paintIcon(null, g, 0, psm.getYTranslation());
			
			g.scale(1/xScale, -1);
		}else{
			g.setColor(gcm.getContrastColorFor(psm.getBackgroundColor()));
			g.fillRect(0, 0, psm.getWidth(), -psm.getYTranslation());
		}
	}


}
