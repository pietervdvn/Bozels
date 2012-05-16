package bozels.physicsModel.shapes.bozelImageShapes;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;

import bozels.gui.resourceModel.ResourceTracker;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class ImageDrawingShape {
	
	private final BufferedImage image;
	
	private final int xTrans;
	private final int yTrans;
	
	public ImageDrawingShape(String imageName, int xTrans, int yTrans, double scale) {
		this.xTrans = xTrans;
		this.yTrans = yTrans;
		
		URL image = this.getClass().getResource("/"+ResourceTracker.RESOURCES_BASE+ResourceTracker.IMAGE_BASE+imageName);
		if(image == null){
			this.image = null;
		}else{
			ImageIcon icon = new ImageIcon(image);
			this.image = new BufferedImage(
					(int) (icon.getIconWidth()),
					(int) (icon.getIconHeight()), BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g = (Graphics2D) this.image.getGraphics();
			icon = new ImageIcon(
				icon.getImage().getScaledInstance((int) (icon.getIconHeight()*scale), -1, java.awt.Image.SCALE_SMOOTH)
				);
			g.scale(1,-1);
			icon.paintIcon(null, g, 0, -icon.getIconHeight());
			g.scale(1,-1);
		}
	}
	
	public boolean paintOn(Graphics2D g, int x, int y){
		if(image == null){
			return false;
		}
		g.drawImage(image, x+xTrans, y+yTrans, null);
		return true;
	}
	
	
}
