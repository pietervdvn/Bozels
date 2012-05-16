package bozels.physicsModel.shapes.bozelImageShapes;

import java.awt.Color;
import java.awt.Graphics2D;

import org.jbox2d.dynamics.Body;

import bozels.physicsModel.shapes.Triangle;

/**
 * Bozels
 * 
 * Door: Pieter Vander Vennet 1ste Bachelor Informatica Universiteit Gent
 * 
 */
public class NormalBozelShape extends Triangle {

	private static final String IMAGE_NAME = "RedBird.png";
	private final ImageDrawingShape drawer;

	public NormalBozelShape() {
		super(2, Math.sqrt(3), 1);
		drawer = new ImageDrawingShape(IMAGE_NAME, -15, -10, 0.2);
	}

	@Override
	public void drawShape(Graphics2D g2d, Body b, int scale, boolean useImage) {
		if (!useImage
				|| !drawer.paintOn(g2d, (int) (b.getPosition().x * scale),
						(int) (b.getPosition().y * scale))) {
			super.drawTriangle(g2d, b.getPosition().x * scale, b.getPosition().y
					* scale, scale);

			g2d.setColor(Color.white);
			super.drawTriangle(g2d, b.getPosition().x * scale, b.getPosition().y
					* scale - scale / 10, 3 * scale / 5);
		}
		
	}
}
