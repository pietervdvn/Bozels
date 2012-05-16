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
public class ExplodingBozelShape extends Square{

	private static final String IMAGE_NAME = "BlackBird.png";
	private final ImageDrawingShape drawer;

	public ExplodingBozelShape() {
		super(2);
		drawer = new ImageDrawingShape(IMAGE_NAME, -15, -13, 0.3);
	}
	
	@Override
	public void drawShape(Graphics2D g2d, Body b, int scale, boolean useImage) {
		if(!useImage || !drawer.paintOn(g2d, (int) (b.getPosition().x*scale), (int) (b.getPosition().y*scale))){
			super.drawShape(g2d, b, scale, false);
		}
		
	}

}
