package bozels.physicsModel.shapes.bozelImageShapes;

import java.awt.Graphics2D;

import org.jbox2d.dynamics.Body;

import bozels.physicsModel.shapes.Square;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class LittlePigShape extends Square{

	private static final String IMAGE_NAME = "Pig.gif";
	private final ImageDrawingShape drawer;

	public LittlePigShape() {
		super(2.5);
		drawer = new ImageDrawingShape(IMAGE_NAME, -19, -18, 0.08);
	}
	
	@Override
	public void drawShape(Graphics2D g2d, Body b, int scale, boolean useImage) {
		if(!useImage || !drawer.paintOn(g2d, (int) (b.getPosition().x*scale), (int) (b.getPosition().y*scale))){
			super.drawShape(g2d, b, scale, false);
		}
	}

}
