package bozels.physicsModel.shapes.bozelImageShapes;

import java.awt.Graphics2D;

import org.jbox2d.dynamics.Body;

import bozels.physicsModel.shapes.Circle;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class SplitBozelShape extends Circle{

	private static final String IMAGE_NAME = "BlueBird.png";
	private final ImageDrawingShape drawer;

	public SplitBozelShape() {
		super(1);
		drawer = new ImageDrawingShape(IMAGE_NAME, -13, -9, 0.07);
	}
	
	@Override
	public void drawShape(Graphics2D g2d, Body b, int scale, boolean useImage) {
		if(!useImage || !drawer.paintOn(g2d, (int) (b.getPosition().x*scale), (int) (b.getPosition().y*scale))){
			super.drawShape(g2d, b, scale, false);
		}
	}

}
