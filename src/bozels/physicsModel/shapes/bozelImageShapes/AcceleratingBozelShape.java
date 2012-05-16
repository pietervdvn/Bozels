package bozels.physicsModel.shapes.bozelImageShapes;

import java.awt.Graphics2D;

import org.jbox2d.dynamics.Body;

import bozels.physicsModel.shapes.Box;

/**
  * Bozels
  * 
  * Door:
  * Pieter Vander Vennet
  * 1ste Bachelor Informatica
  * Universiteit Gent
  * 
  */
public class AcceleratingBozelShape extends Box{

	private static final String IMAGE_NAME = "YellowBird.png";
	private final ImageDrawingShape drawer;

	public AcceleratingBozelShape() {
		super(2, 1);
		drawer = new ImageDrawingShape(IMAGE_NAME, -15, -5, 0.45);
	}
	
	@Override
	public void drawShape(Graphics2D g2d, Body b, int scale, boolean useImage) {
		if(!useImage || !drawer.paintOn(g2d, (int) (b.getPosition().x*scale), (int) (b.getPosition().y*scale))){
			super.drawShape(g2d, b, scale, false);
		}
	}

}
